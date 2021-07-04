package com.a.khalil.restaurants_project.Customer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.a.khalil.restaurants_project.Customer.fragments.BasketFragment
import com.a.khalil.restaurants_project.R
import com.a.khalil.restaurants_project.models.DeatailsMeals
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_meals_destails.*
import kotlinx.android.synthetic.main.rest_item.view.*

class MealsDestails : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meals_destails)


        val s = intent.getParcelableExtra<DeatailsMeals>("data")
//        txtName.text = s!!.name
//        txtinfo.text = s!!.info
//        txtPrice.text = s.price.toString()
//        txtReact.text = s.react.toString()
//        image.setImageResource(s.image)

        var data = ArrayList<DeatailsMeals>()
        var db: FirebaseFirestore? = null
        var adpter: FirestoreRecyclerAdapter<DeatailsMeals, RestViewHolder>? = null

        btnPay.setOnClickListener {
//            var intent= Intent(this, BasketFragment().activity!!::class.java)
//            startActivity(intent)

            supportFragmentManager.beginTransaction().replace(R.id.containerr, BasketFragment()).commit()
            btnPay.setBackgroundColor(resources.getColor(R.color.backColor))
        }

        fun getAllUser() {
            val query = db!!.collection("DeatailsMeals")
            val options = FirestoreRecyclerOptions.Builder<DeatailsMeals>().setQuery(query, DeatailsMeals::class.java).build()

            adpter = object : FirestoreRecyclerAdapter<DeatailsMeals, RestViewHolder>(options) {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RestViewHolder {
                    var view = LayoutInflater.from(applicationContext)
                        .inflate(R.layout.meals_item, parent, false)
                    return RestViewHolder(view)
                }

                override fun onBindViewHolder(holder: RestViewHolder, position: Int, model: DeatailsMeals) {
                    holder.title.text = getText(position)
                    intent.putExtra("image",data[position].image)
                    intent.putExtra("title",data[position].name)
                    intent.putExtra("title",data[position].info)
                    intent.putExtra("title",data[position].price).toString()
                    intent.putExtra("title",data[position].react).toString()
                    Glide.with(applicationContext).load(holder.image).into(holder.image)
                }


            }


        }

        db = Firebase.firestore

        getAllUser()

        data.add(DeatailsMeals(R.drawable.cheken , "تشيكن بيتزا" , "دجاج مع مكونات البيتزا وبطاطا وصوص" , 15 , 9/10))
        data.add(DeatailsMeals( R.drawable.barger, "برجر عربي" , "شريحة لحم مع خضار وبطاطا وصوص" , 14 , 8/10 ))
        data.add(DeatailsMeals(R.drawable.m1, "معكرونة" , "معكرونة بدون اضافات" , 20 , 7/10))
        data.add(DeatailsMeals(R.drawable.m2 , "شاورما" , " شاورما وبطاطا وصوص" , 10  , 10/10))

    }


    class RestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title = view.restaurant_title
        var image = view.restaurant_image

    }


}
