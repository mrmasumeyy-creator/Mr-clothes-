package com.example.data

import com.example.R
import com.example.data.model.ColorOption
import com.example.data.model.LookbookItem
import com.example.data.model.Product

object ProductCatalog {

    val products: List<Product> = listOf(
        Product(
            id = "drift-01",
            name = "Oversized Overshed Jacket",
            price = 120.0,
            originalPrice = 145.0,
            category = "Outerwear",
            badge = "FEATURED",
            description = "Constructed from heavy technical twill with structured drop-shoulder architecture. Features sealed matte hardware, deep storm-pockets, and adjustable hem toggles for custom draping.",
            material = "70% Nylon, 30% Cotton Technical Weave",
            fit = "Signature Oversized Silhouette",
            rating = 4.9f,
            reviewCount = 284,
            imageUrl = "https://images.unsplash.com/photo-1515886657613-9f3515b0c78f?q=80&w=1000&auto=format&fit=crop",
            localDrawableRes = R.drawable.img_drift_hero,
            colors = listOf(
                ColorOption("Obsidian Black", 0xFF0D0D0E),
                ColorOption("Concrete Slate", 0xFF4A4B50),
                ColorOption("Army Olive", 0xFF353A2C)
            ),
            sizes = listOf("S", "M", "L", "XL", "XXL"),
            isFeatured = true
        ),
        Product(
            id = "drift-02",
            name = "Ink Linen Shirt",
            price = 74.0,
            originalPrice = 88.0,
            category = "Tops",
            badge = "BESTSELLER",
            description = "Raw garment-dyed deep ink black linen shirt with relaxed camp collar and subtle split side vents. Breathable yet substantial handfeel for year-round layering.",
            material = "100% European Washed Linen",
            fit = "Relaxed Boxy Fit",
            rating = 4.8f,
            reviewCount = 192,
            imageUrl = "https://images.unsplash.com/photo-1618354691373-d851c5c3a990?q=80&w=800&auto=format&fit=crop",
            colors = listOf(
                ColorOption("Ink Black", 0xFF141416),
                ColorOption("Raw Chalk", 0xFFE8E8E5),
                ColorOption("Deep Sage", 0xFF3D453D)
            ),
            sizes = listOf("S", "M", "L", "XL"),
            isFeatured = true
        ),
        Product(
            id = "drift-03",
            name = "Unbroken Stripe Tee",
            price = 64.0,
            category = "Tops",
            badge = "NEW",
            description = "320GSM ultra-heavyweight cotton knit with knit-in broken architectural micro-stripes. Pre-shrunk with a thick 1.25-inch ribbed mock collar that maintains shape indefinitely.",
            material = "100% Combed Heavy Cotton (320 GSM)",
            fit = "Wide Boxy Street Cut",
            rating = 4.9f,
            reviewCount = 98,
            imageUrl = "https://images.unsplash.com/photo-1576995853123-5a10305d93c0?q=80&w=800&auto=format&fit=crop",
            colors = listOf(
                ColorOption("Monochrome Stripe", 0xFF212124),
                ColorOption("Charcoal / Cream", 0xFF363638)
            ),
            sizes = listOf("S", "M", "L", "XL", "XXL"),
            isFeatured = true
        ),
        Product(
            id = "drift-04",
            name = "Midnight Cami Set",
            price = 118.0,
            originalPrice = 135.0,
            category = "Sets",
            badge = "LIMITED",
            description = "Two-piece fluid satin and modal streetwear set. Combines a structural architectural top with fluid wide-leg trouser pants for effortless evening street presence.",
            material = "High-Density Modal Blend with Matte Satin Sheen",
            fit = "Fluid Relaxed Drape",
            rating = 5.0f,
            reviewCount = 145,
            imageUrl = "https://images.unsplash.com/photo-1509631179647-0177331693ae?q=80&w=800&auto=format&fit=crop",
            colors = listOf(
                ColorOption("Midnight Obsidian", 0xFF0B0B0C),
                ColorOption("Chrome Slate", 0xFF4B4F55)
            ),
            sizes = listOf("XS", "S", "M", "L"),
            isFeatured = true
        ),
        Product(
            id = "drift-05",
            name = "Obsidian Cargo Pant",
            price = 98.0,
            originalPrice = 115.0,
            category = "Bottoms",
            badge = "BESTSELLER",
            description = "Ergonomic 8-pocket cargo trousers engineered with knee articulation darts, bungee ankle adjusters, and magnetic pocket closures. Weather-resistant durable weave.",
            material = "Ripstop Cotton-Polyester Blend",
            fit = "Relaxed Tapered Silhouette",
            rating = 4.9f,
            reviewCount = 312,
            imageUrl = "https://images.unsplash.com/photo-1552374196-1ab2a1c593e8?q=80&w=800&auto=format&fit=crop",
            colors = listOf(
                ColorOption("Matte Black", 0xFF0E0E10),
                ColorOption("Concrete Ash", 0xFF3E4044),
                ColorOption("Dark Khaki", 0xFF4A443A)
            ),
            sizes = listOf("S", "M", "L", "XL", "XXL"),
            isFeatured = false
        ),
        Product(
            id = "drift-06",
            name = "Cyber-Drift Heavy Hoodie",
            price = 135.0,
            category = "Outerwear",
            badge = "NEW DROP",
            description = "500GSM fleeceback french terry hoodie with double-layered crossover hood and zero drawstrings for clean brutalist aesthetic. Hand-distressed ribbed hem and cuffs.",
            material = "100% Heavy French Terry (500 GSM)",
            fit = "Extreme Drop-Shoulder Oversized",
            rating = 4.9f,
            reviewCount = 210,
            imageUrl = "https://images.unsplash.com/photo-1509967419530-da38b4704bc6?q=80&w=800&auto=format&fit=crop",
            colors = listOf(
                ColorOption("Washed Pitch", 0xFF19191C),
                ColorOption("Cement Heather", 0xFF71717A)
            ),
            sizes = listOf("S", "M", "L", "XL"),
            isFeatured = false
        ),
        Product(
            id = "drift-07",
            name = "Raw Acid-Wash Boxy Tee",
            price = 58.0,
            category = "Tops",
            badge = "POPULAR",
            description = "Custom mineral washed tee providing a one-of-a-kind vintage patina. Finished with high-density DRIFT micro-embroidery at the nape of the neck.",
            material = "100% Organic Heavyweight Cotton",
            fit = "Boxy Cropped Street Length",
            rating = 4.7f,
            reviewCount = 88,
            imageUrl = "https://images.unsplash.com/photo-1521572267360-ee0c2909d518?q=80&w=800&auto=format&fit=crop",
            colors = listOf(
                ColorOption("Acid Obsidian", 0xFF232328),
                ColorOption("Washed Asphalt", 0xFF3C3D42)
            ),
            sizes = listOf("S", "M", "L", "XL", "XXL"),
            isFeatured = false
        ),
        Product(
            id = "drift-08",
            name = "Modular Tactical Vest",
            price = 110.0,
            category = "Outerwear",
            badge = "LIMITED",
            description = "Multi-pocket technical utility vest with Fidlock-inspired quick-release side buckles and detachable modular chest pouch. Ideal for tactical layering.",
            material = "1000D Cordura Nylon",
            fit = "Structured Layering Fit",
            rating = 4.8f,
            reviewCount = 76,
            imageUrl = "https://images.unsplash.com/photo-1508296695146-257a814070b4?q=80&w=800&auto=format&fit=crop",
            colors = listOf(
                ColorOption("Tactical Black", 0xFF101012),
                ColorOption("Ranger Olive", 0xFF2D3328)
            ),
            sizes = listOf("S/M", "L/XL"),
            isFeatured = false
        ),
        Product(
            id = "drift-09",
            name = "Prism Tactical Crossbody",
            price = 68.0,
            category = "Accessories",
            badge = "ESSENTIAL",
            description = "Geometric waterproof crossbody pack with YKK Aquaguard zips, seatbelt webbing strap, and engraved DRIFT gunmetal buckle.",
            material = "Weatherproof Tarpaulin & Cordura",
            fit = "One Size (Adjustable Strap)",
            rating = 4.9f,
            reviewCount = 163,
            imageUrl = "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?q=80&w=800&auto=format&fit=crop",
            colors = listOf(
                ColorOption("Obsidian", 0xFF111113),
                ColorOption("Reflective Silver", 0xFF8E8E93)
            ),
            sizes = listOf("One Size"),
            isFeatured = false
        ),
        Product(
            id = "drift-10",
            name = "DRIFT Metal Tag Beanie",
            price = 38.0,
            category = "Accessories",
            badge = "RESTOCK",
            description = "Chunky ribbed 100% Merino wool fisherman beanie featuring a laser-engraved brushed stainless steel DRIFT logo ingot on the fold.",
            material = "100% Extra-fine Merino Wool",
            fit = "Snug Shallow Crown",
            rating = 4.8f,
            reviewCount = 205,
            imageUrl = "https://images.unsplash.com/photo-1576871337622-98d48d1cf531?q=80&w=800&auto=format&fit=crop",
            colors = listOf(
                ColorOption("Deep Black", 0xFF0D0D0E),
                ColorOption("Bone White", 0xFFF0F0EB)
            ),
            sizes = listOf("One Size"),
            isFeatured = false
        )
    )

