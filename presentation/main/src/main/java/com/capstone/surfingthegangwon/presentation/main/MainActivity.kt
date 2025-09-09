package com.capstone.surfingthegangwon.presentation.main

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.core.view.updatePadding
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.capstone.surfingthegangwon.presentation.main.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.capstone.surfingthegangwon.core.navigation.R as navi

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private fun NavDestination.isIn(@IdRes graphId: Int): Boolean =
        generateSequence(this) { it.parent }.any { it.id == graphId }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()
    }

    /**
     * 네비게이션 초기화
     */
    private fun initNavigation() {
        val nav = binding.bottomNavigationView
        val navController =
            binding.fragmentContainer.getFragment<NavHostFragment>().navController
        binding.bottomNavigationView.setupWithNavController(navController)
        val navigationView = binding.cardView

        setUpNaviagtion(nav, navController)
        applySystemBarInsetsPadding(navigationView)
        hideBottomNavigation(navController)
    }

    /**
     * 바텀 네비게이션을 숨겨야 하는 목적지에 따라
     * 네비게이션 뷰의 가시성을 조절하는 함수.
     * hideNavDestinations가 null이거나 비어있으면 항상 보임.
     */
    private fun hideBottomNavigation(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val inWrite = destination.isIn(navi.id.together_nav_graph)
            val isTogether = destination.id == com.capstone.surfingthegangwon.core.navigation.R.id.together    // 작성 첫 화면만 예외로 보이기
            binding.bottomNavigationView.isGone = inWrite && !isTogether
        }
    }

    /**
     * 바텀 네비게이션 아이템 선택 시 네비게이션 그래프의 시작 목적지로 팝하고
     * 해당 메뉴 아이템 목적지로 이동시키는 함수
     */
    private fun setUpNaviagtion(
        nav: BottomNavigationView,
        navController: NavController
    ) {
        val navv = (supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment).navController
        binding.bottomNavigationView.setupWithNavController(navv)

    }

    /**
     * 지정된 뷰에 시스템 표시줄(상단 상태바, 하단 내비게이션바) 인셋을 기준으로
     * 하단 패딩을 추가하여 시스템 UI에 의해 가려지지 않도록 처리하는 함수
     */
    private fun applySystemBarInsetsPadding(uiView: View) {
        ViewCompat.setOnApplyWindowInsetsListener(uiView) { view, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(bottom = systemInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }

    }
}