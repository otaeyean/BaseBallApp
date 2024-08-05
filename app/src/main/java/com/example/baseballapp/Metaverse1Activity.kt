package com.example.baseballapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity

class Metaverse1Activity : AppCompatActivity() {

    private lateinit var character: ImageView
    private lateinit var horizontalScrollView: HorizontalScrollView
    private lateinit var verticalScrollView: ScrollView
    private lateinit var backgroundMap: ImageView

    private val step = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metaverse_1)

        character = findViewById(R.id.character)
        horizontalScrollView = findViewById(R.id.horizontal_scroll_view)
        verticalScrollView = findViewById(R.id.vertical_scroll_view)
        backgroundMap = findViewById(R.id.background_map)

        val buttonUp: Button = findViewById(R.id.button_up)
        val buttonDown: Button = findViewById(R.id.button_down)
        val buttonLeft: Button = findViewById(R.id.button_left)
        val buttonRight: Button = findViewById(R.id.button_right)

        buttonUp.setOnClickListener { moveCharacter(-step, -step) }
        buttonDown.setOnClickListener { moveCharacter(step, step) }
        buttonLeft.setOnClickListener { moveCharacter(-step, step) }
        buttonRight.setOnClickListener { moveCharacter(step, -step) }
    }

    private fun moveCharacter(deltaX: Int, deltaY: Int) {
        val frameWidth = horizontalScrollView.width
        val frameHeight = verticalScrollView.height

        val backgroundWidth = backgroundMap.width
        val backgroundHeight = backgroundMap.height

        // 스크롤 이동
        val currentScrollX = horizontalScrollView.scrollX
        val currentScrollY = verticalScrollView.scrollY

        var newScrollX = currentScrollX + deltaX
        var newScrollY = currentScrollY + deltaY

        val maxScrollX = backgroundWidth - frameWidth
        val maxScrollY = backgroundHeight - frameHeight

        newScrollX = newScrollX.coerceIn(0, maxScrollX)
        newScrollY = newScrollY.coerceIn(0, maxScrollY)

        horizontalScrollView.scrollTo(newScrollX, newScrollY)
        verticalScrollView.scrollTo(newScrollX, newScrollY)

        val characterCurrentX = character.x
        val characterCurrentY = character.y

        val newCharacterX = characterCurrentX + deltaX
        val newCharacterY = characterCurrentY + deltaY

        val adjustedCharacterX = newCharacterX.coerceIn(0f, (backgroundWidth - character.width).toFloat())
        val adjustedCharacterY = newCharacterY.coerceIn(0f, (backgroundHeight - character.height).toFloat())

        character.x = adjustedCharacterX
        character.y = adjustedCharacterY

        if (adjustedCharacterX.toDouble() ==671.0 && adjustedCharacterY.toDouble() ==188.0
            || adjustedCharacterX.toDouble() ==551.0 && adjustedCharacterY.toDouble() ==188.0 ) {
            val intent = Intent(this, Metaverse2Activity::class.java)
            startActivity(intent)
        }
    }
}