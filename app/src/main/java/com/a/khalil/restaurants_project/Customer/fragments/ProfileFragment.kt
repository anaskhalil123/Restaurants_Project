package com.a.khalil.restaurants_project.Customer.fragments

import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.a.khalil.restaurants_project.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.vansuita.pickimage.bean.PickResult
import com.vansuita.pickimage.listeners.IPickResult
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.util.*


class ProfileFragment : Fragment() , IPickResult {
    var db: FirebaseFirestore? = null
    var storage: FirebaseStorage? = null
    var reference: StorageReference? = null
    lateinit var progressDialog: ProgressDialog
    var path: String? = null
    lateinit var root: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        this.root = root;

        db = Firebase.firestore
        storage = Firebase.storage
        reference = storage!!.reference
        progressDialog = ProgressDialog(requireContext().applicationContext)
        progressDialog.setMessage("جاري التحميل")
        progressDialog.setCancelable(false)
        getProfileData()

        root.btnSave.setOnClickListener {
            updateImage(path)
        Toast.makeText(requireContext().applicationContext  ,
            "Saved Successfully" , Toast.LENGTH_SHORT).show()
        }

        root.btnBack.setOnClickListener {

        }

        return root
    }

    private fun updateImage(path: String?) {
        db!!.collection("users").whereEqualTo("email",
            FirebaseAuth.getInstance().currentUser?.email
        ).get()
            .addOnSuccessListener { querySnapshot ->
                if(querySnapshot.isEmpty) return@addOnSuccessListener
                db!!.collection("users").document(querySnapshot.documents.get(0).id)
                    .update("image", path)
            }.addOnFailureListener { exception ->

            }
    }

    fun getProfileData() {
        db!!.collection("users").get()
            .addOnSuccessListener { querySnapshot ->
//                root.edName.setText(querySnapshot.documents.get(0).get("name").toString())
//                root.edPhone.setText(querySnapshot.documents.get(0).get("phone").toString())
//                root.edPassword.setText(querySnapshot.documents.get(0).get("password").toString())
            }.addOnFailureListener { exception ->

            }
    }

    override fun onPickResult(r: PickResult?) {
        imgProfile.setImageBitmap(r!!.bitmap)
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

            }.addOnFailureListener { exception ->

            }
    }

}