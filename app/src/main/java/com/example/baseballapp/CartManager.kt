package com.example.baseballapp

object CartManager {
    private val cartItems = mutableListOf<CartItem>()

    fun addItem(cartItem: CartItem) {
        cartItems.add(cartItem)
    }

    fun getCartItems(): List<CartItem> {
        return cartItems
    }
}