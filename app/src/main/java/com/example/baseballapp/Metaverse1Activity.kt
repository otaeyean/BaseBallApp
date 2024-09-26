package com.example.baseballapp

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import okhttp3.WebSocket
import org.json.JSONArray

class Metaverse1Activity : AppCompatActivity() {

    private lateinit var character: ImageView
    private lateinit var chatMessage: TextView
    private lateinit var npc1Text: TextView
    private lateinit var npc2Text: TextView
    private lateinit var npc3Text: TextView
    private val chatBubbles = mutableMapOf<String, TextView>()

    private val step = 80
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var webSocket: WebSocket

    private val userPositions = mutableMapOf<String, Pair<Float, Float>>()
    private var nickname = "sumin"
    private val userList= mutableListOf<String>()
    private val userCharacters = mutableMapOf<String, ImageView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metaverse1)

        character = findViewById(R.id.character)
        chatMessage = findViewById(R.id.chat_message)

        lateinit var mediaPlayer : MediaPlayer
        val buttonUp: Button = findViewById(R.id.button_up)
        val buttonDown: Button = findViewById(R.id.button_down)
        val buttonLeft: Button = findViewById(R.id.button_left)
        val buttonRight: Button = findViewById(R.id.button_right)
        val chatButton: Button = findViewById(R.id.chat)
        val editTextMessage: EditText = findViewById(R.id.editTextMessage)

        npc1Text = findViewById(R.id.npc1text)
        npc2Text = findViewById(R.id.npc2text)
        npc3Text = findViewById(R.id.npc3text)
        mediaPlayer = MediaPlayer.create(this, R.raw.metaverse1)

        findViewById<Button>(R.id.button_play).setOnClickListener {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
            }
        }
        val metainfoImageView: ImageView = findViewById(R.id.metainfo)
        metainfoImageView.setOnClickListener {
            showInfoDialog()
        }

        buttonUp.setOnClickListener {
            moveCharacter(0, -step)
            Glide.with(this).asGif().load(R.drawable.standing).into(character)
        }
        buttonDown.setOnClickListener {
            moveCharacter(0, step)
            Glide.with(this).asGif().load(R.drawable.standing).into(character)
        }
        buttonLeft.setOnClickListener {
            moveCharacter(-step, 0)
            Glide.with(this).asGif().load(R.drawable.left_running).into(character)
        }
        buttonRight.setOnClickListener {
            moveCharacter(step, 0)
            Glide.with(this).asGif().load(R.drawable.right_running).into(character)
        }

        chatButton.setOnClickListener {
            val message = editTextMessage.text.toString()
            if (message.isNotBlank()) {
                sendChatMessage(message)
                editTextMessage.text.clear()
            }
        }

        Glide.with(this).asGif().load(R.drawable.standing).into(character)
        showNicknameDialog()
        initializeWebSocket()
    }

    private fun initializeWebSocket() {
        val client = OkHttpClient()
        val request = Request.Builder().url("ws://35.216.0.159:8080/ws/map/1234").build()
        val listener = MetaverseWebSocketListener(this)
        webSocket = client.newWebSocket(request, listener)
    }

    override fun onDestroy() {
        super.onDestroy()
        webSocket.close(1000, "Activity destroyed")
    }

    private fun showNicknameDialog() {
        val nicknameEditText = EditText(this)
        nicknameEditText.hint = "      닉네임 작성 후 확인을 눌러주세요 :)"

        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("닉네임 설정")
        dialogBuilder.setMessage("메타버스 안에서 사용할 닉네임을 설정하세요!")
        dialogBuilder.setView(nicknameEditText)

        dialogBuilder.setPositiveButton("확인") { dialog, _ ->
            val enteredNickname = nicknameEditText.text.toString().trim()
            if (enteredNickname.isNotEmpty()) {
                nickname = enteredNickname
            } else {
                nickname = "soo_.ob"
            }

            Toast.makeText(this, "닉네임이 설정되었습니다: $nickname", Toast.LENGTH_SHORT).show()

            sendNicknameData(nickname)
            showUserJoined(nickname)
            dialog.dismiss()
        }

        dialogBuilder.setCancelable(false)
        dialogBuilder.show()
    }

    private fun sendNicknameData(nickname: String) {
        val NicknameData = """{
            "type": "set-nickname",
            "nickname": "$nickname"
        }"""
        webSocket.send(NicknameData)
    }

    fun sendMovementData(x: Float, y: Float) {
        val movementData = """{
        "type": "move",
        "nickname": "$nickname",
        "x": ${x.toInt()},
        "y": ${y.toInt()}
    }"""
        webSocket.send(movementData)
    }

    private fun sendChatMessage(message: String) {
        val chatData = """{
        "type": "chat",
        "nickname": "$nickname",
        "message": "$message"
    }"""
        webSocket.send(chatData)

        showChatMessage(nickname, message)
    }

    private fun updateChatMessagePosition() {
        val characterLocation = IntArray(2)
        character.getLocationOnScreen(characterLocation)

        val characterX = characterLocation[0].toFloat()
        val characterY = characterLocation[1].toFloat()

        chatMessage.x = characterX + (character.width / 2) - (chatMessage.width / 2)
        chatMessage.y = characterY - chatMessage.height
    }

    fun showChatMessage(nickname: String, message: String) {
        val chatBubble = chatBubbles[nickname] ?: createChatBubbleForUser(nickname)

        chatBubble.text = "$nickname: $message"
        chatBubble.visibility = TextView.VISIBLE

        updateChatBubblePosition(nickname)

        handler.postDelayed({
            chatBubble.visibility = TextView.GONE
        }, 3000)
    }

    private fun createChatBubbleForUser(nickname: String): TextView {
        val chatBubble = TextView(this)
        chatBubble.setBackgroundResource(R.drawable.chat_bubble_background)
        chatBubble.setTextColor(Color.BLACK)
        chatBubble.setPadding(8, 8, 8, 8)
        chatBubble.layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val rootLayout: ViewGroup = findViewById(R.id.metverse1)
        rootLayout.addView(chatBubble)
        chatBubbles[nickname] = chatBubble

        return chatBubble
    }

    private fun updateChatBubblePosition(nickname: String) {
        val chatBubble = chatBubbles[nickname] ?: return
        val characterView = if (nickname == this.nickname) character else userCharacters[nickname] ?: return

        val characterLocation = IntArray(2)
        characterView.getLocationOnScreen(characterLocation)

        val characterX = characterLocation[0].toFloat()
        val characterY = characterLocation[1].toFloat()

        chatBubble.x = characterX + (characterView.width / 2) - (chatBubble.width / 2)
        chatBubble.y = characterY - chatBubble.height

        val rootLayout = findViewById<ViewGroup>(R.id.metverse1)
        chatBubble.x = chatBubble.x.coerceIn(0f, (rootLayout.width - chatBubble.width).toFloat())
        chatBubble.y = chatBubble.y.coerceIn(0f, (rootLayout.height - chatBubble.height).toFloat())
    }

    fun updateUserPosition(nickname: String, x: Float, y: Float) {
        userPositions[nickname] = Pair(x, y)
        runOnUiThread {
            val characterView = if (nickname == this.nickname) character else userCharacters[nickname]

            if (characterView == null) {
                createCharacterForUser(nickname, x, y)
            } else {
                characterView.x = x
                characterView.y = y
            }

            updateChatBubblePosition(nickname)
        }
    }

    private fun createCharacterForUser(nickname: String, x: Float, y: Float) {
        if (nickname == this.nickname || userCharacters.containsKey(nickname)) {
            return
        }

        val newCharacter = ImageView(this)
        newCharacter.setImageResource(R.drawable.standing)
        newCharacter.layoutParams = ViewGroup.LayoutParams(70, 70)
        newCharacter.x = x
        newCharacter.y = y
        val rootLayout: ViewGroup = findViewById(R.id.metverse1)
        rootLayout.addView(newCharacter)
        userCharacters[nickname] = newCharacter
    }

    fun showUserJoined(nickname: String) {
        userList.add(nickname)

        runOnUiThread {
            Toast.makeText(this, "$nickname 님이 입장했습니다.", Toast.LENGTH_LONG).show()
        }
    }

    fun updateUserList(userListJson: String) {
        val jsonArray = JSONArray(userListJson)
        userList.clear()
        for (i in 0 until jsonArray.length()) {
            userList.add(jsonArray.getString(i))
        }
        runOnUiThread {
        }
    }

    fun showUserLeft(nickname: String) {
        sendNicknameData(nickname)
        userPositions.remove(nickname)
        userList.remove(nickname)

        val characterView = userCharacters[nickname]
        characterView?.let {
            val rootLayout: ViewGroup = findViewById(R.id.metverse1)
            rootLayout.removeView(it)
            userCharacters.remove(nickname)
        }

        runOnUiThread {
            Toast.makeText(this, "$nickname 님이 퇴장했습니다.", Toast.LENGTH_LONG).show()
        }
    }

    fun showError(errorMessage: String) {
        runOnUiThread {
            AlertDialog.Builder(this)
                .setTitle("오류")
                .setMessage(errorMessage)
                .setPositiveButton("확인", null)
                .show()
        }
    }
    private fun moveCharacter(deltaX: Int, deltaY: Int) {
        val backgroundMap: ImageView = findViewById(R.id.background_map)

        val characterCurrentX = character.x
        val characterCurrentY = character.y

        val newCharacterX = characterCurrentX + deltaX
        val newCharacterY = characterCurrentY + deltaY

        val adjustedCharacterX = newCharacterX.coerceIn(0f, (backgroundMap.width - character.width).toFloat())
        val adjustedCharacterY = newCharacterY.coerceIn(0f, (backgroundMap.height - character.height).toFloat())

        character.x = adjustedCharacterX
        character.y = adjustedCharacterY

        updateChatMessagePosition()

        Log.d("Metaverse1Activity", "Character Position: x = $adjustedCharacterX, y = $adjustedCharacterY")

        sendMovementData(adjustedCharacterX, adjustedCharacterY)

        moveBackground(adjustedCharacterX, adjustedCharacterY)
    }

    private fun moveBackground(adjustedCharacterX: Float, adjustedCharacterY: Float) {
        if ((adjustedCharacterX == 625f && adjustedCharacterY == 295f)
            || (adjustedCharacterX == 545f && adjustedCharacterY == 295f)) {
            val intent = Intent(this, Metaverse2Activity::class.java).apply {
                putExtra("nickname", nickname)
                putExtra("characterX", character.x)
                putExtra("characterY", character.y)
            }
            startActivity(intent)
        }

        if (adjustedCharacterX == 785f && adjustedCharacterY == 455f) {
            showNpcMessage(npc1Text, "야구장에 오신걸 환영해요")
        }

        if ((adjustedCharacterX == 1105f && adjustedCharacterY == 615f)){
            showNpcMessage(npc2Text, "오늘은 좋은 경기가 펼쳐질 거예요")
        }

        if ((adjustedCharacterX == 1505f && adjustedCharacterY == 615f)) {
            showNpcMessage(npc3Text, "재밌는 시간 보내세요!")
        }
    }

    private fun showNpcMessage(npcTextView: TextView, message: String) {
        npcTextView.text = message
        npcTextView.visibility = TextView.VISIBLE

        handler.postDelayed({
            npcTextView.visibility = TextView.GONE
        }, 3000)
    }

    // 다이얼로그
    private fun showInfoDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("안녕하세요!\n야구친구 메타버스에 오신걸 환영합니다!\n")
        dialogBuilder.setMessage(
            "<제공 서비스>\n" +
                    "가상 세계에서 아바타를 이동하며 야구장을 탐험할 수 있습니다.\n" +
                    "NPC와 상호작용하여 다양한 대화를 나누고 가상 콘텐츠를 즐길 수 있습니다.\n" +
                    "입력한 메시지를 대화풍선으로 표시하여 소통 기능을 제공합니다.\n" +
                    "\n" +
                    "<이용 안내>\n" +
                    "화면 내 버튼으로 아바타를 이동할 수 있으며, 특정 위치에 도달하면 야구장이나 이벤트가 활성화됩니다.\n" +
                    "NPC 근처에서 대화를 나누거나 안내를 받을 수 있습니다.\n" +
                    "메시지 입력 창을 통해 채팅을 입력하면, 3초간 대화풍선으로 표시됩니다."
        )

        dialogBuilder.setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
        dialogBuilder.setNeutralButton("오늘의 경기") { dialog, _ -> showTodayScheduleDialog()
        }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        val buttonNeutral = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL)
        buttonNeutral?.let {
            it.setTextColor(ContextCompat.getColor(this, R.color.dozerblue))
            it.textSize = 15f
        }

        val buttonPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        buttonPositive?.let {
            it.setTextColor(ContextCompat.getColor(this, android.R.color.black))
            it.textSize = 15f
        }
    }

    private fun showTodayScheduleDialog() {
        val scheduleDialogBuilder = AlertDialog.Builder(this)
        scheduleDialogBuilder.setTitle("오늘의 경기")
        scheduleDialogBuilder.setMessage("경기 일정을 불러오는 중...")

        val scheduleDialog = scheduleDialogBuilder.create()
        scheduleDialog.show()

        val todayDate = SimpleDateFormat("MM.dd(E)", Locale.KOREAN).format(Date())

        ApiObject.getRetrofitService.getSchedule(todayDate).enqueue(object : Callback<List<ScheduleData>> {
            override fun onResponse(call: Call<List<ScheduleData>>, response: Response<List<ScheduleData>>) {
                if (response.isSuccessful) {
                    val scheduleList = response.body() ?: emptyList()

                    scheduleList.forEach {
                        Log.d("ScheduleDate", "Server date: ${it.date}")
                    }

                    val filteredList = scheduleList.filter { it.date == todayDate }.take(5)

                    if (filteredList.isEmpty()) {
                        scheduleDialog.setMessage("오늘 예정된 경기가 없습니다.")
                    } else {
                        val scheduleMessage = filteredList.joinToString(separator = "\n") { schedule ->
                            "${schedule.time}: ${schedule.team1} vs ${schedule.team2}"
                        }
                        scheduleDialog.setMessage(scheduleMessage)
                    }
                } else {
                    scheduleDialog.setMessage("경기 일정을 불러오는 데 실패했습니다.")
                }
            }

            override fun onFailure(call: Call<List<ScheduleData>>, t: Throwable) {
                scheduleDialog.setMessage("경기 일정을 불러오는 데 실패했습니다.")
            }
        })
    }
}