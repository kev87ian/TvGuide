package com.kev.tvguide.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kev.tvguide.R
import com.kev.tvguide.databinding.SimilarMovieItemBinding
import com.kev.tvguide.models.MovieItem
import com.kev.tvguide.utils.Constants
import com.kev.tvguide.view.fragments.MovieDetailsFragmentDirections

class SimilarMoviesAdapter : RecyclerView.Adapter<SimilarMoviesAdapter.MoviesViewHolder>() {

	class MoviesViewHolder(val binding: SimilarMovieItemBinding) : RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {

		val binding = SimilarMovieItemBinding.inflate(
			LayoutInflater.from(parent.context), parent, false
		)

		return MoviesViewHolder(binding)
	}

	override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
		val currentMovie = differ.currentList[position]
		with(holder){
			binding.cvIvMoviePoster.load(Constants.BASE_POSTER_URL.plus(currentMovie.posterPath)){
				placeholder(R.drawable.loading)
				error(R.drawable.no_picture_icon)
			}

		}

		holder.itemView.setOnClickListener {
			val direction = MovieDetailsFragmentDirections.actionMovieDetailsFragmentToMovieDetailsFragment(currentMovie.id)
			it.findNavController().navigate(direction)
		}
	}

	override fun getItemCount(): Int {
		return differ.currentList.size
	}

	private val diffUtil = object : DiffUtil.ItemCallback<MovieItem>(){
		override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
			return oldItem.id == newItem.id
		}

		override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
			return oldItem == newItem
		}
	}

	val differ = AsyncListDiffer(this, diffUtil)
}