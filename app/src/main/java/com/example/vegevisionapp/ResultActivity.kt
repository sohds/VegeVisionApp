package com.example.vegevisionapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class ResultActivity : AppCompatActivity() {

    lateinit var imageView: ImageView
    lateinit var resView: TextView
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_result)

        imageView = findViewById(R.id.imageView2)
        resView = findViewById(R.id.textView2)
        button = findViewById(R.id.button)

        // Select 페이지에서 넘겨준 이미지 데이터를 받기
        val imageData: ByteArray? = intent.getByteArrayExtra("imageData")
        if (imageData != null) {
            val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
            imageView.setImageBitmap(bitmap)

            // 이미지 분류 모델을 사용하여 결과를 얻는 함수 호출 및 결과 표시
            val result = classifyImage(bitmap)
            resView.text = result
        }

        // 버튼 클릭 리스너 설정
        button.setOnClickListener {
            // 이전 액티비티로 돌아가기
            onBackPressed()
        }
    }

    // 이미지 분류 모델을 사용하여 결과를 얻는 함수
    private fun classifyImage(bitmap: Bitmap): String {
        // Load the TensorFlow Lite model
        val model = Interpreter(FileUtil.loadMappedFile(this, "lite_model_final.tflite"))

        // 이미지 크기 조정과 데이터 타입 변환을 수행하는 ImageProcessor
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
            .add(NormalizeOp(0.0f, 1.0f)) // Rescale to [0.0, 1.0]
            .build()

        // 선택한 이미지를 전처리
        var tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(bitmap)
        // 이미지 프로세싱
        tensorImage = imageProcessor.process(tensorImage)

        // 입출력 텐서 생성
        val inputShape = model.getInputTensor(0).shape()
        val inputFeature0 = TensorBuffer.createFixedSize(inputShape, DataType.FLOAT32)
        inputFeature0.loadBuffer(tensorImage.buffer)

        val outputShape = model.getOutputTensor(0).shape()
        val outputFeature0 = TensorBuffer.createFixedSize(outputShape, DataType.FLOAT32)

        // 모델 추론 실행 및 결과
        model.run(inputFeature0.buffer, outputFeature0.buffer)

        // 출력 텐서를 처리하여 결과를 가져오기
        val outputFeature0FloatArray = outputFeature0.floatArray

        var maxIdx = 0
        outputFeature0FloatArray.forEachIndexed { index, fl ->
            if (outputFeature0FloatArray[maxIdx] < fl) {
                maxIdx = index
            }
        }

        // 모델 리소스 해제 (반드시 시켜줘야 함!)
        model.close()

        // 결과를 문자열로 변환하여 반환
        val labels = application.assets.open("label.txt").bufferedReader().readLines()
        return labels[maxIdx]
    }
}