package com.capstone.surfingthegangwon.presentation.together

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.capstone.surfingthegangwon.presentation.together.databinding.ItemAreaBinding
import com.capstone.surfingthegangwon.core.resource.R as CoreRes

class AreaAdapter(private val items: List<String>) :
    RecyclerView.Adapter<AreaAdapter.ViewHolder>() {

    // 현재 선택된 항목 위치
    private var selectedPosition = 0

    inner class ViewHolder(val binding: ItemAreaBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // 클릭 시 선택 상태 갱신
            binding.area.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val previousPosition = selectedPosition
                    selectedPosition = position

                    // 이전 항목과 새 항목만 갱신
                    notifyItemChanged(previousPosition)
                    notifyItemChanged(selectedPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAreaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val isSelected = position == selectedPosition

        // 텍스트 설정
        holder.binding.area.text = items[position]

        // 폰트 설정
        val typeface = if (isSelected) {
            ResourcesCompat.getFont(context, CoreRes.font.roboto_bold)
        } else {
            ResourcesCompat.getFont(context, CoreRes.font.roboto_regular)
        }

        // 색상 설정
        val textColor = if (isSelected) {
            ContextCompat.getColor(context, CoreRes.color.blue_700)
        } else {
            Color.BLACK
        }

        // 스타일 적용
        holder.binding.area.apply {
            setTextColor(textColor)
            this.typeface = typeface
        }
    }

    override fun getItemCount() = items.size
}


