package com.example.vegevisionapp

import android.content.Intent
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

open class BaseActivity : AppCompatActivity() {
    // protected로 선언
    protected lateinit var toolbar: Toolbar
    protected val btnMyPage by lazy {
        findViewById<ImageButton>(R.id.mypage_image)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_toolbar)

        // 커스텀 툴바 설정
        toolbar = findViewById(R.id.custom_toolbar)
        setSupportActionBar(toolbar)

        // ActionBar의 앱 이름 숨기기
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
}