package com.a.khalil.restaurants_project.Admin

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import com.a.khalil.restaurants_project.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.vansuita.pickimage.bean.PickResult
import com.vansuita.pickimage.listeners.IPickResult
import kotlinx.android.synthetic.main.activity_add_meal.*

class AddMealActivity : AppCompatActivity(), IPickResult {
    var db: FirebaseFirestore? = null
    var reference: StorageReference? = null
    var storage: FirebaseStorage? = null
    lateinit var progressDialog: ProgressDialog
    var path: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_meal)

        db = Firebase.firestore
        storage = Firebase.storage
        reference = storage!!.reference


        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("جاري التحميل")
        progressDialog.setCancelable(false)

        val restaurant_name = intent.getStringExtra("restaurant_name")
        restaurantName.setText("إضافة وجبات لـ ${restaurant_name} ")

        val meal_name = tvName.text.toString()
        val meal_price = tvPrice.text.toString()
        val meal_description = tvDescription.text.toString()
        val meal_rate = ratingMeal.rating.toString().toFloat()


        ratingMeal.setOnRatingBarChangeListener(object : RatingBar.OnRatingBarChangeListener {
            override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {

                val rateValue = ratingMeal.rating

                if (rateValue <= 1 && rateValue > 0) {
                    rateMealCount.setText("Bad " + rateValue + "/5")
                } else if (rateValue <= 2 && rateValue > 1) {
                    rateMealCount.setText("OK " + rateValue + "/5")
                } else if (rateValue <= 3 && rateValue > 2) {
                    rateMealCount.setText("Good " + rateValue + "/5")
                } else if (rateValue <= 4 && rateValue > 3) {
                    rateMealCount.setText("Very Good " + rateValue + "/5")
                } else if (rateValue <= 5 && rateValue > 5) {
                    rateMealCount.setText("Great " + rateValue + "/5")
                } else
                    rateMealCount.setText("" + rateValue + "/5")
            }

        })

        imgMeal.setOnClickListener {

        }


    }

    override fun onPickResult(r: PickResult?) {
        TODO("Not yet implemented")
    }


    fun addMeal(path: String, name: String, price: Int, description: String, rate: Float) {
        val meal = hashMapOf(
            "path" to path,
            "name" to name,
            "price" to price,
            "description" to description,
            "rate" to rate
        )

        db!!.collection("").add(meal)

    }


}