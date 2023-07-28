package com.example.vegevisionapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream

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
        Cabbage = findViewById(R.id.cabbage)
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


        Beansprouts.setOnClickListener {
            val beansproutimage = BitmapFactory.decodeResource(resources, R.drawable.beansprouts)
            val intent1 =Intent(this,TipsForVeges::class.java)
            intent1.putExtra("buttonType", "beansprouts")
            intent1.putExtra("newText","콩나물은 머리와 줄기가 적당히 통통하고 노란색을 띠며, 검은 반점이 없는 것을 골라야 한다. 줄기 부분이 너무 통통하거나 잔뿌리가 전혀 없이 깨끗한 콩나물은 성장촉진제를 많이 사용했을 가능성이 있으므로 주의한다. 또한 줄기가 무르거나 썩은 냄새, 약품 냄새가 나는 콩나물도 구입하지 않는 것이 좋다. 콩 머리가 파란 것은 햇빛에 많이 노출된 콩나물로 독성이 있을 수 있으며, 검은 반점이 있는 콩나물은 상했을 가능성이 높으므로 피해야 한다.")
            intent1.putExtra("image", bitmapToByteArray(beansproutimage))
            startActivity(intent1)
        }
        Carrot.setOnClickListener {
            val carrotimage = BitmapFactory.decodeResource(resources, R.drawable.carrot)
            val intent2 =Intent(this,TipsForVeges::class.java)
            intent2.putExtra("buttonType", "carrot")
            intent2.putExtra("newText","꼭지가 작고 선명한 주황빛이 도는 당근,표면이 단단한 당근")
            intent2.putExtra("image", bitmapToByteArray(carrotimage))
            startActivity(intent2)
        }

        Cabbage.setOnClickListener {
            val cabbageimage = BitmapFactory.decodeResource(resources, R.drawable.cabbage)
            val intent3 =Intent(this,TipsForVeges::class.java)
            intent3.putExtra("buttonType", "cabbage")
            intent3.putExtra("newText","모양이 동그랗고 이쁠수록, 겉잎이 진한 녹색일수록, 손가락으로 눌러봤을 때 단단함이 느껴질수록 무게가 무거울수록")
            intent3.putExtra("image", bitmapToByteArray(cabbageimage))
            startActivity(intent3)
        }

        Onion.setOnClickListener {
            val onionimage = BitmapFactory.decodeResource(resources, R.drawable.onion)
            val intent4 =Intent(this,TipsForVeges::class.java)
            intent4.putExtra("buttonType", "onion")
            intent4.putExtra("newText","검은 반점이 생기기 시작하면서 곰팡이가 피기 시작합니다. 그리고 양파를 눌렀을 때 단단하지 않고 부드럽다면 상하기 시작하는 신호입니다. 양파에 싹이 난 경우, 독성이 있으므로 먹지 말아야 합니다.")
            intent4.putExtra("image", bitmapToByteArray(onionimage))
            startActivity(intent4)
        }

        Grape.setOnClickListener {
            val grapeimage = BitmapFactory.decodeResource(resources, R.drawable.grape)
            val intent5 =Intent(this,TipsForVeges::class.java)
            intent5.putExtra("buttonType", "grape")
            intent5.putExtra("newText","포도를 들어 올리기 전부터 떨어져 있는 포도 알갱이들이 흐느적해졌거나 벗겨진 입 부분이 과육 이외의 색으로 변색되었으면 먹지 말아야 합니다. 또한 곰팡이가 피어있어도 먹지 말아야 하는데 다만,포도 껍질의 표면을 덮고 있는 흰 것은 곰팡이가 아니고 포도의 열매를 보호하는 물질이므로 먹어도 됩니다.")
            intent5.putExtra("image", bitmapToByteArray(grapeimage))
            startActivity(intent5)
        }

        SweetPotato.setOnClickListener {
            val sweetpotatoimage = BitmapFactory.decodeResource(resources, R.drawable.sweetpotato)
            val intent6 =Intent(this,TipsForVeges::class.java)
            intent6.putExtra("buttonType", "sweetpotato")
            intent6.putExtra("newText","흠집이 있을 경우 균이 침입하여 썩을 가능성이 높고, 잔털이 많을수록 육질에 섬유가 많아 맛이 없으니 잔털이 없을수록 좋으며, 고구마를 손끝으로 눌렀을 때 물렁물렁하거나 껍질에 검은 반점이 있는 경우 상한 것이니 주의해야하고 구매장소가 10도씨 이하라면 냉해를 입기가 쉬움.")
            intent6.putExtra("image", bitmapToByteArray(sweetpotatoimage))
            startActivity(intent6)
        }
        Watermelon.setOnClickListener {
            val watermelonimage = BitmapFactory.decodeResource(resources, R.drawable.watermelon)
            val intent7 =Intent(this,TipsForVeges::class.java)
            intent7.putExtra("buttonType", "watermelon")
            intent7.putExtra("newText","모양이 원형이거나 단타원형으로 예쁘고, 수박꼭지가 짧고 마르지 않았으며, 줄기의 반대편에 있는 배꼽의 크기가 작아야 함. 그리고, 두드렸을때 '통통' 과 같은 맑은 소리가 나야하며, 진동이 들고 있는 손에도 잘 느껴지면 됨.")
            intent7.putExtra("image", bitmapToByteArray(watermelonimage))
            startActivity(intent7)
        }
        Peach.setOnClickListener {
            val peachimage = BitmapFactory.decodeResource(resources, R.drawable.peach)
            val intent8 =Intent(this,TipsForVeges::class.java)
            intent8.putExtra("buttonType", "peach")
            intent8.putExtra("newText","껍질에 흠이 없고 털이 고르게 나 있으며, 꼭지에 틈이 없는 것. 상했을 때는 꼭지에 구멍이 뚫리고 주변에 초파리가 꼬임.")
            intent8.putExtra("image", bitmapToByteArray(peachimage))
            startActivity(intent8)
        }
    }
    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}