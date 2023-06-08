package org.jash.mvvmapplication.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import io.reactivex.rxjava3.core.Maybe
import org.jash.mvvmapplication.model.Product

@Dao
interface ProductDao {
    @Query("select * from product")
    fun getAll():Maybe<List<Product>>
    @Query("select * from product where id = :id")
    fun getProductById(id:Int):Maybe<Product>
    @Upsert
    fun insert(product: Product)

}