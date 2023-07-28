package com.example.vegevisionapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : BaseActivity() {

    lateinit var AboutBtn: ImageButton
    lateinit var homeButton: ImageButton
    lateinit var TipBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.introducing_page)

        // 마이페이지로 이동하는 이미지 버튼 클릭 리스너 설정
        val btnMyPage = findViewById<ImageButton>(R.id.mypage_image)
        btnMyPage.setOnClickListener {
            if (AppPreferences.getInstance(this).isLoggedIn) {
                // 로그인된 상태에서는 마이페이지로 이동
                val intent = Intent(this, MyPageActivity::class.java)
                startActivity(intent)
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }


        AboutBtn = findViewById(R.id.imageButton2)
        homeButton = findViewById(R.id.homebutton)
        TipBtn = findViewById(R.id.imageButton8)

        // AboutBtn 클릭 이벤트 처리 - AboutActivity로 이동 (현재 액티비티)
        AboutBtn.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        // homeButton 클릭 이벤트 처리 - MainActivity로 이동
        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // TipBtn 클릭 이벤트 처리 - TipActivity로 이동
        TipBtn.setOnClickListener {
            val intent = Intent(this, TipActivity::class.java)
            startActivity(intent)
        }
    }

}
