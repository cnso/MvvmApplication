package org.jash.mvvmapplication.viewmodel

import androidx.lifecycle.ViewModel
import org.jash.mvvmapplication.BR
import org.jash.mvvmapplication.R
import org.jash.mvvmapplication.model.Cart
import org.jash.mvvmapplication.model.CartList
import org.jash.mylibrary.adapter.CommonAdapter

class CartViewModel:ViewModel() {
    val cartList = CartList()
    val adapter = CommonAdapter(mapOf(Cart::class.java to (R.layout.cart_item to BR.cart)), cartList.data)
}