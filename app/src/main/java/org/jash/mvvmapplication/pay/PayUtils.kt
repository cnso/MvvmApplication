package org.jash.mvvmapplication.pay

import android.R
import android.app.Activity
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.alipay.sdk.app.APayEntranceActivity
import com.alipay.sdk.app.EnvUtils
import com.alipay.sdk.app.PayTask
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jash.mvvmapplication.pay.util.OrderInfoUtil2_0
import org.jash.mylibrary.processor


/**
 * 用于支付宝支付业务的入参 app_id。
 */
const val APPID = "2021000121636591"

/**
 * 用于支付宝账户登录授权业务的入参 pid。
 */
const val PID = ""

/**
 * 用于支付宝账户登录授权业务的入参 target_id。
 */
const val TARGET_ID = ""

/**
 * pkcs8 格式的商户私钥。
 *
 * 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个，如果两个都设置了，本 Demo 将优先
 * 使用 RSA2_PRIVATE。RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议商户使用
 * RSA2_PRIVATE。
 *
 * 建议使用支付宝提供的公私钥生成工具生成和获取 RSA2_PRIVATE。
 * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
 */
const val RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCHLFzKdXPh6d4YfQUdyBYhhevW+PMoDnr+uY/snPZWTXmpF3wjAc9bni4RiTe/GisdFfdBAcqCWa5hffHK5JTRJAbdYI4Ca7BHphdFFcc8pzN75CGlO708XWkqRrtjuPTwW3DsI7u0Ue4Kjk2jHHx6EQ3zOHXR8P32UppcEOrcEFwmJCO9hUkqnEehvgp6FKLfdAH2eE4ZFwGKhEjOmfrF2r0MjDags8zxKf9LJmMJ7hXvsm9TyjcpdGj7fJz/3vsU7pzaPm8anyjjtOoO3veuacbik9PHXvztCHooAtI2LrIJQ7hF4W5gUtQHuvSFU7ob7H0c0a8eL87/EbYmlQkhAgMBAAECggEBAIUw8T2M/eJczrrSdXgi76uYigoIw490tTzJG0fKYfAn5vj2axsTbX6ZHkupKYTkBBdkzX0AhspqE6l8sAN7uCdbmGgJ++PmuIvg3Iz2KJtbJR+uanilpJEWecluaLaaSL+NWndTvmCvWh6MmXNfXkfpBhXiTDXRgGx2eCK/iKvM+n+e0RuJ/GnB7LP41L01LApxIyYkdoUeR1L23ppM0z/TBzTLkpbQHLQkKoRpV8ZTdWwvMvESB5ETP6g2+Oe1lvtIkk4yLlVhEJnATZIg458JXG2w5TsGfpQ+BDu4xiUtdtG88eeDKiMiurD21RCri//M0ZV2lph1UCvyAHwXUQECgYEA6pUJoZE9FCFZ76X4Ba7kH9qHUAQwA/R5L3S7/FEHDI3/7hfmrTRyWvIAzfyzakNUcXiZgVveYN+/kPa15LHmki287S1DB5wqT5ocWFQ+VKRFP7mzBkPvQZ5SdvmIQKXcSHPD1e1YXj4HmfezFpPRs7it+VLn3zWEst0SFXBdgccCgYEAk4PPV5IrMEOe3HatX6np0Kh0wplzj8AP8Zq8r181otBwuh6watcvvBCp9YWuOs6MNlqJM93Eyz0rAmhFMXWemWxFKyU51RHOAaYisFsLvvLgS9NSpYF8slJGyZzbex4y1zFUe/QbYzN+3eKsQFsNM6GPYaehExWHbbKbvbTandcCgYBrddaxotz+CcEvXTJfOBzc0rjF2QU96pJRQQKUtXGrOKmTInPTyIDSVKY1tptiBHH8gSig77rRWe5htALjPvPG5xjy9ZcNN2bSjGCLvhvHnnDynlj6d8h0oNhaFDlslA5zSkTeDRo8IeJNkAEESLb5w5I+8tGxmMyvO0J7e7v6JQKBgBkpk7WhVkKeVjvO9pXp2ttf0MLdAAxWO6H/vg3AFNsX6RKmZ3tiB19yDDu4ONcJInunB95UBtyQK4T2mk7sfEg5YrcQW4QPzG8Vkrc4YJGKxl3Ix0E6DDYyuKZtXjbrGMsCPCIEqqNKRmq83pr/rQnQli+XM2+Y7Yf1voEml1/zAoGAXgep0sVggICXj3FmH0HzGmLvst7EX5CS0Bq/Qz5S4mIzFDsyl5Xc/LBfGIqhlX+fOg5e3xjltAPAH/qc4a8ETfUKTx+eX3mjiIxY1L5gUbsA2tDb2qpukKh2DNx5mx5pzXtjDlDl8PhZzdkT15ykcacYEX3BSB2vt+eBctzpNpo="
const val RSA_PRIVATE = ""

/**
 * 支付宝支付业务示例
 */
fun payV2(activity: Activity) {
    if (TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE)) {
        processor.onNext("初始化错误")
        return
    }

    /*
		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
		 *
		 * orderInfo 的获取必须来自服务端；
		 */
    val rsa2 = RSA2_PRIVATE.isNotEmpty()
    val params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2)
    val orderParam = OrderInfoUtil2_0.buildOrderParam(params)
    val privateKey = if (rsa2) RSA2_PRIVATE else RSA_PRIVATE
    val sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2)
    val orderInfo = "$orderParam&$sign"
    val d = Observable.create {
        val alipay = PayTask(activity)
        val result = alipay.payV2(orderInfo, true)
        Log.d("TAG", "payV2: $result")
        it.onNext(PayResult(result))
    }.subscribeOn(Schedulers.io())
        .subscribe(processor::onNext)

}

