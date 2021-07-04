package com.a.khalil.restaurants_project.Customer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a.khalil.restaurants_project.Adapters.Adapter
import com.a.khalil.restaurants_project.R
import com.a.khalil.restaurants_project.models.Meal
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_meals.*
import kotlinx.android.synthetic.main.fragment_restaurants.*
import kotlinx.android.synthetic.main.rest_item.view.*

class MealsActivity : AppCompatActivity() , Adapter.onClick {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meals)
        var data = ArrayList<Meal>()
        var db: FirebaseFirestore? = null
        var adpter: FirestoreRecyclerAdapter<Meal,RestViewHolder>? = null

        fun getAllUser() {
            val query = db!!.collection("meals")
            val options = FirestoreRecyclerOptions.Builder<Meal>().setQuery(query, Meal::class.java).build()

            adpter = object : FirestoreRecyclerAdapter<Meal , RestViewHolder>(options) {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RestViewHolder {
                    var view = LayoutInflater.from(applicationContext)
                        .inflate(R.layout.meals_item, parent, false)
                    return RestViewHolder(view)
                }

                override fun onBindViewHolder(p0: RestViewHolder, p1: Int, p2: Meal) {
                    p0.title.text = getText(p1)
                    intent.putExtra("image",data[p1].image)
                    intent.putExtra("title",data[p1].title)
                    Glide.with(applicationContext).load(p0.image).into(p0.image)
                }

            }


        }

            db = Firebase.firestore

            getAllUser()

            data.add(Meal(R.drawable.cheken , "تشيكن بيتزا"))
            data.add(Meal( R.drawable.barger, "برجر عربي" ))
            data.add(Meal(R.drawable.m1, "معكرونة"))
            data.add(Meal(R.drawable.m2 , "شاورما"))

            var adapter= Adapter(this, data, Click = this)
            restaurant_list.layoutManager = GridLayoutManager(this, 2)
            restaurant_list.adapter = adapter



        }

       override fun onClickItem(position: Int) {
            var intent= Intent(this, MealsActivity::class.java)
            this.startActivity(intent)
       }

        class RestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var title = view.restaurant_title
            var image = view.restaurant_image

        }


    }
