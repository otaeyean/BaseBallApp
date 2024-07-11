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
            add(ProfileData(img = R.drawable.uniform, name = "유니폼", price = 90000))
            add(ProfileData(img = R.drawable.zip_up, name = "하프 집업(네이비)", price = 75000))
            add(ProfileData(img = R.drawable.cap, name = "레트로 모자", price = 33000))
            add(ProfileData(img = R.drawable.key_holder, name = "유니폼 키홀더", price = 7000))
            add(ProfileData(img = R.drawable.patch, name = "최다연승 기념 패치", price = 7000))
            add(ProfileData(img = R.drawable.patch, name = "여섯번째", price = 7000))
        }

        profileAdapter.datas = datas
        profileAdapter.notifyDataSetChanged()
    }
}