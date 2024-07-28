package com.t4zb.e_commerce.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.t4zb.e_commerce.data.model.User
import com.t4zb.e_commerce.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepo: UserRepo
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

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