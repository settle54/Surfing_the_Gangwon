package com.capstone.surfingthegangwon.presentation.together

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.surfingthegangwon.domain.together.model.SessionListItem
import com.capstone.surfingthegangwon.presentation.together.databinding.ItemSectionHeaderBinding
import com.capstone.surfingthegangwon.presentation.together.databinding.ItemSessionBinding

class SessionAdapter : ListAdapter<SessionListItem, RecyclerView.ViewHolder>(DiffCallback) {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_CONTENT = 1

        private val DiffCallback = object : DiffUtil.ItemCallback<SessionListItem>() {
            override fun areItemsTheSame(
                oldItem: SessionListItem,
                newItem: SessionListItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: SessionListItem,
                newItem: SessionListItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is SessionListItem.Header -> TYPE_HEADER
        is SessionListItem.Content -> TYPE_CONTENT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(
                ItemSectionHeaderBinding.inflate(inflater, parent, false)
            )

            TYPE_CONTENT -> ContentViewHolder(
                ItemSessionBinding.inflate(inflater, parent, false)
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is SessionListItem.Header -> (holder as HeaderViewHolder).bind(item)
            is SessionListItem.Content -> (holder as ContentViewHolder).bind(item)
        }
    }

    /** ViewHolder: 세션 헤더 (날씨/해변 정보 등) */
    inner class HeaderViewHolder(private val binding: ItemSectionHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SessionListItem.Header) = with(binding) {
            beachName.text = item.beachName
            temperature.text = item.temperature
            wave.text = item.wave
            wind.text = item.wind
        }
    }

    /** ViewHolder: 세션 컨텐츠 (타이틀, 시간, 참가자 등) */
    inner class ContentViewHolder(private val binding: ItemSessionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SessionListItem.Content) = with(binding) {
            // 텍스트 세팅
            title.text = item.title
            sessionTime.text = item.sessionTime
            time.text = item.time
            numbers.text = item.participants
            grade.setGrade(item.grade)
        }
    }
}
