package com.dhandev.recam.networking.data

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dhandev.recam.databinding.ItemRowResultBinding
import com.dhandev.recam.networking.data.response.ResponsePaperItem

class RecycleAdapter : RecyclerView.Adapter<RecycleAdapter.UserViewHolder>() {
    private val list = ArrayList<ResponsePaperItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallBack(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(users: ArrayList<ResponsePaperItem>) {
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: ItemRowResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var context = itemView.context
        fun bind(user: ResponsePaperItem) {
            binding.apply {
                root.setOnClickListener{
                    onItemClickCallback?.onItemClicked(user)
                }
                Glide.with(itemView)
                    .load(user.image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(imageView2)
                Title.text = user.judul
                desc.text = user.isih1
//                itemView.setOnClickListener {
//                    val intent = Intent(context, MainActivity::class.java)
//                    intent.putExtra("item", user)
//                    context.startActivity(intent)
//                }
            }
            Log.e(RecycleAdapter::class.java.simpleName,"User : ${user} ")
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

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(user: ResponsePaperItem)
    }
}