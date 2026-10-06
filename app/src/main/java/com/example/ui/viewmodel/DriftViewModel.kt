package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ProductCatalog
import com.example.data.local.DriftDatabase
import com.example.data.model.CartItemEntity
import com.example.data.model.LookbookItem
import com.example.data.model.OrderEntity
import com.example.data.model.Product
import com.example.data.repository.DriftRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class DriftTab(val title: String) {
    HOME("Home"),
    SHOP("Shop"),
    LOOKBOOK("Lookbook"),
    BAG("Bag"),
    VIP("VIP Club")
}

class DriftViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DriftRepository

    init {
        val database = DriftDatabase.getDatabase(application)
        repository = DriftRepository(database.driftDao())
    }

    // Active Navigation
    private val _currentTab = MutableStateFlow(DriftTab.HOME)
    val currentTab: StateFlow<DriftTab> = _currentTab.asStateFlow()

    // Modals / Details
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

    private val _selectedLookbook = MutableStateFlow<LookbookItem?>(null)
    val selectedLookbook: StateFlow<LookbookItem?> = _selectedLookbook.asStateFlow()

    private val _isCheckoutOpen = MutableStateFlow(false)
    val isCheckoutOpen: StateFlow<Boolean> = _isCheckoutOpen.asStateFlow()

    private val _orderConfirmation = MutableStateFlow<OrderEntity?>(null)
    val orderConfirmation: StateFlow<OrderEntity?> = _orderConfirmation.asStateFlow()

    // Filter & Search
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _selectedSort = MutableStateFlow("Featured")
    val selectedSort: StateFlow<String> = _selectedSort.asStateFlow()

    // Notification toast events
    private val _userFeedback = MutableSharedFlow<String>()
    val userFeedback: SharedFlow<String> = _userFeedback.asSharedFlow()

    // Promo Code
    private val _appliedPromoCode = MutableStateFlow<String?>(null)
    val appliedPromoCode: StateFlow<String?> = _appliedPromoCode.asStateFlow()

    private val _discountPercent = MutableStateFlow(0.0)
    val discountPercent: StateFlow<Double> = _discountPercent.asStateFlow()

    // Drop Countdown (Simulated live ticker)
    private val _countdownText = MutableStateFlow("04:18:22")
    val countdownText: StateFlow<String> = _countdownText.asStateFlow()

    // Database reactive streams
    val cartItems: StateFlow<List<CartItemEntity>> = repository.cartItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val wishlistSet: StateFlow<Set<String>> = repository.wishlistItems
        .map { list -> list.map { it.productId }.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val orders: StateFlow<List<OrderEntity>> = repository.orders
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val cartCount: StateFlow<Int> = cartItems
        .map { items -> items.sumOf { it.quantity } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val subtotal: StateFlow<Double> = cartItems
        .map { items -> items.sumOf { it.price * it.quantity } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    // Filtered Products
    val filteredProducts: StateFlow<List<Product>> = combine(
        _searchQuery,
        _selectedCategory,
        _selectedSort
    ) { query, category, sort ->
        var list = ProductCatalog.products

        if (category != "All") {
            list = list.filter { it.category.equals(category, ignoreCase = true) }
        }

        if (query.isNotBlank()) {
            val q = query.trim().lowercase()
            list = list.filter {
                it.name.lowercase().contains(q) ||
                it.category.lowercase().contains(q) ||
                it.description.lowercase().contains(q) ||
                (it.badge?.lowercase()?.contains(q) == true)
            }
        }

        when (sort) {
            "Price: Low to High" -> list.sortedBy { it.price }
            "Price: High to Low" -> list.sortedByDescending { it.price }
            "Top Rated" -> list.sortedByDescending { it.rating }
            else -> list // Featured order
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductCatalog.products)

    init {
        // Start countdown ticker
        startCountdownTicker()
    }

    private fun startCountdownTicker() {
        viewModelScope.launch {
            var secondsRemaining = 4 * 3600 + 18 * 60 + 22
            while (true) {
                delay(1000)
                if (secondsRemaining > 0) secondsRemaining--
                val hours = secondsRemaining / 3600
                val minutes = (secondsRemaining % 3600) / 60
                val seconds = secondsRemaining % 60
                _countdownText.value = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            }
        }
    }

    fun selectTab(tab: DriftTab) {
        _currentTab.value = tab
    }

    fun openProductDetail(product: Product) {
        _selectedProduct.value = product
    }

    fun closeProductDetail() {
        _selectedProduct.value = null
    }

    fun openLookbook(lookbook: LookbookItem) {
        _selectedLookbook.value = lookbook
    }

    fun closeLookbook() {
        _selectedLookbook.value = null
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun selectSort(sort: String) {
        _selectedSort.value = sort
    }

    fun toggleWishlist(product: Product) {
        viewModelScope.launch {
            val isWish = wishlistSet.value.contains(product.id)
            repository.toggleWishlist(product.id, isWish)
            val msg = if (isWish) "Removed from Wishlist" else "Saved to Wishlist"
            _userFeedback.emit(msg)
        }
    }

    fun addToCart(product: Product, color: String, size: String, qty: Int = 1) {
        viewModelScope.launch {
            repository.addToCart(product, color, size, qty)
            _userFeedback.emit("Added ${product.name} to Bag ($size)")
        }
    }

    fun updateCartQuantity(id: Long, qty: Int) {
        viewModelScope.launch {
            repository.updateCartQuantity(id, qty)
        }
    }

    fun removeCartItem(id: Long) {
        viewModelScope.launch {
            repository.removeFromCart(id)
            _userFeedback.emit("Item removed from Bag")
        }
    }

    fun applyPromoCode(code: String) {
        viewModelScope.launch {
            val clean = code.trim().uppercase()
            if (clean == "DRIFT15") {
                _appliedPromoCode.value = "DRIFT15"
                _discountPercent.value = 0.15
                _userFeedback.emit("15% OFF applied with code DRIFT15!")
            } else if (clean == "VIP20") {
                _appliedPromoCode.value = "VIP20"
                _discountPercent.value = 0.20
                _userFeedback.emit("VIP 20% discount unlocked!")
            } else {
                _userFeedback.emit("Invalid promo code. Try DRIFT15")
            }
        }
    }

    fun removePromoCode() {
        _appliedPromoCode.value = null
        _discountPercent.value = 0.0
    }

    fun openCheckout() {
        _isCheckoutOpen.value = true
    }

    fun closeCheckout() {
        _isCheckoutOpen.value = false
    }

    fun placeOrder(deliveryAddress: String) {
        viewModelScope.launch {
            val items = cartItems.value
            if (items.isEmpty()) return@launch

            val sub = items.sumOf { it.price * it.quantity }
            val disc = sub * _discountPercent.value
            val total = sub - disc

            val order = repository.createOrder(items, total, deliveryAddress)
            _orderConfirmation.value = order
            _isCheckoutOpen.value = false
            _appliedPromoCode.value = null
            _discountPercent.value = 0.0
            _userFeedback.emit("Order #${order.orderId} Confirmed!")
        }
    }

    fun dismissOrderConfirmation() {
        _orderConfirmation.value = null
    }
}
