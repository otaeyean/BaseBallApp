package com.example.baseballapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.baseballapp.ApiObject.getRetrofitService
import com.example.baseballapp.databinding.FragmentInformationBinding
import com.example.baseballapp.databinding.PlayerBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InformationFragment : Fragment() {

    private lateinit var binding: FragmentInformationBinding
    private lateinit var playerAdapter: PlayerAdapter
    private val playerList: MutableList<PlayerData> = mutableListOf()
    private lateinit var homeground: TextView
    private lateinit var selectedTeam:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val teams = resources.getStringArray(R.array.team_array)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, teams)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        playerAdapter = PlayerAdapter()
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.recyclerView.adapter = playerAdapter

        fetchPlayers() //모든 선수 데이터 가져오기

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedTeam = teams[position]
                loadPlayer(selectedTeam)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun loadPlayer(team: String) {
        homeground = binding.homeground

        when (team) {
            "팀 선택" -> {
                homeground.text = "홈구장:"
                filterPlayersByTeam("팀 선택")
            }
            "LG 트윈스" -> {
                homeground.text = "홈구장: 잠실 야구장"
                filterPlayersByTeam("LG")
            }
            "KT 위즈" -> {
                homeground.text = "홈구장: 수원 KT 위즈파크"
                filterPlayersByTeam("KT")
            }
            "SSG 랜더스" -> {
                homeground.text = "홈구장: 인천 SSG 랜더스필드"
                filterPlayersByTeam("SSG")
            }
            "NC 다이노스" -> {
                homeground.text = "홈구장: 창원 NC파크"
                filterPlayersByTeam("NC")
            }
            "두산 베어스" -> {
                homeground.text = "홈구장: 잠실 야구장"
                filterPlayersByTeam("두산")
            }
            "KIA 타이거즈" -> {
                homeground.text = "홈구장: 기아 챔피언스 필드"
                filterPlayersByTeam("KIA")
            }
            "롯데 자이언츠" -> {
                homeground.text = "홈구장: 사직 야구장"
                filterPlayersByTeam("롯데")
            }
            "삼성 라이온즈" -> {
                homeground.text = "홈구장: 대구 삼성 라이온즈 파크"
                filterPlayersByTeam("삼성")
            }
            "한화 이글스" -> {
                homeground.text = "홈구장: 한화생명 이글스 파크"
                filterPlayersByTeam("한화")
            }
            "키움 히어로즈" -> {
                homeground.text = "홈구장: 고척 스카이돔"
                filterPlayersByTeam("키움")
            }
        }
    }

    private fun fetchPlayers() { //모든 선수 가져오기
        val call = getRetrofitService.getAllPlayers()
        call.enqueue(object : Callback<List<PlayerData>> {
            override fun onResponse(
                call: Call<List<PlayerData>>,
                response: Response<List<PlayerData>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        playerList.addAll(it)
                        playerAdapter.setList(playerList)
                        filterPlayersByTeam(selectedTeam)
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to load data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<PlayerData>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun filterPlayersByTeam(team: String) { //팀 별로 선수 필터링
        val filteredList = when (team) {
            "팀 선택" -> listOf(PlayerData(null, "", "팀을 선택하세요", null, null, null, null, null))
            else -> playerList.filter { it.team == team }
        }
        playerAdapter.setList(filteredList)
    }
}
