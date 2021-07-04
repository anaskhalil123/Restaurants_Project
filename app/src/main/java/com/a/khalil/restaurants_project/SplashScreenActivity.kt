package com.a.khalil.restaurants_project

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.a.khalil.restaurants_project.Customer.CustomerLoginActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val intent = Intent(this, CustomerLoginActivity::class.java)

            startActivity(intent)

            finish()

        }, 3500)

    }

    override fun onResume() {
        super.onResume()
        hideNavigationBar()
    }


    private fun hideNavigationBar() {
        val overlay: View = findViewById(R.id.mylayout)

        overlay.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }


}
