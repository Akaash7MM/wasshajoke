package com.wassha.androidcodechallenge.di

import android.app.Application
import androidx.room.Room
import com.wassha.androidcodechallenge.data.JokeRepositoryImpl
import com.wassha.androidcodechallenge.data.local.JokeDatabase
import com.wassha.androidcodechallenge.data.remote.JokeApi
import com.wassha.androidcodechallenge.domain.JokeRepository
import com.wassha.androidcodechallenge.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesJokeApi(retrofit: Retrofit): JokeApi {
        return retrofit.create(JokeApi::class.java)
    }

    @Provides
    @Singleton
    fun providesJokeRepository(
        jokeApi: JokeApi,
        jokeDatabase: JokeDatabase,
    ): JokeRepository {
        return JokeRepositoryImpl(jokeApi, jokeDatabase.jokeDao())
    }

    @Provides
    @Singleton
    fun providesJokeDatabase(application: Application): JokeDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            JokeDatabase::class.java,
            "jokeentity",
        ).build()
    }
}
