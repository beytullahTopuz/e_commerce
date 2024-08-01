package com.t4zb.e_commerce.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.t4zb.e_commerce.data.local.repo.BasketRepo
import com.t4zb.e_commerce.data.model.Basket
import com.t4zb.e_commerce.data.model.Order
import com.t4zb.e_commerce.data.model.User
import com.t4zb.e_commerce.data.repository.OrderRepo
import com.t4zb.e_commerce.data.repository.PaymentRepository
import com.t4zb.e_commerce.data.repository.UserRepo
import com.t4zb.paymentsdk.PaymentSDK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository,
    private val basketRepo: BasketRepo,
    private val orderRepo: OrderRepo
) : ViewModel() {

    private val _cardNumber = MutableLiveData<String>()
    val cardNumber: LiveData<String> get() = _cardNumber

    private val _cardNumberError = MutableLiveData<String?>()
    val cardNumberError: LiveData<String?> get() = _cardNumberError

    private val _paymentResult = MutableLiveData<Boolean>()
    val paymentResult: LiveData<Boolean> get() = _paymentResult

    private val _otpRequired = MutableLiveData<Boolean>()
    val otpRequired: LiveData<Boolean> get() = _otpRequired

    private val _confirmationResult = MutableLiveData<Boolean>()
    val confirmationResult: LiveData<Boolean> get() = _confirmationResult

    fun onCardNumberChanged(newCardNumber: String) {
        _cardNumber.value = newCardNumber
        validateCardNumber(newCardNumber)
    }

    private fun validateCardNumber(cardNumber: String) {
        _cardNumberError.value = if (cardNumber.length == 16) null else "Invalid card number"
    }

    fun startPayment(
        totalPrice: Double,
        cardNumber: String,
        cardHolderName: String,
        expiryMonth: String,
        expiryYear: String,
        cvv: String
    ) {
        val expDate = "$expiryMonth/$expiryYear"
        paymentRepository.startPayment(totalPrice, cardNumber, expDate, cvv) { success ->
            if (success) {
                _otpRequired.postValue(true)
            } else {
                _paymentResult.postValue(false)
            }
        }
    }

    fun confirmPayment(opt: String) {
        paymentRepository.confirmPayment(opt) { success ->
            _confirmationResult.postValue(success)
            _otpRequired.postValue(false)
        }
    }

    fun deleteBasket(basketId: Int) {
        viewModelScope.launch {
            basketRepo.deleteBasketById(basketId)
        }
    }

    fun insertOrder(order: Order) {
        orderRepo.insertOrder(order) { isSuccess, result ->
            run {
                Log.d("TAG", "insertOrder: ${result.toString()}")
            }
        }
    }
}