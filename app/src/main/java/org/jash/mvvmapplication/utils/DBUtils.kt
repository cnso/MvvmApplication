package org.jash.mvvmapplication.utils

import android.content.Context
import org.jash.mvvmapplication.App

val Context.database
    get() = (applicationContext as App).database