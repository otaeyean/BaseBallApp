package com.example.baseballapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostAdapter(
    private var postList: List<BoardData>,
    private val onItemClick: (BoardData) -> Unit
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val content: TextView = itemView.findViewById(R.id.tv_content)
        val author: TextView = itemView.findViewById(R.id.tv_author)
        val createdAt: TextView = itemView.findViewById(R.id.tv_created_at)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view).apply {
            itemView.setOnClickListener {
                onItemClick(postList[adapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]
        holder.title.text = post.title
        holder.content.text = post.content
        holder.author.text = post.authorId
        holder.createdAt.text = post.createdAt
    }

    override fun getItemCount(): Int = postList.size

    fun setPosts(posts: List<BoardData>) {
        postList = posts
        notifyDataSetChanged()
    }
}
