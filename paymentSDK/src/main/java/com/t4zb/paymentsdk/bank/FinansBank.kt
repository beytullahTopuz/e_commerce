package com.t4zb.paymentsdk.bank

import android.os.Handler
import android.os.Looper
import com.t4zb.paymentsdk.Bank

class FinansBank: Bank {

    override fun startPayment(cardNo: String, expDate: String, cvv: String, amount: Double, callback: (Boolean) -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            val success = cardNo.startsWith("3") && amount <= 1000
            callback(success)
        }, 2000)
    }

    override fun confirmPayment(opt: String, callback: (Boolean) -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            val success = opt == "123456"
            callback(success)
        }, 1000)
    }
}