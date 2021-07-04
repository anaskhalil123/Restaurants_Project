package com.a.khalil.restaurants_project.Admin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.a.khalil.restaurants_project.R
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        btnCreateRes.setOnClickListener {
            startActivity(Intent(this, CreateRestaurantActivity::class.java))
        }

        btnViewRes.setOnClickListener {
            startActivity(Intent(this, ViewRestaurantsActivity::class.java))
        }


    }
}