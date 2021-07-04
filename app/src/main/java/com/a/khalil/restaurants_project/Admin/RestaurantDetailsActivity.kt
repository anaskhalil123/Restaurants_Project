package com.a.khalil.restaurants_project.Admin

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.a.khalil.restaurants_project.R
import com.a.khalil.restaurants_project.StaticThings
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_restaurant_details.*

class RestaurantDetailsActivity : AppCompatActivity() {

    var db: FirebaseFirestore? = null
    lateinit var sharedPreferences: SharedPreferences
    var latitude: Float? = null
    var longitude: Float? = null
    var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_details)
        sharedPreferences = getSharedPreferences("interact with map", MODE_PRIVATE)

        db = Firebase.firestore
        if (intent.getStringExtra("name") == null) {
            name = intent.getStringExtra("nameFromMap")
        } else {
            name = intent.getStringExtra("name")
        }

        getRestaurantData()

//        openMap.setOnClickListener {
//            val intent = Intent(this, AdminMapsActivity::class.java)
//            intent.putExtra("latitude", StaticThings.currentLatitude!!)
//            intent.putExtra("longitude", StaticThings.currentLongitude!!)
//            intent.putExtra("title", StaticThings.RestaurantTitle)
//            startActivity(intent)
//        }

        addMealsBtn.setOnClickListener {
            val intent = Intent(this, AddMealActivity::class.java)
            intent.putExtra("restaurant_name", StaticThings.RestaurantTitle)
            startActivity(intent)
        }


        openMapDetails.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.putString("from", "I'm from details")
            editor.apply()
            val intent = Intent(this, AdminMapsActivity::class.java)
            intent.putExtra("detailsLatitude", latitude)
            intent.putExtra("detailsLongitude", longitude)
            startActivity(intent)
        }


        btnDeleteRes.setOnClickListener {

        }


    }

    fun getRestaurantData() {

        db!!.collection("restaurants").whereEqualTo("name", name).get()
            .addOnSuccessListener { querySnapshot ->

                textRestaurantNameDetails.setText(
                    querySnapshot.documents.get(0).get("name").toString()
                )
                textRestaurantAddressDetails.setText(
                    querySnapshot.documents.get(0).get("Address").toString()
                )
                ratingBarDetails.rating =
                    querySnapshot.documents.get(0).get("rating").toString().toFloat()
                Glide.with(this@RestaurantDetailsActivity)
                    .load(querySnapshot.documents.get(0).get("path").toString()).into(imageForRess)
                StaticThings.currentLatitude =
                    querySnapshot.documents.get(0).get("latitude").toString().toFloat()


                latitude = querySnapshot.documents.get(0).get("latitude").toString().toFloat()
                StaticThings.currentLongitude =
                    querySnapshot.documents.get(0).get("longitude").toString().toFloat()


                longitude = querySnapshot.documents.get(0).get("longitude").toString().toFloat()
                StaticThings.RestaurantTitle = textRestaurantNameDetails.text.toString()

                Log.e(
                    "Details",
                    "Name: ${textRestaurantNameDetails.text}"
                            + "Address: ${textRestaurantAddressDetails.text}"
                            + "rate: ${ratingBarDetails.rating}"
                            + "latitude ${StaticThings.currentLatitude}"
                            + "longitude ${StaticThings.currentLongitude}"
                )

                restaurantRateDetails.setText(ratingBarDetails.rating.toString())

                restaurantLatlng.setText("${StaticThings.currentLatitude} , ${StaticThings.currentLongitude}")

            }.addOnFailureListener { exception ->


            }
    }


    fun updateRestaurant() {
        val name = textRestaurantNameDetails.text.toString()
        val address = textRestaurantAddressDetails.text.toString()
        val rating = ratingBarDetails.rating.toString().toFloat()
        val latitude = StaticThings.currentLatitude
        val longitude = StaticThings.currentLongitude

        val restaurant = hashMapOf(
//            "path" to path,
            "name" to name,
            "Address" to address,
            "rating" to rating,
            "latitude" to latitude,
            "longitude" to longitude
        )


    }
}