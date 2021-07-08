package com.a.khalil.restaurants_project.Admin

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.a.khalil.restaurants_project.R
import com.a.khalil.restaurants_project.StaticThings
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.vansuita.pickimage.bean.PickResult
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import com.vansuita.pickimage.listeners.IPickResult
import kotlinx.android.synthetic.main.activity_create_restaurant.*
import java.util.*

class CreateRestaurantActivity : AppCompatActivity(), IPickResult {

    var db: FirebaseFirestore? = null
    var reference: StorageReference? = null
    var storage: FirebaseStorage? = null
    lateinit var progressDialog: ProgressDialog
    var path: String? = null
    lateinit var sharedPreferences: SharedPreferences
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var restaurant_name = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_restaurant)
        sharedPreferences = getSharedPreferences("create with map", MODE_PRIVATE)

        if (applicationContext.getSharedPreferences("create with map", MODE_PRIVATE)
                .getFloat("latitude", 0.0f) != 0.0f
        ) {
            pointInMap.text = "${latitude.toFloat()} , ${longitude.toFloat()}"
        }

        storage = Firebase.storage
        reference = storage!!.reference
        db = Firebase.firestore

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("جاري التحميل")
        progressDialog.setCancelable(false)


//        if (intent.getFloatExtra("latitudeToCreate", 0f) == 0f
//            && intent.getFloatExtra("latitudeToCreate", 0f) == 0f
//        ) {
//
//        } else {
//            latitude = intent.getFloatExtra("latitudeToCreate", 0f)
//            longitude = intent.getFloatExtra("longitudeToCreate", 0f)
//            pointInMap.text = "$latitude , $longitude"
//        }

        imageForRess.setOnClickListener {
            PickImageDialog.build(PickSetup()).show(this)
        }


        ratingBarDetails.setOnRatingBarChangeListener(object : RatingBar.OnRatingBarChangeListener {
            override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
                sharedPreferences = getSharedPreferences("create with map", MODE_PRIVATE)

                latitude = sharedPreferences.getFloat("latitude", 0.0f).toDouble()

                longitude = sharedPreferences.getFloat("longitude", 0.0f).toDouble()

                if (latitude != 0.0 && longitude != 0.0) {
                    pointInMap.text = "${latitude.toFloat()} , ${longitude.toFloat()}"
                } else {
                    pointInMap.text = ""
                }

                val rateValue = ratingBarDetails.rating

                if (rateValue <= 1 && rateValue > 0) {
                    rateCount.setText("Bad " + rateValue + "/5")
                } else if (rateValue <= 2 && rateValue > 1) {
                    rateCount.setText("OK " + rateValue + "/5")
                } else if (rateValue <= 3 && rateValue > 2) {
                    rateCount.setText("Good " + rateValue + "/5")
                } else if (rateValue <= 4 && rateValue > 3) {
                    rateCount.setText("Very Good " + rateValue + "/5")
                } else if (rateValue <= 5 && rateValue > 5) {
                    rateCount.setText("Great " + rateValue + "/5")
                } else
                    rateCount.setText("" + rateValue + "/5")
            }

        })

        ratingBarDetails.setOnClickListener {
            ratingBarDetails.rating = 0F
            rateCount.setText("")
        }

        openMapDetails.setOnClickListener {
//            if (!textRestaurantNameDetails.text.equals(null)) {
//                StaticThings.RestaurantTitle = textRestaurantNameDetails.text.toString()
//            Log.e("anas", "$latitude , $longitude")

            if (latitude == 0.0 && longitude == 0.0) {
                val intent = Intent(this, AdminMapsActivity::class.java)
                if (!textRestaurantName.text.equals(null) && !textRestaurantName.text.equals(" ")) {
                    intent.putExtra("nameFromCreate", textRestaurantName.text.toString())
                }
                startActivity(intent)
            } else {
                val intent = Intent(this, AdminMapsActivity::class.java)
                intent.putExtra("latitudeFromCreate", latitude)
                intent.putExtra("longitudeFromCreate", longitude)
                if (!textRestaurantName.text.equals(null) && !textRestaurantName.text.equals(" ")) {
                    intent.putExtra("nameFromCreate", textRestaurantName.text.toString())
                }
                startActivity(intent)
            }


//            if (latitude != null && longitude != null) {
//                val intent = Intent(this, AdminMapsActivity::class.java)
//                intent.putExtra("createlatitude", latitude)
//                intent.putExtra("createlongitude", longitude)
//                if (!restaurant_name.equals("")) {
//                    intent.putExtra("nameFromCreate", restaurant_name)
//                }
//                startActivity(intent)
//            } else {
//                startActivity(Intent(this, AdminMapsActivity::class.java))
//            }

//            }

        }

        createResBtn.setOnClickListener {

            if (!path.equals(null) && !(textRestaurantName.text.equals(null)
                        || textRestaurantName.text.equals(" ")) &&
                !(textRestaurantAddressDetails.text.equals(null)
                        || textRestaurantName.text.equals(" "))
                && !(ratingBarDetails.rating.equals(null) || ratingBarDetails.rating.equals(0.0f))
                && latitude != 0.0
                && longitude != 0.0
            ) {
                restaurant_name = textRestaurantName.text.toString()
                val restaurant_address = textRestaurantAddressDetails.text.toString()
                val rateValue = ratingBarDetails.rating


                StaticThings.RestaurantTitle = restaurant_name

                addRestaurant(
                    path!!,
                    restaurant_name,
                    restaurant_address,
                    rateValue,
                    latitude,
                    longitude
                )

                Toast.makeText(
                    this,
                    "تم إضافة مطعم", Toast.LENGTH_SHORT
                ).show()
                Log.e("name", restaurant_name)
//                finish()
            } else {
                Toast.makeText(
                    this,
                    "Fill the fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onPickResult(r: PickResult?) {
        val bitmap = r!!.bitmap
        imageForRess!!.setImageBitmap(bitmap)
        uploadImage(r.uri)
    }

    private fun uploadImage(uri: Uri?) {
        progressDialog.show()
        reference!!.child("profile/" + UUID.randomUUID().toString()).putFile(uri!!)
            .addOnSuccessListener { taskSnapshot ->
                progressDialog.dismiss()

                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    path = uri.toString()
                }
                Toast.makeText(this, "تم حفظ الصورة", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { exception ->
                Toast.makeText(this, "فشل حفظ الصورة", Toast.LENGTH_SHORT).show()
                Log.e("exception123", exception.toString())
            }
    }

    private fun addRestaurant(
        path: String,
        name: String,
        address: String,
        rating: Float,
        latitude: Double,
        longitude: Double
    ) {
        val restaurant = hashMapOf(
            "path" to path,
            "name" to name,
            "Address" to address,
            "rating" to rating,
            "latitude" to latitude,
            "longitude" to longitude
        )

        db!!.collection("restaurants").add(restaurant).addOnSuccessListener { documentReference ->
            Log.e("restaurant name", documentReference.id)
        }.addOnFailureListener { exception ->

        }
        finish()
    }

}