package com.t4zb.paymentsdk

interface Bank {
    fun startPayment(cardNo: String, expDate: String, cvv: String, amount: Double, callback: (Boolean) -> Unit)
    fun confirmPayment(opt: String, callback: (Boolean) -> Unit)
}