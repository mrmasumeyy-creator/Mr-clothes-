package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: String,
    val productName: String,
    val price: Double,
    val selectedColor: String,
    val selectedSize: String,
    val quantity: Int = 1,
    val imageUrl: String,
    val localDrawableRes: Int? = null,
    val timestamp: Long = System.currentTimeMillis()
)
