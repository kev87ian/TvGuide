package com.kev.tvguide.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kev.tvguide.R
import com.kev.tvguide.databinding.FragmentFavoriteMoviesBinding
import com.kev.tvguide.view.adapters.FavoriteMoviesAdapter
import com.kev.tvguide.viewmodel.FavoriteMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMoviesFragment : Fragment(R.layout.fragment_favorite_movies) {
	private var _binding: FragmentFavoriteMoviesBinding? = null
	private val binding get() = _binding!!

	private val viewModel: FavoriteMoviesViewModel by viewModels()
	private lateinit var favoriteMoviesAdapter: FavoriteMoviesAdapter

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false)
		return binding.root
	}


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		fetchData()
	}

	private fun fetchData(){
		favoriteMoviesAdapter = FavoriteMoviesAdapter()
		binding.recyclerview.apply {
			adapter = favoriteMoviesAdapter
			layoutManager = LinearLayoutManager(requireContext())
		}

		if (viewModel.countRecords().isEmpty()){
			binding.textView.visibility = View.VISIBLE

		}
		else {
			viewModel.getSavedMovies().observe(viewLifecycleOwner) {
				favoriteMoviesAdapter.differ.submitList(it)
			}
		}


	}
	override fun onDestroy() {
		_binding = null
		super.onDestroy()
	}
}