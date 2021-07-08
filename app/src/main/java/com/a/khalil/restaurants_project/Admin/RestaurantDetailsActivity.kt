package com.a.khalil.restaurants_project.Admin

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.a.khalil.restaurants_project.R
import com.a.khalil.restaurants_project.StaticThings
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_create_restaurant.*
import kotlinx.android.synthetic.main.activity_restaurant_details.*
import kotlinx.android.synthetic.main.activity_restaurant_details.imageForRess
import kotlinx.android.synthetic.main.activity_restaurant_details.openMapDetails
import kotlinx.android.synthetic.main.activity_restaurant_details.ratingBarDetails
import kotlinx.android.synthetic.main.activity_restaurant_details.textRestaurantAddressDetails

class RestaurantDetailsActivity : AppCompatActivity() {

    var db: FirebaseFirestore? = null
    lateinit var sharedPreferences: SharedPreferences
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var name: String? = null
    var restaurant_name_from_details = ""
    var progressDialog: ProgressDialog? = null
    lateinit var stringId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_details)
        sharedPreferences = getSharedPreferences("details with map", MODE_PRIVATE)

        progressDialog = ProgressDialog(this)
        progressDialog!!.setCancelable(false)

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
            intent.putExtra("restaurant_name", restaurant_name_from_details)
            startActivity(intent)
        }

        openMapDetails.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.putString("from", "I'm from details")
            editor.apply()
            val intent = Intent(this, AdminMapsActivity::class.java)
            intent.putExtra("detailsLatitude", latitude)
            intent.putExtra("detailsLongitude", longitude)
            intent.putExtra("nameFromDetails", restaurant_name_from_details)
            startActivity(intent)
        }


        btnDeleteRes.setOnClickListener {
            deleteRestaurant()
        }

        updateResBtn.setOnClickListener {
            sharedPreferences = getSharedPreferences("details with map", MODE_PRIVATE)

            latitude = sharedPreferences.getFloat("latitude", 0.0f).toDouble()
            longitude = sharedPreferences.getFloat("longitude", 0.0f).toDouble()

            if (latitude != 0.0 && longitude != 0.0) {
                restaurantLatlng.text = "${latitude.toFloat()} , ${longitude.toFloat()}"
            } else {
                restaurantLatlng.text = ""
            }

        }


    }

    fun getRestaurantData() {

        db!!.collection("restaurants").whereEqualTo("name", name).get()
            .addOnSuccessListener { querySnapshot ->

                stringId = querySnapshot.documents.get(0).id as String
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


                latitude = querySnapshot.documents.get(0).get("latitude").toString().toDouble()
                StaticThings.currentLongitude =
                    querySnapshot.documents.get(0).get("longitude").toString().toFloat()

                longitude = querySnapshot.documents.get(0).get("longitude").toString().toDouble()
                StaticThings.RestaurantTitle = textRestaurantNameDetails.text.toString()

                restaurant_name_from_details = textRestaurantNameDetails.text.toString()

                Log.e(
                    "Details",
                    "Name: ${textRestaurantNameDetails.text}"
                            + "Address: ${textRestaurantAddressDetails.text}"
                            + "rate: ${ratingBarDetails.rating}"
                            + "latitude ${latitude.toFloat()}"
                            + "longitude ${longitude.toFloat()}"
                )

                restaurantRateDetails.setText(ratingBarDetails.rating.toString())

                restaurantLatlng.setText("${latitude.toFloat()} , ${longitude.toFloat()}")

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


    fun deleteRestaurant() {
        progressDialog!!.setTitle("جاري الحذف ...")

        progressDialog!!.show()

        db!!.collection("restaurants").document(stringId).delete()
            .addOnCompleteListener {
                Toast.makeText(this, "تم حذف المطعم", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener { exception ->
                progressDialog!!.dismiss()
                Log.e("anas", exception.toString())
            }

    }


}