package com.t4zb.e_commerce.di

import com.t4zb.paymentsdk.PaymentSDK
import com.t4zb.paymentsdk.PaymentSDKImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PaymentModule {

    @Provides
    @Singleton
    fun providePaymentSDK(): PaymentSDK {
        return PaymentSDKImpl()
    }
}