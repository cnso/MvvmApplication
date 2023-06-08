package org.jash.mvvmapplication.model

data class Res<T>(
    var code:Int,
    var message:String,
    var data:T
)
