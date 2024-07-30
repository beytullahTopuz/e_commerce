package com.t4zb.e_commerce.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Basket(
    @PrimaryKey(autoGenerate = true)
    var basketId: Int = 0,
    val userId: String? = null,
    val createDate: Date? = null,
    val basketProductList: List<ProductRoom>? = null
)
