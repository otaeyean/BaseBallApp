import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.baseballapp.R

class RankingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_ranking, container, false)

        val spinner = rootView.findViewById<Spinner>(R.id.spinner_ranking_category)
        val layoutRankings = rootView.findViewById<LinearLayout>(R.id.layout_rankings)

        val categories = resources.getStringArray(R.array.ranking_categories)
        val adapter = object : ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, categories) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                view.setTextColor(Color.parseColor("#123269")) // 파란색으로 변경
                view.setTypeface(null, Typeface.BOLD)
                view.textSize = 18f // 글자 크기 변경
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                view.setTextColor(Color.parseColor("#123269")) // 파란색으로 변경
                view.setTypeface(null, Typeface.BOLD) // 진하게 변경
                view.textSize = 18f // 글자 크기 변경
                return view
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        // 스피너의 아이템 선택 리스너 설정
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCategory = parent.getItemAtPosition(position).toString()
                updateRankings(selectedCategory, layoutRankings)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // 아무것도 선택되지 않았을 때 처리
            }
        }
        return rootView
    }

    // 선택된 카테고리에 따라 순위를 업데이트하는 함수
    private fun updateRankings(category: String, layoutRankings: LinearLayout) {
        layoutRankings.removeAllViews()

        val tableLayout = TableLayout(context)
        tableLayout.layoutParams = TableLayout.LayoutParams(
            TableLayout.LayoutParams.MATCH_PARENT,
            TableLayout.LayoutParams.WRAP_CONTENT
        )

        when (category) {
            "팀순위" -> {
                addTableHeader(tableLayout, arrayOf("순위", "팀", "경기", "승", "무", "패", "승차", "연속"), arrayOf())
                addTableRow(tableLayout, arrayOf("1", "LG", "120", "70", "5", "45", "-", "0.609"))
                addTableRow(tableLayout, arrayOf("2", "KIA", "120", "65", "5", "50", "5", "0.565"))
                // 추가 데이터...
            }
            "타자 주요순위" -> {
                addTableHeader(tableLayout, arrayOf("순위", "이름", "경기", "타석", "타수", "안타", "2타", "3타", "홈런", "타점", "득점", "도루", "사사구", "삼진", "타율", "출루율", "장타율", "OPS"), arrayOf(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17))
                addTableRow(tableLayout, arrayOf("1", "홍창기", "77", "334", "305", "110", "15", "1", "9", "62", "43", "3", "25", "41", "0.351", "0.398", "0.505", "0.903"))
                // 추가 데이터...
            }
            "투수 주요순위" -> {
                addTableHeader(tableLayout, arrayOf("순위", "이름", "경기", "승", "패", "세이브", "홀드", "이닝", "투구수", "피안타", "피홈런", "탈삼진", "사사구", "실점", "자책", "평균자책", "WHIP", "QS"), arrayOf(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17))
                addTableRow(tableLayout, arrayOf("1", "네일", "12", "12", "12", "12", "12", "12", "12", "12", "12", "12", "12", "12", "12", "12", "12", "12"))
                // 추가 데이터...
            }
            else -> {
                val textView = TextView(context)
                textView.text = "카테고리를 선택하세요"
                textView.textSize = 80f
                layoutRankings.addView(textView)
            }
        }

        layoutRankings.addView(tableLayout)
    }

    // 테이블 헤더 추가 함수
    private fun addTableHeader(tableLayout: TableLayout, headers: Array<String>, sortableColumns: Array<Int>) {
        val tableRow = TableRow(context)
        for ((index, header) in headers.withIndex()) {
            val textView = TextView(context)
            textView.text = header
            textView.setTypeface(null, android.graphics.Typeface.BOLD)
            textView.setPadding(30, 30, 30, 30)
            textView.textSize = 18f
            textView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white)) // 헤더 배경 색상 설정
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.black)) // 글씨 색상 설정

            // 정렬 가능 열인지 확인
            if (sortableColumns.contains(index)) {
                textView.setOnClickListener {
                    sortTableByColumn(tableLayout, index)
                    highlightColumnHeader(tableLayout, index)
                }
            }
            tableRow.addView(textView)
        }
        tableLayout.addView(tableRow)
    }

    // 테이블 행 추가 함수
    private fun addTableRow(tableLayout: TableLayout, row: Array<String>) {
        val tableRow = TableRow(context)
        for (cell in row) {
            val textView = TextView(context)
            textView.text = cell
            textView.setPadding(30, 30, 30, 30)
            textView.textSize = 16f
            tableRow.addView(textView)
        }
        tableLayout.addView(tableRow)
    }

    // 테이블을 열 기준으로 정렬하는 함수
    private fun sortTableByColumn(tableLayout: TableLayout, columnIndex: Int) {
        val rows = mutableListOf<TableRow>()
        for (i in 1 until tableLayout.childCount) {
            rows.add(tableLayout.getChildAt(i) as TableRow)
        }

        rows.sortWith { row1, row2 ->
            val text1 = (row1.getChildAt(columnIndex) as TextView).text.toString()
            val text2 = (row2.getChildAt(columnIndex) as TextView).text.toString()
            text1.compareTo(text2)
        }

        for (row in rows) {
            tableLayout.removeView(row)
            tableLayout.addView(row)
        }
    }

    // 클릭된 열 헤더를 강조하는 함수
    private fun highlightColumnHeader(tableLayout: TableLayout, columnIndex: Int) {
        val headerRow = tableLayout.getChildAt(0) as TableRow
        for (i in 0 until headerRow.childCount) {
            val textView = headerRow.getChildAt(i) as TextView
            if (i == columnIndex) {
                textView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            } else {
                textView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
        }
    }
}
