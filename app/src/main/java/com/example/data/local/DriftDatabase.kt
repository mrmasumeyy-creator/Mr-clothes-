package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.CartItemEntity
import com.example.data.model.OrderEntity
import com.example.data.model.WishlistEntity

@Database(
    entities = [CartItemEntity::class, WishlistEntity::class, OrderEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DriftDatabase : RoomDatabase() {
    abstract fun driftDao(): DriftDao

    companion object {
        @Volatile
        private var INSTANCE: DriftDatabase? = null

        fun getDatabase(context: Context): DriftDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DriftDatabase::class.java,
                    "drift_streetwear.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
