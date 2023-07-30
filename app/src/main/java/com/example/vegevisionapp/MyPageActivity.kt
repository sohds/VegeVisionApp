package com.example.vegevisionapp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class MyPageActivity : BaseActivity() {

    private lateinit var emailshow: TextView
    private lateinit var logoutBtn: Button
    private lateinit var deleteBtn: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_page)

        emailshow = findViewById(R.id.mypage_email)
        logoutBtn = findViewById(R.id.logoutBtn)
        deleteBtn = findViewById(R.id.button2)

        auth = FirebaseAuth.getInstance()
        emailshow.text = auth.currentUser?.email

        logoutBtn.setOnClickListener {
            //firebase auth에서 sign out 기능 호출
            auth.signOut()

            // 로그인 상태를 false로 변경
            AppPreferences.getInstance(this).isLoggedIn = false

            val intent = Intent(this@MyPageActivity, LoginActivity::class.java)
            startActivity(intent)
            showToast("로그아웃 했습니다.")
        }

        deleteBtn.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("회원 탈퇴 확인")
            alertDialogBuilder.setMessage("정말 회원 탈퇴하시겠습니까?")
            alertDialogBuilder.setPositiveButton("네") { dialogInterface: DialogInterface, _: Int ->
                // 회원 탈퇴 기능 호출
                val user = auth.currentUser
                user?.delete()
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val currentUser = auth.currentUser
                            if (currentUser == null) {
                                // 회원 탈퇴 성공적으로 완료됨
                                val intent = Intent(this@MyPageActivity, OnboardingActivity::class.java)
                                startActivity(intent)
                                showToast("탈퇴했어요. 우리 다음에 또 볼 수 있겠죠?")
                                finishAfterDelay(3500) // 3.5초 뒤에 앱 종료
                            } else {
                                // 로그인 상태라면 실패 메시지 띄우기
                                showToast("회원 탈퇴를 실패했습니다.")
                            }
                        } else {
                            // 회원 탈퇴 실패
                            showToast("회원 탈퇴를 실패했습니다.")
                        }
                    }
            }
            alertDialogBuilder.setNegativeButton("아니요") { dialogInterface: DialogInterface, _: Int ->
                // 사용자가 "아니요" 버튼을 누르면 아무 작업도 수행하지 않음
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun finishAfterDelay(delayMillis: Long) {
        Handler(Looper.getMainLooper()).postDelayed({ finish() }, delayMillis)

    }
}
