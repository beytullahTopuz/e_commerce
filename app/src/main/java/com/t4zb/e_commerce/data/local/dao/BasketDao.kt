package com.t4zb.e_commerce.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.t4zb.e_commerce.data.model.Basket

@Dao
interface BasketDao {
    @Query("SELECT * FROM basket WHERE userId = :userId")
    suspend fun getBasketByUserId(userId: String): Basket?

    @Insert
    suspend fun insertBasket(basket: Basket): Long

    @Update
    suspend fun updateBasket(basket: Basket)

    @Delete
    suspend fun deleteBasket(basket: Basket)

    @Query("DELETE FROM Basket WHERE basketId = :basketId")
    suspend fun deleteBasketById(basketId: Int)


}