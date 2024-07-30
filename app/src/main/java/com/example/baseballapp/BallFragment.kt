package com.example.baseballapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.baseballapp.databinding.FragmentBallBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BallFragment : Fragment() {

    private var _binding: FragmentBallBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBallBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnAddToCart.setOnClickListener {
            addToCart()
            Toast.makeText(requireContext(), "공인구가 장바구니에 추가되었습니다.", Toast.LENGTH_SHORT).show()
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
    private fun addToCart() {
        val cartItem = CartItem(
            productName = "[KBO] 공인구",
            productPrice = 18000,
            productImage = R.drawable.ball,
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}