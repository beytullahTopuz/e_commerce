package com.t4zb.e_commerce.di

import android.content.Context
import androidx.room.Room
import com.t4zb.e_commerce.data.local.AppDatabase
import com.t4zb.e_commerce.data.local.dao.BasketDao
import com.t4zb.e_commerce.data.local.repo.BasketRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "e_commerce_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBasketDao(appDatabase: AppDatabase): BasketDao {
        return appDatabase.basketDao()
    }

    @Provides
    @Singleton
    fun provideBasketRepository(basketDao: BasketDao): BasketRepo {
        return BasketRepo(basketDao)
    }

}