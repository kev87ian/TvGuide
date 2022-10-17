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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kev.tvguide.R
import com.kev.tvguide.databinding.FragmentTopRatedMoviesBinding
import com.kev.tvguide.view.adapters.PaginatedMoviesLoadStateAdapter
import com.kev.tvguide.view.adapters.TopRatedMoviesPagingAdapter
import com.kev.tvguide.viewmodel.TopRatedMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopRatedMoviesFragment : Fragment(R.layout.fragment_top_rated_movies) {

	private var _binding: FragmentTopRatedMoviesBinding? = null
	private val binding get() = _binding!!

	private lateinit var moviesPagingAdapter: TopRatedMoviesPagingAdapter
	private val viewModel: TopRatedMoviesViewModel by viewModels()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.shimmerLayout.startShimmer()

		(activity as AppCompatActivity).supportActionBar?.title = "Tv Guide"
		initTopRatedMoviesRecyclerView()
		observeUiState()
		loadTopRatedMovies()
		observePaginationState()

	}

	private fun observePaginationState() {
		binding.topRatedRecyclerview.adapter = moviesPagingAdapter.withLoadStateHeaderAndFooter(
			header = PaginatedMoviesLoadStateAdapter(moviesPagingAdapter::retry),
			footer = PaginatedMoviesLoadStateAdapter(moviesPagingAdapter::retry)
		)


	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentTopRatedMoviesBinding.inflate(inflater, container, false)
		return binding.root
	}

	private fun initTopRatedMoviesRecyclerView() {
		moviesPagingAdapter = TopRatedMoviesPagingAdapter()
		binding.topRatedRecyclerview.apply {
			adapter = moviesPagingAdapter
			layoutManager = GridLayoutManager(requireContext(), 2)
		}
	}

	private fun observeUiState() {
		moviesPagingAdapter.addLoadStateListener { loadstate ->
			if (loadstate.refresh is LoadState.Loading) {
				binding.shimmerLayout.visibility = View.VISIBLE
				binding.shimmerLayout.startShimmer()

			} else {
				binding.shimmerLayout.stopShimmer()
				binding.shimmerLayout.visibility = View.GONE
			}
		}
	}

	private fun loadTopRatedMovies() {
		lifecycleScope.launch {
			viewModel.listData.collect {
				moviesPagingAdapter.submitData(it)
			}
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}