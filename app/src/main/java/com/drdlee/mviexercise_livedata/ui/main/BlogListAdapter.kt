package com.drdlee.mviexercise_livedata.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.drdlee.mviexercise_livedata.databinding.LayoutBlogListItemBinding
import com.drdlee.mviexercise_livedata.models.Blog

class BlogListAdapter() :
    ListAdapter<Blog, BlogListAdapter.BlogViewHolder>(BlogListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        return BlogViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BlogViewHolder(val binding: LayoutBlogListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Blog) {
            binding.blogItem = item
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): BlogViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = LayoutBlogListItemBinding.inflate(layoutInflater, parent, false)

                return BlogViewHolder(view)
            }
        }
    }

    class BlogListDiffCallback : DiffUtil.ItemCallback<Blog>() {
        override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean {
            return oldItem.pk == newItem.pk
        }

        override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean {
            return oldItem == newItem
        }
    }
}