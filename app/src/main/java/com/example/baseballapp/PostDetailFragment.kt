package com.example.baseballapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.baseballapp.databinding.FragmentPostDetailBinding

class PostDetailFragment : Fragment() {

    private var _binding: FragmentPostDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var post: BoardData

    companion object {
        private const val ARG_POST = "post"

        fun newInstance(post: BoardData) = PostDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_POST, post)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        post = arguments?.getParcelable(ARG_POST) ?: return

        binding.tvDetailTitle.text = post.title
        binding.tvDetailContent.text = post.content
        binding.tvDetailAuthor.text = post.authorId
        binding.tvDetailCreatedAt.text = post.createdAt

        binding.btnDetailUpvote.setOnClickListener {
            // 추천 버튼 클릭 시 처리
            Toast.makeText(context, "추천 버튼 클릭", Toast.LENGTH_SHORT).show()
        }

        binding.btnSubmitComment.setOnClickListener {
            // 댓글 작성 버튼 클릭 시 처리
            val comment = binding.etComment.text.toString()
            if (comment.isNotEmpty()) {
                Toast.makeText(context, "댓글 작성: $comment", Toast.LENGTH_SHORT).show()
                binding.etComment.text.clear()
            } else {
                Toast.makeText(context, "댓글을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
