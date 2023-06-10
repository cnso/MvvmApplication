package org.jash.mvvmapplication

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.room.Room
import com.alipay.sdk.app.EnvUtils
import com.hyphenate.chat.ChatClient
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jash.mvvmapplication.database.AppDatabase
import org.jash.mvvmapplication.model.Cart
import org.jash.mvvmapplication.model.Category
import org.jash.mvvmapplication.model.Product
import org.jash.mvvmapplication.model.User
import org.jash.mylibrary.activity.SafeSubscribe
import org.jash.mylibrary.logd
import org.jash.mylibrary.processor

class App:Application() {
    lateinit var database:AppDatabase
    override fun onCreate() {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX)
        super.onCreate()
        database = Room.databaseBuilder(this, AppDatabase::class.java, "store").build()
        val safeSubscribe = SafeSubscribe(
            processor.observeOn(Schedulers.io())
                .ofType(User::class.java)
                .subscribe { database.getUserDao().insert(it) },
            processor.observeOn(Schedulers.io())
                .ofType(Category::class.java)
                .subscribe { database.getCategoryDao().insert(it) },
            processor.observeOn(Schedulers.io())
                .ofType(Product::class.java)
                .subscribe { database.getProductDao().insert(it) },
            processor.observeOn(Schedulers.io())
                .ofType(Cart::class.java)
                .subscribe { database.getCartDao().insert(it) },
        )
        ProcessLifecycleOwner.get().lifecycle.addObserver(safeSubscribe)
        val options = ChatClient.Options().setAppkey("1482230609107295#kefuchannelapp106981")
            .setTenantId("106981")
        val init = ChatClient.getInstance().init(this, options)
        logd("ChatClient: $init")
    }
}