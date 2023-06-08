package org.jash.mvvmapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jash.mvvmapplication.utils.service
import org.jash.mylibrary.processor

@Entity
data class User(
    val address: String? = null,
    val admin: Boolean? = null,
    val birth: String? = null,
    val coinCount: Int? = null,
    val email: String? = null,
    val icon: String? = null,
    @PrimaryKey
    val id: Int? = null,
    val nickname: String? = null,
    var password: String? = null,
    val publicName: String? = null,
    val sex: String? = null,
    val token: String? = null,
    val type: Int? = null,
    var username: String? = null,
) {
    fun login() {
        service.login(this).subscribe {
            if (it.code == 200) {
                processor.onNext(it.data)
            } else {
                processor.onNext(it.message)
            }
        }
//        userDao.insert(user).subscribeOn(Schedulers.io()).subscribe({ println("保存完成")}, {it.printStackTrace()})
    }
}