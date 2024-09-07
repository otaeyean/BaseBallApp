package com.example.baseballapp

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseballapp.databinding.FragmentShopBinding

class ShopFragment : Fragment() {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileAdapter: ProfileAdapter
    private val datas = mutableListOf<ProfileData>()

    // 다이얼로그 상태를 저장하는 변수 (앱 실행 중에만 유효)
    private var isAdDialogShown = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 광고 다이얼로그를 체크하고 띄우기 (앱이 실행되는 동안에만 다이얼로그 상태를 유지)
        showAdDialogIfNeeded()

        initRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecycler() {
        profileAdapter = ProfileAdapter(requireContext(), parentFragmentManager)
        binding.rvProfile.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProfile.adapter = profileAdapter

        datas.apply {
            add(ProfileData(img = R.drawable.uniform, name = "구단별 유니폼", price = 90000))
            add(ProfileData(img = R.drawable.cap, name = "구단별 모자", price = 35000))
            add(ProfileData(img = R.drawable.bat, name = "[TEAMKOREA] 폼배트", price = 18000))
            add(ProfileData(img = R.drawable.ball, name = "[KBO]2024 KBO 공인구", price = 20000))
            add(ProfileData(img = R.drawable.muffler, name = "[TEAMKOREA] KOREA 응원 머플러", price = 15000))
            add(ProfileData(img = R.drawable.glove, name = "[TEAM KOREA] PVC 글러브", price = 32000))
        }

        profileAdapter.datas = datas
        profileAdapter.notifyDataSetChanged()
    }

    private fun showAdDialogIfNeeded() {
        if (!isAdDialogShown) {
            // NOTICE 제목을 빨간색으로 변경
            val title = SpannableString("NOTICE")
            title.setSpan(ForegroundColorSpan(Color.RED), 0, title.length, 0)

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(title) // 빨간색으로 설정된 제목

            builder.setMessage("상품들 중 주문 상태가 상품 준비 중, 배송 중 상태로 변경된 건은" +
                    "\n 출고 처리 진행 중으로 교환, 환불 신청 시 즉시 처리가 불가하며" +
                    "\n 배송이 진행될 수 있습니다. \n\n 감사합니다 :) ")

            builder.setPositiveButton("다시 보지 않음") { dialog, _ ->
                isAdDialogShown = true
                dialog.dismiss()
            }

            builder.setNegativeButton("닫기") { dialog, _ ->
                dialog.dismiss()
            }

            builder.create().show()
        }
    }
}