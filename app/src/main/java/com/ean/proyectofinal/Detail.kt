package com.ean.proyectofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ean.proyectofinal.databinding.ActivityDetailBinding
import android.annotation.SuppressLint
import android.util.Log
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Detail : AppCompatActivity() {
    private lateinit var bindingActivityDetail: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivityDetail = ActivityDetailBinding.inflate(layoutInflater)
        val view = bindingActivityDetail.root
        setContentView(view)

        val key = intent.getStringExtra("key")
        val database = Firebase.database
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS") val myRef = database.getReference("Food").child(key.toString())

        myRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val mAlimento:Alimento? = dataSnapshot.getValue(Alimento::class.java)
                if (mAlimento != null) {
                    bindingActivityDetail.nameTextView.text = mAlimento.name.toString()
                    bindingActivityDetail.priceTextView.text = mAlimento.price.toString() + " $"
                    bindingActivityDetail.descriptionTextView.text = mAlimento.description.toString()
                    Glide.with(view)
                        .load(mAlimento.url.toString())
                        .into(bindingActivityDetail.posterImgeView)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }
}