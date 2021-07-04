package com.a.khalil.restaurants_project.Customer.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a.khalil.restaurants_project.Adapters.Adapter
import com.a.khalil.restaurants_project.Customer.MealsActivity
import com.a.khalil.restaurants_project.R
import com.a.khalil.restaurants_project.models.Restaurants
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_restaurants.view.*
import kotlinx.android.synthetic.main.rest_item.view.*

class RestaurantsFragment : Fragment()
//    , Adapter.onClick
{

//    var data = ArrayList<Restaurants>()
//    var db: FirebaseFirestore? = null
//    var adapter: FirestoreRecyclerAdapter<Restaurants, RestViewHolder>? = null
//
//    fun getAllUser() {
//        val query = db!!.collection("restaurants")
//        val options =
//            FirestoreRecyclerOptions.Builder<Restaurants>().setQuery(query, Restaurants::class.java).build()
//
//        adapter = object : FirestoreRecyclerAdapter<Restaurants, RestViewHolder>(options) {
//            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestViewHolder {
//                val view = LayoutInflater.from(requireContext().applicationContext)
//                    .inflate(R.layout.rest_item, parent, false)
//                return RestViewHolder(view)
//            }
//
//            override fun onBindViewHolder(p0: RestViewHolder, p1: Int, p2: Restaurants) {
//                p0.title.text = getText(p1)
//                Glide.with(requireContext().applicationContext).load(p0.image).into(p0.image)
//            }
//
//        }
//
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_restaurants, container!!, false)
//
//        db = Firebase.firestore
//
//        getAllUser()
//
//        data.add(Restaurants(R.drawable.ital , "مطعم ايطالينو"))
//        data.add(Restaurants(R.drawable.r2 , "مطعم الطازج"))
//        data.add(Restaurants(R.drawable.r7 , "مطعم الطابون"))
//        data.add(Restaurants( R.drawable.r4, "مطعم التايلندي" ))
//        data.add(Restaurants(R.drawable.r3, "مطعم بالميرا"))
//        data.add(Restaurants(R.drawable.r5 , "مطعم التركي"))
//
//        var adapter= Adapter(this.requireActivity()  , data = arrayListOf() , MealsActivity())
//        root.restaurant_list.layoutManager = GridLayoutManager(context, 2)
//        root.restaurant_list.adapter = adapter

        return root
    }

//    override fun onClickItem(position: Int) {
//        val intent= Intent(this.requireActivity(), MealsActivity::class.java)
//        intent.putExtra("image",data[position].image)
//        intent.putExtra("title",data[position].title)
//        this.requireActivity().startActivity(intent)
//    }
//
//    class RestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        var title = view.restaurant_title
//        var image = view.restaurant_image
//    }
}