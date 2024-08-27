package com.example.baseballapp

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseballapp.databinding.FragmentChatingBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class ChatingFragment : Fragment() {
    private lateinit var webSocket: WebSocket
    private lateinit var binding: FragmentChatingBinding
    private lateinit var nickname: String
    private val messageList = ArrayList<ChatMessageData>()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val roomId = arguments?.getString("ROOM_ID") ?: "default"
        nickname = arguments?.getString("NICKNAME") ?: "soo_.ob"

        Log.d("ChatFragment", "Room ID: $roomId")
        Log.d("ChatFragment", "Nickname: $nickname")

        loadMessages(roomId)

        val client = OkHttpClient()
        val request = Request.Builder().url("ws://35.216.0.159:8080/ws/chat/$roomId").build()
        val listener = ChatWebSocketListener(this)

        webSocket = client.newWebSocket(request, listener)

        chatAdapter = ChatAdapter(messageList, nickname)
        binding.recyclerViewMessages.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewMessages.adapter = chatAdapter

        binding.buttonSend.setOnClickListener {
            val message = binding.editTextMessage.text.toString()
            if (message.isNotEmpty()) {
                val time = getCurrentTimeInKorea()
                val formattedMessage = "$nickname $time ] $message"

                webSocket.send(formattedMessage)
                saveMessage(roomId, formattedMessage)
                binding.editTextMessage.text.clear()
            }
        }
    }

    private fun getCurrentTimeInKorea(): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.KOREAN).apply {
            timeZone = TimeZone.getTimeZone("Asia/Seoul")
        }
        return dateFormat.format(Date())
    }

    fun showMessage(message: String) {
        val chatMessage = ChatMessageData(R.drawable.baseball, message)
        addMessage(chatMessage)
    }

    private fun addMessage(chatMessage: ChatMessageData) {
        messageList.add(chatMessage)
        chatAdapter.notifyDataSetChanged()
        binding.recyclerViewMessages.scrollToPosition(messageList.size - 1)
    }

    private fun saveMessage(roomId: String, message: String) {
        val sharedPreferences = requireActivity().getSharedPreferences("ChatPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val currentMessages = sharedPreferences.getString(roomId, "") ?: ""
        editor.putString(roomId, currentMessages + message + "\n")
        editor.apply()
    }

    private fun loadMessages(roomId: String) {
        val sharedPreferences = requireActivity().getSharedPreferences("ChatPrefs", Context.MODE_PRIVATE)
        val savedMessages = sharedPreferences.getString(roomId, "") ?: ""

        savedMessages.split("\n").forEach { msg ->
            if (msg.isNotEmpty()) {
                val chatMessage = ChatMessageData(R.drawable.baseball, msg)
                messageList.add(chatMessage)
            }
        }
    }
}
