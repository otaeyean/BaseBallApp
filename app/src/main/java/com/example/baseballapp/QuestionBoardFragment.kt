package com.example.baseballapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseballapp.databinding.FragmentQuestionBoardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionBoardFragment : Fragment() {

    private var _binding: FragmentQuestionBoardBinding? = null
    private val binding get() = _binding!!
    private lateinit var postAdapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postAdapter = PostAdapter(emptyList())
        binding.recyclerViewPosts.adapter = postAdapter
        binding.recyclerViewPosts.layoutManager = LinearLayoutManager(context)

        fetchPosts()
    }

    private fun fetchPosts() {
        ApiObject.getRetrofitService.getAllBoards().enqueue(object : Callback<List<BoardData>> {
            override fun onResponse(call: Call<List<BoardData>>, response: Response<List<BoardData>>) {
                if (response.isSuccessful) {
                    response.body()?.let { boards ->
                        postAdapter.setPosts(boards)
                    }
                } else {
                    Toast.makeText(context, "게시글을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<BoardData>>, t: Throwable) {
                Toast.makeText(context, "네트워크 오류: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
