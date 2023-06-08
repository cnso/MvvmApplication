package org.jash.mvvmapplication.viewmodel

import androidx.lifecycle.ViewModel
import org.jash.mvvmapplication.adapter.ProductBannerAdapter
import org.jash.mvvmapplication.model.Product

class DetailViewModel:ViewModel() {
    var product:Product? = null
    var adapter:ProductBannerAdapter? = null
}