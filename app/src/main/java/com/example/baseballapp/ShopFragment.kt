package com.example.baseballapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseballapp.databinding.FragmentShopBinding

class ShopFragment : Fragment() {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileAdapter: ProfileAdapter
    private val datas = mutableListOf<ProfileData>()

    private val discountMessages = listOf(
        "🎉 첫 구매 시 10% 할인! \uD83C\uDF89",
        "🔥 여름 세일 20% 할인 중! \uD83D\uDD25",
        "🎁 친구 초대하면 추가 할인! \uD83C\uDF81"
    )

    private var currentIndex = 0
    private val handler = Handler(Looper.getMainLooper())
    private val timerRunnable = object : Runnable {
        override fun run() {
            binding.tvDiscountInfo.text = discountMessages[currentIndex]
            currentIndex = (currentIndex + 1) % discountMessages.size
            handler.postDelayed(this, 2000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()

        binding.tvDiscountInfo.text = discountMessages[currentIndex]
        handler.postDelayed(timerRunnable, 3000) // 타이머 시작
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(timerRunnable)
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
}
