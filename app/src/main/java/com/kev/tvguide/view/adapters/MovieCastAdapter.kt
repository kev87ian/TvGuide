package com.kev.tvguide.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kev.tvguide.R
import com.kev.tvguide.databinding.CastLayoutItemBinding
import com.kev.tvguide.models.Cast
import com.kev.tvguide.utils.Constants

class MovieCastAdapter : RecyclerView.Adapter<MovieCastAdapter.CastViewHolder>() {

	class CastViewHolder(val binding: CastLayoutItemBinding) : RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {

		val binding = CastLayoutItemBinding.inflate(
			LayoutInflater.from(parent.context), parent, false
		)

		return CastViewHolder(binding)

	}

	override fun onBindViewHolder(holder: CastViewHolder, position: Int) {

		val currentCast = differ.currentList[position]
		with(holder){
			binding.characterNameTv.text = currentCast.name

			binding.characterImageView.load(Constants.BASE_POSTER_URL.plus(currentCast.profilePath)){
				error(R.drawable.no_picture_icon)
			}
		}
	}

	override fun getItemCount(): Int {
		return differ.currentList.size
	}


	private val diffUtil = object : DiffUtil.ItemCallback<Cast>() {
		override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
			return oldItem.id == newItem.id
		}

		override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
			return oldItem == newItem
		}
	}

	val differ = AsyncListDiffer(this, diffUtil)

}