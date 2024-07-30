import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baseballapp.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RankingFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var teamRankAdapter: TeamRankAdapter
    private lateinit var hitterRankAdapter: HitterRankAdapter
    private lateinit var pitcherRankAdapter: PitcherRankAdapter
    private lateinit var headerScrollView: HorizontalScrollView
    private val dataScrollViews = mutableListOf<HorizontalScrollView>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_ranking, container, false)

        val rankingImage = rootView.findViewById<ImageView>(R.id.ranking_image)
        val spinner = rootView.findViewById<Spinner>(R.id.spinner_ranking_category)
        recyclerView = rootView.findViewById(R.id.recycler_view_rankings)
        headerScrollView = rootView.findViewById(R.id.header_scroll_view)

        teamRankAdapter = TeamRankAdapter()
        hitterRankAdapter = HitterRankAdapter(emptyList(), ::registerScrollView)
        pitcherRankAdapter = PitcherRankAdapter(emptyList(), ::registerScrollView)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = teamRankAdapter // 기본적으로 팀 순위 어댑터 설정

        val categories = resources.getStringArray(R.array.ranking_categories)
        val adapter = object : ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            categories
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                view.setTextColor(Color.parseColor("#123269"))
                view.setTypeface(null, Typeface.BOLD)
                view.textSize = 23f
                return view
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                view.setTextColor(Color.parseColor("#123269"))
                view.setTypeface(null, Typeface.BOLD)
                view.textSize = 23f
                return view
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCategory = parent.getItemAtPosition(position).toString()
                when (selectedCategory) {
                    "팀순위" -> showTeamRankings()
                    "타자 주요순위" -> showHitterRankings()
                    "투수 주요순위" -> showPitcherRankings()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // 아무것도 선택되지 않았을 때 처리
            }
        }

        headerScrollView.setOnScrollChangeListener { _, scrollX, _, _, _ ->
            dataScrollViews.forEach { it.scrollTo(scrollX, 0) }
        }

        return rootView
    }

    private fun registerScrollView(scrollView: HorizontalScrollView) {
        dataScrollViews.add(scrollView)
        scrollView.setOnScrollChangeListener { _, scrollX, _, _, _ ->
            headerScrollView.scrollTo(scrollX, 0)
            dataScrollViews.forEach { if (it != scrollView) it.scrollTo(scrollX, 0) }
        }
    }

    private fun showTeamRankings() {
        headerScrollView.visibility = View.GONE
        recyclerView.adapter = teamRankAdapter
        fetchTeamRankings()
    }

    private fun showHitterRankings() {
        headerScrollView.visibility = View.VISIBLE
        headerScrollView.removeAllViews()
        val hitterHeaderView = layoutInflater.inflate(R.layout.hitter_rank_header_item, headerScrollView, false)
        headerScrollView.addView(hitterHeaderView)
        recyclerView.adapter = hitterRankAdapter
        fetchHitterRankings()
    }

    private fun showPitcherRankings() {
        headerScrollView.visibility = View.VISIBLE
        headerScrollView.removeAllViews()
        val pitcherHeaderView = layoutInflater.inflate(R.layout.pitcher_rank_header_item, headerScrollView, false)
        headerScrollView.addView(pitcherHeaderView)
        recyclerView.adapter = pitcherRankAdapter
        fetchPitcherRankings()
    }

    private fun fetchTeamRankings() {
        ApiObject.getRetrofitService.getAllTeams().enqueue(object : Callback<List<TeamRankData>> {
            override fun onResponse(call: Call<List<TeamRankData>>, response: Response<List<TeamRankData>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        teamRankAdapter.setList(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<TeamRankData>>, t: Throwable) {
                Toast.makeText(context, "데이터를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchHitterRankings() {
        ApiObject.getRetrofitService.getAllHitters().enqueue(object : Callback<List<HitterRankData>> {
            override fun onResponse(call: Call<List<HitterRankData>>, response: Response<List<HitterRankData>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        hitterRankAdapter.setList(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<HitterRankData>>, t: Throwable) {
                Toast.makeText(context, "데이터를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchPitcherRankings() {
        ApiObject.getRetrofitService.getAllPitchers().enqueue(object : Callback<List<PitcherRankData>> {
            override fun onResponse(call: Call<List<PitcherRankData>>, response: Response<List<PitcherRankData>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        pitcherRankAdapter.setList(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<PitcherRankData>>, t: Throwable) {
                Toast.makeText(context, "데이터를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
