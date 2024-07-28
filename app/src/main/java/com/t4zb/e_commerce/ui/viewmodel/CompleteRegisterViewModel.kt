package com.t4zb.e_commerce.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.t4zb.e_commerce.data.config.AppConfig
import com.t4zb.e_commerce.data.model.User
import com.t4zb.e_commerce.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CompleteRegisterViewModel @Inject constructor(
    private val userRepo: UserRepo
) : ViewModel() {

    private val _completionResult = MutableLiveData<Boolean>()
    val completionResult: LiveData<Boolean> get() = _completionResult

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user


    fun isValid(phoneNumber: String, address: String, gender: String): Boolean {
        return phoneNumber.isNotEmpty() && address.isNotEmpty() && gender.isNotEmpty()
    }

    fun completeProfile(phoneNumber: String, address: String, gender: String) {
        val userId = AppConfig.userId
        if (userId != null) {
            userRepo.getUserById(userId) { existingUser ->
                if (existingUser != null) {
                    val updatedUser = existingUser.copy(
                        user_number = phoneNumber,
                        user_address_id = address,
                        gender = gender
                    )
                    userRepo.updateUser(updatedUser) { isSuccess ->
                        _completionResult.value = isSuccess
                    }
                } else {
                    _completionResult.value = false
                }
            }
        } else {
            _completionResult.value = false
        }
    }

    fun fetchUser(userId: String) {
        userRepo.getUserById(userId) { user ->
            if (user != null) {
                _user.value = user!!
            }
        }
    }

    fun updateUserProfileImage(userId: String, imageUri: Uri) {
        userRepo.uploadUserProfileImage(userId, imageUri) { isSuccess, downloadUrl ->
            if (isSuccess && downloadUrl != null) {
                userRepo.updateUserPicture(userId, downloadUrl) { updateSuccess ->
                    if (updateSuccess) {
                        fetchUser(userId)
                    } else {
                        //todo: Handle update failure
                    }
                }
            } else {
                //todo: Handle upload failure
            }
        }
    }

}