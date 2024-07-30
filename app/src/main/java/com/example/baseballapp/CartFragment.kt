package com.example.baseballapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.baseballapp.databinding.FragmentCartBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root

        cartAdapter = CartAdapter(requireContext(), R.layout.cart_item, getCartItems())
        binding.lvCartItems.adapter = cartAdapter

        binding.btnCheckout.setOnClickListener {
            // 결제 로직 처리
        }

        return view
    }

    private fun getCartItems(): MutableList<CartItem> {
        val sharedPreferences = requireActivity().getSharedPreferences("cart", Context.MODE_PRIVATE)
        val cartItemsJson = sharedPreferences.getString("cartItems", "[]") ?: "[]"
        val type = object : TypeToken<MutableList<CartItem>>() {}.type
        return Gson().fromJson(cartItemsJson, type) ?: mutableListOf()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}