package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.DriftBottomNav
import com.example.ui.components.DriftTopBar
import com.example.ui.screens.CartScreen
import com.example.ui.screens.CheckoutDialog
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LookbookDetailDialog
import com.example.ui.screens.LookbookScreen
import com.example.ui.screens.OrderSuccessDialog
import com.example.ui.screens.ProductDetailSheet
import com.example.ui.screens.ShopScreen
import com.example.ui.screens.VipClubScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.DriftTab
import com.example.ui.viewmodel.DriftViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                DriftApp()
            }
        }
    }
}

@Composable
fun DriftApp(viewModel: DriftViewModel = viewModel()) {
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()
    val cartCount by viewModel.cartCount.collectAsStateWithLifecycle()
    val wishlistSet by viewModel.wishlistSet.collectAsStateWithLifecycle()
    val orders by viewModel.orders.collectAsStateWithLifecycle()
    val filteredProducts by viewModel.filteredProducts.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val selectedSort by viewModel.selectedSort.collectAsStateWithLifecycle()
    val selectedProduct by viewModel.selectedProduct.collectAsStateWithLifecycle()
    val selectedLookbook by viewModel.selectedLookbook.collectAsStateWithLifecycle()
    val isCheckoutOpen by viewModel.isCheckoutOpen.collectAsStateWithLifecycle()
    val orderConfirmation by viewModel.orderConfirmation.collectAsStateWithLifecycle()
    val appliedPromoCode by viewModel.appliedPromoCode.collectAsStateWithLifecycle()
    val discountPercent by viewModel.discountPercent.collectAsStateWithLifecycle()
    val countdownText by viewModel.countdownText.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    // Listen for feedback messages
    LaunchedEffect(Unit) {
        viewModel.userFeedback.collectLatest { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    // BackHandler support
    BackHandler(enabled = currentTab != DriftTab.HOME || selectedProduct != null || selectedLookbook != null || isCheckoutOpen) {
        when {
            selectedProduct != null -> viewModel.closeProductDetail()
            selectedLookbook != null -> viewModel.closeLookbook()
            isCheckoutOpen -> viewModel.closeCheckout()
            currentTab != DriftTab.HOME -> viewModel.selectTab(DriftTab.HOME)
        }
    }

    Scaffold(
        topBar = {
            DriftTopBar(
                cartCount = cartCount,
                searchQuery = searchQuery,
                onSearchChange = { viewModel.setSearchQuery(it) },
                onCartClick = { viewModel.selectTab(DriftTab.BAG) },
                onLogoClick = {
                    viewModel.setSearchQuery("")
                    viewModel.selectCategory("All")
                    viewModel.selectTab(DriftTab.HOME)
                },
                onShopClick = {
                    if (currentTab != DriftTab.SHOP) {
                        viewModel.selectTab(DriftTab.SHOP)
                    }
                }
            )
        },
        bottomBar = {
            DriftBottomNav(
                currentTab = currentTab,
                cartCount = cartCount,
                onTabSelected = { tab -> viewModel.selectTab(tab) }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = currentTab,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "TabTransition"
            ) { tab ->
                when (tab) {
                    DriftTab.HOME -> {
                        HomeScreen(
                            countdownText = countdownText,
                            wishlistSet = wishlistSet,
                            onExploreClick = { viewModel.selectTab(DriftTab.SHOP) },
                            onWatchLookbookClick = { viewModel.selectTab(DriftTab.LOOKBOOK) },
                            onProductClick = { viewModel.openProductDetail(it) },
                            onWishlistToggle = { viewModel.toggleWishlist(it) },
                            onAddToCart = {
                                viewModel.addToCart(it, it.colors.first().name, it.sizes.first())
                            },
                            onPromoCopy = { code ->
                                viewModel.applyPromoCode(code)
                                viewModel.selectTab(DriftTab.BAG)
                            }
                        )
                    }

                    DriftTab.SHOP -> {
                        ShopScreen(
                            products = filteredProducts,
                            selectedCategory = selectedCategory,
                            selectedSort = selectedSort,
                            searchQuery = searchQuery,
                            wishlistSet = wishlistSet,
                            onCategorySelect = { viewModel.selectCategory(it) },
                            onSortSelect = { viewModel.selectSort(it) },
                            onProductClick = { viewModel.openProductDetail(it) },
                            onWishlistToggle = { viewModel.toggleWishlist(it) },
                            onAddToCart = {
                                viewModel.addToCart(it, it.colors.first().name, it.sizes.first())
                            }
                        )
                    }

                    DriftTab.LOOKBOOK -> {
                        LookbookScreen(
                            onLookbookClick = { viewModel.openLookbook(it) },
                            onProductClick = { viewModel.openProductDetail(it) }
                        )
                    }

                    DriftTab.BAG -> {
                        CartScreen(
                            cartItems = cartItems,
                            appliedPromoCode = appliedPromoCode,
                            discountPercent = discountPercent,
                            onQuantityChange = { id, qty -> viewModel.updateCartQuantity(id, qty) },
                            onRemoveItem = { id -> viewModel.removeCartItem(id) },
                            onApplyPromo = { code -> viewModel.applyPromoCode(code) },
                            onRemovePromo = { viewModel.removePromoCode() },
                            onCheckoutClick = { viewModel.openCheckout() },
                            onExploreShop = { viewModel.selectTab(DriftTab.SHOP) }
                        )
                    }

                    DriftTab.VIP -> {
                        VipClubScreen(
                            orders = orders,
                            onClaimVipCode = { code ->
                                viewModel.applyPromoCode(code)
                                viewModel.selectTab(DriftTab.BAG)
                            },
                            onShopNow = { viewModel.selectTab(DriftTab.SHOP) }
                        )
                    }
                }
            }
        }

        // Product Detail Bottom Sheet
        if (selectedProduct != null) {
            ProductDetailSheet(
                product = selectedProduct!!,
                isWishlisted = wishlistSet.contains(selectedProduct!!.id),
                onDismiss = { viewModel.closeProductDetail() },
                onAddToCart = { prod, col, size, qty ->
                    viewModel.addToCart(prod, col, size, qty)
                },
                onWishlistToggle = { viewModel.toggleWishlist(it) }
            )
        }

        // Checkout Modal Dialog
        if (isCheckoutOpen) {
            val sub = cartItems.sumOf { it.price * it.quantity }
            val disc = sub * discountPercent
            val shipping = if (sub > 50.0 || cartItems.isEmpty()) 0.0 else 12.0
            val total = sub - disc + shipping

            CheckoutDialog(
                cartItems = cartItems,
                totalAmount = total,
                onDismiss = { viewModel.closeCheckout() },
                onConfirmOrder = { address ->
                    viewModel.placeOrder(address)
                }
            )
        }

        // Lookbook Detail Dialog
        if (selectedLookbook != null) {
            LookbookDetailDialog(
                lookbook = selectedLookbook!!,
                onDismiss = { viewModel.closeLookbook() },
                onProductClick = { prod ->
                    viewModel.closeLookbook()
                    viewModel.openProductDetail(prod)
                },
                onAddToCart = { prod ->
                    viewModel.addToCart(prod, prod.colors.first().name, prod.sizes.first())
                }
            )
        }

        // Order Success Confirmation Dialog
        if (orderConfirmation != null) {
            OrderSuccessDialog(
                order = orderConfirmation!!,
                onDismiss = { viewModel.dismissOrderConfirmation() },
                onViewOrders = {
                    viewModel.dismissOrderConfirmation()
                    viewModel.selectTab(DriftTab.VIP)
                }
            )
        }
    }
}
