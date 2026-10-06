package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val orderId: String,
    val timestamp: Long = System.currentTimeMillis(),
    val totalAmount: Double,
    val itemsCount: Int,
    val itemsSummary: String,
    val status: String, // "Confirmed", "In Transit", "Delivered"
    val deliveryAddress: String,
    val trackingCode: String
)
