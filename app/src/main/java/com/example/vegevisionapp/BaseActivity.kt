package com.example.vegevisionapp

import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

open class BaseActivity : AppCompatActivity() {
    protected lateinit var toolbar: Toolbar

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
