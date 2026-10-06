package com.example.data.model

import androidx.annotation.DrawableRes

data class ColorOption(
    val name: String,
    val hex: Long
)

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val originalPrice: Double? = null,
    val category: String, // "Outerwear", "Tops", "Bottoms", "Sets", "Accessories"
    val badge: String? = null, // "BESTSELLER", "NEW DROP", "LIMITED 100", "EXCLUSIVE"
    val description: String,
    val material: String,
    val fit: String,
    val rating: Float = 4.9f,
    val reviewCount: Int = 124,
    val imageUrl: String,
    @DrawableRes val localDrawableRes: Int? = null,
    val colors: List<ColorOption> = listOf(
        ColorOption("Obsidian Black", 0xFF121214),
        ColorOption("Phantom Slate", 0xFF3F3F46),
        ColorOption("Chalk White", 0xFFF4F4F5)
    ),
    val sizes: List<String> = listOf("S", "M", "L", "XL", "XXL"),
    val isFeatured: Boolean = false
)

data class LookbookItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val season: String,
    val imageUrl: String,
    @DrawableRes val localDrawableRes: Int? = null,
    val featuredProducts: List<String>, // Product IDs
    val description: String
)
