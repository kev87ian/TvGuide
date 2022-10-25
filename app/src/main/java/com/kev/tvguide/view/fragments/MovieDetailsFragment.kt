package com.kev.tvguide.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.kev.tvguide.R
import com.kev.tvguide.R.layout.fragment_movie_details
import com.kev.tvguide.databinding.FragmentMovieDetailsBinding
import com.kev.tvguide.models.MovieDetailsResponse
import com.kev.tvguide.utils.Constants
import com.kev.tvguide.utils.State
import com.kev.tvguide.viewmodel.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(fragment_movie_details) {
	private var _binding: FragmentMovieDetailsBinding? = null
	private val binding get() = _binding!!

	private val viewModel: MovieDetailsViewModel by viewModels()
	private val args: MovieDetailsFragmentArgs by navArgs()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		fetchData()
	}

	private fun fetchData() {
		viewModel.fetchMovieDetails(args.movieID)

		viewModel.movieDetailsObservable.observe(viewLifecycleOwner) { resource ->
			when (resource) {
				is State.Error -> {
					binding.uiStateLayout.visibility = View.VISIBLE
					binding.progressBar.visibility = View.GONE
					binding.errorMsgTextview.visibility = View.VISIBLE
					binding.errorMsgTextview.text = resource.message
					binding.retryBtn.visibility = View.VISIBLE
				}

				is State.Success -> {
					binding.uiStateLayout.visibility = View.GONE
					binding.views.visibility = View.VISIBLE
					bindUi(resource.data!!)

				}

				is State.Loading -> {
					binding.progressBar.visibility = View.VISIBLE
					binding.errorMsgTextview.visibility = View.GONE
					binding.retryBtn.visibility = View.GONE
				}
			}
		}
	}

	private fun bindUi(movie: MovieDetailsResponse) {

		binding.imageView.load(Constants.BASE_POSTER_URL.plus(movie.posterPath)) {
			error(R.drawable.no_picture_icon)
		}

		binding.movieTitleTextView.text = movie.title
		binding.releaseDateTextview.text = movie.releaseDate
		binding.synopsisTextview.text = movie.overview
		binding.ratingTextview.text = movie.voteAverage.toFloat().toString()

		//TODO soma fragment lifecyle vizuri and understand why the movies fragment glitches on resume

	}


}