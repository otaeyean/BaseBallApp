package com.example.baseballapp.Community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.baseballapp.CommentData
import com.example.baseballapp.R

class CommentAdapter(
    private var commentList: List<CommentData>,
    private val currentUsername: String, // 현재 로그인한 사용자 이름을 전달
    private val onDeleteComment: (Long) -> Unit // 댓글 삭제 함수
) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userImage: ImageView = itemView.findViewById(R.id.iv_user_image)
        val author: TextView = itemView.findViewById(R.id.tv_comment_author)
        val content: TextView = itemView.findViewById(R.id.tv_comment_content)
        val createdAt: TextView = itemView.findViewById(R.id.tv_comment_created_at)
        val deleteButton: Button = itemView.findViewById(R.id.btn_delete_comment) // 삭제 버튼을 일반 버튼으로 변경
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = commentList[position]
        holder.author.text = comment.authorId
        holder.content.text = comment.content
        holder.createdAt.text = comment.createdAt.substring(0, 10)

        // 현재 로그인된 사용자와 댓글 작성자가 같을 때만 삭제 버튼을 보이게 함
        if (comment.authorId == currentUsername) {
            holder.deleteButton.visibility = View.VISIBLE
            holder.deleteButton.setOnClickListener {
                onDeleteComment(comment.id.toLong()) // 댓글 ID를 Long 타입으로 변환하여 호출
            }
        } else {
            holder.deleteButton.visibility = View.GONE
        }
    }


    override fun getItemCount(): Int = commentList.size

    fun setComments(comments: List<CommentData>) {
        commentList = comments
        notifyDataSetChanged()
    }
}
