package com.kev.tvguide.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.kev.tvguide.R
import com.kev.tvguide.databinding.LoadStateLayoutItemBinding

class PaginatedMoviesLoadStateAdapter(
	parent: ViewGroup,
	retry: () -> Unit
) : RecyclerView.ViewHolder(
	LayoutInflater.from(parent.context).inflate(R.layout.load_state_layout_item, parent, false)
) {
	private val binding = LoadStateLayoutItemBinding.bind(itemView)
	private val progressBar: ProgressBar = binding.progressBar
	private val errorTextView: TextView = binding.errorMsgTextview
	private val retryButton: Button = binding.btnRetry
		.also {
			it.setOnClickListener {
				retry()
			}
		}


	fun bind(loadState: LoadState) {
		if (loadState is LoadState.Error) {
			errorTextView.text = loadState.error.localizedMessage
		}
		progressBar.isVisible = loadState is LoadState.Loading
		retryButton.isVisible = loadState is LoadState.Error
		errorTextView.isVisible = loadState is LoadState.Error
	}
}