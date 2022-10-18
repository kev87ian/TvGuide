package com.kev.tvguide.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kev.tvguide.R
import com.kev.tvguide.databinding.FragmentTopRatedMoviesBinding
import com.kev.tvguide.utils.Resource
import com.kev.tvguide.view.adapters.MoviesNowPlayingAdapter
import com.kev.tvguide.view.adapters.PaginatedMoviesLoadStateAdapter
import com.kev.tvguide.view.adapters.TopRatedMoviesPagingAdapter
import com.kev.tvguide.viewmodel.MoviesNowPlayingViewModel
import com.kev.tvguide.viewmodel.TopRatedMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopRatedMoviesFragment : Fragment(R.layout.fragment_top_rated_movies) {

	private var _binding: FragmentTopRatedMoviesBinding? = null
	private val binding get() = _binding!!

	private lateinit var moviesPagingAdapter: TopRatedMoviesPagingAdapter
	private val viewModel: TopRatedMoviesViewModel by viewModels()

	private val nowPlayingViewModel : MoviesNowPlayingViewModel by viewModels()
	private lateinit var nowPlayingAdapter : MoviesNowPlayingAdapter

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.shimmerLayout.startShimmer()

		(activity as AppCompatActivity).supportActionBar?.title = "Tv Guide"
		initTopRatedMoviesRecyclerView()
		observePaginationUiState()
		loadTopRatedMovies()
		observePaginationState()

		binding.btnRetry.setOnClickListener {
			retryFetchingMovies()
		}
		binding.nowPlayingRetryBtn.setOnClickListener {
			fetchMoviesNowPlaying()
		}


		initMoviesNowPlayingAdapter()
		fetchMoviesNowPlaying()
		observeNowPlayingUi()

	}


	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentTopRatedMoviesBinding.inflate(inflater, container, false)
		return binding.root
	}





	private fun fetchMoviesNowPlaying() {
		nowPlayingViewModel.fetchMoviesNowPlaying()
	}


	private fun observeNowPlayingUi(){
		nowPlayingViewModel.moviesNowPlayingObservable.observe(viewLifecycleOwner){state->
			when(state){
				is Resource.Loading ->{
					binding.shimmerLayoutNowPlaying.startShimmer()
					binding.shimmerLayoutNowPlaying.visibility = View.VISIBLE
					binding.nowPlayingRetryBtn.visibility = View.GONE
					binding.nowPlayingErrorTextview.visibility = View.GONE
				}

				is Resource.Success->{
					binding.shimmerLayoutNowPlaying.stopShimmer()
					binding.shimmerLayoutNowPlaying.visibility = View.GONE
					nowPlayingAdapter.differ.submitList(state.data)
				}
				is Resource.Error->{

					binding.shimmerLayoutNowPlaying.visibility = View.GONE
					binding.nowPlayingErrorTextview.visibility = View.VISIBLE
					binding.nowPlayingErrorTextview.text = state.message
					binding.nowPlayingRetryBtn.visibility = View.VISIBLE
					Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()

				}
			}

		}
	}

	private fun initMoviesNowPlayingAdapter(){
		nowPlayingAdapter = MoviesNowPlayingAdapter()
		binding.moviesNowPlayingRecyclerView.apply {
			adapter = nowPlayingAdapter
			layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

		}
	}


	private fun retryFetchingMovies() {
		moviesPagingAdapter.retry()
	}

	private fun observePaginationState() {
		binding.topRatedRecyclerview.adapter = moviesPagingAdapter.withLoadStateHeaderAndFooter(
			header = PaginatedMoviesLoadStateAdapter(moviesPagingAdapter::retry),
			footer = PaginatedMoviesLoadStateAdapter(moviesPagingAdapter::retry)
		)


	}


	private fun initTopRatedMoviesRecyclerView() {
		moviesPagingAdapter = TopRatedMoviesPagingAdapter()
		binding.topRatedRecyclerview.apply {
			adapter = moviesPagingAdapter
			layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
			//layoutManager = GridLayoutManager(requireContext(), 2)
		}
	}

	private fun observePaginationUiState() {
		moviesPagingAdapter.addLoadStateListener { loadstate ->
			if (loadstate.refresh is LoadState.Loading) {
				binding.errorMsgTextview.visibility = View.GONE
				binding.btnRetry.visibility = View.GONE
				binding.shimmerLayout.visibility = View.VISIBLE
				binding.shimmerLayout.startShimmer()
			}
			else if (loadstate.refresh is LoadState.Error) {
				binding.shimmerLayout.visibility = View.GONE
				binding.errorMsgTextview.visibility = View.VISIBLE
				binding.btnRetry.visibility = View.VISIBLE
			} else {
				binding.errorMsgTextview.visibility = View.GONE
				binding.btnRetry.visibility = View.GONE
				binding.shimmerLayout.stopShimmer()
				binding.shimmerLayout.visibility = View.GONE
			}
		}
	}

	private fun loadTopRatedMovies() {
		lifecycleScope.launch {
			viewModel.fetchPopularMovies().collect { moviesPagingAdapter.submitData(it) }
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}