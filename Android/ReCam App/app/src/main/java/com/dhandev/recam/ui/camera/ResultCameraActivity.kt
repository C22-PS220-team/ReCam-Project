package com.dhandev.recam.ui.camera

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dhandev.recam.R
import com.dhandev.recam.databinding.ActivityResultCameraBinding
import com.dhandev.recam.ml.Model3
import com.dhandev.recam.rotateBitmap
import com.dhandev.recam.ui.result.ResultActivity
import com.dhandev.recam.uriToFile
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ResultCameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultCameraBinding
    private var getFile: File? = null
    private lateinit var bitmap: Bitmap
    val imageSize = 256
    private lateinit var sharepref : SharedPreferences

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        if (binding.previewImage.drawable == null){
            val intent = Intent(this@ResultCameraActivity, CameraActivity::class.java)
            launcherIntentCameraX.launch(intent)
        }
        //set default Bitmap values
        val defUri = Uri.parse("android.resource://"+ this@ResultCameraActivity.packageName +"/drawable/plastic")
        val bitmapDef : Bitmap = MediaStore.Images.Media.getBitmap(this@ResultCameraActivity.contentResolver, defUri)
        bitmap = Bitmap.createBitmap(bitmapDef)

        binding.apply {
            btnKamera.setOnClickListener {
                val intent = Intent(this@ResultCameraActivity, CameraActivity::class.java)
                launcherIntentCameraX.launch(intent)
            }
            btnGaleri.setOnClickListener {
                val intent = Intent()
                intent.action = ACTION_GET_CONTENT
                intent.type = "image/*"
                val chooser = Intent.createChooser(intent, "Choose a Picture")
                launcherIntentGallery.launch(chooser)
            }
            arrowBack.setOnClickListener {
                onBackPressed()
            }

            btnLanjut.setOnClickListener(View.OnClickListener {
                if(bitmap == Bitmap.createBitmap(bitmapDef)){
                    Toast.makeText(this@ResultCameraActivity, getString(R.string.choose_image), Toast.LENGTH_SHORT).show()
                } else {
                    val resize = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, false)
                    classifyImage(resize)
                }
            })
        }
        sharepref = this.getSharedPreferences("KLASIFIKASI", MODE_PRIVATE)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == CAMERA_X_RESULT) {
            val myFile = result.data?.getSerializableExtra("picture") as File
            val isBackCamera = result.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = myFile
            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )
            binding.previewImage.setImageBitmap(result)
            bitmap = BitmapFactory.decodeFile(myFile.path)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)
            getFile = myFile
            binding.previewImage.setImageURI(selectedImg)
            bitmap = MediaStore.Images.Media.getBitmap(this@ResultCameraActivity.contentResolver, selectedImg)
        }
    }

    private fun classifyImage(image: Bitmap?) {
        val modelLabel = "label.txt"
        val inputString = application.assets.open(modelLabel).bufferedReader().use { it.readText() }
        //use it when there is lot of bahan
//        var listBahan = inputString.split("\n")
        var listBahan = listOf("kaca", "cardboard", "kertas", "besi", "plastik", "trash")

        val model = Model3.newInstance(this)

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 256, 256, 3), DataType.FLOAT32)
        var byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(imageSize * imageSize)
        image!!.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
        var pixel = 0
        for (i in 0 until imageSize) {
            for (j in 0 until imageSize) {
                val `val` = intValues[pixel++] // RGB
                byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
            }
        }

        inputFeature0.loadBuffer(byteBuffer)

        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val confidences = outputFeature0.floatArray
        var maxPos = 0
        var maxConfidence = 0f
        for (i in confidences.indices) {
            if (confidences[i] > maxConfidence) {
                maxConfidence = confidences[i]
                maxPos = i
            }
        }
//        Toast.makeText(this, listBahan[maxPos], Toast.LENGTH_SHORT).show()

        val editor : SharedPreferences.Editor = sharepref.edit()
        editor.putString("RESULT_DETECT", listBahan[maxPos])
        editor.apply()

        if(listBahan[maxPos] == listBahan[5]){
            Toast.makeText(this, getString(R.string.not_detected), Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this@ResultCameraActivity,ResultActivity::class.java)
            intent.putExtra("result", listBahan[maxPos])
            startActivity(intent)
        }
        model.close()
    }

    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}