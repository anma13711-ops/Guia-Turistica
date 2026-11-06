package com.anmaoff.citytour

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    // Los tres estados posibles del audio
    private enum class Estado { DETENIDO, REPRODUCIENDO, PAUSADO }

    private var reproductor: MediaPlayer? = null
    private var estado = Estado.DETENIDO

    private lateinit var tvEstado: TextView
    private lateinit var btnPlay: Button
    private lateinit var btnPausa: Button
    private lateinit var btnStop: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Carrusel con las imágenes de res/drawable
        val imagenes = listOf(
            R.drawable.monumento1,
            R.drawable.monumento2,
            R.drawable.monumento3
        )
        findViewById<ViewPager2>(R.id.carrusel).adapter = ImagenAdapter(imagenes)

        tvEstado = findViewById(R.id.tvEstado)
        btnPlay = findViewById(R.id.btnPlay)
        btnPausa = findViewById(R.id.btnPausa)
        btnStop = findViewById(R.id.btnStop)

        btnPlay.setOnClickListener { reproducir() }
        btnPausa.setOnClickListener { pausar() }
        btnStop.setOnClickListener { detener() }

        actualizarBotones()
    }

    private fun reproducir() {
        when (estado) {
            Estado.DETENIDO -> {
                // El audio está en res/raw
                reproductor = MediaPlayer.create(this, R.raw.explicacion_historica)
                reproductor?.setOnCompletionListener { detener() }
                reproductor?.start()
                estado = Estado.REPRODUCIENDO
            }
            Estado.PAUSADO -> {
                reproductor?.start() // continúa donde se quedó
                estado = Estado.REPRODUCIENDO
            }
            Estado.REPRODUCIENDO -> { }
        }
        actualizarBotones()
    }

    private fun pausar() {
        if (estado == Estado.REPRODUCIENDO) {
            reproductor?.pause()
            estado = Estado.PAUSADO
        }
        actualizarBotones()
    }

    private fun detener() {
        reproductor?.release() // libera la memoria
        reproductor = null
        estado = Estado.DETENIDO
        actualizarBotones()
    }

    // Habilita o deshabilita los botones según el estado
    private fun actualizarBotones() {
        btnPlay.isEnabled = estado != Estado.REPRODUCIENDO
        btnPausa.isEnabled = estado == Estado.REPRODUCIENDO
        btnStop.isEnabled = estado != Estado.DETENIDO

        btnPlay.text = if (estado == Estado.PAUSADO) "Reanudar" else "Reproducir"
        tvEstado.text = when (estado) {
            Estado.DETENIDO -> "Estado: detenido"
            Estado.REPRODUCIENDO -> "Estado: reproduciendo"
            Estado.PAUSADO -> "Estado: en pausa"
        }
    }

    override fun onStop() {
        super.onStop()
        pausar()
    }

    override fun onDestroy() {
        super.onDestroy()
        detener()
    }
}