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
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class MainActivity : AppCompatActivity() {

    // 한 창에서 전부 해결했을 때의 코드 상태
    // ResultActivity.kt로 따로 결과 창에 구현해야하는 코틀린 코드 필요

    lateinit var GalleryBtn: Button
    lateinit var CameraBtn: Button
    lateinit var predBtn: Button
    lateinit var resView: TextView
    lateinit var imageView: ImageView

    var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GalleryBtn = findViewById(R.id.GalleryBtn)
        CameraBtn = findViewById(R.id.CameraBtn)
        predBtn = findViewById(R.id.predictBtn)
        resView = findViewById(R.id.resView)
        imageView = findViewById(R.id.imageView)

        var labels = application.assets.open("label.txt").bufferedReader().readLines()

        // 이미지 크기 조정과 데이터 타입 변환을 수행하는 ImageProcessor
        var imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
            .add(NormalizeOp(0.0f, 1.0f)) // Rescale to [0.0, 1.0]
            .build()

        GalleryBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
        }


        CameraBtn.setOnClickListener {
            // 카메라 권한 요청
            requestCameraPermission()
        }

        predBtn.setOnClickListener {
            if (bitmap != null) {
                // Preprocess the selected image
                var tensorImage = TensorImage(DataType.FLOAT32)
                tensorImage.load(bitmap)

                tensorImage = imageProcessor.process(tensorImage)

                // Load the TensorFlow Lite model
                val model = Interpreter(FileUtil.loadMappedFile(this, "lite_model_final.tflite"))

                // Create inputs and outputs tensors
                val inputShape = model.getInputTensor(0).shape()
                val inputFeature0 = TensorBuffer.createFixedSize(inputShape, DataType.FLOAT32)
                inputFeature0.loadBuffer(tensorImage.buffer)

                val outputShape = model.getOutputTensor(0).shape()
                val outputFeature0 = TensorBuffer.createFixedSize(outputShape, DataType.FLOAT32)

                // Run model inference and get result
                model.run(inputFeature0.buffer, outputFeature0.buffer)

                // Process the output tensor to get the result
                val outputFeature0FloatArray = outputFeature0.floatArray

                var maxIdx = 0
                outputFeature0FloatArray.forEachIndexed { index, fl ->
                    if (outputFeature0FloatArray[maxIdx] < fl) {
                        maxIdx = index
                    }
                }

                resView.text = labels[maxIdx]

                // Release model resources
                model.close()
            }
        }
    }

    // 갤러리 권한 요청에 대한 결과 처리
    private val REQUEST_GALLERY_PERMISSION = 1002

    private fun requestGalleryPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 권한이 없는 경우 권한을 요청합니다.
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_GALLERY_PERMISSION
            )
        } else {
            // 이미 권한이 있는 경우 갤러리를 열 수 있는 로직을 수행합니다.
            openGallery()
        }
    }

    // 갤러리 열기
    private val REQUEST_IMAGE_GALLERY = 101

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
    }

    // 카메라 권한 요청에 대한 결과 처리
    private val REQUEST_CAMERA_PERMISSION = 1003

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 권한이 없는 경우 권한을 요청합니다.
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION
            )
        } else {
            // 이미 권한이 있는 경우 카메라를 열 수 있는 로직을 수행합니다.
            openCamera()
        }
    }

    // 카메라 열기
    private val REQUEST_IMAGE_CAPTURE = 102

    private fun openCamera() {
        val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    // 권한 요청 결과 처리
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_GALLERY_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한을 얻었을 때 갤러리를 열 수 있는 로직을 수행
                    openGallery()
                } else {
                    // 권한을 거부한 경우에 대한 처리를 추가할 수 있음
                    // 예를 들어 사용자에게 앱의 기능을 사용하려면 권한을 필요로 한다는 안내를 표시하거나
                    // 다른 대안적인 기능을 제공하는 등의 방법으로 처리 가능
                    // 전자앨범 때 코드 비슷하게 가도 괜찮을듯
                }
            }

            REQUEST_CAMERA_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한을 얻었을 때 카메라를 열 수 있는 로직을 수행합니다.
                    openCamera()
                } else {
                    // 권한을 거부한 경우에 대한 처리를 추가할 수 있습니다.
                    // 예를 들어 사용자에게 앱의 기능을 사용하려면 권한을 필요로 한다는 안내를 표시하거나
                    // 다른 대안적인 기능을 제공하는 등의 방법으로 처리할 수 있습니다.
                }
            }
        }
    }

    // 이미지 선택 및 카메라로 찍은 사진 결과 처리
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_GALLERY -> {
                    if (data != null && data.data != null) {
                        val imageUri: Uri = data.data!! // Nullable 타입을 Non-Nullable 타입으로 변환
                        try {
                            val inputStream = contentResolver.openInputStream(imageUri)
                            bitmap = BitmapFactory.decodeStream(inputStream)
                            if (bitmap == null) {
                                return
                            }
                            imageView.setImageBitmap(bitmap) // 선택한 이미지를 ImageView에 표시
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    bitmap = imageBitmap
                    imageView.setImageBitmap(bitmap) // 찍은 사진을 ImageView에 표시
                }
            }
        }
    }
}