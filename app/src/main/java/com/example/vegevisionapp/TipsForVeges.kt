package com.example.vegevisionapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TipsForVeges: AppCompatActivity() {


    lateinit var tips: TextView
    lateinit var myHelper: myDBHelper
    lateinit var sqlDB:SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.toolbar)

        tips = findViewById<TextView>(R.id.fruitTips)

    }


        inner class myDBHelper(context: Context):SQLiteOpenHelper(context,"groupDB",null,1){
            override fun onCreate(db: SQLiteDatabase?) {
                db!!.execSQL("CREATE TABLE groupTBL(fruit CHAR(8) PRIMARY KEY, tips String);")
            }

            override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
             db!!.execSQL("DROP TABLE IF EXISTS groupTBL")
            }
        }


}