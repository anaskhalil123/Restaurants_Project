package com.a.khalil.restaurants_project.Admin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.a.khalil.restaurants_project.R
import kotlinx.android.synthetic.main.activity_details_meals.*

class DetailsMeals : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_meals)

        btnEdit.setOnClickListener {

        }

        btnDelete.setOnClickListener {

            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("حذف الوجبة")
            alertDialog.setMessage("هل انت متاكد من حذف الوجبة؟")
            alertDialog.setIcon(R.drawable.ic_baseline_delete_24)


            alertDialog.setPositiveButton("نعم") { d, i ->
                Toast.makeText(this, "نعم", Toast.LENGTH_SHORT).show()
            }

            alertDialog.setPositiveButton("لا") { d, i ->
                Toast.makeText(this, "لا", Toast.LENGTH_SHORT).show()
            }

            alertDialog.create().show()

        }
    }
}