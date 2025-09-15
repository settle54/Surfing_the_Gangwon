package com.capstone.surfingthegangwon.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.surfingthegangwon.domain.login.model.TokenPair
import com.capstone.surfingthegangwon.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadingActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val access = intent.getStringExtra(EXTRA_ACCESS)
        val refresh = intent.getStringExtra(EXTRA_REFRESH)
        if (access == null || refresh == null) {
            finishWithError("토큰이 유효하지 않습니다.")
            return
        }

        loginViewModel.exchangeCode(
            TokenPair(
                accessToken = access,
                refreshToken = refresh
            )
        )

        loginViewModel.loginState.observe(this) { success ->
            if (success) {
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
            } else {
                finishWithError("로그인에 실패했습니다. 다시 시도해 주세요.")
            }
        }
    }

    private fun finishWithError(message: String) {
        val data = Intent().putExtra(EXTRA_ERROR_MSG, message)
        setResult(RESULT_CANCELED, data)
        finish()
    }

    companion object {
        const val EXTRA_ACCESS = "extra_access"
        const val EXTRA_REFRESH = "extra_refresh"
        const val EXTRA_ERROR_MSG = "extra_error_msg"
    }
}
