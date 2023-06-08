package org.jash.mvvmapplication.viewmodel

import androidx.lifecycle.ViewModel
import org.jash.mvvmapplication.model.User

class UserViewModel:ViewModel() {
    val user = User(username = "111", password = "111")
}