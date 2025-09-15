package com.capstone.surfingthegangwon.presentation.together

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.surfingthegangwon.core.ui.CustomHeaderView
import com.capstone.surfingthegangwon.core.util.DateUtils
import com.capstone.surfingthegangwon.presentation.together.databinding.FragmentTogetherBinding
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import com.capstone.surfingthegangwon.core.resource.R as CoRes

@AndroidEntryPoint
class TogetherFragment : Fragment() {
    private lateinit var binding: FragmentTogetherBinding

    private lateinit var weekTitle: TextView
    private lateinit var weekAdapter: WeekAdapter
    private lateinit var areaAdapter: AreaAdapter
    private lateinit var sessionAdapter: SessionAdapter

    private val seashoreViewModel: SeashoreViewModel by viewModels()
    private val sessionViewModel: SessionViewModel by viewModels()

    private var baseDate: LocalDate = LocalDate.now()
    private val dateFormatter = DateUtils.KOREAN_DATE
    private var selectedDate: LocalDate? = baseDate

    private var selectedTabIndex: Int = 0
    private var selectedSeashoreId: Int? = 1
    private val selectedAreaByTab = mutableMapOf<Int, Int>()

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

    override fun onResume() {
        super.onResume()
        tryFetchSessions()
    }

    /** 초기 UI 세팅: 헤더, 클릭 리스너, 리사이클러뷰, 주간 캘린더 데이터 */
    private fun initUI() {
        setupHeader()
        setupClickListeners()
        setupRecyclerViews()
        updateWeek()
        tryFetchSessions()
        Log.d(TAG, "initUi")
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

    private fun tryFetchSessions() {
        val seashoreId = selectedSeashoreId
        val date = selectedDate
        if (seashoreId != null && date != null) {
            sessionViewModel.getGatheringPosts(seashoreId, date)
        }
    }

    /** 지역 리스트 RecyclerView 설정 */
    private fun setupAreaRecyclerView() {
        areaAdapter = AreaAdapter { seashore ->
            selectedSeashoreId = seashore.seashoreId
            selectedAreaByTab[selectedTabIndex] = seashore.seashoreId
            tryFetchSessions()
        }
        binding.recyclerArea.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = areaAdapter
        }
        observeSeashores()
    }

    private fun observeSeashores() {
        seashoreViewModel.seashores.observe(viewLifecycleOwner) { items ->
            areaAdapter.submitList(items) {
                val storedId = selectedAreaByTab[selectedTabIndex]
                val targetId = storedId?.takeIf { id -> items.any { it.seashoreId == id } }
                    ?: items.firstOrNull()?.seashoreId  // 저장값이 리스트에 없으면 첫 번째로 fallback

                if (targetId != null) {
                    // UI 선택 복원 + onSelected 콜백 → tryFetchSessions() 연결
                    areaAdapter.selectById(targetId)
                } else {
                    // 리스트가 비면 세션도 비워둠
                    sessionAdapter.submitList(emptyList())
                    selectedSeashoreId = null
                }
            }
        }
    }

    /** 주간 캘린더 RecyclerView 설정 */
    private fun setupWeekRecyclerView() {
        weekTitle = binding.weekTitle
        weekAdapter = WeekAdapter { clickedDate ->
            selectedDate = clickedDate
            weekTitle.text = clickedDate.format(dateFormatter)
            tryFetchSessions()
        }

        binding.recyclerWeek.apply {
            layoutManager = FlexboxLayoutManager(requireContext()).apply {
                flexDirection = FlexDirection.ROW
                justifyContent = JustifyContent.CENTER
            }
            adapter = weekAdapter
        }

        selectedDate?.let { weekTitle.text = it.format(dateFormatter) }
    }

    /** 세션 리스트 RecyclerView 설정 및 더미 데이터 삽입 */
    private fun setupSessionRecyclerView() {
        sessionAdapter = SessionAdapter { session ->
            val action = TogetherFragmentDirections.actionTogetherToReading(session)
            findNavController().navigate(action)
        }
        binding.recyclerSession.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = sessionAdapter
        }
        observeSessions()
    }

    private fun observeSessions() {
        sessionViewModel.sessions.observe(viewLifecycleOwner) { items ->
            sessionAdapter.submitList(items)
        }
    }

    /** 기준 날짜의 주간 날짜 리스트를 만들고 캘린더에 표시 */
    private fun updateWeek() {
        val weekDates = getWeekDates(baseDate)
        Log.d(TAG, weekDates.toString())

        val prevSelectedDayOfWeek = weekAdapter.getSelectedDate()?.dayOfWeek

        weekAdapter.submitList(weekDates) {
            val byExactSelected = selectedDate?.takeIf { it in weekDates }
            val bySameDow = prevSelectedDayOfWeek?.let { dow ->
                weekDates.find { it.dayOfWeek == dow }
            }
            val today = LocalDate.now().takeIf { it in weekDates }

            val dateToSelect = byExactSelected ?: bySameDow ?: today ?: baseDate

            weekAdapter.selectDate(dateToSelect)
            selectedDate = dateToSelect
            weekTitle.text = dateToSelect.format(dateFormatter)
        }
    }

    /** 해당 날짜가 포함된 주(일~토) 리스트 생성 */
    private fun getWeekDates(date: LocalDate): List<LocalDate> {
        val sunday = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
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
                selectedTabIndex = position
                val place = when (position) {
                    0 -> getString(CoRes.string.gangneung)
                    1 -> getString(CoRes.string.yangyang)
                    2 -> getString(CoRes.string.sokcho)
                    3 -> getString(CoRes.string.goseong)
                    else -> "미지정"
                }
                val id = position + 1
                seashoreViewModel.getSeashores(id)
                Log.d(TAG, "선택된 탭: $position, 장소: $place")
            }
        })
        binding.headerView.selectTab(selectedTabIndex)
    }

    companion object {
        private const val TAG = "TogetherFragment"
    }

}