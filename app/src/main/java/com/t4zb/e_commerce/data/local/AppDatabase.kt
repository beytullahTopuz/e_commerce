package com.t4zb.e_commerce.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.t4zb.e_commerce.data.local.converters.Converters
import com.t4zb.e_commerce.data.local.dao.BasketDao
import com.t4zb.e_commerce.data.model.Basket
import com.t4zb.e_commerce.data.model.ProductRoom

@Database(entities = [Basket::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun basketDao(): BasketDao
}