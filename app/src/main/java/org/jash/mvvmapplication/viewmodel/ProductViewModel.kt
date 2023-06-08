package org.jash.mvvmapplication.viewmodel

import androidx.lifecycle.ViewModel
import org.jash.mvvmapplication.R
import org.jash.mvvmapplication.BR
import org.jash.mvvmapplication.model.Category
import org.jash.mvvmapplication.model.Product
import org.jash.mylibrary.adapter.CommonAdapter

class ProductViewModel:ViewModel() {
    val subcategoryAdapter = CommonAdapter(mapOf(Category::class.java to (R.layout.subcategory_item to BR.category)))
    val categoryAdapter = CommonAdapter(mapOf(Category::class.java to (R.layout.category_main to BR.category)))
    val adapter = CommonAdapter(mapOf(CommonAdapter::class.java to (R.layout.categroies_item to BR.adapter), Product::class.java to (R.layout.product_item to BR.product)), mutableListOf(subcategoryAdapter))

}