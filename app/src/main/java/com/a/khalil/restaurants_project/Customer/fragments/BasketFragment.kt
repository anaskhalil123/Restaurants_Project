package com.a.khalil.restaurants_project.Customer.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a.khalil.restaurants_project.Adapters.Adapter
import com.a.khalil.restaurants_project.R
import com.a.khalil.restaurants_project.models.Order
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_meals_destails.*
import kotlinx.android.synthetic.main.cart_item.view.*
import kotlinx.android.synthetic.main.fragment_basket.*
import kotlinx.android.synthetic.main.fragment_basket.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class BasketFragment : Fragment(), Adapter.onClick {

    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var data = ArrayList<Order>()
    var db: FirebaseFirestore? = null
    var adapter: FirestoreRecyclerAdapter<Order, BasketFragment.CartViewHolder>? = null

    fun getAllUser() {
        val query = db!!.collection("order")
        val options =
            FirestoreRecyclerOptions.Builder<Order>().setQuery(query, Order::class.java).build()

        adapter = object : FirestoreRecyclerAdapter<Order, BasketFragment.CartViewHolder>(options) {

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): BasketFragment.CartViewHolder {
                var view = LayoutInflater.from(requireContext().applicationContext)
                    .inflate(R.layout.cart_item, parent, false)
                return BasketFragment.CartViewHolder(view)

            }

            override fun onBindViewHolder(p0: BasketFragment.CartViewHolder, p1: Int, p2: Order) {
                p0.name.text = getText(p1)
                p0.price.text = getText(p1)
                p0.total.text = getText(p1)
                Glide.with(requireContext().applicationContext).load(p0.image).into(p0.image)

            }

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        db = Firebase.firestore

        getAllUser()


        val root = inflater.inflate(R.layout.fragment_basket, container, false)
        root.listCart.setHasFixedSize(true)

        root.listCart.layoutManager = LinearLayoutManager(requireContext().applicationContext)

        root.btnOrder.setOnClickListener {
            totalList()
        }


        return root

    }

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.cartName
        var image = view.imgCart
        var price = view.cartPrice
        var total = view.txtTotal

        var delete = view.deleteCart.setOnClickListener {

        }

    }


    override fun onClickItem(position: Int) {
        var intent = Intent(this.requireActivity(), BasketFragment::class.java)
        intent.putExtra("image", data[position].image)
        intent.putExtra("name", data[position].name)
        intent.putExtra("price", data[position].price)
        intent.putExtra("quantity", data[position].quantity)
        intent.putExtra("discount", data[position].Discount)
        this.requireActivity().startActivity(intent)

    }

    fun totalList() {
        var total = 0
        var order: Order
        var cart = data
        for (order in cart) {
            total += (Integer.parseInt(order.price)) * (Integer.parseInt(order.quantity))
            var local: Locale = Locale("ar")
            var fmt: NumberFormat = NumberFormat.getCurrencyInstance(local)
            txtTotal.setText(fmt.format(total))
        }
    }

}