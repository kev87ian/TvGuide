package com.kev.tvguide.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kev.tvguide.R
import com.kev.tvguide.databinding.MovieLayoutItemBinding
import com.kev.tvguide.model.data.MovieItem
import com.kev.tvguide.utils.Constants.Companion.BASE_POSTER_URL

class TopRatedMoviesPagingAdapter() :
	PagingDataAdapter<MovieItem, TopRatedMoviesPagingAdapter.MoviesViewHolder>(diffCallBack) {


	class MoviesViewHolder(val binding: MovieLayoutItemBinding) :
		RecyclerView.ViewHolder(binding.root)


	companion object {
		val diffCallBack = object : DiffUtil.ItemCallback<MovieItem>() {
			override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
				return oldItem.id == newItem.id
			}

			override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
				return oldItem == newItem
			}
		}
	}

	override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {

		//this method is unique to the paging adapter class
		val currentMovie = getItem(position)

		holder.binding.apply {
		/*	cvMovieTitle.text = currentMovie?.title
			cvMovieRating.text = currentMovie?.voteAverage.toString()
			cvMovieReleaseDate.text = currentMovie?.releaseDate*/

			cvIvMoviePoster.load(BASE_POSTER_URL.plus(currentMovie?.posterPath)) {
				placeholder(R.drawable.loading)
				error(R.drawable.no_picture_icon)
			}
		}

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {

		return MoviesViewHolder(
			MovieLayoutItemBinding.inflate(
				LayoutInflater.from(parent.context),
				parent, false
			)
		)

	}
}