package com.capstone.surfingthegangwon.presentation.sessionstatus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.surfingthegangwon.core.model.SessionItem
import com.capstone.surfingthegangwon.core.model.SessionState
import com.capstone.surfingthegangwon.core.ui.databinding.ItemSessionBinding

class SessionAdapter(
    private val onClick: (SessionItem) -> Unit
) : ListAdapter<SessionItem, SessionAdapter.ContentViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SessionItem>() {
            override fun areItemsTheSame(
                oldItem: SessionItem,
                newItem: SessionItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: SessionItem,
                newItem: SessionItem
            ): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val binding = ItemSessionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /** ViewHolder: 세션 컨텐츠 (타이틀, 시간, 참가자 등) */
    inner class ContentViewHolder(
        private val binding: ItemSessionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SessionItem) = with(binding) {
            // 텍스트 세팅
            title.text = item.title
            time.text = item.sessionTime
            numbers.text = item.participants
            grade.setGrade(item.grade)
            overlay.isVisible = when (item.state) {
                SessionState.OPEN -> false
                SessionState.CLOSE -> true
            }

            root.setOnClickListener {
                onClick(item)
            }
        }
    }
}
