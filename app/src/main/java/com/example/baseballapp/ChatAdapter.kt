import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.baseballapp.ChatMessageData
import com.example.baseballapp.R

class ChatAdapter(private val messages: List<ChatMessageData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_CURRENT_USER = 1
        private const val VIEW_TYPE_OTHER_USER = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isCurrentUser) {
            VIEW_TYPE_CURRENT_USER
        } else {
            VIEW_TYPE_OTHER_USER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_CURRENT_USER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_current_user, parent, false)
            CurrentUserViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_other_user, parent, false)
            OtherUserViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chatMessage = messages[position]
        if (holder is CurrentUserViewHolder) {
            holder.bind(chatMessage)
        } else if (holder is OtherUserViewHolder) {
            holder.bind(chatMessage)
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    inner class CurrentUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.chatMessage)

        fun bind(chatMessage: ChatMessageData) {
            messageTextView.text = chatMessage.message
        }
    }

    inner class OtherUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.chatMessage)

        fun bind(chatMessage: ChatMessageData) {
            messageTextView.text = chatMessage.message
        }
    }
}
