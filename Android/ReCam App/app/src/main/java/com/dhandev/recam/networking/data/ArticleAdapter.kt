package com.dhandev.recam.networking.data

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dhandev.recam.databinding.ItemRowResultBinding
import com.dhandev.recam.networking.data.response.ArticlesItem
import com.dhandev.recam.networking.data.response.ResponseArticle
import com.dhandev.recam.networking.data.response.ResponsePaperItem
import com.dhandev.recam.withDateFormat
import kotlin.math.min

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.UserViewHolder>() {
    private val list = ArrayList<ArticlesItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallBack(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(users: List<ArticlesItem>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: ItemRowResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var context = itemView.context
        fun bind(user: ArticlesItem) {
            binding.apply {
                root.setOnClickListener{
                    onItemClickCallback?.onItemClicked(user)
                }
                Glide.with(itemView)
                    .load(user.urlToImage)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(imageView2)
                Title.text = user.title
                desc.text = user.author
            }
            Log.e(ArticleAdapter::class.java.simpleName,"User : ${user} ")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemRowResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener { onItemClickCallback?.onItemClicked(list[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
//        val limit = 3
//        return min(list.size, limit)
        return list.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: ArticlesItem)
    }
}