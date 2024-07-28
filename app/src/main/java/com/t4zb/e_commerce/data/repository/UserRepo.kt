package com.t4zb.e_commerce.data.repository

import android.net.Uri
import com.t4zb.e_commerce.data.model.User
import com.t4zb.e_commerce.data.model.UserResultType
import com.t4zb.e_commerce.data.remote.firebase.FirebaseDataSource
import javax.inject.Inject

class UserRepo @Inject constructor(private val firebaseDataSource: FirebaseDataSource) {


    fun createUserWithEmail(
        email: String,
        password: String,
        user: User,
        onComplete: (UserResultType) -> Unit
    ) {
        firebaseDataSource.createUserWithEmail(email, password) { isSuccess, userId ->
            if (isSuccess && userId != null) {
                firebaseDataSource.saveUserToDatabase(userId, user) { dbSuccess ->
                    onComplete(dbSuccess)
                }
            } else {
                onComplete(UserResultType(false,null))
            }
        }
    }

    fun loginWithEmail(
        email: String,
        password: String,
        onComplete: (UserResultType) -> Unit
    ) {
        firebaseDataSource.loginWithEmail(email, password) { resultType ->
            onComplete(resultType)
        }
    }

    fun getUserById(userId: String, onComplete: (User?) -> Unit) {
        firebaseDataSource.getUserById(userId, onComplete)
    }

    fun updateUser(user: User, onComplete: (Boolean) -> Unit) {
        firebaseDataSource.updateUser(user) { isSuccess ->
            onComplete(isSuccess)
        }
    }


    fun uploadUserProfileImage(userId: String, imageUri: Uri, onComplete: (Boolean, String?) -> Unit) {
        firebaseDataSource.uploadImage(userId, imageUri, onComplete)
    }

    fun updateUserPicture(userId: String, downloadUrl: String, onComplete: (Boolean) -> Unit) {
        firebaseDataSource.updateUserPicture(userId, downloadUrl, onComplete)
    }


    /*
        fun signInWithGoogle(idToken: String, onComplete: (Boolean, User?) -> Unit) {
            firebaseDataSource.signInWithGoogle(idToken) { isSuccess, user ->
                onComplete(isSuccess, user)
            }
        }

        fun getGoogleSignInClient() = firebaseDataSource.getGoogleSignInClient()
    */

    fun registerUser() {

    }

    fun updateUserInfo() {

    }

    fun insertUserBasketItem() {

    }

    fun deleteUserBasketItem() {

    }

    fun clearUserBasketList() {

    }
}