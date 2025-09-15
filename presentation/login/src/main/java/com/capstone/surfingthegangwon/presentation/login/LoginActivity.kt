package com.capstone.surfingthegangwon.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.capstone.surfingthegangwon.core.auth.TokenStore
import com.capstone.surfingthegangwon.core.ui.ColorGradient.Companion.setTextColorAsLinearGradient
import com.capstone.surfingthegangwon.presentation.login.databinding.ActivityLoginBinding
import com.capstone.surfingthegangwon.presentation.main.MainActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.capstone.surfingthegangwon.core.resource.R as CoRes

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var tokenStore: TokenStore

    private val loadingLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_CANCELED) {
                val msg = result.data?.getStringExtra(LoadingActivity.EXTRA_ERROR_MSG)
                    ?: "로그인에 실패했습니다. 다시 시도해 주세요."
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                // 여기서 그대로 로그인 화면에 머무름
            } else {
                // 성공인 경우 LoadingActivity가 Main으로 넘어가며 finishAffinity 처리
            }
        }

    private val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
            launchLoading(token)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!tokenStore.getAccessToken().isNullOrEmpty()) {
            switchActivityToMain()
        }

        setTvLogoAsGradient()
        setClickKakaoLoginBtn()
    }

    private fun setClickKakaoLoginBtn() {
        binding.kakaoLoginBtn.setOnClickListener {
            loginWithKakao()
        }
    }

    private fun loginWithKakao() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    launchLoading(token)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    private fun launchLoading(token: OAuthToken) {
        val intent = Intent(this, LoadingActivity::class.java).apply {
            putExtra(LoadingActivity.EXTRA_ACCESS, token.accessToken)
            putExtra(LoadingActivity.EXTRA_REFRESH, token.refreshToken)
        }
        loadingLauncher.launch(intent)
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