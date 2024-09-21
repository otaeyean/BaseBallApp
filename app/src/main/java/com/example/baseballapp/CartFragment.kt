package com.example.baseballapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.example.baseballapp.databinding.FragmentCartBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val loginService by lazy { LoginService(requireContext()) }

    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root

        cartAdapter = CartAdapter(requireContext(), R.layout.cart_item, getCartItems(), ::updateTotalPrice)
        binding.lvCartItems.adapter = cartAdapter

        binding.btnCheckout.setOnClickListener {
            loginService.checkToken { isValid ->
                if (isValid) {
                    val selectedItems = getSelectedItems()
                    val payFragment = PayFragment.newInstance(selectedItems)
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, payFragment)
                        .addToBackStack(null)
                        .commit()
                } else {
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        updateTotalPrice()

        return view
    }

    private fun getCartItems(): MutableList<CartItem> {
        val sharedPreferences = requireActivity().getSharedPreferences("cart", Context.MODE_PRIVATE)
        val cartItemsJson = sharedPreferences.getString("cartItems", "[]") ?: "[]"
        val type = object : TypeToken<MutableList<CartItem>>() {}.type
        return Gson().fromJson(cartItemsJson, type) ?: mutableListOf()
    }

    private fun getSelectedItems(): List<CartItem> {
        val selectedItems = mutableListOf<CartItem>()
        for (i in 0 until binding.lvCartItems.adapter.count) {
            val cartItem = binding.lvCartItems.adapter.getItem(i) as CartItem
            val itemView = binding.lvCartItems.getChildAt(i)
            val checkBox = itemView?.findViewById<CheckBox>(R.id.cbSelectProduct)
            if (checkBox?.isChecked == true) {
                selectedItems.add(cartItem)
            }
        }
        return selectedItems
    }

    private fun updateTotalPrice() {
        var totalPrice = 0
        for (i in 0 until binding.lvCartItems.adapter.count) {
            val cartItem = binding.lvCartItems.adapter.getItem(i) as CartItem
            val itemView = binding.lvCartItems.getChildAt(i)
            val checkBox = itemView?.findViewById<CheckBox>(R.id.cbSelectProduct)

            if (checkBox?.isChecked == true) {
                totalPrice += cartItem.productPrice * cartItem.productQuantity
            }
        }
        binding.tvTotalPrice.text = "총 합계: $totalPrice 원"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}