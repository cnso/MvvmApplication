package org.jash.mvvmapplication.utils

import io.reactivex.rxjava3.core.Observable
import org.jash.mvvmapplication.model.Cart
import org.jash.mvvmapplication.model.Category
import org.jash.mvvmapplication.model.Product
import org.jash.mvvmapplication.model.Res
import org.jash.mvvmapplication.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GoodService {
    @GET("/goods/category")
    fun getCategory(@Query("parent_id") id:Int):Observable<Res<List<Category>>>
    @POST("/user/loginjson")
    fun login(@Body user: User):Observable<Res<User>>
    @GET("/goods/info")
    fun getProducts(
        @Query("category_id") id:Int,
        @Query("currentPage") currentPage:Int,
        @Query("pageSize") pageSize:Int
    ):Observable<Res<List<Product>>>
//    @GET("/goods/detail")
//    fun getDetail(@Query("goods_id") id:Int):Observable<Res<Product>>
    @GET("/goods/selectCar")
    fun getCarts():Observable<Res<List<Cart>>>
    @POST("/goods/addCar")
    fun addCart(@Body cart: Cart):Observable<Res<String>>
    @POST("/goods/deleteCar")
    fun delCart(@Body ids:Map<String, String>):Observable<Res<String>>
}