package com.t4zb.e_commerce.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.t4zb.e_commerce.data.model.User
import com.t4zb.e_commerce.data.model.UserResultType
import com.t4zb.e_commerce.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepo: UserRepo
) : ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    private val _userSurname = MutableLiveData<String>()
    val userSurname: LiveData<String> get() = _userSurname

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password

    private val _isValid = MutableLiveData<Boolean>()
    val isValid: LiveData<Boolean> get() = _isValid

    private val _registrationResult = MutableLiveData<UserResultType>()
    val registrationResult: LiveData<UserResultType> get() = _registrationResult

//    private val _googleSignInResult = MutableLiveData<Boolean>()
//    val googleSignInResult: LiveData<Boolean> get() = _googleSignInResult


    fun validateInput() {
        _isValid.value = !(_userName.value.isNullOrEmpty() ||
                _userSurname.value.isNullOrEmpty() ||
                _email.value.isNullOrEmpty() ||
                _password.value.isNullOrEmpty())
    }

    fun registerUser() {
        if (_isValid.value == true) {
            val user = User(
                user_id = null,
                user_name = _userName.value ?: "",
                user_surname = _userSurname.value ?: "",
                user_picture = null,
                user_basket_id = null,
                user_order_list_id = null,
                user_number = null,
                user_address_id = null,
                user_email = _email.value ?: ""
            )
            userRepo.createUserWithEmail(_email.value!!, _password.value!!, user) { userResultType ->
                _registrationResult.value = userResultType
            }
        }
    }

    fun saveUserToDatabase(user: User) {
        user.user_name?.let {
            user.user_surname?.let { it1 ->
                userRepo.createUserWithEmail(it, it1, user) { userResultType ->
                    _registrationResult.value = userResultType
                }
            }
        }
    }

    fun setUserName(value: String) {
        _userName.value = value
        validateInput()
    }

    fun setUserSurname(value: String) {
        _userSurname.value = value
        validateInput()
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

