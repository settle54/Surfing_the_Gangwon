package com.capstone.surfingthegangwon.presentation.sessionwriting

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.surfingthegangwon.presentation.sessionwriting.databinding.FragmentSessionWritingBinding

class SessionWritingFragment : Fragment() {
    private lateinit var binding: FragmentSessionWritingBinding

    private lateinit var regionAdapter: RegionAdapter
    private lateinit var beachAdapter: BeachAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSessionWritingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun initUi() {
        setTopToolBar()
        setupRecyclerViews()
        setupClickListeners()
    }

    private fun setTopToolBar() {
        binding.topAppBar.setTitle(getString(R.string.create_new_session))
        binding.topAppBar.setOnBackClick {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    /** 버튼 클릭 리스너 설정 */
    private fun setupClickListeners() {
        binding.timeTv.setOnClickListener { view -> showTimePicker(view) }
        binding.calanderTv.setOnClickListener { view -> showDatePicker(view) }
    }

    /** 모든 리사이클러 뷰 초기화 */
    private fun setupRecyclerViews() {
        setupRegionRecyclerView()
        setupBeachesRecyclerView()
    }

    /**
     * 지역 리사이클러 뷰 설정
     */
    private fun setupRegionRecyclerView() {
        val dummyRegions =
            listOf("양양", "고성", "속초", "강릉", "양양", "고성", "속초", "강릉")

        regionAdapter = RegionAdapter(dummyRegions)
        binding.regionsRcv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = regionAdapter
        }
    }

    /**
     * 해변 리사이클러 뷰 설정
     */
    private fun setupBeachesRecyclerView() {
        val dummyBeaches =
            listOf("죽도해변A", "죽도해변B", "죽도해변C", "죽도해변D", "죽도해변A", "죽도해변B", "죽도해변C", "죽도해변D")

        beachAdapter = BeachAdapter(dummyBeaches)
        binding.beachesRcv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = beachAdapter
        }
    }

    /**
     * 데이트피커를 보여주고 텍스트뷰 날짜를 변경하는 함수
     */
    private fun showDatePicker(view: View) {
        val dateTextView = view as TextView

        // 오늘 날짜 가져오기
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) // 0부터 시작 (0 = 1월)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val listener = DatePickerDialog.OnDateSetListener { _, y, m, d ->
            // m은 0부터 시작 → +1 해줘야 함
            dateTextView.text = "%d년 %d월 %d일".format(y, m + 1, d)
            dateTextView.isSelected = true
        }

        DatePickerDialog(requireContext(), listener, year, month, day).show()
    }

    /**
     * 타임피커를 보여주고 텍스트뷰 시간을 변경하는 함수
     */
    private fun showTimePicker(view: View) {
        val timeTextView = view as TextView

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val listener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            // 오전/오후 결정
            val amPm = if (hourOfDay < 12) "오전" else "오후"

            // 0시는 12시로, 13~23시는 12를 빼서 1~11시로 표시
            val hour12 = when {
                hourOfDay == 0 -> 12  // 자정
                hourOfDay > 12 -> hourOfDay - 12
                else -> hourOfDay
            }

            // 최종 문자열
            val timeString = if (minute == 0) {
                "%s %d시".format(amPm, hour12)
            } else {
                "%s %d시 %d분".format(amPm, hour12, minute)
            }
            timeTextView.text = timeString
            timeTextView.isSelected = true
        }

        TimePickerDialog(requireContext(), listener, hour, minute, true).show()
    }
}