package org.jash.mvvmapplication.model

import android.content.Context
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.jash.mvvmapplication.utils.retrofit
import org.jash.mvvmapplication.utils.service
import org.jash.mvvmapplication.BR
import org.jash.mylibrary.processor

@Entity
class Cart(
    val goodsDefaultIcon: String? = null,
    val goodsDefaultPrice: Double? = null,
    val goodsDesc: String? = null,
    val goodsId: Int,
    @PrimaryKey
    val id: Int? = null,
    val orderId: Int? = null,
    val userId: Int? = null,
    count:Int

) : BaseObservable() {
    @Ignore
    @get:Bindable
    var checked:Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.checked)
            cartList?.notifyPropertyChanged(BR.all)
            cartList?.notifyPropertyChanged(BR.sum)
        }
    @Ignore
    var cartList: CartList? = null
    @get:Bindable
    var count: Int = count
        set(value) {
            if (value >= 0){
                field = value
                notifyPropertyChanged(BR.count)
                if(checked)
                    cartList?.notifyPropertyChanged(BR.sum)
            }
        }
    fun addCount(num:Int) {
        count += num
    }
    fun delCart(context: Context):Boolean {

        MaterialAlertDialogBuilder(context)
            .setTitle("确认删除")
            .setMessage("是否要删除$goodsDesc?")
            .setPositiveButton("确定", {_,_ ->
               val d =  service.delCart(mapOf("ids" to id.toString())).subscribe {
                    if (it.code == 200) {
                        processor.onNext("removeCart" to id)
                    }
                    processor.onNext(it.message)
                }
            })
            .setNegativeButton("取消", null)
            .show()
        return true
    }

}