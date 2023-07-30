package com.example.vegevisionapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream

class MainActivity : BaseActivity() {

    lateinit var GalleryBtn: Button
    lateinit var CameraBtn: Button

    lateinit var AboutBtn: ImageButton
    lateinit var homeButton: ImageButton
    lateinit var TipBtn: ImageButton

    var bitmap: Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GalleryBtn = findViewById(R.id.GalleryBtn)
        CameraBtn = findViewById(R.id.CameraBtn)
        AboutBtn = findViewById(R.id.imageButton2)
        homeButton = findViewById(R.id.homebutton)
        TipBtn = findViewById(R.id.imageButton8)


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

        // homeButton 클릭 이벤트 처리 - MainActivity(현재 액티비티)로 이동
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

    private val REQUEST_CAMERA_PERMISSION = 1003
    private val REQUEST_IMAGE_GALLERY = 101
    private val REQUEST_IMAGE_CAPTURE = 102

    // 갤러리 앱에 권한 요청을 하지 않아도 되는 이유:
    // 갤러리 앱은 이미 시스템 레벨에서 이미지를 가져오기 위해 필요한 권한을 가지고 있을 수 있음
    // 따라서 우리 앱에서 갤러리 앱을 실행할 때, 갤러리 앱은 이미 권한이 허용되어 있으므로
    // 권한을 다시 요청하지 않고 이미지를 선택하는 작업이 가능


    // 반면 카메라 앱은 일반적으로 런타임에 권한을 요청해야 하기 때문에
    // 사용자에게 권한을 요청하는 코드를 구현해야 함!

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
                    // 카메라 권한이 거부된 경우
                    showCameraPermissionDeniedToast()
                }
            }
        }
    }

    private fun showCameraPermissionDeniedToast() {
        // 카메라 권한이 거부된 경우에 토스트 메시지를 보여줍니다.
        Toast.makeText(this, "카메라의 권한을 허용해야 사용 가능합니다.", Toast.LENGTH_SHORT).show()

        // 권한을 다시 요청합니다.
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_CAMERA_PERMISSION
        )
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