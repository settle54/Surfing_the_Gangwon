package com.capstone.surfingthegangwon.presentation.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.capstone.surfingthegangwon.core.ui.ColorGradient.Companion.setTextColorAsLinearGradient
import com.capstone.surfingthegangwon.presentation.login.databinding.ActivityLoginBinding
import com.capstone.surfingthegangwon.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.capstone.surfingthegangwon.core.resource.R as CoRes

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val baseUrl = BuildConfig.SERVICE_BASE_URL
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTvLogoAsGradient()
        setClickKakaoLoginBtn()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("OAUTH", "onNewIntent data=${intent?.data}")
//        loginViewModel.onDeepLink(intent.data)
    }

    private fun setClickKakaoLoginBtn() {
        binding.kakaoLoginBtn.setOnClickListener {
//            loginViewModel.onClickKakaoLogin()
            switchActivityToMain()
        }
    }

    /**
     * 메인 액티비티로 이동
     */
    private fun switchActivityToMain() {
        val intent = Intent(
            this,
            MainActivity::class.java
        )
        startActivity(intent)
    }

    private fun onCreateKakaoLogic() {
        // 기존 토큰 유효하면 바로 이동
        loginViewModel.initAutoNavigateIfValid()

        Log.d("OAUTH", "onCreate data=${intent?.data}")
        // 딥링크 수신
        loginViewModel.onDeepLink(intent?.data)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.events.collect { e ->
                    when (e) {
                        is LoginEvent.OpenKakao -> CustomTabsIntent.Builder()
                            .build().launchUrl(this@LoginActivity, Uri.parse(e.url))

                        is LoginEvent.NavigateMain -> {
                            switchActivityToMain()
                            finish()
                        }

                        is LoginEvent.ToastMsg -> Toast.makeText(
                            this@LoginActivity,
                            e.msg,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    /**
     * 로고 텍스트에 그라데이션 적용
     */
    private fun setTvLogoAsGradient() {
        val colors = arrayOf(
            ContextCompat.getColor(this, CoRes.color.logo_start_color),
            ContextCompat.getColor(this, CoRes.color.logo_end_color)
        )
        binding.titleSurfing.setTextColorAsLinearGradient(colors)
        binding.titleThe.setTextColorAsLinearGradient(colors)
        binding.titleGangwon.setTextColorAsLinearGradient(colors)
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}