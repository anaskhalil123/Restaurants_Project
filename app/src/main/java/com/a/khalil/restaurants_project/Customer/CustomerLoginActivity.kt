package com.a.khalil.restaurants_project.Customer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.a.khalil.restaurants_project.Admin.AdminLoginActivity
import com.a.khalil.restaurants_project.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_customer_login.*

class CustomerLoginActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
/*
*    Abdel Azeez
*    12345678
*    0599999991
*  */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_login)

        db = Firebase.firestore

        textGoToAdmin.setOnClickListener {
            startActivity(Intent(this, AdminLoginActivity::class.java))
        }

        btnLogin.setOnClickListener {

            if (textName.text != null && textPassword.text != null && textPhone.text != null) {
                loginAsCustomer(
                    textName.text.toString(),
                    textPassword.text.toString(),
                    textPhone.text.toString()
                )


            } else {
                Toast.makeText(
                    this,
                    "أكمل تعبأة البيانات",
                    Toast.LENGTH_SHORT
                ).show()

            }

        }

    }

    fun loginAsCustomer(name: String, pass: String, phone: String) {
        db.collection("Customer").get().addOnSuccessListener { querySnapShot ->

            val userName = querySnapShot.documents.get(0).get("name").toString()
            val userPassword = querySnapShot.documents.get(0).get("password").toString()
            val userPhone = querySnapShot.documents.get(0).get("phone").toString()

            if (name.equals(userName) && pass.equals(userPassword) && phone.equals(userPhone)) {

                startActivity(Intent(this, MainActivity::class.java))

                Toast.makeText(
                    this,
                    "تم  تسجيل الدخول بنجاح",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                Toast.makeText(
                    this,
                    "فشل تسجل الدخول",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "فشل  تسجيل الدخول", Toast.LENGTH_SHORT).show()
        }
    }

}