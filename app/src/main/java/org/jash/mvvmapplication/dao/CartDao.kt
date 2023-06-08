package org.jash.mvvmapplication.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import io.reactivex.rxjava3.core.Maybe
import org.jash.mvvmapplication.model.Cart
import org.jash.mvvmapplication.model.Product

@Dao
interface CartDao {
    @Query("select * from cart")
    fun getAll():Maybe<List<Cart>>
    @Query("select * from cart where id = :id")
    fun getProductById(id:Int):Maybe<Cart>
    @Upsert
    fun insert(product: Cart)
}