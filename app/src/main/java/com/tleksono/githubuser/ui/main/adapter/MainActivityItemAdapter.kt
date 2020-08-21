package com.tleksono.githubuser.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tleksono.githubuser.R
import com.tleksono.githubuser.domain.model.User
import kotlinx.android.synthetic.main.item_main_content.view.*

/**
 * Created by trileksono on 18/08/20
 */
class MainActivityItemAdapter(private val users: MutableList<User?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_VIEW_TYPE_CONTENT = 1
        const val ITEM_VIEW_TYPE_LOADING = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_CONTENT -> ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_main_content, parent, false)
            )
            else -> LoadingViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (users[position] == null) ITEM_VIEW_TYPE_LOADING else ITEM_VIEW_TYPE_CONTENT
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == ITEM_VIEW_TYPE_CONTENT) {
            holder.itemView.run {
                users[position]?.let { user ->
                    tvUsername.text = user.login
                    Glide.with(this)
                        .load(user.avatarUrl)
                        .into(imgAvatar)
                }
            }
        }
    }

    fun addLoadingView() {
        users.add(null)
        notifyItemInserted(users.size - 1)
    }

    fun addContent(data: MutableList<User>) {
        users.addAll(data)
        notifyItemRangeInserted(users.size, users.size + data.size)
    }

    fun clear() {
        users.clear()
        notifyDataSetChanged()
    }

    fun removeLoadingView() {
        //Remove loading item
        if (users.size != 0) {
            users.removeAt(users.size - 1)
            notifyItemRemoved(users.size)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)

}
