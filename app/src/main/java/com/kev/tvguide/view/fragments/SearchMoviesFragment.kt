package com.kev.tvguide.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.kev.tvguide.R
import com.kev.tvguide.databinding.FragmentSearchMoviesBinding
import com.kev.tvguide.utils.State
import com.kev.tvguide.view.adapters.SearchMoviesAdapter
import com.kev.tvguide.viewmodel.SearchMovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchMoviesFragment : Fragment(R.layout.fragment_search_movies) {

	private var _binding: FragmentSearchMoviesBinding? = null
	private val binding get() = _binding!!

	private val viewModel: SearchMovieViewModel by viewModels()
	private lateinit var myAdapter: SearchMoviesAdapter

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		fetchData()
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View{
		_binding = FragmentSearchMoviesBinding.inflate(inflater, container, false)
		return binding.root
	}

	private fun fetchData() {
		myAdapter = SearchMoviesAdapter()
		binding.recyclerView.apply {
			adapter = myAdapter
			layoutManager = GridLayoutManager(requireContext(), 2)
		}


		var job:Job? = null
		binding.searchView.addTextChangedListener { editable->
			job?.cancel()
			job = MainScope().launch {
				delay(500L)
				editable?.let {
					if (editable.toString().isNotBlank()){
						viewModel.searchForMovies(editable.toString())
					}
				}
			}
		}

		viewModel.searchMovieObservable.observe(viewLifecycleOwner){state->
			when(state){
				is State.Loading->{
					binding.errorMsgTextview.visibility = View.GONE
					binding.progressBar.visibility = View.VISIBLE
					binding.recyclerView.visibility = View.GONE
				}

				is State.Success->{
					binding.progressBar.visibility = View.GONE
					binding.recyclerView.visibility = View.VISIBLE
					myAdapter.differ.submitList(state.data?.movieItems!!)
				}

				is State.Error->{
					binding.recyclerView.visibility = View.GONE
					binding.progressBar.visibility = View.GONE
					binding.errorMsgTextview.visibility = View.VISIBLE
					binding.errorMsgTextview.text = state.message
				}
			}


		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}