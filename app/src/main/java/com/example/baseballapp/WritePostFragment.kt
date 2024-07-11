package com.example.yourapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.baseballapp.databinding.FragmentWritePostBinding

class WritePostFragment : Fragment() {

    private var _binding: FragmentWritePostBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWritePostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitPostButton.setOnClickListener {
            val boardType = binding.boardSpinner.selectedItem.toString()
            val title = binding.postTitle.text.toString()
            val content = binding.postContent.text.toString()

            // 데이터를 서버로 전송하거나 로컬에 저장하는 코드 작성
            // 예시: submitPost(boardType, title, content)

            // 작성 완료 후 이전 화면으로 이동
            parentFragmentManager.popBackStack()
        }

        binding.uploadImageButton.setOnClickListener {
            // 이미지 업로드 기능 구현
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
