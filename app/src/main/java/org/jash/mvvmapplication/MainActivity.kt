package org.jash.mvvmapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import org.jash.mvvmapplication.databinding.ActivityMainBinding
import org.jash.mvvmapplication.model.User
import org.jash.mvvmapplication.utils.loginUser
import org.jash.mvvmapplication.viewmodel.UserViewModel
import org.jash.mylibrary.activity.BaseActivity
import org.jash.mylibrary.annotations.BindingLayout
import org.jash.mylibrary.annotations.Subscribe

class MainActivity : BaseActivity() {
    @set:BindingLayout("activity_main")
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModels by viewModels<UserViewModel>()
        binding.user = viewModels.user
    }
    @Subscribe
    fun showMessage(msg:String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
    @Subscribe
    fun login(user:User) {
        loginUser = user
        startActivity(Intent(this, ProductActivity::class.java))
        finish()
    }
}