    val lookbooks: List<LookbookItem> = listOf(
        LookbookItem(
            id = "lb-01",
            title = "Tokyo Noir 2026",
            subtitle = "Vol. 1: Midnight Echoes",
            season = "FW 2026 DROP 1",
            imageUrl = "https://images.unsplash.com/photo-1515886657613-9f3515b0c78f?q=80&w=1000&auto=format&fit=crop",
            localDrawableRes = R.drawable.img_drift_hero,
            featuredProducts = listOf("drift-01", "drift-05", "drift-09"),
            description = "Shot on location in Shibuya's concrete underpasses. Monochromatic layers, waterproof technical shells, and architectural proportions designed for neon-lit nightscapes."
        ),
        LookbookItem(
            id = "lb-02",
            title = "Concrete Mirage",
            subtitle = "Vol. 2: Architectural Form",
            season = "SS 2026 ARCHIVE",
            imageUrl = "https://images.unsplash.com/photo-1509631179647-0177331693ae?q=80&w=800&auto=format&fit=crop",
            localDrawableRes = R.drawable.img_drift_banner,
            featuredProducts = listOf("drift-02", "drift-03", "drift-04"),
            description = "Brutalist simplicity. Fluid linen textures contrasted against rigid geometry. The intersection of modern tailoring and raw street rebellion."
        ),
        LookbookItem(
            id = "lb-03",
            title = "Cyber-Drift Protocol",
            subtitle = "Vol. 3: Heavy Gauge",
            season = "SPECIAL LIMITED EDITION",
            imageUrl = "https://images.unsplash.com/photo-1509967419530-da38b4704bc6?q=80&w=800&auto=format&fit=crop",
            featuredProducts = listOf("drift-06", "drift-07", "drift-08", "drift-10"),
            description = "Heavyweight fleece and modular tactical utility. Engineered for unrestricted movement through the modern metropolis."
        )
    )

    fun getProductById(id: String): Product? = products.find { it.id == id }
}
