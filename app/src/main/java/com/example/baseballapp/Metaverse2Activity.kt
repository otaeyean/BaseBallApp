package com.example.baseballapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide

class Metaverse2Activity : AppCompatActivity() {

    private lateinit var character: ImageView
    private lateinit var editTextMessage: EditText
    private lateinit var mainLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metaverse2)

        character = findViewById(R.id.character)
        editTextMessage = findViewById(R.id.editTextMessage)
        mainLayout = findViewById(R.id.main)

        Glide.with(this).asGif().load(R.drawable.standing).into(character)

        editTextMessage.setOnEditorActionListener { _, _, _ ->
            val message = editTextMessage.text.toString()
            if (message.isNotEmpty()) {
                showBalloonMessage(message)
                editTextMessage.text.clear()
            }
            true
        }
    }

    private fun showBalloonMessage(message: String) {
        val textView = TextView(this).apply {
            text = message
            setBackgroundResource(R.drawable.balloon_background)
            setPadding(16, 8, 16, 8)
            setTextColor(resources.getColor(android.R.color.black, null))
            textSize = 16f
            gravity = Gravity.CENTER
            id = ViewCompat.generateViewId()
        }

        mainLayout.addView(textView)

        val set = ConstraintSet()
        set.clone(mainLayout)

        set.connect(
            textView.id,
            ConstraintSet.START,
            R.id.character,
            ConstraintSet.START,
            100
        )
        set.connect(textView.id, ConstraintSet.END, R.id.character, ConstraintSet.END)
        set.connect(
            textView.id,
            ConstraintSet.BOTTOM,
            R.id.character,
            ConstraintSet.BOTTOM,
            240
        )
        set.setHorizontalBias(textView.id, 0.8f)
        set.applyTo(mainLayout)

        Handler(Looper.getMainLooper()).postDelayed({
            mainLayout.removeView(textView)
        }, 3000)
    }
}