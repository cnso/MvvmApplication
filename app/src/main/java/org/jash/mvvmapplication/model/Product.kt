package org.jash.mvvmapplication.model

import android.content.Context
import android.content.Intent
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.jash.mvvmapplication.DetailActivity
import org.jash.mvvmapplication.utils.service
import org.jash.mylibrary.processor

@Entity
data class Product(
    @SerializedName("bannerList")
    val bannerList: List<String>,
    val categoryId: Int,
    val goodsAttribute: String?,
    val goodsBanner: String,
    val goodsCode: String,
    val goodsDefaultIcon: String,
    val goodsDefaultPrice: Float,
    val goodsDesc: String,
    val goodsDetailOne: String,
    val goodsDetailTwo: String,
    val goodsSalesCount: Int,
    val goodsStockCount: Int,
    @PrimaryKey
    val id: Int
) {
    fun displayDetail(context: Context) {
        Intent(context, DetailActivity::class.java).also {
            it.putExtra("id", id)
            context.startActivity(it)
        }
    }
    fun addToCart() {
        val d = service.addCart(Cart(goodsId = id, count = 1)).subscribe {
            processor.onNext(it.message)
        }
    }
}