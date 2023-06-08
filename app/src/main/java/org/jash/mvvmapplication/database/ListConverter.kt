package org.jash.mvvmapplication.database

import androidx.room.TypeConverter


class ListConverter() {
    @TypeConverter
    fun toBannerList(src:String) = src.split(",")
    @TypeConverter
    fun fromBannerList(src:List<String>) = src.joinToString(",")
}