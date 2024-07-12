package com.example.baseballapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.baseballapp.databinding.FragmentUniformBinding

class UniformFragment : Fragment() {

    private var _binding: FragmentUniformBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUniformBinding.inflate(inflater, container, false)
        val view = binding.root

        val teams = resources.getStringArray(R.array.teams_array)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, teams)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedTeam = parent.getItemAtPosition(position).toString()
                updateTeamInfo(selectedTeam)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        binding.button2.setOnClickListener {
            val selectedTeam = binding.spinner.selectedItem.toString()
            navigateToTeamWebsite(selectedTeam)
        }

        return view
    }

    private fun updateTeamInfo(team: String) {
        when (team) {
            "팀 선택" -> {
                binding.textView2.text = "팀을 선택해주세요"
                binding.textView3.text = "원"
                binding.textView4.text = "브랜드: ?"
                binding.textView5.text = "제조사: ?"
                binding.textView6.text = "원산지: ?"
                binding.imageView.setImageResource(R.drawable.kia_uniform)
            }
            "KIA" -> {
                binding.textView2.text = "기아 유니폼"
                binding.textView3.text = "75,000원"
                binding.textView4.text = "브랜드: 기아 타이거즈"
                binding.textView5.text = "제조사: KIA FANSHOP"
                binding.textView6.text = "원산지: KOREA"
                binding.imageView.setImageResource(R.drawable.kia_uniform)
            }
            "두산" -> {
                binding.textView2.text = "두산 유니폼"
                binding.textView3.text = "90,000원"
                binding.textView4.text = "브랜드: 두산베어스"
                binding.textView5.text = "제조사: WeFAN"
                binding.textView6.text = "원산지: KOREA"
                binding.imageView.setImageResource(R.drawable.doosan_uniform)
            }
            "LG" -> {
                binding.textView2.text = "LG 유니폼"
                binding.textView3.text = "85,000원"
                binding.textView4.text = "브랜드: LG 트윈스"
                binding.textView5.text = "제조사: FANDOME"
                binding.textView6.text = "원산지: KOREA"
                binding.imageView.setImageResource(R.drawable.lg_uniform)
            }
            "삼성" -> {
                binding.textView2.text = "삼성 유니폼"
                binding.textView3.text = "80,000원"
                binding.textView4.text = "브랜드: 삼성 라이온즈"
                binding.textView5.text = "제조사: SAMFAN"
                binding.textView6.text = "원산지: KOREA"
                binding.imageView.setImageResource(R.drawable.samsung_uniform)
            }
            "SSG" -> {
                binding.textView2.text = "SSG 유니폼"
                binding.textView3.text = "80,000원"
                binding.textView4.text = "브랜드: SSG 랜더스"
                binding.textView5.text = "제조사: SSG FANSHOP"
                binding.textView6.text = "원산지: KOREA"
                binding.imageView.setImageResource(R.drawable.ssg_uniform)
            }
            "NC" -> {
                binding.textView2.text = "NC 유니폼"
                binding.textView3.text = "80,000원"
                binding.textView4.text = "브랜드: NC 다이노스"
                binding.textView5.text = "제조사: NC FANSHOP"
                binding.textView6.text = "원산지: KOREA"
                binding.imageView.setImageResource(R.drawable.nc_uniform)
            }
            "KT" -> {
                binding.textView2.text = "KT 유니폼"
                binding.textView3.text = "80,000원"
                binding.textView4.text = "브랜드: KT 위즈"
                binding.textView5.text = "제조사: KT FANSHOP"
                binding.textView6.text = "원산지: KOREA"
                binding.imageView.setImageResource(R.drawable.kt_uniform)
            }
            "롯데" -> {
                binding.textView2.text = "롯데 유니폼"
                binding.textView3.text = "80,000원"
                binding.textView4.text = "브랜드: 롯데 자이언츠"
                binding.textView5.text = "제조사: LOTTE FANSHOP"
                binding.textView6.text = "원산지: KOREA"
                binding.imageView.setImageResource(R.drawable.lotte_uniform)
            }
            "한화" -> {
                binding.textView2.text = "한화 유니폼"
                binding.textView3.text = "80,000원"
                binding.textView4.text = "브랜드: 한화 이글스"
                binding.textView5.text = "제조사: HANWHA FANSHOP"
                binding.textView6.text = "원산지: KOREA"
                binding.imageView.setImageResource(R.drawable.hanwha_uniform)
            }
            "키움" -> {
                binding.textView2.text = "키움 유니폼"
                binding.textView3.text = "80,000원"
                binding.textView4.text = "브랜드: 키움 히어로즈"
                binding.textView5.text = "제조사: KIUM FANSHOP"
                binding.textView6.text = "원산지: KOREA"
                binding.imageView.setImageResource(R.drawable.kiwoom_uniform)
            }
        }
    }

    private fun navigateToTeamWebsite(team: String) {
        val url = when (team) {
            "KIA" -> "https://teamstore.tigers.co.kr/"
            // 다른 팀들에 대한 URL 추가
            else -> ""
        }

        if (url.isNotEmpty()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}