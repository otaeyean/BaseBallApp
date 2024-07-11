package com.example.yourapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.baseballapp.R

class QuestionBoardFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_free_board, container, false)
    }
}

// QuestionBoardFragment.kt, TradeBoardFragment.kt 도 동일한 방식으로 작성
