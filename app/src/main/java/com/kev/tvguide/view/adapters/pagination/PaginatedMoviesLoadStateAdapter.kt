package com.kev.tvguide.view.adapters.pagination

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class PaginatedMoviesLoadStateAdapter(
	private val retry:() -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {

	override fun onBindViewHolder(
		holder: LoadStateViewHolder,
		loadState: LoadState
	) = holder.bind(loadState)

	override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) = LoadStateViewHolder(parent, retry)
}
/*

}*/
