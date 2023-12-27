package com.razqy26.suitmediatest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.razqy26.suitmediatest.databinding.ItemUsersBinding
import com.razqy26.suitmediatest.response.DataItem

class UserAdapter(private val onItemClick: (String) -> Unit) : ListAdapter<DataItem, UserAdapter.ViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user, onItemClick)
    }

    inner class ViewHolder(private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: DataItem,onItemClick: (String) -> Unit) {
            with(binding) {
                tvUsername.text = "${user.firstName} ${user.lastName}"
                tvEmail.text = user.email
                Glide.with(root.context)
                    .load(user.avatar)
                    .into(imgAvatar)

                binding.root.setOnClickListener {
                    onItemClick("${user.firstName} ${user.lastName}")
                }
            }
        }

    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}
