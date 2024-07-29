package com.t4zb.e_commerce.data.model

data class Product(
    var productId: String? = null,
    val productName: String? = null,
    val productDescription: String? = null,
    val productPrice: Double? = null,
    val productCategory: String? = null,
    val productStockCount: Int? = null,
    val productPictureList: List<String>? = null,
    val productOwner: String? = null,
    val productColor: String? = null,
    val productSize: String? = null,
    val productSizeList: List<String>? = null,
    val productLabel: String? = null,
    val star: Double? = null
)