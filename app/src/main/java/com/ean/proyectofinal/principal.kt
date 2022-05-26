package com.ean.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class principal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

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

        val userbueger = findViewById<ImageButton>(R.id.HamburgesaButton)

        userbueger.setOnClickListener {
            val intent = Intent(this, MostrarBurger::class.java)
            startActivity(intent)
        }
        val userbeer = findViewById<ImageButton>(R.id.BebidasButton)

        userbeer.setOnClickListener {
            val intent = Intent(this, MostrarBeer::class.java)
            startActivity(intent)
        }
        val userburr = findViewById<ImageButton>(R.id.BurritosButton)

        userburr.setOnClickListener {
            val intent = Intent(this, MostrarBurrrito::class.java)
            startActivity(intent)
        }
        val userdog = findViewById<ImageButton>(R.id.HotDogsButton)

        userdog.setOnClickListener {
            val intent = Intent(this, MostrarDog::class.java)
            startActivity(intent)
        }
        val userempana = findViewById<ImageButton>(R.id.EmpanadasButton)

        userempana.setOnClickListener {
            val intent = Intent(this, MostrarEmpanadas::class.java)
            startActivity(intent)
        }
        val userdona = findViewById<ImageButton>(R.id.DonutsButton)

        userdona.setOnClickListener {
            val intent = Intent(this, MostrarDonas::class.java)
            startActivity(intent)
        }


    }
}




