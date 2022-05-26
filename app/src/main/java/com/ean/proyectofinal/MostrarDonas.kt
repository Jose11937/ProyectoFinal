package com.ean.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MostrarDonas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_donas)

        val userregresar = findViewById<ImageButton>(R.id.imageButton5)

        userregresar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val userb= findViewById<ImageButton>(R.id.imageButton6)

        userb.setOnClickListener {
            val intent = Intent(this, principal::class.java)
            startActivity(intent)
        }
    }
}