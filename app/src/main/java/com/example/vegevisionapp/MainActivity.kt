package com.example.vegevisionapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}package com.example.vegevisionapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.vegevisionapp.R
import com.example.vegevisionapp.ml.LiteModelFinal
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class MainActivity : AppCompatActivity() {

    lateinit var selectBtn: Button
    lateinit var predBtn: Button
    lateinit var resView: TextView
    lateinit var imageView: ImageView

    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        selectBtn = findViewById(R.id.selectBtn)
        predBtn = findViewById(R.id.predictBtn)
        resView = findViewById(R.id.resView)
        imageView = findViewById(R.id.imageView)

        var labels = application.assets.open("label.txt").bufferedReader().readLines()

        // ImageProcessor with preprocessing steps
        var imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(150, 150, ResizeOp.ResizeMethod.BILINEAR))
            .add(NormalizeOp(0.0f, 1.0f)) // Rescale to [0.0, 1.0]
            .build()

        selectBtn.setOnClickListener {
            var intent: Intent = Intent()
            intent.setAction(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(intent, 100)
        }

        predBtn.setOnClickListener {
            if (bitmap != null) {
                // Preprocess the selected image
                var tensorImage = TensorImage(DataType.FLOAT32)
                tensorImage.load(bitmap)
                tensorImage = imageProcessor.process(tensorImage)

                val model = LiteModelFinal.newInstance(this)

                // Create inputs for reference
                val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 150, 150, 3), DataType.FLOAT32)
                inputFeature0.loadBuffer(tensorImage.buffer)

                // Run model inference and get result
                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

                var maxIdx = 0
                outputFeature0.forEachIndexed { index, fl ->
                    if(outputFeature0[maxIdx] < fl) {
                        maxIdx = index
                    }
                }

                resView.setText(labels[maxIdx])
                // Release model resources if no longer used.
                model.close()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            var uri = data?.data
            try {
                val inputStream = uri?.let { contentResolver.openInputStream(it) }
                bitmap = inputStream?.let { BitmapFactory.decodeStream(it) }
                if (bitmap == null) {
                    return
                }
                imageView.setImageBitmap(bitmap) // Show the selected image in ImageView
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}