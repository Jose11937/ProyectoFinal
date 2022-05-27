package com.ean.proyectofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ean.proyectofinal.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class FoodPrincipal : AppCompatActivity() {

    private lateinit var bindingFoodPrincipal: ActivityMainBinding
    private lateinit var messagesListener: ValueEventListener

    private val database = Firebase.database
    private val listVideoGames:MutableList<Alimento> = ArrayList()
    private val myRef = database.getReference("Food")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingFoodPrincipal = ActivityMainBinding.inflate(layoutInflater)
        val view = bindingFoodPrincipal.root
        setContentView(view)

        bindingFoodPrincipal.bgProfileImageView.setOnClickListener { v ->
            val intent = Intent(this, Add::class.java)
            v.context.startActivity(intent)
        }

        listVideoGames.clear()
        setupRecyclerview(bindingFoodPrincipal.reciclervierw)
    }




    fun setupRecyclerView(recyclerView: RecyclerView) {

        messagesListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listVideoGames.clear()
                dataSnapshot.children.forEach { resp ->
                    val mAlimento =
                        Alimento(  resp.child("name").getValue<String>(),
                            resp.child("price").getValue<String>(),
                            resp.child("description").getValue<String>(),
                            resp.child("url").getValue<String>(),
                            resp.key)
                    mAlimento.let { listVideoGames.add(it) }
                }
                recyclerView.adapter = VideogameViewAdapter(listVideoGames)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "messages:onCancelled: ${error.message}")
            }
        }
        myRef.addValueEventListener(messagesListener)

        deleteSwipe(recyclerView)
    }

    class VideogameViewAdapter(private val values: List<Alimento>) :
        RecyclerView.Adapter<VideogameViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val mAlimento = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_food_content, parent, false)
            return ViewHolder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val mAlimento = values[position]
            holder.mNameTextView.text = mAlimento.name
            holder.mPriceTextView.text = mAlimento.price + " $"
            holder.mPosterImgeView.let {
                Glide.with(holder.itemView.context)
                    .load(mAlimento.url)
                    .into(it)
            }

            holder.itemView.setOnClickListener { v ->
                val intent = Intent(v.context, Detail::class.java).apply {
                    putExtra("key", mAlimento.key)
                }
                v.context.startActivity(intent)
            }

            holder.itemView.setOnLongClickListener{ v ->
                val intent = Intent(v.context, Edit::class.java).apply {
                    putExtra("key", mAlimento.key)
                }
                v.context.startActivity(intent)
                true
            }

        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val mNameTextView: TextView = view.findViewById(R.id.nameTextView) as TextView
            val mDateTextView: TextView = view.findViewById(R.id.dateTextView) as TextView
            val mPriceTextView: TextView = view.findViewById(R.id.priceTextView) as TextView
            val mPosterImgeView: ImageView = view.findViewById(R.id.posterImgeView) as ImageView
        }
    }

    fun deleteSwipe(recyclerView: RecyclerView){
        val touchHelperCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val imageFirebaseStorage = FirebaseStorage.getInstance().reference.child("game/img"+listVideoGames[viewHolder.adapterPosition].key)
                imageFirebaseStorage.delete()

                listVideoGames[viewHolder.adapterPosition].key?.let { myRef.child(it).setValue(null) }
                listVideoGames.removeAt(viewHolder.adapterPosition)

                recyclerView.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                recyclerView.adapter?.notifyDataSetChanged()
            }
        }
        val itemTouchHelper = ItemTouchHelper(touchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
        }
    }
}