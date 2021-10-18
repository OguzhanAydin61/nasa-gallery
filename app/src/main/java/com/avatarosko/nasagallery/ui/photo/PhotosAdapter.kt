package com.avatarosko.nasagallery.ui.photo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avatarosko.nasagallery.R
import com.avatarosko.nasagallery.data.model.ResponsePhoto
import com.avatarosko.nasagallery.databinding.ItemPhotoBinding
import com.avatarosko.nasagallery.di.GlideApp

class PhotosAdapter(
    private val onClick: (ResponsePhoto) -> Unit
) : ListAdapter<ResponsePhoto, PhotosAdapter.PhotoViewHolder>(DIFF) {
    companion object {
        val DIFF = object : DiffUtil.ItemCallback<ResponsePhoto>() {
            override fun areItemsTheSame(oldItem: ResponsePhoto, newItem: ResponsePhoto): Boolean =
                oldItem.mId == newItem.mId

            override fun areContentsTheSame(
                oldItem: ResponsePhoto,
                newItem: ResponsePhoto
            ): Boolean = oldItem.mImgSrc == newItem.mImgSrc
        }
    }

    inner class PhotoViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photoItem: ResponsePhoto) {
            GlideApp.with(binding.root).load(photoItem.mImgSrc).error(R.drawable.img_error)
                .into(binding.root)
            binding.root.setOnClickListener {
                onClick.invoke(photoItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }
}