package com.example.vegevisionapp

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.BitmapFactory
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


        var intent = getIntent()
        val newText = intent.getStringExtra("newText")
        val buttonType = intent.getStringExtra("buttonType")
        val byteArray = intent.getByteArrayExtra("image")
        val imageBitmap = byteArray?.let { BitmapFactory.decodeByteArray(byteArray, 0, it.size) }



        if (buttonType == "beansprouts") {
            tips.text = newText.toString()
            imageOf.setImageBitmap(imageBitmap)

        }
        if (buttonType == "carrot") {
            tips.text = newText.toString()
            imageOf.setImageBitmap(imageBitmap)

        }

        if (buttonType == "cabbage") {
            tips.text = newText.toString()
            imageOf.setImageBitmap(imageBitmap)

        }

        if (buttonType == "onion") {
            tips.text = newText.toString()
            imageOf.setImageBitmap(imageBitmap)

        }

        if (buttonType == "grape") {
            tips.text = newText.toString()
            imageOf.setImageBitmap(imageBitmap)

        }
        if (buttonType == "sweetpotato") {
            tips.text = newText.toString()
            imageOf.setImageBitmap(imageBitmap)

        }

        if (buttonType == "watermelon") {
            tips.text = newText.toString()
            imageOf.setImageBitmap(imageBitmap)

        }

        if (buttonType == "peach") {
            tips.text = newText.toString()
            imageOf.setImageBitmap(imageBitmap)

        }
    }

}