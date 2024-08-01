package com.t4zb.e_commerce.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.t4zb.e_commerce.data.model.Order
import com.t4zb.e_commerce.data.repository.OrderRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepo: OrderRepo
) : ViewModel() {

    private val _orderListResult = MutableLiveData<Result<List<Order>>>()
    val orderListResult: LiveData<Result<List<Order>>> = _orderListResult
    fun getOrderListByUserId(userId: String) {
        orderRepo.getOrderListByUserId(userId) { isSuccess, orderList, errorMessage ->
            if (isSuccess) {
                _orderListResult.postValue(Result.success(orderList ?: emptyList()))
            } else {
                _orderListResult.postValue(Result.failure(Exception(errorMessage)))
            }
        }
    }
}