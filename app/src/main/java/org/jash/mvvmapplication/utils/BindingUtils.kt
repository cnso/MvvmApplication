package org.jash.mvvmapplication.utils

import androidx.databinding.BindingAdapter
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerAdapter
@BindingAdapter("android:adapter")
fun setBannerAdapter(banner: Banner<*,BannerAdapter<*,*>>, adapter:BannerAdapter<*,*>?) {
    if (adapter != null && banner.getAdapter() != adapter) {
        banner.setAdapter(adapter)
    }
}