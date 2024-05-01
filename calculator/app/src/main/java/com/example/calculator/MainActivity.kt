package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    // Deklarasi variabel yang dibutuhkan
    private lateinit var resultTextView: TextView
    private var operand1: Double = 0.0
    private var operand2: Double = 0.0
    private var operation: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi TextView untuk menampilkan hasil
        resultTextView = findViewById(R.id.resultTextView)
    }

    // Method untuk menangani tombol angka
    fun onDigitClick(view: View) {
        val button = view as Button
        val digit = button.text.toString()
        resultTextView.append(digit)
    }

    // Method untuk menangani tombol operasi matematika
    fun onOperationClick(view: View) {
        val button = view as Button
        operation = button.text.toString()
        operand1 = resultTextView.text.toString().toDouble()
        resultTextView.text = ""
    }

    // Method untuk menangani tombol sama dengan
    fun onEqualClick(view: View) {
        operand2 = resultTextView.text.toString().toDouble()
        val result: Double = when (operation) {
            "+" -> operand1 + operand2
            "-" -> operand1 - operand2
            "*" -> operand1 * operand2
            "/" -> operand1 / operand2
            else -> 0.0
        }
        resultTextView.text = result.toString()
    }

    // Method untuk menangani tombol clear
    fun onClearClick(view: View) {
        operand1 = 0.0
        operand2 = 0.0
        operation = ""
        resultTextView.text = ""
    }
}
