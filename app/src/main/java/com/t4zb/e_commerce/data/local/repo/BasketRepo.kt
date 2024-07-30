package com.t4zb.e_commerce.data.local.repo

import com.t4zb.e_commerce.data.local.dao.BasketDao
import com.t4zb.e_commerce.data.model.Basket
import javax.inject.Inject

class BasketRepo @Inject constructor(
    private val basketDao: BasketDao,
) {
    suspend fun getBasketByUserId(userId: String): Basket? {
        return basketDao.getBasketByUserId(userId)
    }

    suspend fun insertBasket(basket: Basket): Result<Long> {
        return try {
            val id = basketDao.insertBasket(basket)
            if (id != -1L) {
                Result.success(id)
            } else {
                Result.failure(Exception("Failed to insert basket"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateBasket(basket: Basket) {
        basketDao.updateBasket(basket)
    }

    suspend fun deleteBasket(basket: Basket) {
        basketDao.deleteBasket(basket)
    }

    suspend fun insertOrUpdateBasket(basket: Basket): Boolean {
        return try {
            val existingBasket = basketDao.getBasketByUserId(basket.userId ?: "")
            if (existingBasket != null) {
                // Update existing basket
                val updatedProductList = existingBasket.basketProductList?.toMutableList() ?: mutableListOf()
                updatedProductList.addAll(basket.basketProductList ?: emptyList())
                val updatedBasket = existingBasket.copy(basketProductList = updatedProductList)
                basketDao.updateBasket(updatedBasket)
            } else {
                // Insert new basket
                basketDao.insertBasket(basket)
            }
            true
        } catch (e: Exception) {
            false
        }
    }

}