package com.example.vegevisionapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class OnboardingActivity : AppCompatActivity() {

    lateinit var AppleBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_onboarding)

        AppleBtn = findViewById(R.id.imageView)

        // AboutBtn 클릭 이벤트 처리 - AboutActivity로 이동 (현재 액티비티)
        AppleBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}