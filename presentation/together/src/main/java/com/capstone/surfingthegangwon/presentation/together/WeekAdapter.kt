package com.capstone.surfingthegangwon.presentation.together

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.surfingthegangwon.presentation.together.databinding.ItemWeekDayBinding
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import com.capstone.surfingthegangwon.core.resource.R as CoreRes


class WeekAdapter(private val onDateClick: (LocalDate) -> Unit) :
    ListAdapter<LocalDate, WeekAdapter.DateViewHolder>(DiffCallback) {

    // 현재 선택된 날짜
    private var selectedDate: LocalDate? = null

    object DiffCallback : DiffUtil.ItemCallback<LocalDate>() {
        override fun areItemsTheSame(oldItem: LocalDate, newItem: LocalDate) = oldItem == newItem
        override fun areContentsTheSame(oldItem: LocalDate, newItem: LocalDate) = oldItem == newItem
    }

    inner class DateViewHolder(
        private val binding: ItemWeekDayBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(date: LocalDate) = with(binding) {
            tvDay.text = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
            tvDate.text = date.dayOfMonth.toString()

            applyTextColor(date)
            applyFontStyle(date)

            root.setOnClickListener {
                handleClick(adapterPosition, date)
            }
        }

        /** 요일에 따라 색상 적용 */
        private fun applyTextColor(date: LocalDate) = with(binding) {
            val context = root.context
            val red = ContextCompat.getColor(context, CoreRes.color.sunday)
            val blue = ContextCompat.getColor(context, CoreRes.color.saturday)
            val default = Color.BLACK

            val color = when (date.dayOfWeek) {
                DayOfWeek.SUNDAY -> red
                DayOfWeek.SATURDAY -> blue
                else -> default
            }

            tvDay.setTextColor(color)
            tvDate.setTextColor(color)
        }

        /** 선택 여부에 따라 글꼴 적용 */
        private fun applyFontStyle(date: LocalDate) = with(binding) {
            val context = root.context
            val bold = ResourcesCompat.getFont(context, CoreRes.font.pretendard_bold)
            val regular = ResourcesCompat.getFont(context, CoreRes.font.pretendard_regular)

            val typeface = if (date == selectedDate) bold else regular
            tvDay.typeface = typeface
            tvDate.typeface = typeface
        }

        /** 클릭 시 선택 날짜 변경 및 갱신 */
        private fun handleClick(position: Int, clickedDate: LocalDate) {
            if (position == RecyclerView.NO_POSITION || clickedDate == selectedDate) return

            val previousDate = selectedDate
            selectedDate = clickedDate
            onDateClick(clickedDate)

            previousDate?.let {
                notifyItemChanged(currentList.indexOf(it))
            }
            notifyItemChanged(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val binding = ItemWeekDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /** 현재 선택된 날짜 반환 */
    fun getSelectedDate(): LocalDate? = selectedDate

    /**
     * 외부에서 선택 날짜를 지정할 때 사용
     * → 이전 선택과 현재 선택 항목을 갱신
     */
    fun selectDate(date: LocalDate) {
        val previous = selectedDate
        selectedDate = date
        onDateClick(date)

        previous?.let { notifyItemChanged(currentList.indexOf(it)) }
        notifyItemChanged(currentList.indexOf(date))
    }
}

