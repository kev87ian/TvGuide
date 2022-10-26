package com.kev.tvguide.view.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.kev.tvguide.R
import com.kev.tvguide.R.layout.fragment_movie_details
import com.kev.tvguide.databinding.FragmentMovieDetailsBinding
import com.kev.tvguide.models.MovieDetailsResponse
import com.kev.tvguide.utils.Constants
import com.kev.tvguide.utils.State
import com.kev.tvguide.view.adapters.MovieCastAdapter
import com.kev.tvguide.view.adapters.SimilarMoviesAdapter
import com.kev.tvguide.viewmodel.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(fragment_movie_details) {
	private var _binding: FragmentMovieDetailsBinding? = null
	private val binding get() = _binding!!

	private val viewModel: MovieDetailsViewModel by viewModels()
	private val args: MovieDetailsFragmentArgs by navArgs()

	private lateinit var mdialog: ProgressDialog

	private lateinit var castAdapter: MovieCastAdapter
	private lateinit var similarMoviesAdapter: SimilarMoviesAdapter

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
		mdialog = ProgressDialog(requireContext())

		fetchData()
		fetchCast()
		fetchSimilarMovies()
	}

	private fun fetchSimilarMovies() {
		similarMoviesAdapter = SimilarMoviesAdapter()
		binding.similarMoviesRecyclerview.apply {
			adapter = similarMoviesAdapter
			layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
		}

		viewModel.fetchSimilarMovies(args.movieID)

		viewModel.similarMoviesObservable.observe(viewLifecycleOwner){state->
			when(state){
				is State.Error->{

				}
				is State.Success->{
					similarMoviesAdapter.differ.submitList(state.data?.movieItems!!)
				}

				is State.Loading->{

				}
			}
		}

	}

	private fun fetchCast() {
		castAdapter = MovieCastAdapter()
		binding.castRecyclerview.apply {
			adapter = castAdapter
			layoutManager =
				LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
		}


		viewModel.fetchMovieCast(args.movieID)

		viewModel.movieCastObservable.observe(viewLifecycleOwner) { state ->
			when (state) {
				is State.Error -> {
					Toast.makeText(
						requireContext(),
						"Couldn't fetch cast. Please retry.",
						Toast.LENGTH_SHORT
					).show()
				}

				is State.Success -> {
					castAdapter.differ.submitList(state.data?.cast!!)
				}

				is State.Loading -> {

				}

			}

		}
	}

	private fun fetchData() {
		viewModel.fetchMovieDetails(args.movieID)

		viewModel.movieDetailsObservable.observe(viewLifecycleOwner) { state ->
			when (state) {
				is State.Error -> {
					mdialog.hide()
					binding.uiStateLayout.visibility = View.VISIBLE
					/*binding.progressBar.visibility = View.GONE*/
					binding.errorMsgTextview.visibility = View.VISIBLE
					binding.errorMsgTextview.text = state.message
					binding.retryBtn.visibility = View.VISIBLE
				}

				is State.Success -> {
					mdialog.hide()
					binding.uiStateLayout.visibility = View.GONE
					binding.views.visibility = View.VISIBLE
					bindUi(state.data!!)

				}

				is State.Loading -> {
/*					binding.progressBar.visibility = View.VISIBLE*/
					binding.errorMsgTextview.visibility = View.GONE
					binding.retryBtn.visibility = View.GONE
					mdialog.setTitle("Working")
					mdialog.setMessage("Please wait.")
					mdialog.show()
				}
			}
		}
	}

	private fun bindUi(movie: MovieDetailsResponse) {

		binding.imageView.load(Constants.BASE_POSTER_URL.plus(movie.posterPath)) {
			error(R.drawable.no_picture_icon)
			placeholder(R.drawable.loading)
		}

		binding.movieTitleTextView.text = movie.title
		binding.releaseDateTextview.text = movie.releaseDate
		binding.synopsisTextview.text = movie.overview
		binding.ratingTextview.text = movie.voteAverage.toString()

		val hours = movie.runtime?.div(60)
		val minutes = movie.runtime?.rem(60)
		binding.runtimeTextview.text =
			hours.toString().plus("hr ").plus(minutes.toString()).plus("min")

		//TODO soma fragment lifecyle vizuri and understand why the movies fragment glitches on resume

	}


}