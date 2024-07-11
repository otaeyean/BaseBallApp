import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.baseballapp.PlayerAdapter
import com.example.baseballapp.PlayerData
import com.example.baseballapp.R
import com.example.baseballapp.databinding.FragmentInformationBinding

class InformationFragment : Fragment() {

    private lateinit var binding: FragmentInformationBinding
    private lateinit var playerAdapter: PlayerAdapter
    private val playerList: MutableList<PlayerData> = mutableListOf()
    private lateinit var homeground:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 스피너 설정
        val teams = resources.getStringArray(R.array.team_array)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, teams)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        // RecyclerView 설정
        playerAdapter = PlayerAdapter(playerList)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 1) // 2열 그리드
        binding.recyclerView.adapter = playerAdapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedTeam = teams[position]
                loadPlayer(selectedTeam)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun loadPlayer(team: String) {
        homeground=binding.homeground
        playerList.clear()

        when (team) {
            "팀 선택"->{
                playerList.add(PlayerData(R.drawable.baseball, "팀을 선택하세요", null))
                homeground.text="홈구장:"

            }
            "LG 트윈스" -> {
                playerList.add(PlayerData(R.drawable.hcg, "홍창기 51", "외야수 - 우투좌타"))
                playerList.add(PlayerData(R.drawable.lck, "임찬규 1", "투수 - 우투우타"))
                playerList.add(PlayerData(R.drawable.ojh, "오지환 10", "내야수 - 우투좌타"))
                homeground.text="홈구장: 잠실 야구장"
            }
            "KT 위즈" -> {
                playerList.add(PlayerData(R.drawable.kbh, "강백호 50", "내야수 - 우투좌타"))
                playerList.add(PlayerData(R.drawable.shj, "소형준 30", "투수 - 우투우타"))
                homeground.text="홈구장: 수원 KT 위즈파크"
            }
            "SSG 랜더스" -> {
                playerList.add(PlayerData(R.drawable.cj, "최정 14", "내야수 - 우투우타"))
                playerList.add(PlayerData(R.drawable.psh, "박성한 2", "내야수 - 우투좌타"))
                homeground.text="홈구장: 인천 SSG 랜더스필드"
            }
            "NC 다이노스" -> {
                playerList.add(PlayerData(R.drawable.kjw, "김주원 7", "내야수 - 우투양타"))
                playerList.add(PlayerData(R.drawable.pkw, "박건우 37", "외야수 - 우투우타"))
                homeground.text="홈구장: 창원 NC파크"
            }
            "두산 베어스" ->{
                playerList.add(PlayerData(R.drawable.yej, "양의지 25", "포수 - 우투우타"))
                playerList.add(PlayerData(R.drawable.kty, "김택연 63", "투수 - 우투우타"))
                homeground.text="홈구장: 잠실 야구장"
            }
            "KIA 타이거즈" ->{
                homeground.text="홈구장: 기아 챔피언스 필드"
            }
            "롯데 자이언츠" -> {
                homeground.text="홈구장: 사직 야구장"
            }
            "삼성 라이온즈" ->{
                homeground.text="홈구장: 대구 삼성 라이온즈 파크"
            }
            "한화 이글스" ->{
                homeground.text="홈구장: 한화생명 이글스 파크"
            }
            "키움 히어로즈" ->{
                homeground.text="홈구장: 고척 스카이돔"
            }
        }

        playerAdapter.notifyDataSetChanged() // RecyclerView에 변경 사항을 알림
    }
}

