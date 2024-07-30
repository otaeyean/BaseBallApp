package com.example.yourapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.baseballapp.ApiObject
import com.example.baseballapp.Post
import com.example.baseballapp.databinding.FragmentWritePostBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

// WritePostFragment 클래스는 Fragment를 상속받아 게시글 작성 기능을 구현하는 프래그먼트입니다.
class WritePostFragment : Fragment() {

    // 뷰 바인딩을 위한 변수 선언
    private var _binding: FragmentWritePostBinding? = null
    private val binding get() = _binding!!

    // 프래그먼트의 뷰를 생성하는 메서드..
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // FragmentWritePostBinding을 사용하여 레이아웃을 확장하고 바인딩 객체를 초기화
        _binding = FragmentWritePostBinding.inflate(inflater, container, false)
        return binding.root
    }

    // 뷰가 생성된 후 추가적인 초기화를 수행하는 메서드
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 게시글 제출 버튼 클릭 리스너 설정
        binding.submitPostButton.setOnClickListener {
            // 사용자가 선택한 게시판 종류, 입력한 제목과 내용을 가져옴
            val boardType = binding.boardSpinner.selectedItem.toString()
            val title = binding.postTitle.text.toString()
            val content = binding.postContent.text.toString()
            val author = "작성자 이름"  // 실제 작성자 이름으로 교체
            val createdAt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

            // 제목과 내용이 비어있으면 경고 메시지를 표시하고 리스너 종료
            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(context, "제목과 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Post 객체 생성
            val post = Post(boardType, title, content, author, createdAt)
            // Post 객체를 서버로 제출하는 메서드 호출
            submitPost(post)
        }
    }

    // 서버로 게시글을 제출하는 메서드
    private fun submitPost(post: Post) {
        // Retrofit을 사용하여 비동기적으로 POST 요청을 보냄
        ApiObject.getRetrofitService.submitPost(post).enqueue(object : Callback<Void> {
            // 서버 응답이 성공적일 때 호출되는 메서드
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // 성공 메시지를 표시하고 이전 화면으로 돌아감
                    Toast.makeText(context, "게시글이 성공적으로 작성되었습니다.", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                } else {
                    // 실패 메시지를 표시
                    Toast.makeText(context, "게시글 작성에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            // 네트워크 오류가 발생했을 때 호출되는 메서드
            override fun onFailure(call: Call<Void>, t: Throwable) {
                // 네트워크 오류 메시지를 표시
                Toast.makeText(context, "네트워크 오류: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 뷰가 파괴될 때 호출되는 메서드
    override fun onDestroyView() {
        super.onDestroyView()
        // 메모리 누수를 방지하기 위해 바인딩 객체를 null로 설정
        _binding = null
    }
}
