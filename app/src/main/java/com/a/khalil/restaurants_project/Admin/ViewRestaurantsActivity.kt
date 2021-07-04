package com.a.khalil.restaurants_project.Admin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a.khalil.restaurants_project.R
import com.a.khalil.restaurants_project.models.BigRestaurant
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_view_restaurant.*
import kotlinx.android.synthetic.main.res_item.view.*

class ViewRestaurantsActivity : AppCompatActivity() {

    var db: FirebaseFirestore? = null
    var adapter: FirestoreRecyclerAdapter<BigRestaurant, ResViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_restaurant)

        db = Firebase.firestore

        getAllRestaurants()
    }

    fun getAllRestaurants() {
        val query = db!!.collection("restaurants")

        val options =
            FirestoreRecyclerOptions.Builder<BigRestaurant>()
                .setQuery(query, BigRestaurant::class.java)
                .build()

        adapter = object : FirestoreRecyclerAdapter<BigRestaurant, ResViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResViewHolder {
                val view =
                    LayoutInflater.from(this@ViewRestaurantsActivity)
                        .inflate(R.layout.res_item, parent, false)

                return ResViewHolder(view)
            }

            override fun onBindViewHolder(holder: ResViewHolder, position: Int, model: BigRestaurant) {
                holder.name.text = model.name
                holder.address.text = model.Address
                Glide.with(this@ViewRestaurantsActivity).load(model.path).into(holder.image)

                holder.button.setOnClickListener {
                    val intent =
                        Intent(this@ViewRestaurantsActivity, RestaurantDetailsActivity::class.java)
                    intent.putExtra("name", holder.name.text.toString())
                    startActivity(intent)
                }
            }
        }


        allRestaurants.layoutManager = GridLayoutManager(this, 2)
        allRestaurants.adapter = adapter
    }

    class ResViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.restaurant_item_name
        var address = view.restaurant_item_address
        var image = view.restaurant_item_img
        val button = view.restaurant_item_btn
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }
}