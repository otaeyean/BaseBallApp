package com.example.baseballapp

import android.os.Bundle
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
}