package com.example.wallzio.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.wallzio.Categories
import com.example.wallzio.Model.catModel
import com.example.wallzio.R
import com.example.wallzio.Set_Wallpaper

class CategoriesAdapter(val requireContext: Context, val listOfCatgories: ArrayList<catModel>) : RecyclerView.Adapter<CategoriesAdapter.CatViewholder>() {

    inner class CatViewholder(itemView: View) : ViewHolder(itemView){
        val imageView = itemView.findViewById<ImageView>(R.id.image_cat)
        val name = itemView.findViewById<TextView>(R.id.name_cat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewholder {
        return CatViewholder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_categories, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CatViewholder, position: Int) {
        holder.name.text = listOfCatgories[position].name
        Glide.with(requireContext).load(listOfCatgories[position].link).into(holder.imageView)
        holder.itemView.setOnClickListener {
            val intent= Intent(requireContext, Categories::class.java)
            intent.putExtra("uid", listOfCatgories[position].id)
            intent.putExtra("name", listOfCatgories[position].name)
            requireContext.startActivity(intent)
        }

    }

    override fun getItemCount() = listOfCatgories.size
}
