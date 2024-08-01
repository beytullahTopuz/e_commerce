package com.t4zb.e_commerce.data.repository

import com.t4zb.e_commerce.data.model.Order
import com.t4zb.e_commerce.data.remote.firebase.FirebaseDataSource
import javax.inject.Inject

class OrderRepo
@Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) {

    fun insertOrder(order: Order, callback: (Boolean, String?) -> Unit) {
        firebaseDataSource.insertOrder(order) { isSuccess, result ->
            if (isSuccess) {
                callback(true, result)
            } else {
                callback(false, result)
            }
        }
    }

    fun getOrderListByUserId(userId: String, callback: (Boolean, List<Order>?, String?) -> Unit) {
        firebaseDataSource.getOrderListByUserId(userId, callback)
    }
}
