package com.example.yourapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.baseballapp.R
import com.example.baseballapp.databinding.FragmentCommunityBinding

class CommunityFragment : Fragment() {

    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 초기 게시판 프래그먼트 로드
        replaceFragment(FreeBoardFragment())
        updateButtonState(binding.freeBoardButton)

        // 버튼 클릭 리스너 설정
        binding.freeBoardButton.setOnClickListener {
            replaceFragment(FreeBoardFragment())
            updateButtonState(binding.freeBoardButton)
        }
        binding.questionBoardButton.setOnClickListener {
            replaceFragment(QuestionBoardFragment())
            updateButtonState(binding.questionBoardButton)
        }
        binding.tradeBoardButton.setOnClickListener {
            replaceFragment(TradeBoardFragment())
            updateButtonState(binding.tradeBoardButton)
        }

        // 글쓰기 버튼 클릭 리스너
        binding.writePostButton.setOnClickListener {
            // 글쓰기 프래그먼트로 이동
            parentFragmentManager.beginTransaction()
                .replace(R.id.boardContainer, WritePostFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    // 프래그먼트 교체 함수
    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.boardContainer, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    // 버튼 상태 업데이트 함수
    private fun updateButtonState(activeButton: Button) {
        val buttons = listOf(binding.freeBoardButton, binding.questionBoardButton, binding.tradeBoardButton)
        buttons.forEach { button ->
            if (button == activeButton) {
                button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            } else {
                button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
