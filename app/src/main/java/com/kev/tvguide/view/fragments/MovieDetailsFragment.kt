package com.kev.tvguide.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.kev.tvguide.R
import com.kev.tvguide.R.layout.fragment_movie_details
import com.kev.tvguide.databinding.FragmentMovieDetailsBinding
import com.kev.tvguide.model.data.MovieDetailsResponse
import com.kev.tvguide.utils.Resource
import com.kev.tvguide.viewmodel.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(fragment_movie_details){
	private var _binding  : FragmentMovieDetailsBinding? = null
	private val binding get() = _binding!!

	private val viewModel : MovieDetailsViewModel by viewModels()

	private val args : MovieDetailsFragmentArgs by navArgs()

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

		viewModel.movieDetailsObservable.observe(viewLifecycleOwner){resource->
			when(resource){
				is Resource.Error->{
					//TODO
				}

				is Resource.Success ->{
				//	Toast.makeText(requireContext(), args.movieID.toString(), Toast.LENGTH_SHORT).show()
					bindUi(resource.data!!)

				}

				is Resource.Loading ->{
				}
			}
		}
	}

	private fun bindUi(data: MovieDetailsResponse) {

		Toast.makeText(requireContext(), data.overview, Toast.LENGTH_SHORT).show()

	}


}