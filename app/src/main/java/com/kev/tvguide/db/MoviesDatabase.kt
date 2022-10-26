package com.kev.tvguide.db

import android.content.Context
import android.graphics.Movie
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kev.tvguide.models.MovieDetailsResponse
import com.kev.tvguide.models.MovieItem

@Database(entities = [MovieDetailsResponse::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase(){

	abstract fun MoviesDao() : FavoriteMoviesDao

	companion object{
		private var DB_INSTANCE : MoviesDatabase? = null

		fun getMovieDbInstance(context: Context) : MoviesDatabase{
			if (DB_INSTANCE == null){
				DB_INSTANCE = Room.databaseBuilder(
					context,
					MoviesDatabase::class.java,
					"MOVIE_DB"
				)
					.allowMainThreadQueries()
					.build()
			}

			return DB_INSTANCE!!
		}
	}

}