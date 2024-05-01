package com.example.camera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import java.io.File
import java.text.DateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var btn: Button
    private lateinit var imgView: ImageView
    private var kodeKamera = 222
    private lateinit var namaFile: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.button)
        imgView = findViewById(R.id.imageView)

        btn.setOnClickListener {
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, kodeKamera) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                kodeKamera -> prosesKamera(data)
            }
        }
    }

    private fun prosesKamera(datanya: Intent?) {
        var bm = datanya?.extras?.get("data") as Bitmap?
        bm?.let {
            imgView.setImageBitmap(it)
            Toast.makeText(this, "Data Telah Terload ke ImageView", Toast.LENGTH_SHORT).show()
        }
    }
}