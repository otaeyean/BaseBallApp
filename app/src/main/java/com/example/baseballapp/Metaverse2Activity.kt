package com.example.baseballapp

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import com.example.baseballapp.ApiObject.getRetrofitService
import com.google.android.material.tabs.TabLayout
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class Metaverse2Activity : AppCompatActivity(){

    private lateinit var character: ImageView
    private lateinit var sendButton: Button
    private lateinit var editTextMessage: EditText
    private lateinit var mainLayout: ConstraintLayout
    private lateinit var tabLayout: TabLayout
    private lateinit var liveStreamScrollView: ScrollView
    private lateinit var liveStreamTextView: TextView
    private lateinit var matchResponse: MatchResponse
    private lateinit var webSocket: WebSocket
    private val charactersMap = mutableMapOf<String, ImageView>()
    private var selectedTeam= "KIA"
    private var date="09.25(수)"
    private var nickname: String = ""
    private val userList = mutableListOf<String>()
    private val timersMap = mutableMapOf<String, Handler>()

    @RequiresApi(Build.VERSION_CODES.O)
    val todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM.dd(E)", Locale.KOREAN))

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metaverse2)

        selectedTeam = getSelectedTeam()?:return

        character = findViewById(R.id.character)
        editTextMessage = findViewById(R.id.editTextMessage)
        mainLayout = findViewById(R.id.metaverse2)
        sendButton = findViewById(R.id.sendbutton)
        tabLayout = findViewById(R.id.tabLayout)
        liveStreamScrollView = findViewById(R.id.liveStreamScrollView)
        liveStreamTextView = findViewById(R.id.liveStreamTextView)

        nickname = intent.getStringExtra("nickname").toString()

        Glide.with(this).asGif().load(R.drawable.standing).into(character)

        sendButton.setOnClickListener {
            val message = editTextMessage.text.toString()
            if (message.isNotEmpty()) {
                sendChatMessage(message)
                editTextMessage.text.clear()
            }
        }
        setupTabs()

        fetchMatchDetails(selectedTeam, date, 1)

        initializeWebSocket()

        getTeamSchedule(date)
    }

    private fun getSelectedTeam(): String? {
        val sharedPreferences = getSharedPreferences("baseballAppPrefs", MODE_PRIVATE)
        return sharedPreferences.getString("selectedTeam", null)
    }

    private fun initializeWebSocket() {
        val client = OkHttpClient()
        val request = Request.Builder().url("ws://35.216.0.159:8080/ws/map/1235").build()
        val listener = MetaverseWebSocketListener2(this)
        webSocket = client.newWebSocket(request, listener)
    }

    private fun getTeamSchedule(date: String) {
        getRetrofitService.getTeamSchedule(date).enqueue(object : Callback<List<MetaverseMatch>> {
            override fun onResponse(call: Call<List<MetaverseMatch>>, response: Response<List<MetaverseMatch>>) {
                if (response.isSuccessful) {
                    response.body()?.let { matches ->
                        val filteredMatches = matches.filter { match ->
                            (match.team1 == selectedTeam || match.team2 == selectedTeam) && match.date==date
                        }
                        filteredMatches.forEach { metaverseMatch ->
                            displayMatch(metaverseMatch)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<MetaverseMatch>>, t: Throwable) {
            }
        })
    }

    private fun displayMatch(metaverseMatch: MetaverseMatch) {
        val team1: TextView = findViewById(R.id.team1)
        val team2: TextView = findViewById(R.id.team2)
        val team1Score: TextView = findViewById(R.id.team1Score)
        val team2Score: TextView = findViewById(R.id.team2Score)
        val team1Logo:ImageView=findViewById(R.id.team1Logo)
        val team2Logo:ImageView=findViewById(R.id.team2Logo)

        team1.text = metaverseMatch.team1
        team2.text = metaverseMatch.team2
        team1Score.text = metaverseMatch.team1Score
        team2Score.text = metaverseMatch.team2Score
        team1Logo.setImageResource(getLogoResource(team1.text.toString()))
        team2Logo.setImageResource(getLogoResource(team2.text.toString()))

        if(team1Score.text=="none" || team2Score.text=="none"){
            team1Score.visibility= View.GONE
            team2Score.visibility= View.GONE
        }
    }

    private fun getLogoResource(team: String): Int {
        return when (team) {
            "두산" -> R.drawable.doosan_logo
            "KIA" -> R.drawable.kia_logo
            "키움" -> R.drawable.kiwoom_logo
            "KT" -> R.drawable.kt_logo
            "LG" -> R.drawable.lg_logo
            "롯데" -> R.drawable.lotte_logo
            "NC" -> R.drawable.nc_logo
            "삼성" -> R.drawable.samsung_logo
            "SSG" -> R.drawable.ssg_logo
            "한화" -> R.drawable.hanwha_logo
            else -> R.drawable.baseball
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupTabs() {
        for (i in 1..9) {
            val tab = tabLayout.newTab()
            tab.text = "${i}회"
            tabLayout.addTab(tab)
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    val inningNumber = it.position + 1
                    fetchMatchDetails(selectedTeam, date, inningNumber)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun fetchMatchDetails(selectedTeam: String, matchDate: String, inningNumber: Int) {

        val mappedTeam = when (selectedTeam) {
            "LG" -> "한화"
            "NC" -> "SSG"
            "삼성" -> "키움"
            "KIA" -> "롯데"
            else -> selectedTeam
        }

        getRetrofitService.getMatchDetails(mappedTeam, matchDate)
            .enqueue(object : Callback<MatchResponse> {
                override fun onResponse(call: Call<MatchResponse>, response: Response<MatchResponse>) {
                    if (response.isSuccessful) {
                        matchResponse = response.body()!!
                        displayMatchDetails(inningNumber)
                    } else {
                        liveStreamTextView.text = "문자 중계 데이터를 불러오지 못했습니다."
                    }
                }

                override fun onFailure(call: Call<MatchResponse>, t: Throwable) {
                    liveStreamTextView.text = "문자 중계 데이터를 불러오지 못했습니다."
                }
            })
    }

    private fun displayMatchDetails(inningNumber: Int) {
        val inning = matchResponse.innings.find { it.inningNumber == inningNumber }
        val stringBuilder = SpannableStringBuilder()

        inning?.details?.forEach { detail ->
            val detailParts = detail.split("|")

            detailParts.forEachIndexed { index, part ->
                if (index == 0 && part.contains("공격")) {
                    val spannableString = SpannableString(part.trim() + "\n")
                    spannableString.setSpan(
                        StyleSpan(Typeface.BOLD),
                        0, spannableString.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    spannableString.setSpan(
                        RelativeSizeSpan(1.2f),
                        0, spannableString.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    stringBuilder.append(spannableString)
                } else if (part.contains("타자")) {
                    val spannableString = SpannableString(part.trim() + "\n")
                    spannableString.setSpan(
                        ForegroundColorSpan(Color.RED),
                        0, spannableString.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    spannableString.setSpan(
                        StyleSpan(Typeface.BOLD),
                        0, spannableString.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    stringBuilder.append(spannableString)
                    stringBuilder.append("\n")
                } else if (part.contains(":")) {
                    val darkBlueColor = ContextCompat.getColor(this, R.color.dozerblue)
                    val resultStartIndex = part.indexOf(":") + 1
                    val resultText = part.substring(resultStartIndex).trim()
                    if (resultText.isNotEmpty()) {
                        val spannableString = SpannableString(part.trim() + "\n")
                        spannableString.setSpan(
                            ForegroundColorSpan(darkBlueColor),
                            resultStartIndex, spannableString.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        stringBuilder.append(spannableString)
                    } else {
                        stringBuilder.append(part.trim() + "\n")
                    }
                } else {
                    stringBuilder.append(part.trim() + "\n")
                }
            }
        }
        liveStreamTextView.text = stringBuilder
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

    fun onChatMessageReceived(nickname: String, message: String) {
        charactersMap[nickname] ?: run {

            val newCharacterX = character.x - convertDpToPx(100)
            val newCharacterY = character.y
            createCharacterForUser(nickname, newCharacterX, newCharacterY)
            charactersMap[nickname]
        }

        showChatMessageForUser(nickname, message)
        resetUserTimer(nickname)
    }

    fun showChatMessageForUser(nickname: String, message: String) {
        val characterView = charactersMap[nickname] ?: return

        val textView = TextView(this).apply {
            text = "$nickname: $message"
            setBackgroundResource(R.drawable.chat_bubble_background)
            setPadding(8, 8, 8, 8)
            setTextColor(resources.getColor(android.R.color.black, null))
            textSize = 16f
            gravity = Gravity.CENTER
            id = ViewCompat.generateViewId()
        }

        mainLayout.addView(textView)

        textView.post {
            textView.x = characterView.x + (characterView.width - textView.width) / 2 +30
            textView.y = characterView.y - textView.height + 80
        }

        Handler(Looper.getMainLooper()).postDelayed({
            mainLayout.removeView(textView)
        }, 3000)
    }

    fun createCharacterForUser(nickname: String, x: Float, y: Float) {
        if (nickname == this.nickname || charactersMap.containsKey(nickname)) {
            return
        }

        val newCharacter = ImageView(this).apply {
            setImageResource(R.drawable.standing)
            layoutParams = ViewGroup.LayoutParams(convertDpToPx(150), convertDpToPx(150))
            this.x = x
            this.y = y
        }
        mainLayout.addView(newCharacter)
        charactersMap[nickname] = newCharacter
    }

    fun showChatMessage(nickname: String, message: String) {

        val textView = TextView(this).apply {
            text = "$nickname: $message"
            setBackgroundResource(R.drawable.chat_bubble_background)
            setPadding(8, 8, 8, 8)
            setTextColor(resources.getColor(android.R.color.black, null))
            textSize = 16f
            gravity = Gravity.CENTER
            id = ViewCompat.generateViewId()
        }

        mainLayout.addView(textView)

        textView.post {
            textView.x = character.x + (character.width - textView.width) / 2 + 30
            textView.y = character.y - textView.height + 80
        }

        Handler(Looper.getMainLooper()).postDelayed({
            mainLayout.removeView(textView)
        }, 3000)
    }

    fun updateUserList(users: String) {
        userList.clear()
        userList.addAll(users.split(","))
    }

    fun showUserJoined(nickname: String) {
        val newCharacter = ImageView(this).apply {
            setImageResource(R.drawable.standing)
            contentDescription = nickname
        }

        val params = ConstraintLayout.LayoutParams(150, 150)
        mainLayout.addView(newCharacter, params)

        val characterX = character.x
        val characterY = character.y
        val newCharacterX = characterX - convertDpToPx(200)
        val newCharacterY = characterY

        newCharacter.x = newCharacterX
        newCharacter.y = newCharacterY

        charactersMap[nickname] = newCharacter
    }

    private fun resetUserTimer(nickname: String) {

        timersMap[nickname]?.removeCallbacksAndMessages(null)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({

            showUserLeft(nickname)
            timersMap.remove(nickname)
        }, 20000)

        timersMap[nickname] = handler
    }
    fun showUserLeft(nickname: String) {
        charactersMap[nickname]?.let { characterView ->
            mainLayout.removeView(characterView)
            charactersMap.remove(nickname)
            timersMap.remove(nickname)
        }
    }

    private fun convertDpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

}