package org.jash.mvvmapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import org.jash.mvvmapplication.databinding.ActivityProductBinding
import org.jash.mvvmapplication.model.Category
import org.jash.mvvmapplication.model.Product
import org.jash.mvvmapplication.utils.service
import org.jash.mvvmapplication.viewmodel.ProductViewModel
import org.jash.mylibrary.activity.BaseActivity
import org.jash.mylibrary.annotations.BindingLayout
import org.jash.mylibrary.annotations.Subscribe
import org.jash.mylibrary.processor

class ProductActivity : BaseActivity() {
    @set:BindingLayout("activity_product")
    lateinit var binding:ActivityProductBinding
    lateinit var viewModel: ProductViewModel
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp by  viewModels<ProductViewModel>()
        viewModel = temp
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle = ActionBarDrawerToggle(this, binding.drawer, binding.toolbar, R.string.app_name, R.string.app_name)
        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()
        (binding.grid.layoutManager as GridLayoutManager).spanSizeLookup = object :SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when(position) {
                    0 -> 2
                    else -> 1
                }
            }
        }
        binding.adapter = viewModel.adapter
        binding.categoryAdapter = viewModel.categoryAdapter
        val a = service.getCategory(0).subscribe {
            if(it.code == 200) {
                if (it.data.isNotEmpty()) {
                    it.data[0].loadSubcategory()
                }
                it.data.forEach(processor::onNext)
            } else {
                processor.onNext(it.message)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId) {
            R.id.cart -> {
                startActivity(Intent(this, CartActivity::class.java))
                true
            }
            android.R.id.home -> toggle.onOptionsItemSelected(item)
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_menu, menu)
        return true
    }
    @Subscribe
    fun loadMessage(msg: String) {
        when(msg) {
            "clearSubcategory" -> viewModel.subcategoryAdapter.clear()
            "clearProduct" -> viewModel.adapter.removeIf{ it is Product }
            "closeDrawer" -> binding.drawer.closeDrawer(GravityCompat.START)
            else -> Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }
    @Subscribe
    fun loadProduct(product: Product) {
        viewModel.adapter += product
    }
    fun isMainCategory(category: Category) = category.parentId == 0
    fun isSubcategory(category: Category) = category.parentId != 0
    @Subscribe(filter = ["isMainCategory"])
    fun loadMainCategory(category: Category) {
        viewModel.categoryAdapter += category
    }
    @Subscribe(filter = ["isSubcategory"])
    fun loadSubcategory(category: Category) {
        viewModel.subcategoryAdapter += category
    }
}