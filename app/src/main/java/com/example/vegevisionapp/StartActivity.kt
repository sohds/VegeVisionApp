package com.example.vegevisionapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {

    lateinit var appleBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_page)

        appleBtn = findViewById(R.id.imageView)

        // AboutBtn 클릭 이벤트 처리 - AboutActivity로 이동 (현재 액티비티)
        appleBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
