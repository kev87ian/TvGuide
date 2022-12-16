package com.kev.tvguide.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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
		swipeToDelete()


	}



	private fun fetchData() {
		favoriteMoviesAdapter = FavoriteMoviesAdapter()
		binding.recyclerview.apply {
			adapter = favoriteMoviesAdapter
			layoutManager = GridLayoutManager(requireContext(), 2)
		}


		viewModel.getSavedMovies().observe(viewLifecycleOwner){
			if (it.isEmpty()){
				binding.textView.visibility = View.VISIBLE
			}
			else{
				binding.textView.visibility = View.GONE
				favoriteMoviesAdapter.differ.submitList(it)
			}
		}


	}

	private fun swipeToDelete() {
		val itemTouchCallBack = object : ItemTouchHelper.SimpleCallback(
			ItemTouchHelper.UP or ItemTouchHelper.DOWN,
			ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
		) {
			override fun onMove(
				recyclerView: RecyclerView,
				viewHolder: RecyclerView.ViewHolder,
				target: RecyclerView.ViewHolder
			): Boolean {
				return true
			}

			override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
				val position = viewHolder.absoluteAdapterPosition
				val movie = favoriteMoviesAdapter.differ.currentList[position]
				viewModel.deleteMovie(movie)

			}
		}

		ItemTouchHelper(itemTouchCallBack).apply {
			attachToRecyclerView(binding.recyclerview)
		}


	}


	override fun onDestroy() {
		_binding = null
		super.onDestroy()
	}
}