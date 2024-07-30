package com.kev.tvguide.di

import android.content.Context
import androidx.room.Room
import com.kev.tvguide.data.local.RemoteKeysDao
import com.kev.tvguide.data.local.ShowsDB
import com.kev.tvguide.data.local.ShowsDao
import com.kev.tvguide.data.remote.APIService

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesShowsDao(db: ShowsDB): ShowsDao = db.showsDao

    @Provides
    @Singleton
    fun providesRemoteKeysDao(db: ShowsDB): RemoteKeysDao = db.remoteKeysDao

    @Provides
    @Singleton
    fun providesBeerDatabase(@ApplicationContext context: Context): ShowsDB {
        return Room.databaseBuilder(
            context,
            ShowsDB::class.java,
            "shows_db",
            ).build()
    }

    @Provides
    @Singleton
    fun providesLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,

        ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)

            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }


    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit
        .Builder()
        .baseUrl("https://api.themoviedb.org/3/tv/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Singleton
    @Provides
    fun createsApiService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }


}