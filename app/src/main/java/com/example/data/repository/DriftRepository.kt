package com.example.data.repository

import com.example.data.local.DriftDao
import com.example.data.model.CartItemEntity
import com.example.data.model.OrderEntity
import com.example.data.model.Product
import com.example.data.model.WishlistEntity
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class DriftRepository(private val driftDao: DriftDao) {

    val cartItems: Flow<List<CartItemEntity>> = driftDao.getAllCartItems()
    val wishlistItems: Flow<List<WishlistEntity>> = driftDao.getAllWishlistItems()
    val orders: Flow<List<OrderEntity>> = driftDao.getAllOrders()

    suspend fun addToCart(product: Product, selectedColor: String, selectedSize: String, quantity: Int = 1) {
        val existing = driftDao.findCartItem(product.id, selectedColor, selectedSize)
        if (existing != null) {
            driftDao.updateCartQuantity(existing.id, existing.quantity + quantity)
        } else {
            driftDao.insertCartItem(
                CartItemEntity(
                    productId = product.id,
                    productName = product.name,
                    price = product.price,
                    selectedColor = selectedColor,
                    selectedSize = selectedSize,
                    quantity = quantity,
                    imageUrl = product.imageUrl,
                    localDrawableRes = product.localDrawableRes
                )
            )
        }
    }

    suspend fun updateCartQuantity(id: Long, newQuantity: Int) {
        if (newQuantity <= 0) {
            driftDao.deleteCartItem(id)
        } else {
            driftDao.updateCartQuantity(id, newQuantity)
        }
    }

    suspend fun removeFromCart(id: Long) {
        driftDao.deleteCartItem(id)
    }

    suspend fun clearCart() {
        driftDao.clearCart()
    }

    suspend fun toggleWishlist(productId: String, isCurrentlyWishlisted: Boolean) {
        if (isCurrentlyWishlisted) {
            driftDao.removeWishlist(productId)
        } else {
            driftDao.insertWishlist(WishlistEntity(productId = productId))
        }
    }

    fun isWishlisted(productId: String): Flow<Boolean> {
        return driftDao.isWishlisted(productId)
    }

    suspend fun createOrder(
        cartItems: List<CartItemEntity>,
        totalAmount: Double,
        deliveryAddress: String
    ): OrderEntity {
        val randomSuffix = (1000..9999).random()
        val orderId = "DRFT-$randomSuffix"
        val trackingCode = "TRK-" + UUID.randomUUID().toString().take(8).uppercase()

        val summary = cartItems.joinToString(", ") { "${it.quantity}x ${it.productName} (${it.selectedSize})" }
        val order = OrderEntity(
            orderId = orderId,
            timestamp = System.currentTimeMillis(),
            totalAmount = totalAmount,
            itemsCount = cartItems.sumOf { it.quantity },
            itemsSummary = summary,
            status = "Confirmed",
            deliveryAddress = deliveryAddress,
            trackingCode = trackingCode
        )

        driftDao.insertOrder(order)
        driftDao.clearCart()
        return order
    }
}
