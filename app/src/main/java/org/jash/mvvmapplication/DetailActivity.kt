package org.jash.mvvmapplication

import android.database.DatabaseUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jash.mvvmapplication.adapter.ProductBannerAdapter
import org.jash.mvvmapplication.databinding.ActivityDetailBinding
import org.jash.mvvmapplication.databinding.ConfigItemBinding
import org.jash.mvvmapplication.model.Product
import org.jash.mvvmapplication.pay.PayResult
import org.jash.mvvmapplication.utils.database
import org.jash.mvvmapplication.viewmodel.DetailViewModel
import org.jash.mylibrary.activity.BaseActivity
import org.jash.mylibrary.annotations.BindingLayout
import org.jash.mylibrary.annotations.Subscribe
import org.jash.mylibrary.processor

class DetailActivity : BaseActivity() {
    @set:BindingLayout("activity_detail")
    lateinit var binding: ActivityDetailBinding
    lateinit var viewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getIntExtra("id", 0)
        val temp by viewModels<DetailViewModel>()
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel = temp
        if (viewModel.product == null) {
            val d = database.getProductDao().getProductById(id).subscribeOn(Schedulers.io())
                .subscribe { processor.onNext(it) }
        } else {
            processor.onNext(viewModel.product!!)
        }
    }
    @Subscribe
    fun loadPay(payResult: PayResult) {
        MaterialAlertDialogBuilder(this)
            .setTitle("支付结果")
            .setMessage("支付${if(payResult.resultStatus == "9000") "成功" else "失败"}: ${payResult.result}")
    }
    @Subscribe
    fun loadMessage(s:String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }
    @Subscribe
    fun displayProduct(product: Product) {
        viewModel.product = product
        viewModel.adapter = ProductBannerAdapter(product.bannerList)
        binding.product = viewModel.product
        binding.adapter = viewModel.adapter
        product.goodsAttribute?.also {
            val map:Map<String, List<String>> = Gson().fromJson<Any>(it, Map::class.java) as Map<String, List<String>>
            map.entries.forEach {
                val b = DataBindingUtil.inflate<ConfigItemBinding>(
                    layoutInflater,
                    R.layout.config_item,
                    binding.configLayout,
                    false
                )
                b.textInput.hint = it.key
                b.edit.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, it.value))
                binding.configLayout.addView(b.root)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}