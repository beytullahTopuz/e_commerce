package com.t4zb.paymentsdk

interface PaymentSDK {
    fun startPayment(cardNo: String, expDate: String, cvv: String, amount: Double, callback: (Boolean) -> Unit)
    fun confirmPayment(opt: String, callback: (Boolean) -> Unit)
}