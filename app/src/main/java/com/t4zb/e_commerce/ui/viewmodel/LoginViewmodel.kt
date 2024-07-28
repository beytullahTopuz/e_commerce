package com.t4zb.e_commerce.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.t4zb.e_commerce.data.model.UserResultType
import com.t4zb.e_commerce.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewmodel @Inject constructor(
    private val userRepo: UserRepo
) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password

    private val _isValid = MutableLiveData<Boolean>()
    val isValid: LiveData<Boolean> get() = _isValid

    private val _loginResult = MutableLiveData<UserResultType>()
    val loginResult: LiveData<UserResultType> get() = _loginResult

    fun validateInput() {
        _isValid.value = !(_email.value.isNullOrEmpty() ||
                _password.value.isNullOrEmpty())
    }

    fun loginUser() {
        if (_isValid.value == true) {
            userRepo.loginWithEmail(_email.value!!, _password.value!!) { resultType ->
                _loginResult.value = resultType
            }
        }
    }

    fun setEmail(value: String) {
        _email.value = value
        validateInput()
    }

    fun setPassword(value: String) {
        _password.value = value
        validateInput()
    }
}