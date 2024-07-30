package com.example.baseballapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.baseballapp.databinding.FragmentCapBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CapFragment : Fragment() {

    private var _binding: FragmentCapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCapBinding.inflate(inflater, container, false)
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

            }
        }

        binding.btnAddToCart.setOnClickListener {
            val selectedTeam = binding.spinner.selectedItem.toString()
            if (selectedTeam != "팀 선택") {
                addToCart(selectedTeam)
                Toast.makeText(requireContext(), "$selectedTeam 모자가 장바구니에 추가되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "모자를 선택해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.button3.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, CartFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }

    private fun updateTeamInfo(team: String) {
        when (team) {
            "팀 선택" -> {
                binding.textView2.text = "팀을 선택해주세요"
                binding.textView3.text = "원"
                binding.textView4.text = "상품코드: ?"
                binding.textView5.text = "제조사: ?"
                binding.textView6.text = "원산지: ?"
                binding.textView7.text = "제조일: ?"
                binding.imageView.setImageResource(R.drawable.kia_cap)
            }
            "KIA" -> {
                binding.textView2.text = "[KIA타이거즈] 레플리카 모자"
                binding.textView3.text = "33,000원"
                binding.textView4.text = "상품코드: 1000004065"
                binding.textView5.text = "제조사: 굿즈연구소"
                binding.textView6.text = "원산지: KOREA"
                binding.textView7.text = "제조일: 2024-03-26"
                binding.imageView.setImageResource(R.drawable.kia_cap)
            }
            "두산" -> {
                binding.textView2.text = "[두산베어스] 오리지널 벨크로 캡"
                binding.textView3.text = "25,000원"
                binding.textView4.text = "상품코드: 1000004087"
                binding.textView5.text = "제조사: ㈜케이엔코리아"
                binding.textView6.text = "원산지: KOREA"
                binding.textView7.text = "제조일: 2024-04-16"
                binding.imageView.setImageResource(R.drawable.doosan_cap)
            }
            "LG" -> {
                binding.textView2.text = "[LG트윈스] 오리지널 모자"
                binding.textView3.text = "29,000원"
                binding.textView4.text = "상품코드: 1000003748"
                binding.textView5.text = "제조사: 인터파크"
                binding.textView6.text = "원산지: KOREA"
                binding.textView7.text = "제조일: 2024-03-24"
                binding.imageView.setImageResource(R.drawable.lg_cap)
            }
            "삼성" -> {
                binding.textView2.text = "[삼성라이온즈] 프로페셔널 모자"
                binding.textView3.text = "42,000원"
                binding.textView4.text = "상품코드: 1000003779"
                binding.textView5.text = "제조사: 씨앤에이치"
                binding.textView6.text = "원산지: KOREA"
                binding.textView7.text = "제조일: 2023-04-03"
                binding.imageView.setImageResource(R.drawable.samsung_cap)
            }
            "SSG" -> {
                binding.textView2.text = "[SSG랜더스] 레드 레플리카 모자"
                binding.textView3.text = "39,000원"
                binding.textView4.text = "상품코드: 1000004101"
                binding.textView5.text = "제조사: ㈜케이엔코리아"
                binding.textView6.text = "원산지: KOREA"
                binding.textView7.text = "제조일: 2024-04-16"
                binding.imageView.setImageResource(R.drawable.ssg_cap)
            }
            "NC" -> {
                binding.textView2.text = "[NC다이노스] 클러치 모자"
                binding.textView3.text = "33,000원"
                binding.textView4.text = "상품코드: 1000004086"
                binding.textView5.text = "제조사:㈜케이엔코리아"
                binding.textView6.text = "원산지: KOREA"
                binding.textView7.text = "제조일: 2024-04-05"
                binding.imageView.setImageResource(R.drawable.nc_cap)
            }
            "KT" -> {
                binding.textView2.text = "[KT위즈] 벨크로 모자"
                binding.textView3.text = "32,000원"
                binding.textView4.text = "상품코드: 1000003793"
                binding.textView5.text = "제조사: (주)케이엔코리아"
                binding.textView6.text = "원산지: KOREA"
                binding.textView7.text = "제조일: 2024-04-24"
                binding.imageView.setImageResource(R.drawable.kt_cap)
            }
            "롯데" -> {
                binding.textView2.text = "[롯데자이언츠] 레플리카 사이즈캡"
                binding.textView3.text = "25,000원"
                binding.textView4.text = "상품코드: 1000004126"
                binding.textView5.text = "제조사: ㈜코어커뮤니케이션"
                binding.textView6.text = "원산지: 중국"
                binding.textView7.text = "제조일: 2024-05-23"
                binding.imageView.setImageResource(R.drawable.lotte_cap)
            }
            "한화" -> {
                binding.textView2.text = "[한화이글스] 올스타기념 스냅백"
                binding.textView3.text = "21,000원"
                binding.textView4.text = "브랜드: 한화 이글스"
                binding.textView5.text = "제조사: (주케이엔코리아"
                binding.textView6.text = "원산지: KOREA"
                binding.textView7.text = "제조일: 2019-06-01"
                binding.imageView.setImageResource(R.drawable.hanwha_cap)
            }
            "키움" -> {
                binding.textView2.text = "[키움히어로즈] 일반형 모자"
                binding.textView3.text = "29,000원"
                binding.textView4.text = "상품코드: 1000004089"
                binding.textView5.text = "제조사: ㈜케이엔코리아"
                binding.textView6.text = "원산지: KOREA"
                binding.textView7.text = "제조일: 2024-04-18"
                binding.imageView.setImageResource(R.drawable.kiwoom_cap)
            }
        }
    }

    private fun addToCart(team: String) {
        val cartItem = CartItem(
            productName = team,
            productPrice = getPriceForTeam(team),
            productImage = getImageForTeam(team),
            productQuantity = 1
        )

        val sharedPreferences = requireActivity().getSharedPreferences("cart", Context.MODE_PRIVATE)
        val cartItemsJson = sharedPreferences.getString("cartItems", "[]")
        val type = object : TypeToken<MutableList<CartItem>>() {}.type
        val cartItems = Gson().fromJson<MutableList<CartItem>>(cartItemsJson, type) ?: mutableListOf()

        cartItems.add(cartItem)

        val newCartItemsJson = Gson().toJson(cartItems)
        sharedPreferences.edit().putString("cartItems", newCartItemsJson).apply()
    }

    private fun getPriceForTeam(team: String): Int {
        return when (team) {
            "KIA" -> 33000
            "두산" -> 25000
            "LG" -> 29000
            "삼성" -> 42000
            "SSG" -> 39000
            "NC" -> 33000
            "KT" -> 32000
            "롯데" -> 25000
            "한화" -> 21000
            "키움" -> 29000
            else -> 0
        }
    }

    private fun getImageForTeam(team: String): Int {
        return when (team) {
            "KIA" -> R.drawable.kia_cap
            "두산" -> R.drawable.doosan_cap
            "LG" -> R.drawable.lg_cap
            "삼성" -> R.drawable.samsung_cap
            "SSG" -> R.drawable.ssg_cap
            "NC" -> R.drawable.nc_cap
            "KT" -> R.drawable.kt_cap
            "롯데" -> R.drawable.lotte_cap
            "한화" -> R.drawable.hanwha_cap
            "키움" -> R.drawable.kiwoom_cap
            else -> R.drawable.hanwha_cap
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}