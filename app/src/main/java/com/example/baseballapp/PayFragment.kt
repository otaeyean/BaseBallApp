package com.example.baseballapp

import SelectedItemsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PayFragment : Fragment() {

    private lateinit var selectedItems: List<CartItem>
    private var totalPrice = 0

    companion object {
        fun newInstance(selectedItems: List<CartItem>): PayFragment {
            val fragment = PayFragment()
            fragment.selectedItems = selectedItems
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pay, container, false)

        totalPrice = selectedItems.sumOf { it.productPrice * it.productQuantity }
        view.findViewById<TextView>(R.id.tvTotalAmount).text = "총 합계: ₩$totalPrice"

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvSelectedItems)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = SelectedItemsAdapter(selectedItems)
        recyclerView.adapter = adapter

        val paymentMethod = getRandomPaymentMethod()
        view.findViewById<TextView>(R.id.tvPaymentMethod).text = "결제 수단: $paymentMethod"

        // 결제 진행하기 버튼 클릭 리스너 추가
        view.findViewById<Button>(R.id.btnProceedToPayment).setOnClickListener {
            if (areAllFieldsFilled(view)) {
                showOrderCompletedDialog()
            } else {
                showAlert("모든 정보를 입력해 주세요.")
            }
        }

        return view
    }

    private fun areAllFieldsFilled(view: View): Boolean {
        val recipientName = view.findViewById<EditText>(R.id.etRecipientName).text.toString().trim()
        val deliveryAddress = view.findViewById<EditText>(R.id.etDeliveryAddress).text.toString().trim()
        val phoneNumber = view.findViewById<EditText>(R.id.etPhoneNumber).text.toString().trim()
        val deliveryRequest = view.findViewById<EditText>(R.id.etDeliveryRequest).text.toString().trim()

        return recipientName.isNotEmpty() && deliveryAddress.isNotEmpty() && phoneNumber.isNotEmpty() && deliveryRequest.isNotEmpty()
    }

    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("입력 오류")
        builder.setMessage(message)
        builder.setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }

    private fun showOrderCompletedDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("주문 완료")
        builder.setMessage("주문이 완료되었습니다!")
        builder.setPositiveButton("확인") { dialog, _ ->
            dialog.dismiss()
            navigateToShopFragment() // ShopFragment로 이동
        }
        builder.create().show()
    }

    private fun navigateToShopFragment() {
        val shopFragment = ShopFragment()
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.fragment_container, shopFragment) // fragment_container는 ShopFragment가 표시될 ViewGroup의 ID
        transaction.addToBackStack(null) // 이전 Fragment로 돌아갈 수 있도록 추가
        transaction.commit()
    }

    private fun getRandomPaymentMethod(): String {
        val paymentMethods = arrayOf("한성은행: 계좌이체", "한성은행: 무통장 입금")
        return paymentMethods.random()
    }
}
