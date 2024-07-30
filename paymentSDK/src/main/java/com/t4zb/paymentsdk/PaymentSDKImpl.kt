package com.t4zb.paymentsdk

import com.t4zb.paymentsdk.util.BankFactory

class PaymentSDKImpl : PaymentSDK {
    private var selectedBank: Bank? = null

    private fun getBank(cardNo: String): Bank {
        return BankFactory.getBank(cardNo)
    }

    override fun startPayment(
        cardNo: String,
        expDate: String,
        cvv: String,
        amount: Double,
        callback: (Boolean) -> Unit
    ) {
        selectedBank = getBank(cardNo)
        selectedBank?.startPayment(cardNo, expDate, cvv, amount, callback)
    }

    override fun confirmPayment(opt: String, callback: (Boolean) -> Unit) {
        selectedBank?.confirmPayment(opt, callback) ?: callback(false)
    }
}