package org.jash.mvvmapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jash.mvvmapplication.utils.service
import org.jash.mylibrary.logd
import org.jash.mylibrary.processor
@Entity
data class Category(
    val categoryIcon: String?,
    val categoryName: String,
    @PrimaryKey
    val id: Int,
    val parentId: Int
) {
    fun loadSubcategory() {
        processor.onNext("closeDrawer")
        val d = service.getCategory(id).subscribe {
            if(it.code == 200) {
                processor.onNext("clearSubcategory")
                it.data.forEach(processor::onNext)
                if (it.data.isNotEmpty()) {
                    it.data[0].loadProduct()
                }
            } else {
                processor.onNext(it.message)
            }
        }
    }
    fun loadProduct() {
        val d = service.getProducts(id, 1 ,20).subscribe {
            if(it.code == 200) {
                processor.onNext("clearProduct")
                it.data.forEach(processor::onNext)
            } else {
                processor.onNext(it.message)
            }
        }
    }
}