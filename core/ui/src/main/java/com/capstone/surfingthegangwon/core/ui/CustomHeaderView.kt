package com.capstone.surfingthegangwon.core.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.capstone.surfingthegangwon.core.ui.ColorGradient.Companion.setTextColorAsLinearGradient
import com.capstone.surfingthegangwon.core.ui.databinding.HeaderBinding
import com.google.android.material.tabs.TabLayout

class CustomHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = HeaderBinding.inflate(LayoutInflater.from(context), this, true)
    private var tabSelectedListener: OnTabSelectedListener? = null

    interface OnTabSelectedListener {
        fun onTabSelected(position: Int)
    }

    init {
        setTvLogoAsGradient()
    }

    fun setScreenTitle(title: String) {
        binding.tvScreenTitle.text = title
    }

    fun setBeachTabItem(tabItems: List<String> = listOf("강릉", "양양", "속초", "고성")) {
        val tabLayout = binding.beachTabLayout

        tabItems.forEach { beachName ->
            tabLayout.addTab(tabLayout.newTab().setText(beachName))
        }
    }

    fun setOnTabSelectedListener(listener: OnTabSelectedListener) {
        tabSelectedListener = listener

        binding.beachTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.let {
                    tabSelectedListener?.onTabSelected(it.position)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    fun selectTab(index: Int) {
        val tabLayout = binding.beachTabLayout
        tabLayout.getTabAt(index)?.select()
    }

    /**
     * 인디케이터의 넓이를 조정함
     */
    fun adjustIndicatorWidth(ratio: Float = 0.7f) {
        val tabLayout = binding.beachTabLayout
        val tabStrip = tabLayout.getChildAt(0) as? ViewGroup ?: return

        tabLayout.post {
            for (i in 0 until tabStrip.childCount) {
                val tabView = tabStrip.getChildAt(i)

                // 원래 너비 가져오기
                val originalWidth = tabView.width
                val newWidth = (originalWidth * ratio).toInt()

                // 가운데 정렬하려면 margin 조절
                val margin = (originalWidth - newWidth) / 2

                val params = tabView.layoutParams as MarginLayoutParams
                params.width = newWidth
                params.marginStart = margin
                params.marginEnd = margin
                tabView.layoutParams = params

                tabView.invalidate()
            }
        }
    }

    /**
     * 로고 텍스트에 그라데이션 적용
     */
    private fun setTvLogoAsGradient() {
        val colors = arrayOf(
            ContextCompat.getColor(context, R.color.logo_start_color),
            ContextCompat.getColor(context, R.color.logo_end_color)
        )
        binding.appTitle.setTextColorAsLinearGradient(colors)
    }
}
