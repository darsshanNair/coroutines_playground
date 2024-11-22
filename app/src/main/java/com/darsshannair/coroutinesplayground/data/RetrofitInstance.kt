package com.darsshannair.coroutinesplayground.data

import com.darsshannair.coroutinesplayground.data.interfaces.BankApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.sampleapis.com/fakebank/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))  // Use this Moshi instance
            .build()
    }

    val api: BankApiService by lazy {
        retrofit.create(BankApiService::class.java)
    }
}
