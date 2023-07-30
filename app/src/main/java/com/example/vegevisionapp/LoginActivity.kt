package com.example.vegevisionapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var loginBtn: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        auth = FirebaseAuth.getInstance()

        emailEt = findViewById(R.id.editid)
        passwordEt = findViewById(R.id.passwordEditText)
        loginBtn = findViewById(R.id.loginBtn)

        // 비밀번호 입력 창에 TextWatcher를 등록
        passwordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 이전 텍스트 값
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 현재 텍스트 값
                val currentText = s.toString()

                // 입력된 비밀번호를 가려진 형태로 보여줌
                passwordEt.removeTextChangedListener(this)
                passwordEt.setText(currentText)
                passwordEt.setSelection(passwordEt.length())
                passwordEt.addTextChangedListener(this)
            }

            override fun afterTextChanged(s: Editable?) {
                // 입력이 완료된 후 작업
            }
        })

        loginBtn.setOnClickListener {
            val email = emailEt.text.toString()
            val password = passwordEt.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                // 이메일 또는 비밀번호가 비어있을 경우
                Toast.makeText(this, "이메일 또는 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { result ->
                        if (result.isSuccessful) {
                            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish() // 로그인 액티비티 종료
                        } else {
                            // 로그인 실패 시 오류 메시지 확인
                            val error = result.exception?.message
                            if (error == "The password is invalid or the user does not have a password.") {
                                // 비밀번호가 틀린 경우
                                Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
                            } else {
                                // 기타 로그인 실패 사유
                                Toast.makeText(this, "로그인 실패: $error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            }
        }
    }
}
