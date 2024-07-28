package com.t4zb.e_commerce.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.t4zb.e_commerce.R
import com.t4zb.e_commerce.data.remote.firebase.FirebaseDataSource
import com.t4zb.e_commerce.data.repository.UserRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase =
        FirebaseDatabase.getInstance("https://ecommerce-f6145-default-rtdb.europe-west1.firebasedatabase.app/")

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseDataSource(
        database: FirebaseDatabase,
        auth: FirebaseAuth,
        googleSignInClient: GoogleSignInClient,
        firebaseStorage: FirebaseStorage

    ): FirebaseDataSource {
        return FirebaseDataSource(database, auth, googleSignInClient, firebaseStorage)
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()


    @Provides
    @Singleton
    fun provideGoogleSignInClient(@ApplicationContext context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    @Provides
    @Singleton
    fun provideUserRepository(firebaseDataSource: FirebaseDataSource): UserRepo {
        return UserRepo(firebaseDataSource)
    }
}
