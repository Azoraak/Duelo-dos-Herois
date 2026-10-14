package com.example.duelodosherois

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val vitoria = intent.getBooleanExtra("vitoria", false)
        val turnos = intent.getIntExtra("turnos", 0)

        val tvResultado = findViewById<TextView>(R.id.tvResultado)
        val tvDetalhes = findViewById<TextView>(R.id.tvDetalhes)
        val btnReiniciar = findViewById<Button>(R.id.btnReiniciar)
        val btnMenu = findViewById<Button>(R.id.btnMenu)

        if (vitoria) {
            tvResultado.text = "🏆 VITÓRIA!"
            tvResultado.setTextColor(0xFF2ECC71.toInt())
        } else {
            tvResultado.text = "💀 DERROTA"
            tvResultado.setTextColor(0xFFE74C3C.toInt())
        }
        tvDetalhes.text = "Você durou $turnos turno(s)"

        btnReiniciar.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
            finish()
        }

        btnMenu.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(i)
            finish()
        }
    }
}