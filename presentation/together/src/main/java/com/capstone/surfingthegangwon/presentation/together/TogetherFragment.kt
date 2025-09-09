package com.capstone.surfingthegangwon.presentation.together

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.surfingthegangwon.core.model.Grade
import com.capstone.surfingthegangwon.core.ui.CustomHeaderView
import com.capstone.surfingthegangwon.domain.together.model.SessionListItem
import com.capstone.surfingthegangwon.presentation.together.databinding.FragmentTogetherBinding
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import com.capstone.surfingthegangwon.core.resource.R as CoRes

class TogetherFragment : Fragment() {
    private lateinit var binding: FragmentTogetherBinding

    private lateinit var weekTitle: TextView
    private lateinit var weekAdapter: WeekAdapter
    private lateinit var areaAdapter: AreaAdapter
    private lateinit var sessionAdapter: SessionAdapter

    private var baseDate: LocalDate = LocalDate.now()
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일", Locale.KOREA)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTogetherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    /** 초기 UI 세팅: 헤더, 클릭 리스너, 리사이클러뷰, 주간 캘린더 데이터 */
    private fun initUI() {
        setupHeader()
        setupClickListeners()
        setupRecyclerViews()
        updateWeek()
    }

    /** 버튼 클릭 리스너 설정 */
    private fun setupClickListeners() {
        binding.btnPrevWeek.setOnClickListener {
            baseDate = baseDate.minusWeeks(1)
            updateWeek()
        }

        binding.btnNextWeek.setOnClickListener {
            baseDate = baseDate.plusWeeks(1)
            updateWeek()
        }

        binding.fab.setOnClickListener {
            val action = TogetherFragmentDirections.actionTogetherToWriting()
            findNavController().navigate(action)
        }
    }

    /** 모든 리사이클러뷰 초기화 */
    private fun setupRecyclerViews() {
        setupAreaRecyclerView()
        setupWeekRecyclerView()
        setupSessionRecyclerView()
    }

    /** 지역 리스트 RecyclerView 설정 */
    private fun setupAreaRecyclerView() {
        val dummyAreas =
            listOf("죽도해변A", "죽도해변B", "죽도해변C", "죽도해변D", "죽도해변A", "죽도해변B", "죽도해변C", "죽도해변D")

        areaAdapter = AreaAdapter(dummyAreas)
        binding.recyclerArea.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = areaAdapter
        }
    }

    /** 주간 캘린더 RecyclerView 설정 */
    private fun setupWeekRecyclerView() {
        weekTitle = binding.weekTitle
        weekAdapter = WeekAdapter { clickedDate ->
            weekTitle.text = clickedDate.format(dateFormatter)
        }

        binding.recyclerWeek.apply {
            layoutManager = FlexboxLayoutManager(requireContext()).apply {
                flexDirection = FlexDirection.ROW
                justifyContent = JustifyContent.CENTER
            }
            adapter = weekAdapter
        }
    }

    /** 세션 리스트 RecyclerView 설정 및 더미 데이터 삽입 */
    private fun setupSessionRecyclerView() {
        sessionAdapter = SessionAdapter()
        binding.recyclerSession.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = sessionAdapter
        }
        loadDummySessions()
    }

    /** 세션 더미 데이터 로딩 */
    private fun loadDummySessions() {
        val dummySessions = listOf(
            SessionListItem.Header(
                beachName = "죽도해변 A",
                temperature = "기온 15C 수온 21.3C",
                wave = "0.69M - 4.49S",
                wind = "W 8.6 m/h"
            ),
            SessionListItem.Content(
                title = "약한 파도로 함께 입문해봐요!",
                sessionTime = "세션 시간",
                time = "14:00",
                participants = "3/5",
                grade = Grade.from("초급")
            ),
            SessionListItem.Content(
                title = "조금 더 도전해볼까?",
                sessionTime = "세션 시간",
                time = "16:00",
                participants = "4/6",
                grade = Grade.from("중급")
            ),
            SessionListItem.Content(
                title = "고수들과 함께 실력 업!",
                sessionTime = "세션 시간",
                time = "16:00",
                participants = "4/6",
                grade = Grade.from("고급")
            ),
            SessionListItem.Content(
                title = "약한 파도로 함께 입문해봐요!",
                sessionTime = "세션 시간",
                time = "14:00",
                participants = "3/5",
                grade = Grade.from("초급")
            ),
            SessionListItem.Content(
                title = "조금 더 도전해볼까?",
                sessionTime = "세션 시간",
                time = "16:00",
                participants = "4/6",
                grade = Grade.from("중급")
            ),
            SessionListItem.Content(
                title = "고수들과 함께 실력 업!",
                sessionTime = "세션 시간",
                time = "16:00",
                participants = "4/6",
                grade = Grade.from("고급")
            )
        )

        sessionAdapter.submitList(dummySessions)
    }

    /** 기준 날짜의 주간 날짜 리스트를 만들고 캘린더에 표시 */
    private fun updateWeek() {
        val weekDates = getWeekDates(baseDate)

        // 선택된 날짜의 요일을 저장해 다음 주에도 유지
        val prevSelectedDayOfWeek = weekAdapter.getSelectedDate()?.dayOfWeek

        // 캘린더 텍스트 업데이트
        weekTitle.text = baseDate.format(dateFormatter)

        // 주간 리스트 갱신 및 선택된 요일 유지
        weekAdapter.submitList(weekDates) {
            val dateToSelect = weekDates.find { it.dayOfWeek == prevSelectedDayOfWeek }
                ?: weekDates.firstOrNull { it.dayOfWeek == DayOfWeek.SUNDAY }

            dateToSelect?.let { weekAdapter.selectDate(it) }
        }
    }

    /** 해당 날짜가 포함된 주(일~토) 리스트 생성 */
    private fun getWeekDates(date: LocalDate): List<LocalDate> {
        val sunday = date.with(DayOfWeek.SUNDAY)
        return List(7) { offset -> sunday.plusDays(offset.toLong()) }
    }

    /**
     * 헤더 뷰를 세팅하는 메소드
     */
    private fun setupHeader() {
        binding.headerView.setBeachTabItem()
        binding.headerView.adjustIndicatorWidth()
        binding.headerView.setScreenTitle(getString(R.string.sessions))
        binding.headerView.setOnTabSelectedListener(object :
            CustomHeaderView.OnTabSelectedListener {
            override fun onTabSelected(position: Int) {
                val place = when (position) {
                    0 -> getString(CoRes.string.yangyang)
                    1 -> getString(CoRes.string.goseong)
                    2 -> getString(CoRes.string.sokcho)
                    3 -> getString(CoRes.string.gangneung)
                    else -> "미지정"
                }
                Log.d("TogetherFragment", "선택된 탭: $position, 장소: $place")
            }
        })
    }


}