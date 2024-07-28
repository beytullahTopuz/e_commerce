package com.t4zb.e_commerce.data.remote.firebase

import android.net.Uri
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.t4zb.e_commerce.data.model.User
import com.t4zb.e_commerce.data.model.UserResultType
import javax.inject.Inject

class FirebaseDataSource @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
    private val firebaseStorage: FirebaseStorage
) {

    private val TAG = "T4ZB"
    private val databaseReference = firebaseDatabase.reference

    fun createUserWithEmail(
        email: String,
        password: String,
        onComplete: (Boolean, String?) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    val firebaseUser = firebaseAuth.currentUser
                    val userId = firebaseUser?.uid
                    if (userId != null) {
                        Log.d(TAG, "Authentication successful. User ID: $userId")
                        onComplete(true, userId)
                    } else {
                        Log.e(TAG, "User ID is null")
                        onComplete(false, null)
                    }
                } else {
                    Log.e(TAG, "Authentication failed", authTask.exception)
                    onComplete(false, null)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Authentication failed", exception)
                onComplete(false, null)
            }
    }

    fun saveUserToDatabase(userId: String, user: User, onComplete: (UserResultType) -> Unit) {
        user.user_id = userId
        databaseReference.child("users").child(userId).setValue(user)
            .addOnCompleteListener { dbTask ->
                if (dbTask.isSuccessful) {
                    Log.d(TAG, "Database write successful")
                    onComplete(UserResultType(true,userId))
                } else {
                    Log.e(TAG, "Database write failed", dbTask.exception)
                    onComplete(UserResultType(false,null))
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Database write failed", exception)
                onComplete(UserResultType(false,null))
            }
    }

    fun signInWithGoogle(idToken: String, onComplete: (Boolean, User?) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = firebaseAuth.currentUser
                    firebaseUser?.let {
                        val user = User(
                            user_id = it.uid,
                            user_name = it.displayName ?: "",
                            user_surname = "",
                            user_picture = it.photoUrl?.toString(),
                            user_basket_id = null,
                            user_order_list_id = null,
                            user_number = it.phoneNumber,
                            user_email = it.email,
                            user_address_id = null
                        )
                        onComplete(true, user)
                    } ?: run {
                        onComplete(false, null)
                    }
                } else {
                    onComplete(false, null)
                }
            }
            .addOnFailureListener {
                onComplete(false, null)
            }
    }

    fun getGoogleSignInClient() = googleSignInClient

    fun loginWithEmail(
        email: String,
        password: String,
        onComplete: (UserResultType) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    Log.d(TAG, "Authentication successful.")
                    onComplete(UserResultType(true, authTask.result.user?.uid))
                } else {
                    Log.e(TAG, "Authentication failed", authTask.exception)
                    onComplete(UserResultType(false,null))
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Authentication failed", exception)
                onComplete(UserResultType(false,null))
            }
    }

    fun getUserById(userId: String, onComplete: (User?) -> Unit) {
        firebaseDatabase.reference.child("users").child(userId).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result.getValue(User::class.java)
                    onComplete(user)
                } else {
                    onComplete(null)
                }
            }
    }

    fun updateUser(user: User, onComplete: (Boolean) -> Unit) {
        user.user_id?.let { userId ->
            firebaseDatabase.reference.child("users").child(userId).setValue(user)
                .addOnCompleteListener { task ->
                    onComplete(task.isSuccessful)
                }
        } ?: onComplete(false)
    }

    fun uploadImage(userId: String, imageUri: Uri, onComplete: (Boolean, String?) -> Unit) {
        val ref = firebaseStorage.reference.child("profile_images/$userId.jpg")
        ref.putFile(imageUri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { downloadUri ->
                    onComplete(true, downloadUri.toString())
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Image upload failed: ${exception.message}", exception)
                onComplete(false, null)
            }
    }

    fun updateUserPicture(userId: String, downloadUrl: String, onComplete: (Boolean) -> Unit) {
        firebaseDatabase.reference.child("users").child(userId).child("user_picture").setValue(downloadUrl)
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
            .addOnFailureListener { exception ->
                onComplete(false)
            }
    }
}
