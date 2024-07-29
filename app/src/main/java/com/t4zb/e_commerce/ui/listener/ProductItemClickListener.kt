package com.t4zb.e_commerce.ui.listener

import com.t4zb.e_commerce.data.model.Product

interface ProductItemClickListener {
    fun itemClicked(itemIndex: Int, product: Product);
}