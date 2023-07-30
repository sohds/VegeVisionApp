package com.example.vegevisionapp

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class TipsForVeges: BaseActivity() {

    lateinit var tips: TextView
    lateinit var imageOf:ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tips_result)

        tips = findViewById(R.id.fruitTips)
        imageOf = findViewById(R.id.imageViewOf)

        // BaseActivity에서 protected로 구현된 툴바 변수 btnMyPage
        btnMyPage.setOnClickListener {
            if (AppPreferences.getInstance(this).isLoggedIn) {
                // 로그인된 상태에서는 마이페이지로 이동
                val intent = Intent(this, MyPageActivity::class.java)
                startActivity(intent)
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }

        // 인텐트를 받아오는 작업
        val intent = getIntent()
        val newText = intent.getStringExtra("newText")
        val buttonType = intent.getStringExtra("buttonType")
        val byteArray = intent.getByteArrayExtra("image")
        val imageBitmap = byteArray?.let { BitmapFactory.decodeByteArray(byteArray, 0, it.size) }


        // 인텐트에서 받아온 버튼 타입에 따른 동작-텍스트 설정과 이미지 설정
        // 콩나물
        if (buttonType == "beansprouts") {
            tips.text = newText.toString()
            imageOf.setImageBitmap(imageBitmap)

        }

        // 당근
        if (buttonType == "carrot") {
            tips.text = newText.toString()
            imageOf.setImageBitmap(imageBitmap)

        }

        // 양배추
        if (buttonType == "cabbage") {
            tips.text = newText.toString()
            imageOf.setImageBitmap(imageBitmap)

        }

        // 양파
        if (buttonType == "onion") {
            tips.text = newText.toString()
            imageOf.setImageBitmap(imageBitmap)

        }

        // 포도
        if (buttonType == "grape") {
            tips.text = newText.toString()
            imageOf.setImageBitmap(imageBitmap)

        }

        // 고구마
        if (buttonType == "sweetpotato") {
            tips.text = newText.toString()
            imageOf.setImageBitmap(imageBitmap)

        }

        // 수박
        if (buttonType == "watermelon") {
            tips.text = newText.toString()
            imageOf.setImageBitmap(imageBitmap)

        }

        // 복숭아
        if (buttonType == "peach") {
            tips.text = newText.toString()
            imageOf.setImageBitmap(imageBitmap)

        }

    }

}