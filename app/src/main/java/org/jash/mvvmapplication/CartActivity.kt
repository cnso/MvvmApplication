package org.jash.mvvmapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import org.jash.mvvmapplication.databinding.ActivityCartBinding
import org.jash.mvvmapplication.model.Cart
import org.jash.mvvmapplication.utils.service
import org.jash.mvvmapplication.viewmodel.CartViewModel
import org.jash.mylibrary.activity.BaseActivity
import org.jash.mylibrary.annotations.BindingLayout
import org.jash.mylibrary.annotations.Subscribe
import org.jash.mylibrary.processor

class CartActivity : BaseActivity() {
    @set:BindingLayout("activity_cart")
    lateinit var binding:ActivityCartBinding
    lateinit var viewModel: CartViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp by viewModels<CartViewModel>()
        viewModel = temp
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.cartList = viewModel.cartList
        binding.adapter = viewModel.adapter
        val d = service.getCarts().subscribe {
            it.data.forEach(processor::onNext)
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
    @Subscribe
    fun loadMessage(s:String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }
    @Subscribe
    fun doAction(pair: Pair<String, Int>) {
        when(pair.first) {
            "removeCart" -> viewModel.adapter.removeIf{it.id == pair.second}
        }

    }
    @Subscribe
    fun loadCart(cart: Cart) {
        cart.cartList = viewModel.cartList
        viewModel.adapter += cart
    }
}