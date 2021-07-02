package com.example.githubuser.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ListUserBinding
import com.example.githubuser.retrofit.UserModel

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    private val list = ArrayList<UserModel>()
    fun setterList(github: ArrayList<UserModel>) {
        list.clear()
        list.addAll(github)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userlist: UserModel) {
            binding.apply {

                Glide.with(itemView)
                    .load(userlist.avatar_url)
                    .into(imagePhoto)


                tvUsername.text = userlist.login
            }
            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(userlist)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ListUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: UserModel)
    }
}