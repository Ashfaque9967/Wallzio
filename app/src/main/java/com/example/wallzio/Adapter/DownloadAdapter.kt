package com.example.wallzio.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallzio.R
import com.makeramen.roundedimageview.RoundedImageView

class DownloadAdapter(val requireContext: Context, val listOfWallpapers: ArrayList<String>) : RecyclerView.Adapter<DownloadAdapter.bomViewHolder>() {
    inner class bomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView = itemView.findViewById<RoundedImageView>(R.id.catwalls_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bomViewHolder {
        return bomViewHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_cat_walls, parent, false)
        )
    }

    override fun onBindViewHolder(holder: bomViewHolder, position: Int) {

        Glide.with(requireContext).load(listOfWallpapers[position]).into(holder.imageView)
//        holder.itemView.setOnClickListener{
//            val intent= Intent(requireContext, Set_Wallpaper::class.java)
//            intent.putExtra("jpg", listOfWallpapers[position])
//            requireContext.startActivity(intent)
//        }

    }

    override fun getItemCount() = listOfWallpapers.size

}