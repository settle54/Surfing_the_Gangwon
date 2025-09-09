package com.capstone.surfingthegangwon.presentation.sessionwriting

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.surfingthegangwon.presentation.sessionwriting.databinding.ItemRcvTvBinding

class BeachAdapter(
    private val items: List<String>,
    private val onSelected: ((String) -> Unit)? = null
) :
    RecyclerView.Adapter<BeachAdapter.ViewHolder>() {

    // 현재 선택된 항목 위치
    private var selectedPosition = 0

    companion object {
        private const val PAYLOAD_CHECKED = "payload_checked"
    }

    inner class ViewHolder(val binding: ItemRcvTvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val tv = binding.textView
            tv.text = items[position]
            tv.isSelected = (position == selectedPosition)
            tv.refreshDrawableState()
            setClick(tv)
        }


        /**
         * 선택 상태만 바인딩 (payload 갱신에서 호출)
         * - 텍스트 등은 건드리지 않고 selected 상태만 최소 변경
         */
        fun bindSelected(position: Int) {
            val tv = binding.textView
            val shouldSelect = (position == selectedPosition)

            // 불필요한 리프레시 방지: 상태가 바뀔 때만 적용
            if (tv.isSelected != shouldSelect) {
                tv.isSelected = shouldSelect
                tv.refreshDrawableState()
            }
        }

        /**
         * 클릭 리스너 설정
         * - 클릭 시 선택 포지션을 현재 아이템으로 이동
         * - 이전 선택 항목/현재 항목 2곳만 payload로 부분 갱신
         */
        private fun setClick(tv: TextView) {
            tv.setOnClickListener {
                val pos = adapterPosition
                if (pos == RecyclerView.NO_POSITION || pos == selectedPosition) return@setOnClickListener

                val old = selectedPosition
                selectedPosition = pos

                // 이전/새 항목만 payload로 갱신 (레아이아웃/텍스트 재바인딩 없이 선택 상태만 갱신)
                if (old != RecyclerView.NO_POSITION) notifyItemChanged(old, PAYLOAD_CHECKED)
                notifyItemChanged(pos, PAYLOAD_CHECKED)

                onSelected?.invoke(items[pos])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRcvTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    /**
     * 부분 바인딩: notifyItemChanged(..., payload)로 호출될 때 실행
     * - 우리가 지정한 PAYLOAD_CHECKED가 있으면 선택상태만 갱신
     * - 그 외의 경우는 상위 구현(super)로 위임하여 기본 동작 수행
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.contains(PAYLOAD_CHECKED)) {
            holder.bindSelected(position)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    /**
     * 외부에서 현재 선택 포지션을 얻고 싶을 때 사용
     */
    fun getSelectedPosition(): Int = selectedPosition

    /**
     * 외부에서 현재 선택 값(문자열)을 얻고 싶을 때 사용
     */
    fun getSelectedValue(): String? =
        selectedPosition.takeIf { it in items.indices }?.let { items[it] }

    /**
     * 외부에서 선택 포지션을 설정하고 싶을 때 사용
     *
     * @param newPos           새로 선택할 인덱스
     * @param triggerCallback  true면 onSelected 콜백을 즉시 실행
     */
    fun setSelectedPosition(newPos: Int, triggerCallback: Boolean = false) {
        // 인덱스 범위 밖이거나 기존 값과 동일하면 무시
        if (newPos !in items.indices || newPos == selectedPosition) return

        val old = selectedPosition
        selectedPosition = newPos

        // 이전/새 항목만 선택 상태 부분 갱신
        if (old != RecyclerView.NO_POSITION) notifyItemChanged(old, PAYLOAD_CHECKED)
        notifyItemChanged(newPos, PAYLOAD_CHECKED)

        if (triggerCallback) onSelected?.invoke(items[newPos])
    }
}