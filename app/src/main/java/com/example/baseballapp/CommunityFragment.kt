package com.example.yourapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.baseballapp.FreeBoardFragment
import com.example.baseballapp.QuestionBoardFragment
import com.example.baseballapp.R
import com.example.baseballapp.TradeBoardFragment
import com.example.baseballapp.databinding.FragmentCommunityBinding

class CommunityFragment : Fragment() {

    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        replaceFragment(FreeBoardFragment())
        updateButtonState(binding.freeBoardButton)

        binding.freeBoardButton.setOnClickListener {
            replaceFragment(FreeBoardFragment())
            updateButtonState(binding.freeBoardButton)
        }
        binding.questionBoardButton.setOnClickListener {
            replaceFragment(QuestionBoardFragment())
            updateButtonState(binding.questionBoardButton)
        }
        binding.tradeBoardButton.setOnClickListener {
            replaceFragment(TradeBoardFragment())
            updateButtonState(binding.tradeBoardButton)
        }

        binding.writePostButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.boardContainer, WritePostFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.searchButton.setOnClickListener {
            val query = binding.searchEditText.text.toString()
            if (query.isNotEmpty()) {
                performSearch(query)
            } else {
                Toast.makeText(context, "검색어를 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.boardContainer, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    private fun updateButtonState(activeButton: Button) {
        val buttons = listOf(binding.freeBoardButton, binding.questionBoardButton, binding.tradeBoardButton)
        buttons.forEach { button ->
            if (button == activeButton) {
                button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            } else {
                button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            }
        }
    }

    private fun performSearch(query: String) {
        // TODO: Implement the search functionality
        // You can replace the current fragment with a search result fragment or update the current fragment with search results
        Toast.makeText(context, "검색 기능을 구현하세요. 검색어: $query", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
