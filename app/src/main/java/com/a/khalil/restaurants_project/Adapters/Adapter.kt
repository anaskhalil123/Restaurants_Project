package com.a.khalil.restaurants_project.Adapters


import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.khalil.restaurants_project.Customer.MealsActivity
import com.a.khalil.restaurants_project.R
import com.a.khalil.restaurants_project.models.Meal
//import com.example.navitaionresturant.MealsActivity
//import com.example.navitaionresturant.R
//
//import com.example.navitaionresturant.model.Meal
import kotlinx.android.synthetic.main.rest_item.view.*

class Adapter(var activity: Activity, var data: ArrayList<Meal>, var Click: MealsActivity)
    :RecyclerView.Adapter<Adapter.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image = itemView.restaurant_image
        val title = itemView.restaurant_title
        var card = itemView.cardview
        var saved:Boolean = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.MyViewHolder {
        val root = LayoutInflater.from(activity).inflate(R.layout.rest_item,parent,false)
        return MyViewHolder(root)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.image.setImageResource(data[position].image)
        holder.title.text = data[position].title
        holder.card.setOnClickListener{
            Click.onClickItem(holder.adapterPosition)

        }

    }

    override fun getItemCount(): Int {
        return  data.size
    }


interface onClick{
    fun onClickItem(position: Int)
}

}