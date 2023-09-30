package com.samdoreee.fieldgeolog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.samdoreee.fieldgeolog.SignUpActivity
import com.samdoreee.fieldgeolog.R
import com.samdoreee.fieldgeolog.databinding.ActivityLoginBinding
import android.util.Log
import android.view.View
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    private val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(Constants.TAG, "로그인 실패 $error")
        } else if (token != null) {
            Log.d(Constants.TAG, "로그인 성공 ${token.accessToken}")
            nextMainActivity()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnKakaoLogin.id -> {
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                    UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                        if (error != null) {
                            Log.e(Constants.TAG, "로그인 실패 $error")
                            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                                return@loginWithKakaoTalk
                            } else {
                                UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback)
                            }
                        } else if (token != null) {
                            Log.d(Constants.TAG, "로그인 성공 ${token.accessToken}")
                            Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                            nextMainActivity()
                        }
                    }
                } else {
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(Constants.TAG, "keyhash : ${Utility.getKeyHash(this)}")

        KakaoSdk.init(this, Constants.APP_KEY)
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error == null) {
                    nextMainActivity()
                }
            }
        }

        setContentView(binding.root)

        binding.btnKakaoLogin.setOnClickListener(this)

        binding.btnLogin.setOnClickListener {
            Toast.makeText(this, "환영합니다", Toast.LENGTH_SHORT).show()
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignup.setOnClickListener {
            var intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun nextMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}