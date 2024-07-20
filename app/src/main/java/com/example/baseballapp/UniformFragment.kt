package com.example.baseballapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.baseballapp.databinding.FragmentUniformBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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
            }
        }

        binding.btnAddToCart.setOnClickListener {
            val selectedTeam = binding.spinner.selectedItem.toString()
            if (selectedTeam != "팀 선택") {
                addToCart(selectedTeam)
                Toast.makeText(requireContext(), "$selectedTeam 유니폼이 장바구니에 추가되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "유니폼을 선택해 주세요.", Toast.LENGTH_SHORT).show()
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
                binding.textView4.text = "브랜드: ?"
                binding.textView5.text = "제조사: ?"
                binding.textView6.text = "원산지: ?"
                binding.imageView.setImageResource(R.drawable.kia_uniform)
            }
            "KIA" -> {
                binding.textView2.text = "기아 유니폼"
                binding.textView3.text = "69,000원"
                binding.textView4.text = "브랜드: 기아 타이거즈"
                binding.textView5.text = "제조사: KIA FANSHOP"
                binding.textView6.text = "원산지: KOREA"
                binding.imageView.setImageResource(R.drawable.kia_uniform)
            }
            "두산" -> {
                binding.textView2.text = "두산 유니폼"
                binding.textView3.text = "65,000원"
                binding.textView4.text = "브랜드: 두산베어스"
                binding.textView5.text = "제조사: WeFAN"
                binding.textView6.text = "원산지: KOREA"
                binding.imageView.setImageResource(R.drawable.doosan_uniform)
            }
            "LG" -> {
                binding.textView2.text = "LG 유니폼"
                binding.textView3.text = "59,000원"
                binding.textView4.text = "브랜드: LG 트윈스"
                binding.textView5.text = "제조사: FANDOME"
                binding.textView6.text = "원산지: KOREA"
                binding.imageView.setImageResource(R.drawable.lg_uniform)
            }
            "삼성" -> {
                binding.textView2.text = "삼성 유니폼"
                binding.textView3.text = "79,000원"
                binding.textView4.text = "브랜드: 삼성 라이온즈"
                binding.textView5.text = "제조사: SAMFAN"
                binding.textView6.text = "원산지: KOREA"
                binding.imageView.setImageResource(R.drawable.samsung_uniform)
            }
            "SSG" -> {
                binding.textView2.text = "SSG 유니폼"
                binding.textView3.text = "69,000원"
                binding.textView4.text = "브랜드: SSG 랜더스"
                binding.textView5.text = "제조사: SSG FANSHOP"
                binding.textView6.text = "원산지: KOREA"
                binding.imageView.setImageResource(R.drawable.ssg_uniform)
            }
            "NC" -> {
                binding.textView2.text = "NC 유니폼"
                binding.textView3.text = "59,000원"
                binding.textView4.text = "브랜드: NC 다이노스"
                binding.textView5.text = "제조사: NC FANSHOP"
                binding.textView6.text = "원산지: KOREA"
                binding.imageView.setImageResource(R.drawable.nc_uniform)
            }
            "KT" -> {
                binding.textView2.text = "KT 유니폼"
                binding.textView3.text = "65,000원"
                binding.textView4.text = "브랜드: KT 위즈"
                binding.textView5.text = "제조사: KT FANSHOP"
                binding.textView6.text = "원산지: KOREA"
                binding.imageView.setImageResource(R.drawable.kt_uniform)
            }
            "롯데" -> {
                binding.textView2.text = "롯데 유니폼"
                binding.textView3.text = "75,000원"
                binding.textView4.text = "브랜드: 롯데 자이언츠"
                binding.textView5.text = "제조사: LOTTE FANSHOP"
                binding.textView6.text = "원산지: KOREA"
                binding.imageView.setImageResource(R.drawable.lotte_uniform)
            }
            "한화" -> {
                binding.textView2.text = "한화 유니폼"
                binding.textView3.text = "65,000원"
                binding.textView4.text = "브랜드: 한화 이글스"
                binding.textView5.text = "제조사: HANWHA FANSHOP"
                binding.textView6.text = "원산지: KOREA"
                binding.imageView.setImageResource(R.drawable.hanwha_uniform)
            }
            "키움" -> {
                binding.textView2.text = "키움 유니폼"
                binding.textView3.text = "59,000원"
                binding.textView4.text = "브랜드: 키움 히어로즈"
                binding.textView5.text = "제조사: KIUM FANSHOP"
                binding.textView6.text = "원산지: KOREA"
                binding.imageView.setImageResource(R.drawable.kiwoom_uniform)
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
            "KIA" -> 69000
            "두산" -> 65000
            "LG" -> 59000
            "삼성" -> 79000
            "SSG" -> 69000
            "NC" -> 59000
            "KT" -> 65000
            "롯데" -> 75000
            "한화" -> 65000
            "키움" -> 59000
            else -> 0
        }
    }

    private fun getImageForTeam(team: String): Int {
        return when (team) {
            "KIA" -> R.drawable.kia_uniform
            "두산" -> R.drawable.doosan_uniform
            "LG" -> R.drawable.lg_uniform
            "삼성" -> R.drawable.samsung_uniform
            "SSG" -> R.drawable.ssg_uniform
            "NC" -> R.drawable.nc_uniform
            "KT" -> R.drawable.kt_uniform
            "롯데" -> R.drawable.lotte_uniform
            "한화" -> R.drawable.hanwha_uniform
            "키움" -> R.drawable.kiwoom_uniform
            else -> R.drawable.hanwha_uniform
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}