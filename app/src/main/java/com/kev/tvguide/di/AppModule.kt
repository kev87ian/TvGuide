package com.kev.tvguide.di

import android.content.Context
import com.kev.tvguide.db.FavoriteMoviesDao
import com.kev.tvguide.db.MoviesDatabase
import com.kev.tvguide.network.MoviesApiService
import com.kev.tvguide.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Provides
	@Singleton
	fun providesLoggingInterceptor() = HttpLoggingInterceptor().apply {
		level = HttpLoggingInterceptor.Level.BODY
	}

	@Singleton
	@Provides
	fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
		return OkHttpClient.Builder()
			.addInterceptor(loggingInterceptor)
			.build()
	}

	@Singleton
	@Provides
	fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
		return Retrofit.Builder()
			.baseUrl(Constants.BASE_URL)
			.client(okHttpClient)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}

	@Singleton
	@Provides
	fun createsApiClass(retrofit: Retrofit): MoviesApiService {
		return retrofit.create(MoviesApiService::class.java)
	}

	@Singleton
	@Provides
	fun providesInstanceOfMoviesDatabase(@ApplicationContext context: Context): MoviesDatabase {
		return MoviesDatabase.getDbInstance(context)
	}

	@Singleton
	@Provides
	fun providesMoviesDao(moviesDatabase: MoviesDatabase): FavoriteMoviesDao {
		return moviesDatabase.moviesDao()
	}
}