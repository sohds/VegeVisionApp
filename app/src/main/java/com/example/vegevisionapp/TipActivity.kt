package com.example.vegevisionapp

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.media.Image
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TipActivity : AppCompatActivity() {

    lateinit var AboutBtn: ImageButton
    lateinit var homeButton: ImageButton
    lateinit var TipBtn: ImageButton
    lateinit var Beansprouts:ImageButton
    lateinit var Carrot:ImageButton
    lateinit var Cabbage:ImageButton
    lateinit var Onion:ImageButton
    lateinit var Grape:ImageButton
    lateinit var SweetPotato:ImageButton
    lateinit var Watermelon:ImageButton
    lateinit var Peach:ImageButton
    lateinit var myHelper: TipsForVeges.myDBHelper
    lateinit var sqlDB: SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tips_page)

        AboutBtn = findViewById(R.id.imageButton2)
        homeButton = findViewById(R.id.homebutton)
        TipBtn = findViewById(R.id.imageButton8)

        Beansprouts=findViewById(R.id.beansprouts)
        Carrot=findViewById(R.id.carrot)
        Cabbage=findViewById(R.id.cabbage)
        Onion=findViewById(R.id.onion)
        Grape=findViewById(R.id.grape)
        SweetPotato=findViewById(R.id.sweetpotato)
        Watermelon=findViewById(R.id.watermelon)
        Peach=findViewById(R.id.peach)

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

        Beansprouts.setOnClickListener{
            val intent= Intent(this,TipsForVeges::class.java)
        }
        Carrot.setOnClickListener{
                val intent= Intent(this,TipsForVeges::class.java)
            }
        Cabbage.setOnClickListener{
            val intent= Intent(this,TipsForVeges::class.java)
        }
        Onion.setOnClickListener{
            val intent= Intent(this,TipsForVeges::class.java)
        }
        Grape.setOnClickListener{
            val intent= Intent(this,TipsForVeges::class.java)
        }
        SweetPotato.setOnClickListener{
            val intent= Intent(this,TipsForVeges::class.java)
        }
        Watermelon.setOnClickListener{
            val intent= Intent(this,TipsForVeges::class.java)
        }
        Peach.setOnClickListener{
            val intent= Intent(this,TipsForVeges::class.java)
        }
    }

    inner class myDBHelper(context: Context): SQLiteOpenHelper(context,"groupDB",null,1){
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("CREATE TABLE groupTBL(fruit CHAR(8) PRIMARY KEY, tips String);")
        }

        override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS groupTBL")
        }
}
