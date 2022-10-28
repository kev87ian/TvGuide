package com.kev.tvguide.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kev.tvguide.R
import com.kev.tvguide.databinding.FavoriteMoviesItemBinding
import com.kev.tvguide.databinding.MovieLayoutItemBinding
import com.kev.tvguide.models.MovieDetailsResponse
import com.kev.tvguide.models.MovieItem
import com.kev.tvguide.utils.Constants

class FavoriteMoviesAdapter : RecyclerView.Adapter<FavoriteMoviesAdapter.MoviesViewHolder>() {
	class MoviesViewHolder(val binding: FavoriteMoviesItemBinding) :
		RecyclerView.ViewHolder(binding.root)


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {

		val binding = FavoriteMoviesItemBinding.inflate(
			LayoutInflater.from(parent.context), parent, false
		)
		return MoviesViewHolder(binding)
	}

	override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
		val currentMovie = differ.currentList[position]
		with(holder) {
			binding.cvIvMoviePoster.load(Constants.BASE_POSTER_URL.plus(currentMovie.posterPath)) {
				error(R.drawable.no_picture_icon)
				placeholder(R.drawable.loading)
			}
		}
	}

	override fun getItemCount(): Int {
		return differ.currentList.size
	}

	private val diffUtil = object : DiffUtil.ItemCallback<MovieDetailsResponse>() {
		override fun areItemsTheSame(
			oldItem: MovieDetailsResponse,
			newItem: MovieDetailsResponse
		): Boolean {
			return oldItem.id == newItem.id
		}

		override fun areContentsTheSame(
			oldItem: MovieDetailsResponse,
			newItem: MovieDetailsResponse
		): Boolean {
			return oldItem == newItem
		}
	}

	val differ = AsyncListDiffer(this, diffUtil)
}