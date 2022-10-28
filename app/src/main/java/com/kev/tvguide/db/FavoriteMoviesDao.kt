package com.kev.tvguide.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kev.tvguide.models.MovieDetailsResponse

@Dao
interface FavoriteMoviesDao {


	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun saveMovie(movieDetailsResponse: MovieDetailsResponse) : Long

	@Query("SELECT * FROM movie_details")
	fun getSavedMovies() : LiveData<List<MovieDetailsResponse>>

	@Query("SELECT * FROM movie_details")
	fun checkIfDbIsEmpty() : List<MovieDetailsResponse>

	@Delete
	fun deleteMovie(movieDetailsResponse: MovieDetailsResponse)


}