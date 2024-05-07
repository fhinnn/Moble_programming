package com.example.camera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var btn: Button
    private lateinit var imgView: ImageView
    private var kodeKamera = 222

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.button)
        imgView = findViewById(R.id.imageView)

        btn.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, kodeKamera)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                kodeKamera -> {
                    val bitmap = data?.extras?.get("data") as Bitmap?
                    bitmap?.let {
                        imgView.setImageBitmap(it)
                        sendImageToServer(it)
                        Toast.makeText(this, "Data Telah Terload ke ImageView", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun sendImageToServer(bitmap: Bitmap) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL("http://18.141.217.96:8080/upload")
                val connection = url.openConnection() as HttpURLConnection
                connection.doOutput = true
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=*****")

                val outputStream = connection.outputStream
                val writer = DataOutputStream(outputStream)

                // Add image data to the request
                val imageData = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageData)
                val imageDataBytes = imageData.toByteArray()

                writer.writeBytes("--*****\r\n")
                writer.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"image.jpg\"\r\n")
                writer.writeBytes("Content-Type: image/jpeg\r\n\r\n")
                writer.write(imageDataBytes)
                writer.writeBytes("\r\n")
                writer.writeBytes("--*****--\r\n")
                writer.flush()
                writer.close()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Berhasil mengirim gambar ke server
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Gambar berhasil diunggah ke server", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Gagal mengirim gambar ke server
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Gagal mengunggah gambar ke server", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Terjadi kesalahan: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
