package com.capstone.surfingthegangwon.presentation.together

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.surfingthegangwon.domain.city.Seashores
import com.capstone.surfingthegangwon.presentation.together.databinding.ItemAreaBinding
import com.capstone.surfingthegangwon.core.resource.R as CoreRes

class AreaAdapter(
    private val onSelected: (Seashores) -> Unit = {}
) : ListAdapter<Seashores, AreaAdapter.ContentViewHolder>(DIFF_CALLBACK) {

    private var selectedPosition = 0

    // 외부에서 "id로 선택 복원"
    fun selectById(id: Int) {
        val idx = currentList.indexOfFirst { it.seashoreId == id }
        if (idx >= 0) selectPosition(idx)
    }

    // 외부에서 "index로 선택 복원"
    fun selectPosition(idx: Int) {
        val prev = selectedPosition
        selectedPosition = idx
        if (prev != RecyclerView.NO_POSITION) notifyItemChanged(prev)
        if (idx != RecyclerView.NO_POSITION) notifyItemChanged(idx)
        getItemOrNull(idx)?.let(onSelected)
    }

    private fun getItemOrNull(idx: Int) = currentList.getOrNull(idx)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val binding = ItemAreaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ContentViewHolder(
        private val binding: ItemAreaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            setOnClickListener()
        }

        fun bind(item: Seashores) = with(binding) {
            val context = itemView.context
            val isSelected = adapterPosition == selectedPosition

            area.text = item.name

            val typeface = if (isSelected) {
                ResourcesCompat.getFont(context, CoreRes.font.roboto_bold)
            } else {
                ResourcesCompat.getFont(context, CoreRes.font.roboto_regular)
            }

            val textColor = if (isSelected) {
                ContextCompat.getColor(context, CoreRes.color.blue_700)
            } else {
                Color.BLACK
            }

            area.apply {
                setTextColor(textColor)
                this.typeface = typeface
            }
        }

        private fun setOnClickListener() {
            binding.root.setOnClickListener {
                changeColor()
            }
        }

        private fun changeColor() {
            val pos = adapterPosition
            if (pos == RecyclerView.NO_POSITION) return

            val prev = selectedPosition
            selectedPosition = pos
            if (prev != RecyclerView.NO_POSITION) notifyItemChanged(prev)
            notifyItemChanged(selectedPosition)

            onSelected(getItem(pos))
        }

        private fun ss() {

        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Seashores>() {
            override fun areItemsTheSame(oldItem: Seashores, newItem: Seashores): Boolean =
                oldItem.beachCode == newItem.beachCode

            override fun areContentsTheSame(oldItem: Seashores, newItem: Seashores): Boolean =
                oldItem.beachCode == newItem.beachCode
        }
    }
}



