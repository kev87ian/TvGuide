package com.kev.tvguide.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.kev.tvguide.R
import com.kev.tvguide.databinding.FragmentTopRatedMoviesBinding
import com.kev.tvguide.utils.State
import com.kev.tvguide.view.adapters.MoviesNowPlayingAdapter
import com.kev.tvguide.view.adapters.UpcomingMoviesAdapter
import com.kev.tvguide.view.adapters.pagination.PaginatedMoviesLoadStateAdapter
import com.kev.tvguide.view.adapters.pagination.TopRatedMoviesPagingAdapter
import com.kev.tvguide.viewmodel.MoviesNowPlayingViewModel
import com.kev.tvguide.viewmodel.TopRatedMoviesViewModel
import com.kev.tvguide.viewmodel.UpcomingMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopRatedMoviesFragment : Fragment(R.layout.fragment_top_rated_movies) {

	private var _binding: FragmentTopRatedMoviesBinding? = null
	private val binding get() = _binding!!

	private lateinit var moviesPagingAdapter: TopRatedMoviesPagingAdapter
	private val viewModel: TopRatedMoviesViewModel by viewModels()

	private val nowPlayingViewModel: MoviesNowPlayingViewModel by viewModels()
	private lateinit var nowPlayingAdapter: MoviesNowPlayingAdapter

	private val upcomingMoviesViewModel: UpcomingMoviesViewModel by viewModels()
	private lateinit var upcomingMoviesAdapter: UpcomingMoviesAdapter


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		(activity as AppCompatActivity).supportActionBar?.title = "Tv Guide"

		fetchUpcomingMovies()
		loadTopRatedMovies()
		fetchMoviesNowPlaying()


		binding.upcomingRetryBtn.setOnClickListener {
			retryFetchingTopRatedMovies()
			fetchMoviesNowPlaying()
			fetchUpcomingMovies()
		}

	}


	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentTopRatedMoviesBinding.inflate(inflater, container, false)
		return binding.root
	}


	private fun fetchUpcomingMovies() {

		upcomingMoviesAdapter = UpcomingMoviesAdapter()
		binding.upcomingMoviesRecyclerView.apply {
			adapter = upcomingMoviesAdapter
			layoutManager =
				LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
		}

		upcomingMoviesViewModel.fetchUpcomingMovies()


		upcomingMoviesViewModel.upcomingMoviesObservable.observe(viewLifecycleOwner) { resource ->
			when (resource) {
				is State.Loading -> {

					binding.shimmerLayoutUpcomingMovies.startShimmer()
					binding.upcomingMoviesErrorTextview.visibility = View.GONE
					binding.upcomingRetryBtn.visibility = View.GONE
				}
				is State.Success -> {
					binding.shimmerLayoutUpcomingMovies.stopShimmer()
					binding.shimmerLayoutUpcomingMovies.visibility = View.GONE
					binding.upcomingMoviesRecyclerView.visibility = View.VISIBLE
					upcomingMoviesAdapter.differ.submitList(resource.data)
				}
				is State.Error -> {

					binding.shimmerLayoutUpcomingMovies.visibility = View.GONE
					binding.upcomingRetryBtn.visibility = View.VISIBLE
					binding.upcomingMoviesErrorTextview.visibility = View.VISIBLE
					binding.upcomingMoviesErrorTextview.text = resource.message

				}
			}

		}


	}


	private fun fetchMoviesNowPlaying() {
		nowPlayingAdapter = MoviesNowPlayingAdapter()
		binding.moviesNowPlayingRecyclerView.apply {
			adapter = nowPlayingAdapter
			layoutManager =
				LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

		}
		//performs the network call
		nowPlayingViewModel.fetchMoviesNowPlaying()

		//observes changes in the live data to keep the UI update
		nowPlayingViewModel.moviesNowPlayingObservable.observe(viewLifecycleOwner) { state ->
			when (state) {
				is State.Loading -> {

					binding.shimmerLayoutNowPlaying.startShimmer()
					binding.nowPlayingRetryBtn.visibility = View.GONE
					binding.nowPlayingErrorTextview.visibility = View.GONE
				}

				is State.Success -> {
					binding.shimmerLayoutNowPlaying.stopShimmer()
					binding.shimmerLayoutNowPlaying.visibility = View.GONE
					binding.moviesNowPlayingRecyclerView.visibility = View.VISIBLE
					nowPlayingAdapter.differ.submitList(state.data)
				}
				is State.Error -> {

					binding.shimmerLayoutNowPlaying.visibility = View.GONE

					/*	binding.nowPlayingErrorTextview.visibility = View.VISIBLE
						binding.nowPlayingErrorTextview.text = state.message
						binding.nowPlayingRetryBtn.visibility = View.VISIBLE*/


				}
			}

		}
	}


	private fun retryFetchingTopRatedMovies() {
		moviesPagingAdapter.retry()
	}


	private fun loadTopRatedMovies() {

		moviesPagingAdapter = TopRatedMoviesPagingAdapter()
		binding.topRatedRecyclerview.apply {
			adapter = moviesPagingAdapter
			layoutManager =
				LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

		}


		/*This handles the loading and error state of the pagination. (Responsible for Errors/progress bar shown at the end of the currently paginated items) */
		binding.topRatedRecyclerview.adapter = moviesPagingAdapter.withLoadStateHeaderAndFooter(
			header = PaginatedMoviesLoadStateAdapter(moviesPagingAdapter::retry),
			footer = PaginatedMoviesLoadStateAdapter(moviesPagingAdapter::retry)
		)

		lifecycleScope.launch {
			viewModel.fetchPopularMovies().collect { moviesPagingAdapter.submitData(it) }
		}

		moviesPagingAdapter.addLoadStateListener { loadstate ->

			if (loadstate.refresh is LoadState.Loading) {

				binding.shimmerLayoutToprated.startShimmer()
				binding.errorMsgTextview.visibility = View.GONE
				binding.topRatedMoviesBtnRetry.visibility = View.GONE


			} else if (loadstate.refresh is LoadState.Error) {
				binding.shimmerLayoutToprated.visibility = View.GONE
				/*			binding.errorMsgTextview.visibility = View.VISIBLE
							binding.topRatedMoviesBtnRetry.visibility = View.VISIBLE*/
			} else {
				binding.errorMsgTextview.visibility = View.GONE
				binding.shimmerLayoutToprated.visibility = View.GONE
				binding.topRatedRecyclerview.visibility = View.VISIBLE
			}


		}
	}


	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}


}