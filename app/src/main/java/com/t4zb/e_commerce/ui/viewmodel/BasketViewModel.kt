package com.t4zb.e_commerce.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t4zb.e_commerce.data.config.AppConfig
import com.t4zb.e_commerce.data.local.repo.BasketRepo
import com.t4zb.e_commerce.data.model.Basket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val basketRepo: BasketRepo
) : ViewModel() {

    private val _basket = MutableLiveData<Basket>()
    val basket: LiveData<Basket> get() = _basket

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
}