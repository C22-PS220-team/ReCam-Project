package com.dhandev.recam.networking.data.result

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dhandev.recam.databinding.ItemPointBinding
import com.dhandev.recam.databinding.ItemRowResultBinding
import com.dhandev.recam.networking.data.response.ResponsePaperItem

class StepAdapter : RecyclerView.Adapter<StepAdapter.UserViewHolder>() {
    private val list = ArrayList<String>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallBack(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(users: List<String>) {
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: ItemPointBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var context = itemView.context
        fun bind(user: String) {
            binding.apply {
                content.text = user
//                itemView.setOnClickListener {
//                    val intent = Intent(context, MainActivity::class.java)
//                    intent.putExtra("item", user)
//                    context.startActivity(intent)
//                }
            }
            Log.e(StepAdapter::class.java.simpleName,"User : ${user} ")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemPointBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(user: ResponsePaperItem)
    }
}