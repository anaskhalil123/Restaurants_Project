package com.a.khalil.restaurants_project.Customer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.a.khalil.restaurants_project.Customer.fragments.BasketFragment
import com.a.khalil.restaurants_project.Customer.fragments.ProfileFragment
import com.a.khalil.restaurants_project.Customer.fragments.RestaurantsFragment
import com.a.khalil.restaurants_project.R

import com.a.khalil.restaurants_project.databinding.ActivityArrangementFragmentsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_arrangement_fragments.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArrangementFragmentsBinding
    private val profileFragment = ProfileFragment()
    private val basketFragment = BasketFragment()
    private val restaurantFragment = RestaurantsFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArrangementFragmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_profile,
//                R.id.navigation_basket,
//                R.id.navigation_restaurants
//            )
//        )
//
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
//        replaceFragment(profileFragment)

        nav_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_restaurants ->replaceFragment(restaurantFragment)
                R.id.navigation_profile ->replaceFragment(profileFragment)
                R.id.navigation_basket ->replaceFragment(basketFragment)
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment_activity_main, fragment).commit()
        }
    }

}