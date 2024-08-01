package com.t4zb.e_commerce.data.repository

import com.t4zb.e_commerce.data.remote.firebase.FirebaseDataSource
import com.t4zb.paymentsdk.PaymentSDK
import javax.inject.Inject


class PaymentRepository @Inject constructor(
    private val paymentSDK: PaymentSDK
) {

    fun startPayment(
        totalPrice: Double,
        cardNumber: String,
        expDate: String,
        cvv: String,
        callback: (Boolean) -> Unit
    ) {
        paymentSDK.startPayment(cardNumber, expDate, cvv, totalPrice, callback)
    }

    fun confirmPayment(opt: String, callback: (Boolean) -> Unit) {
        paymentSDK.confirmPayment(opt, callback)
    }
}