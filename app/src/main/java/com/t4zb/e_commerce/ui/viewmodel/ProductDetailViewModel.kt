package com.t4zb.e_commerce.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t4zb.e_commerce.data.local.repo.BasketRepo
import com.t4zb.e_commerce.data.model.Basket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val basketRepo: BasketRepo
) : ViewModel() {

    private val _basketOperationResult = MutableLiveData<Boolean>()
    val basketOperationResult: LiveData<Boolean> get() = _basketOperationResult

    fun insertBasket(basket: Basket) {
        viewModelScope.launch {
            basketRepo.insertBasket(basket)
        }
    }

     fun insertOrUpdateBasket(basket: Basket) {
         viewModelScope.launch {
            val result =  basketRepo.insertOrUpdateBasket(basket)
             _basketOperationResult.postValue(result)
             Log.d("BEYTULLAH", "insertOrUpdateBasket: ${result}")
         }
    }

}
