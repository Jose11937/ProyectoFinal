package com.ean.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ean.proyectofinal.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SingIn : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread.sleep(2000)
        setTheme(R.style.SplashTheme)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.signInAppCompatButton.setOnClickListener{
            val mEmail = binding.emailEditText.text.toString()
            val mPassword = binding.passwordEditText.text.toString()

            when{
                mEmail.isEmpty() || mPassword.isEmpty() -> {
                    Toast.makeText(baseContext, "Correo o Contraseña invalidos.",
                        Toast.LENGTH_SHORT).show()
                }
                else -> {
                    SignIn(mEmail, mPassword)
                }
            }
        }

        binding.signUpTextView.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        binding.recoveryAccountTextView.setOnClickListener {
            val intent = Intent(this, AccountRecovery::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            if (currentUser.isEmailVerified){
                reload()
            }else{
                val intent = Intent(this, CheckEmail::class.java)
                startActivity(intent)
            }
        }
    }

    private fun SignIn (email : String, password : String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithEmail:success")
                    reload()
                } else {
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Correo o Contraseña invalidos.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun reload() {
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
    }
}