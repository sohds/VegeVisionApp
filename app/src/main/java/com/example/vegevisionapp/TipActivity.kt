package com.example.vegevisionapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class TipActivity : AppCompatActivity() {

    lateinit var AboutBtn: ImageButton
    lateinit var homeButton: ImageButton
    lateinit var Beansprouts: ImageButton
    lateinit var Carrot: ImageButton
    lateinit var Cabbage: ImageButton
    lateinit var Onion: ImageButton
    lateinit var Grape: ImageButton
    lateinit var SweetPotato: ImageButton
    lateinit var Watermelon: ImageButton
    lateinit var Peach: ImageButton





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tips_page)

        AboutBtn = findViewById(R.id.imageButton2)
        homeButton = findViewById(R.id.homebutton)

        Beansprouts = findViewById(R.id.beansprouts)
        Carrot = findViewById(R.id.carrot)

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


        Beansprouts.setOnClickListener {
            val intent1 =Intent(this,TipsForVeges::class.java)
            intent1.putExtra("buttonType", "beansprouts")
            intent1.putExtra("newText","콩나물은 머리와 줄기가 적당히 통통하고 노란색을 띠며, 검은 반점이 없는 것을 골라야 한다...")
        }
        Carrot.setOnClickListener {
            val intent2 =Intent(this,TipsForVeges::class.java)
            intent2.putExtra("buttonType", "carrot")
            intent2.putExtra("newText","콩나물은 머리와 줄기가 적당히 통통하고 노란색을 띠며, 검은 반점이 없는 것을 골라야 한다...")
        }



    }

}