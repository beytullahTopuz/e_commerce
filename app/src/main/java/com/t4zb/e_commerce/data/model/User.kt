package com.t4zb.e_commerce.data.model

data class User(
    var user_id: String? = null,
    val user_name: String? = null,
    val user_surname: String? = null,
    val user_email: String? = null,
    val user_picture: String? = null,
    val user_basket_id: String? = null,
    val user_order_list_id: String? = null,
    val user_number: String? = null,
    val user_address_id: String? = null,
    var gender: String? = null
)
