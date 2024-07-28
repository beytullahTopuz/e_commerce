package com.t4zb.e_commerce.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.t4zb.e_commerce.data.model.User
import com.t4zb.e_commerce.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


// silinecek test amaclı
@HiltViewModel
class UserViewmodel @Inject constructor(
    private val userRepo: UserRepo
) : ViewModel() {


    fun createUser() {

        var user = User(
            null,
            "test_user_name",
            "test_user_sur_name",
            null,
            null,
            null,
            null,
            null,
            null,
        )

      //  userRepo.cre(user)


    }

}