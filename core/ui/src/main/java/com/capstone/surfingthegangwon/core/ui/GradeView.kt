package com.capstone.surfingthegangwon.core.ui

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.capstone.surfingthegangwon.core.model.Grade
import com.capstone.surfingthegangwon.core.ui.databinding.ViewGradeBinding
import com.capstone.surfingthegangwon.core.resource.R as CoRes

class GradeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding = ViewGradeBinding.inflate(LayoutInflater.from(context), this, true)

    private lateinit var grade: Grade

    /**
     * 외부에서 문자열로 등급을 지정
     */
    fun setGrade(grade: Grade) {
        this.grade = grade
        applyGradeStyle()
    }

    /**
     * Grade에 맞는 스타일 적용
     */
    private fun applyGradeStyle() {
        val (bgResId, colorResId) = when (grade) {
            Grade.Beginner -> CoRes.drawable.grade_beginner_background to CoRes.color.grade_beginner
            Grade.Intermediate -> CoRes.drawable.grade_intermediate_background to CoRes.color.grade_intermediate
            Grade.Advanced -> CoRes.drawable.grade_advanced_background to CoRes.color.grade_advanced
        }

        setBackground(bgResId)
        setText(grade.label)
        setIcon(colorResId)
    }

    private fun setText(text: String) {
        binding.gradeText.text = text
    }

    private fun setBackground(@DrawableRes bgResId: Int) {
        binding.main.setBackgroundResource(bgResId)
    }

    private fun setIcon(@ColorRes tintColorResId: Int) {
        binding.gradeIcon.setColorFilter(
            ContextCompat.getColor(context, tintColorResId),
            PorterDuff.Mode.SRC_IN
        )
    }

    fun getBinding(): ViewGradeBinding = binding // 필요 시 바인딩 직접 접근
}