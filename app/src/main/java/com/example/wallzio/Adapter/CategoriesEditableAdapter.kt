package com.example.wallzio.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.wallzio.Categories
import com.example.wallzio.Model.catModel
import com.example.wallzio.R
import com.example.wallzio.Set_Wallpaper
import com.google.firebase.firestore.FirebaseFirestore

class CategoriesEditableAdapter(val requireContext: Context, val listOfCatgories: ArrayList<catModel>) : RecyclerView.Adapter<CategoriesEditableAdapter.CatViewholder>() {

    val db = FirebaseFirestore.getInstance()

    inner class CatViewholder(itemView: View) : ViewHolder(itemView){
        val imageView = itemView.findViewById<ImageView>(R.id.image_cat)
        val name = itemView.findViewById<TextView>(R.id.name_cat)
        val btn_delete = itemView.findViewById<FrameLayout>(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewholder {
        return CatViewholder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_categorieseditable, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CatViewholder, position: Int) {
        holder.name.text = listOfCatgories[position].name
        Glide.with(requireContext).load(listOfCatgories[position].link).into(holder.imageView)

        holder.btn_delete.setOnClickListener {

            if (listOfCatgories.size>9){

                val dialog = AlertDialog.Builder(requireContext)
                dialog.setMessage("Are you sure want to delete!")
                dialog.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->

                    db.collection("categories").document(listOfCatgories[position].id).delete().addOnCompleteListener {

                        if (it.isSuccessful){
                            Toast.makeText(requireContext, "category deleted", Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(requireContext, ""+it.exception?.localizedMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                })
                dialog.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->

                })

                dialog.show()

            }else{

                Toast.makeText(requireContext, "Minimum Deletion Limit Reached!!!", Toast.LENGTH_LONG).show()

            }

        }

//        holder.itemView.setOnClickListener {
//            val intent= Intent(requireContext, Categories::class.java)
//            intent.putExtra("uid", listOfCatgories[position].id)
//            intent.putExtra("name", listOfCatgories[position].name)
//            requireContext.startActivity(intent)
//        }

    }

    override fun getItemCount() = listOfCatgories.size
}
