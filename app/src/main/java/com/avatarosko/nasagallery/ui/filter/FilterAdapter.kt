package com.avatarosko.nasagallery.ui.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avatarosko.nasagallery.common.model.CameraTypes
import com.avatarosko.nasagallery.common.model.FilterAdapterModel
import com.avatarosko.nasagallery.databinding.ItemFilterBinding

class FilterAdapter : ListAdapter<FilterAdapterModel, FilterAdapter.FilterViewHolder>(DIFF) {
    companion object DIFF : DiffUtil.ItemCallback<FilterAdapterModel>() {
        override fun areItemsTheSame(
            oldItem: FilterAdapterModel,
            newItem: FilterAdapterModel
        ): Boolean = oldItem.cameraTypes == newItem.cameraTypes

        override fun areContentsTheSame(
            oldItem: FilterAdapterModel,
            newItem: FilterAdapterModel
        ): Boolean = oldItem.isChecked == newItem.isChecked

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder =
        FilterViewHolder(
            ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    private fun onSelected(selectedCameraType: CameraTypes) {
        val newList = currentList.toMutableList().map { it.copy() }
        newList.forEach {
            it.isChecked = (it.cameraTypes == selectedCameraType && it.isChecked.not())
        }
        submitList(newList)
    }


    inner class FilterViewHolder(
        private val binding: ItemFilterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FilterAdapterModel) {
            binding.root.text = item.cameraTypes.name
            binding.root.isChecked = item.isChecked
            binding.root.setOnCheckedChangeListener { _, _ ->
                onSelected(item.cameraTypes)
            }
        }
    }


}