package com.t4zb.e_commerce.data.repository

import com.t4zb.e_commerce.data.model.Product
import com.t4zb.e_commerce.data.remote.firebase.FirebaseDataSource
import javax.inject.Inject

class ProductRepo @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) {

    fun getProducts(onComplete: (List<Product>) -> Unit) {
        firebaseDataSource.getProducts(onComplete)
    }
}