package com.example.baseballapp

import com.google.gson.annotations.SerializedName

data class CartItem(
    @SerializedName("productName") var productName: String,
    @SerializedName("productPrice") var productPrice: Int,
    @SerializedName("productImage") var productImage: Int,
    @SerializedName("productQuantity") var productQuantity: Int
)