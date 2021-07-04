package com.a.khalil.restaurants_project.Admin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.a.khalil.restaurants_project.R
import kotlinx.android.synthetic.main.activity_restaurantmeals.*

class RestaurantMeals : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurantmeals)

        btnDetails.setOnClickListener {
            startActivity(Intent(this, DetailsMeals::class.java))
        }


        btnAdd.setOnClickListener {

        }

    }
}