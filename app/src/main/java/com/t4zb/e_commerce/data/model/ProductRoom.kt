package com.t4zb.e_commerce.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductRoom(
    @PrimaryKey(autoGenerate = true)
    var productIdInt: Int,
    var productId: String? = null,
    val productName: String? = null,
    val productDescription: String? = null,
    val productPrice: Double? = null,
    val productCategory: String? = null,
    val productStockCount: Int? = null,
    val productPicture: String? = null,
    val productOwner: String? = null,
    val productColor: String? = null,
    val productSize: String? = null,
    val productLabel: String? = null,
    val star: Double? = null
)