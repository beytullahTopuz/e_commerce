package com.t4zb.e_commerce.data.model


class Order {
    var orderId: String? = null
    lateinit var orderUserId: String
    lateinit var orderCreateDate: String
    var basketProductList: List<ProductRoom> = listOf()

    constructor()

    constructor(
        orderId: String?,
        orderUserId: String,
        orderCreateDate: String,
        basketProductList: List<ProductRoom>
    ) {
        this.orderId = orderId
        this.orderUserId = orderUserId
        this.orderCreateDate = orderCreateDate
        this.basketProductList = basketProductList
    }
}