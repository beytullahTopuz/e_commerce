package com.t4zb.e_commerce.ui.listener

import com.t4zb.e_commerce.data.model.Product
import com.t4zb.e_commerce.data.model.ProductRoom

interface DeleteBasketItemListener {
    fun onItemClicked(productRoom: ProductRoom)
}