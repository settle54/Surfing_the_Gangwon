package com.capstone.surfingthegangwon.core.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isGone
import com.capstone.surfingthegangwon.core.ui.databinding.ViewTopAppBarBinding

class TopAppBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ViewTopAppBarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        // 기본 뒤로가기 클릭 시 동작 없음 → 필요 시 setOnBackClick으로 연결
    }

    fun setTitle(text: String) {
        binding.title.text = text
    }

    fun setOnBackClick(listener: () -> Unit) {
        binding.backArrow.setOnClickListener { listener() }
    }

    fun hideBackClick() {
        binding.backArrow.isGone = true
    }

    fun getBinding(): ViewTopAppBarBinding = binding // 필요 시 바인딩 직접 접근
}
