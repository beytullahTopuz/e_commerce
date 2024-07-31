package com.t4zb.e_commerce.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t4zb.e_commerce.data.config.AppConfig
import com.t4zb.e_commerce.data.local.repo.BasketRepo
import com.t4zb.e_commerce.data.model.Basket
import com.t4zb.e_commerce.data.model.ProductRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val basketRepo: BasketRepo
) : ViewModel() {

    private val _basket = MutableLiveData<Basket>()
    val basket: LiveData<Basket> get() = _basket
    val removeBasketResult = MutableLiveData<Boolean>()


    init {
        val userId = AppConfig.userId
        if (userId != null) {
            fetchBasketByUserId(userId)
        }
    }

     fun fetchBasketByUserId(userId: String) {
        viewModelScope.launch {
            _basket.value = basketRepo.getBasketByUserId(userId)
        }
    }


    fun removeItemOrDeleteBasket(userId: String, productToRemove: ProductRoom) {
        viewModelScope.launch {
            val basket = basketRepo.getBasketByUserId(userId)
            if (basket != null) {
                val result = basketRepo.removeItemOrDeleteBasket(userId, productToRemove)
                removeBasketResult.postValue(result)
            } else {
                removeBasketResult.postValue(false)
            }
        }
    }

}