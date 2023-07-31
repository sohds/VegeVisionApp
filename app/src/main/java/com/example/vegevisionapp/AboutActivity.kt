package com.example.vegevisionapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {

    lateinit var aboutBtn: ImageButton
    lateinit var homeButton: ImageButton
    lateinit var tipBtn: ImageButton
    lateinit var mypageBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.introducing_page)

        aboutBtn = findViewById(R.id.imageButton2)
        homeButton = findViewById(R.id.homebutton)
        tipBtn = findViewById(R.id.imageButton8)
        mypageBtn = findViewById(R.id.)


        // AboutBtn 클릭 이벤트 처리 - AboutActivity로 이동 (현재 액티비티)
        aboutBtn.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        // homeButton 클릭 이벤트 처리 - MainActivity로 이동
        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // TipBtn 클릭 이벤트 처리 - TipActivity로 이동
        tipBtn.setOnClickListener {
            val intent = Intent(this, TipActivity::class.java)
            startActivity(intent)
        }

        // myPageBtn 클릭 이벤트 처리 - MyPageActivity로 이동
        myPageBtn.setOnClickListener {
            if(AppPreferences.getInstance(this).isLoggedIn) {
                // 로그인된 상태에서는 마이페이지로 이동
                val intent = Intent(this, MyPageActivity::class.java)
                startActivity(intent)
            } else {
            startActivity(Intent(this, LoginActivity::class.java))
        } }
    }
}