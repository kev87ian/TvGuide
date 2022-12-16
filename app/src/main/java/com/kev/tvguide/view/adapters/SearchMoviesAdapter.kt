package com.kev.tvguide.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kev.tvguide.R
import com.kev.tvguide.databinding.MovieLayoutItemBinding

import com.kev.tvguide.models.MovieItem
import com.kev.tvguide.utils.Constants
import com.kev.tvguide.view.fragments.SearchMoviesFragmentDirections

class SearchMoviesAdapter : RecyclerView.Adapter<SearchMoviesAdapter.MovieViewHolder>() {

	class MovieViewHolder(val binding: MovieLayoutItemBinding) :
		RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
		val binding = MovieLayoutItemBinding.inflate(
			LayoutInflater.from(parent.context), parent, false
		)

		return MovieViewHolder(binding)
	}

	override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
		val currentMovie = differ.currentList[position]
		with(holder){

			binding.cvIvMoviePoster.load(Constants.BASE_POSTER_URL.plus(currentMovie.posterPath)){
				error(R.drawable.no_picture_icon)
			}
		}

		holder.itemView.setOnClickListener {
			val direction = SearchMoviesFragmentDirections.actionSearchMoviesFragmentToMovieDetailsFragment(currentMovie.id)
			it.findNavController().navigate(direction)
		}
	}

	override fun getItemCount(): Int {
		return differ.currentList.size
	}

	private val diffUtil = object : DiffUtil.ItemCallback<MovieItem>() {
		override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
			return oldItem.id == newItem.id
		}

		override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
			return newItem.id == oldItem.id
		}
	}

	val differ = AsyncListDiffer(this, diffUtil)

}