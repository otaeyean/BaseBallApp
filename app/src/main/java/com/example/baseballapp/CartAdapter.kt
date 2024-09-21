package com.example.baseballapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CartAdapter(
    private val context: Context,
    private val layout: Int,
    private val cartItemList: MutableList<CartItem>,
    private val updateTotalPrice: () -> Unit
) : BaseAdapter() {

    override fun getCount(): Int {
        return cartItemList.size
    }

    override fun getItem(position: Int): Any {
        return cartItemList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(layout, parent, false)

            holder = ViewHolder()
            holder.ivProductImage = view.findViewById(R.id.ivProductImage)
            holder.tvProductName = view.findViewById(R.id.tvProductName)
            holder.tvProductPrice = view.findViewById(R.id.tvProductPrice)
            holder.spinnerProductQuantity = view.findViewById(R.id.spinnerProductQuantity)
            holder.btnRemoveItem = view.findViewById(R.id.btnRemoveItem)
            holder.cbSelectProduct = view.findViewById(R.id.cbSelectProduct) // CheckBox 추가

            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val cartItem = cartItemList[position]
        holder.ivProductImage?.setImageResource(cartItem.productImage)
        holder.tvProductName?.text = cartItem.productName
        holder.tvProductPrice?.text = String.format("₩%d", cartItem.productPrice)

        val quantities = (1..10).map { "수량: $it" }.toTypedArray()
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, quantities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.spinnerProductQuantity?.adapter = adapter
        holder.spinnerProductQuantity?.setSelection(cartItem.productQuantity - 1)

        holder.spinnerProductQuantity?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                cartItem.productQuantity = position + 1
                saveCartItems()
                updateTotalPrice()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        holder.cbSelectProduct?.setOnCheckedChangeListener { _, _ ->
            updateTotalPrice()
        }

        holder.btnRemoveItem?.setOnClickListener {
            cartItemList.removeAt(position)
            notifyDataSetChanged()
            saveCartItems()
            Toast.makeText(context, "장바구니에서 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            updateTotalPrice()
        }

        return view
    }

    private fun saveCartItems() {
        val sharedPreferences = context.getSharedPreferences("cart", Context.MODE_PRIVATE)
        val gson = Gson()
        val type = object : TypeToken<MutableList<CartItem>>() {}.type
        val cartItemsJson = gson.toJson(cartItemList, type)
        sharedPreferences.edit().putString("cartItems", cartItemsJson).apply()
    }

    private class ViewHolder {
        var ivProductImage: ImageView? = null
        var tvProductName: TextView? = null
        var tvProductPrice: TextView? = null
        var spinnerProductQuantity: Spinner? = null
        var btnRemoveItem: Button? = null
        var cbSelectProduct: CheckBox? = null
    }
}