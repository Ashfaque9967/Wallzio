package com.example.wallzio.Adapter

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.wallzio.Model.colortone
import com.example.wallzio.R
import com.example.wallzio.Set_Wallpaper
import org.w3c.dom.Text

class colortoneAdapter(val requireContext: Context,  val listcolortone: ArrayList<colortone>) : RecyclerView.Adapter<colortoneAdapter.colortoneViewHolder>() {

    inner class colortoneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val cardView = itemView.findViewById<LinearLayout>(R.id.itemCard)
        val nametxt = itemView.findViewById<TextView>(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): colortoneViewHolder {
        return colortoneViewHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.items_colortone, parent, false)
        )
    }

    override fun onBindViewHolder(holder: colortoneViewHolder, position: Int) {

        val color = listcolortone[position].color
        holder.cardView.setBackgroundColor(Color.parseColor(color!!))

        holder.nametxt.text = listcolortone[position].name


        holder.itemView.setOnClickListener{
            val intent = Intent(requireContext, Set_Wallpaper::class.java)
            intent.putExtra("link", listcolortone[position].link)
            intent.putExtra("color", listcolortone[position].color)
            requireContext.startActivity(intent)
        }

    }

    override fun getItemCount() = listcolortone.size
}
