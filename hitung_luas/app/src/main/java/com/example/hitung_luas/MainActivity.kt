package com.example.hitung_luas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private lateinit var alasEditText: EditText
    private lateinit var tinggiEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.resultTextView)
        alasEditText = findViewById(R.id.alasEditText)
        tinggiEditText = findViewById(R.id.tinggiEditText)
    }

    fun hitungLuas(view: View) {
        val alas = alasEditText.text.toString().toIntOrNull() ?: return
        val tinggi = tinggiEditText.text.toString().toIntOrNull() ?: return

        val luas = 0.5 * alas * tinggi
        resultTextView.text = "Luas adalah $luas"
    }
}