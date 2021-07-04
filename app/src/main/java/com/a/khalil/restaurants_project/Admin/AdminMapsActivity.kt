package com.a.khalil.restaurants_project.Admin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.a.khalil.restaurants_project.R
import com.a.khalil.restaurants_project.StaticThings
import com.a.khalil.restaurants_project.databinding.ActivityAdminMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_admin_maps.*

class AdminMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private lateinit var binding: ActivityAdminMapsBinding
    private var point: LatLng? = null
    var createLatitude: Float? = null
    var createLongitude: Float? = null
    var detailsLatitude: Float? = null
    var detailsLongitude: Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityAdminMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



        returnBtn.setOnClickListener {

            if (detailsLatitude == null && detailsLongitude == null) {
                val intent = Intent(this, CreateRestaurantActivity::class.java)
                intent.putExtra("latitudeToCreate", createLatitude)
                intent.putExtra("longitudeToCreate", createLongitude)
                startActivity(intent)
            } else {
                val intent = Intent(this,RestaurantDetailsActivity::class.java)
                intent.putExtra("latitudeToDetails",detailsLatitude)
                intent.putExtra("longitudeToDetails",detailsLongitude)
                intent.putExtra("name",StaticThings.RestaurantTitle)
                startActivity(intent)
            }
        }
    }


override fun onMapReady(googleMap: GoogleMap) {

    mMap = googleMap

    if (!intent.getFloatExtra("createlatitude", 0f).equals(null) &&
        !intent.getFloatExtra("createlongitude", 0f).equals(null) &&
        !intent.getFloatExtra("createlatitude", 0f).equals(0f) &&
        !intent.getFloatExtra("createlongitude", 0f).equals(0f)
    ) {

        createLatitude = intent.getFloatExtra("createlatitude", 0f)
        createLongitude = intent.getFloatExtra("createlongitude", 0f)

        point = LatLng(createLatitude!!.toDouble(), createLongitude!!.toDouble())
        mMap!!.addMarker(
            MarkerOptions().position(point).title(StaticThings.RestaurantTitle)
        )
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 15f))
    } else if (
        !intent.getFloatExtra("detailsLatitude", 0f).equals(null) &&
        !intent.getFloatExtra("detailsLatitude", 0f).equals(0f) &&
        !intent.getFloatExtra("detailsLongitude", 0f).equals(null) &&
        !intent.getFloatExtra("detailsLongitude", 0f).equals(0f)
    ) {
        detailsLatitude = intent.getFloatExtra("detailsLatitude", 0f)
        detailsLongitude = intent.getFloatExtra("detailsLongitude", 0f)
        point = LatLng(detailsLatitude!!.toDouble(), detailsLongitude!!.toDouble())
        mMap!!.addMarker(
            MarkerOptions().position(point).title(StaticThings.RestaurantTitle)
        )
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 15f))

    } else {

    }

//        if (StaticThings.currentLatitude != null && StaticThings.currentLongitude != null) {
//            point = LatLng(
//                StaticThings.currentLatitude!!.toDouble(),
//                StaticThings.currentLongitude!!.toDouble()
//            )
//            mMap!!.addMarker(
//                MarkerOptions().position(point).title(StaticThings.RestaurantTitle)
//            )
//            mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 15f))
//            StaticThings.currentLongitude = null
//            StaticThings.currentLatitude = null
//        }
//
//        if (StaticThings.latitude != null && StaticThings.longitude != null) {
//            point = LatLng(StaticThings.latitude!!.toDouble(), StaticThings.longitude!!.toDouble())
//            mMap!!.addMarker(MarkerOptions().position(point).title(StaticThings.RestaurantTitle))
//            mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 15f))
//            StaticThings.longitude = null
//            StaticThings.latitude = null
//        }

    mMap!!.setOnMapClickListener { latLng ->
        mMap!!.clear()
//            StaticThings.latitude = latLng.latitude.toFloat()
//            StaticThings.longitude = latLng.longitude.toFloat()
        point = latLng
        if (detailsLatitude == null && detailsLongitude == null) {
            createLatitude = point!!.latitude.toFloat()
            createLongitude = point!!.longitude.toFloat()
        } else {
            detailsLatitude = point!!.latitude.toFloat()
            detailsLongitude = point!!.longitude.toFloat()
        }
        mMap!!.addMarker(MarkerOptions().position(latLng).title(StaticThings.RestaurantTitle))
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

        Toast.makeText(
            this,
            latLng.latitude.toString() + " , " + latLng.longitude.toString(),
            Toast.LENGTH_SHORT
        ).show()

    }
}
}