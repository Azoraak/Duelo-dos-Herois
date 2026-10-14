package com.example.duelodosherois

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private val PLAYER_MAX_HP = 100
    private val ENEMY_MAX_HP = 100
    private val MAX_ENERGY = 100

    private var playerHP = PLAYER_MAX_HP
    private var enemyHP = ENEMY_MAX_HP
    private var energy = 0
    private var potions = 3
    private var turno = 1
    private var defendendo = false
    private var jogoAcabou = false

    private lateinit var pbPlayerHP: ProgressBar
    private lateinit var pbEnemyHP: ProgressBar
    private lateinit var pbEnergy: ProgressBar
    private lateinit var tvPlayerHP: TextView
    private lateinit var tvEnemyHP: TextView
    private lateinit var tvEnergy: TextView
    private lateinit var tvTurno: TextView
    private lateinit var tvLog: TextView
    private lateinit var btnAtacar: Button
    private lateinit var btnDefender: Button
    private lateinit var btnEspecial: Button
    private lateinit var btnCurar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        pbPlayerHP = findViewById(R.id.pbPlayerHP)
        pbEnemyHP = findViewById(R.id.pbEnemyHP)
        pbEnergy = findViewById(R.id.pbEnergy)
        tvPlayerHP = findViewById(R.id.tvPlayerHP)
        tvEnemyHP = findViewById(R.id.tvEnemyHP)
        tvEnergy = findViewById(R.id.tvEnergy)
        tvTurno = findViewById(R.id.tvTurno)
        tvLog = findViewById(R.id.tvLog)
        btnAtacar = findViewById(R.id.btnAtacar)
        btnDefender = findViewById(R.id.btnDefender)
        btnEspecial = findViewById(R.id.btnEspecial)
        btnCurar = findViewById(R.id.btnCurar)

        btnAtacar.setOnClickListener { acaoAtacar() }
        btnDefender.setOnClickListener { acaoDefender() }
        btnEspecial.setOnClickListener { acaoEspecial() }
        btnCurar.setOnClickListener { acaoCurar() }

        atualizarInterface()
    }

    private fun acaoAtacar() {
        if (jogoAcabou) return
        val dano = Random.nextInt(10, 21)
        enemyHP = maxOf(0, enemyHP - dano)
        energy = minOf(MAX_ENERGY, energy + 20)
        log("⚔️ Você atacou e causou $dano de dano!")

        if (enemyHP <= 0) { finalizar(true); return }
        turnoInimigo()
        if (!jogoAcabou) atualizarInterface()
    }

    private fun acaoDefender() {
        if (jogoAcabou) return
        defendendo = true
        energy = minOf(MAX_ENERGY, energy + 10)
        log("🛡️ Você se preparou para defender!")
        turnoInimigo()
        if (!jogoAcabou) atualizarInterface()
    }

    private fun acaoEspecial() {
        if (jogoAcabou) return
        if (energy < MAX_ENERGY) {
            log("❌ Energia insuficiente para o ataque especial!")
            atualizarInterface()
            return
        }
        val dano = Random.nextInt(35, 51)
        enemyHP = maxOf(0, enemyHP - dano)
        energy = 0
        log("💥 ATAQUE ESPECIAL! Dano: $dano")

        if (enemyHP <= 0) { finalizar(true); return }
        turnoInimigo()
        if (!jogoAcabou) atualizarInterface()
    }

    private fun acaoCurar() {
        if (jogoAcabou) return
        if (potions <= 0) {
            log("❌ Você não tem mais poções!")
            atualizarInterface()
            return
        }
        potions--
        val cura = 25
        playerHP = minOf(PLAYER_MAX_HP, playerHP + cura)
        log("🧪 Você se curou em $cura HP!")
        turnoInimigo()
        if (!jogoAcabou) atualizarInterface()
    }

    private fun turnoInimigo() {
        var dano = Random.nextInt(8, 16)
        if (defendendo) {
            dano /= 2
            log("🛡️ Defesa ativa! Dano reduzido para $dano")
            defendendo = false
        }
        playerHP = maxOf(0, playerHP - dano)
        log("👹 Inimigo atacou! Dano: $dano")
        turno++

        if (playerHP <= 0) finalizar(false)
    }

    private fun finalizar(vitoria: Boolean) {
        jogoAcabou = true
        val i = Intent(this, ResultActivity::class.java)
        i.putExtra("vitoria", vitoria)
        i.putExtra("turnos", turno)
        startActivity(i)
        finish()
    }

    private fun atualizarInterface() {
        pbPlayerHP.progress = playerHP
        pbEnemyHP.progress = enemyHP
        pbEnergy.progress = energy

        tvPlayerHP.text = "HP: $playerHP/$PLAYER_MAX_HP"
        tvEnemyHP.text = "HP: $enemyHP/$ENEMY_MAX_HP"
        tvEnergy.text = "Energia: $energy/$MAX_ENERGY"
        tvTurno.text = "Turno $turno"
        btnCurar.text = "🧪 Curar ($potions)"

        btnEspecial.isEnabled = energy >= MAX_ENERGY
        btnCurar.isEnabled = potions > 0
    }

    private fun log(msg: String) {
        tvLog.text = msg
    }
}