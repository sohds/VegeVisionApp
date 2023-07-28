package com.example.vegevisionapp

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

class TipsForVeges: AppCompatActivity() {


    public lateinit var tips: TextView
    var writingtext: String? = null
    lateinit var imageOf:ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.toolbar)

        tips = findViewById<TextView>(R.id.fruitTips)
        imageOf = findViewById<ImageView>(R.id.imageViewOf)

        val newText1 = intent.getStringExtra("newText")


        val buttonType = intent.getStringExtra("buttonType")

        if (buttonType == "beansprouts") {
            tips.text = newText1
        } else if (buttonType == "carrot") {
            tips.text = newText1

        }

    }

}