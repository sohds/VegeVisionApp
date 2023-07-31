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
                            // 로그인 성공
                            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish() // 로그인 액티비티 종료
                        } else {
                            // 로그인 실패 시 회원가입 진행
                            createUserAndLogin(email, password)
                        }
                    }
            }
        }
    }

    private fun createUserAndLogin(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    // 회원가입 및 로그인 성공
                    Toast.makeText(this, "회원가입 및 로그인 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // 로그인 액티비티 종료
                } else {
                    // 로그인 실패 시 회원가입 진행
                    if (result.exception?.message?.contains("Password should be at least 6 characters") == true) {
                        Toast.makeText(this, "비밀번호는 최소 6자리 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        // 회원가입 실패
                        Toast.makeText(this, "회원가입 실패: ${result.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}
