package com.a.khalil.restaurants_project.Admin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.a.khalil.restaurants_project.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_admin_login.*

class AdminLoginActivity : AppCompatActivity() {
/*
*    Ahmed khalil
*    wwwahmed123
* */
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        db = Firebase.firestore

        btnLogin.setOnClickListener {
            if (textName.text != null && textPassword.text != null) {
                loginAsAdmin(textName.text.toString(), textPassword.text.toString())
            } else {
                Toast.makeText(
                    this,
                    "أكمل تعبأة البيانات",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }

    }

    private fun loginAsAdmin(name: String, pass: String) {
        db.collection("Admin").get().addOnSuccessListener { querySnapShot ->
            val adminName = querySnapShot.documents.get(0).get("name") as String
            val adminPassword = querySnapShot.documents.get(0).get("password") as String
            if (name.equals(adminName) && pass.equals(adminPassword)) {
                Toast.makeText(
                    this,
                    "تم الدخول بنجاح يمكنك استخدام صلاحيات المشرف",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(Intent(this, DashboardActivity::class.java))
            } else {
                Toast.makeText(this, "فشل تسجيل الدخول", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { _ ->
            Toast.makeText(this, "فشل تسجيل الدخول", Toast.LENGTH_SHORT).show()
        }

    }
}