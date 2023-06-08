package org.jash.mvvmapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.jash.mvvmapplication.dao.CartDao
import org.jash.mvvmapplication.dao.CategoryDao
import org.jash.mvvmapplication.dao.ProductDao
import org.jash.mvvmapplication.dao.UserDao
import org.jash.mvvmapplication.model.Cart
import org.jash.mvvmapplication.model.Category
import org.jash.mvvmapplication.model.Product
import org.jash.mvvmapplication.model.User

@Database(entities = [User::class, Category::class, Product::class, Cart::class], version = 1)
@TypeConverters(ListConverter::class)
abstract class AppDatabase:RoomDatabase() {
    abstract fun getUserDao():UserDao
    abstract fun getCategoryDao():CategoryDao
    abstract fun getProductDao():ProductDao
    abstract fun getCartDao():CartDao
}