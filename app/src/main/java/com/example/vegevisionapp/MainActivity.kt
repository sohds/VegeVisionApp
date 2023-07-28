package com.example.vegevisionapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream

class MainActivity : BaseActivity() {

    lateinit var GalleryBtn: Button
    lateinit var CameraBtn: Button
    lateinit var imageView: ImageView

    lateinit var AboutBtn: ImageButton
    lateinit var homeButton: ImageButton
    lateinit var TipBtn: ImageButton

    var bitmap: Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        GalleryBtn = findViewById(R.id.GalleryBtn)
        CameraBtn = findViewById(R.id.CameraBtn)
        AboutBtn = findViewById(R.id.imageButton2)
        homeButton = findViewById(R.id.homebutton)
        TipBtn = findViewById(R.id.imageButton8)


        // GalleryBtn 클릭 이벤트 처리 - 갤러리 열기
        GalleryBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
        }

        // CameraBtn 클릭 이벤트 처리 - 카메라 열기
        CameraBtn.setOnClickListener {
            requestCameraPermission()
        }

        // AboutBtn 클릭 이벤트 처리 - AboutActivity로 이동
        AboutBtn.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        // homeButton 클릭 이벤트 처리 - MainActivity(현재 액티비티)로 이동 (또는 아무 동작 안 하게 설정)
        homeButton.setOnClickListener {
            // Do nothing or you can add custom behavior here
        }

        // TipBtn 클릭 이벤트 처리 - TipActivity로 이동
        TipBtn.setOnClickListener {
            val intent = Intent(this, TipActivity::class.java)
            startActivity(intent)
        }
    }

    private val REQUEST_GALLERY_PERMISSION = 1002
    private val REQUEST_CAMERA_PERMISSION = 1003
    private val REQUEST_IMAGE_GALLERY = 101
    private val REQUEST_IMAGE_CAPTURE = 102

    private fun requestCameraPermission() {
        // 카메라 권한 요청
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION
            )
        } else {
            openCamera()
        }
    }

    private fun openCamera() {
        // 카메라 열기
        val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CAMERA_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    // 권한을 거부한 경우 처리
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_GALLERY -> {
                    if (data != null && data.data != null) {
                        val imageUri: Uri = data.data!!
                        try {
                            val inputStream = contentResolver.openInputStream(imageUri)
                            bitmap = BitmapFactory.decodeStream(inputStream)
                            if (bitmap == null) {
                                return
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    bitmap = imageBitmap
                }
            }

            // 결과 페이지로 이미지 데이터 넘기기
            val resultIntent = Intent(this, ResultActivity::class.java)
            resultIntent.putExtra("imageData", bitmap?.let { bitmap ->
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                stream.toByteArray()
            })
            startActivity(resultIntent)
        }
    }
}
