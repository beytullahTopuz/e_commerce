package com.t4zb.e_commerce.data.local.repo

import android.util.Log
import com.t4zb.e_commerce.data.local.dao.BasketDao
import com.t4zb.e_commerce.data.model.Basket
import com.t4zb.e_commerce.data.model.ProductRoom
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

    suspend fun deleteBasketById(basketId: Int) {
        basketDao.deleteBasketById(basketId)
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
                val updatedProductList =
                    existingBasket.basketProductList?.toMutableList() ?: mutableListOf()
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
    /*

        suspend fun removeItemOrDeleteBasket(userId: String, productToRemove: ProductRoom): Boolean {
            return try {
                val existingBasket = basketDao.getBasketByUserId(userId)
                if (existingBasket != null) {
                    val updatedProductList = existingBasket.basketProductList?.toMutableList() ?: mutableListOf()
                    updatedProductList.remove(productToRemove)
                    if (updatedProductList.isEmpty()) {
                        // If the basket is empty after removing the product, delete the basket
                        basketDao.deleteBasket(existingBasket)
                    } else {
                        // Otherwise, update the basket with the remaining products
                        val updatedBasket = existingBasket.copy(basketProductList = updatedProductList)
                        basketDao.updateBasket(updatedBasket)
                    }
                }
                true
            } catch (e: Exception) {
                false
            }
        }
    */

    suspend fun removeItemOrDeleteBasket(userId: String, productToRemove: ProductRoom): Boolean {
        return try {
            val existingBasket = basketDao.getBasketByUserId(userId)

            if (existingBasket != null) {
                val updatedProductList = existingBasket.basketProductList?.toMutableList() ?: mutableListOf()

                val iterator = updatedProductList.iterator()
                var removed = false
                while (iterator.hasNext()) {
                    val product = iterator.next()
                    if (product.productId == productToRemove.productId) {
                        iterator.remove()
                        removed = true
                        break
                    }
                }

                if (!removed) {
                    Log.d("BasketRepo", "Product not found in basket for user: $userId")
                    return false
                }

                if (updatedProductList.isEmpty()) {
                    basketDao.deleteBasket(existingBasket)
                    Log.d("BasketRepo", "Basket is empty. Deleted basket for user: $userId")
                } else {
                    val updatedBasket = existingBasket.copy(basketProductList = updatedProductList)
                    basketDao.updateBasket(updatedBasket)
                    Log.d("BasketRepo", "Updated basket for user: $userId with remaining products: $updatedProductList")
                }
                return true
            } else {
                // Sepet yoksa, işlem başarısız
                Log.d("BasketRepo", "No basket found for user: $userId")
                return false
            }
        } catch (e: Exception) {
            Log.e("BasketRepo", "Error in removeItemOrDeleteBasket: ${e.message}")
            return false
        }
    }



}