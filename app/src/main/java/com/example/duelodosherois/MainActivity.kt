package com.example.duelodosherois

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnIniciar = findViewById<Button>(R.id.btnIniciar)
        val btnSair = findViewById<Button>(R.id.btnSair)

        btnIniciar.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }

        btnSair.setOnClickListener {
            finishAffinity()
        }
    }
}