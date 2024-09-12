package com.example.baseballapp

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val chatMessagesData: List<ChatMessageData>, private val nickname: String) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val teamLogo: ImageView = itemView.findViewById(R.id.teamLogo)
        val chatMessage: TextView = itemView.findViewById(R.id.chatMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatMessageData = chatMessagesData[position]
        chatMessageData.teamLogoResId?.let { holder.teamLogo.setImageResource(it) }


        val message = chatMessageData.message
        val spannableMessage = SpannableString(message)

        val time = message.substringAfter("$nickname ").substringBefore(" ]")
        val start = message.indexOf(time)
        val end = start + time.length

        if (start >= 0) {
            spannableMessage.setSpan(
                ForegroundColorSpan(Color.GRAY),
                start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        holder.chatMessage.text = spannableMessage

        holder.chatMessage.textSize = 24f
    }

    override fun getItemCount(): Int {
        return chatMessagesData.size
    }
}