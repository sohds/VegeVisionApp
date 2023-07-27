package com.example.vegevisionapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TipActivity : AppCompatActivity() {

    lateinit var AboutBtn: ImageButton
    lateinit var homeButton: ImageButton
    lateinit var TipBtn: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tips_page)

        AboutBtn = findViewById(R.id.imageButton2)
        homeButton = findViewById(R.id.homebutton)
        TipBtn = findViewById(R.id.imageButton8)

        // AboutBtn 클릭 이벤트 처리 - AboutActivity로 이동
        AboutBtn.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        // homeButton 클릭 이벤트 처리 - MainActivity로 이동
        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // TipBtn 클릭 이벤트 처리 - TipActivity로 이동 (현재 액티비티)
        TipBtn.setOnClickListener {
            val intent = Intent(this, TipActivity::class.java)
            startActivity(intent)
        }
    }

}
