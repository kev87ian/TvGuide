package com.kev.tvguide.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kev.tvguide.R
import com.kev.tvguide.databinding.LoadStateLayoutItemBinding

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
