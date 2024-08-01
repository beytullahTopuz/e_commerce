package com.t4zb.e_commerce.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ProductRoom {
    @PrimaryKey(autoGenerate = true)
    var productIdInt: Int = 0
    var productId: String? = null
    var productName: String? = null
    var productDescription: String? = null
    var productPrice: Double? = null
    var productCategory: String? = null
    var productStockCount: Int? = null
    var productPicture: String? = null
    var productOwner: String? = null
    var productColor: String? = null
    var productSize: String? = null
    var productLabel: String? = null
    var star: Double? = null

    constructor()
    constructor(
        productIdInt: Int,
        productId: String?,
        productName: String?,
        productDescription: String?,
        productPrice: Double?,
        productCategory: String?,
        productStockCount: Int?,
        productPicture: String?,
        productOwner: String?,
        productColor: String?,
        productSize: String?,
        productLabel: String?,
        star: Double?
    ) {
        this.productIdInt = productIdInt
        this.productId = productId
        this.productName = productName
        this.productDescription = productDescription
        this.productPrice = productPrice
        this.productCategory = productCategory
        this.productStockCount = productStockCount
        this.productPicture = productPicture
        this.productOwner = productOwner
        this.productColor = productColor
        this.productSize = productSize
        this.productLabel = productLabel
        this.star = star
    }
}