package com.example.wallzio.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallzio.Model.catWallsModel
import com.example.wallzio.R
import com.google.firebase.firestore.FirebaseFirestore
import com.makeramen.roundedimageview.RoundedImageView

class catWallsEditableAdapter(
    val requireContext: Context,
    val listOfWallpapers: ArrayList<catWallsModel>,
    val uid: String
) : RecyclerView.Adapter<catWallsEditableAdapter.bomViewHolder>() {

    val db = FirebaseFirestore.getInstance()

    inner class bomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView = itemView.findViewById<RoundedImageView>(R.id.catwalls_item)
        val btn_delete = itemView.findViewById<FrameLayout>(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bomViewHolder {
        return bomViewHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_bomeditable, parent, false)
        )
    }

    override fun onBindViewHolder(holder: bomViewHolder, position: Int) {

        Glide.with(requireContext).load(listOfWallpapers[position].link).into(holder.imageView)

        holder.btn_delete.setOnClickListener {

            if (listOfWallpapers.size>31){

                val dialog = AlertDialog.Builder(requireContext)
                dialog.setMessage("Are you sure want to delete!")
                dialog.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->

                    db.collection("categories").document(uid).collection("wallpapers").document(listOfWallpapers[position].id).delete().addOnCompleteListener {

                        if (it.isSuccessful){
                            Toast.makeText(requireContext, "Wallpaper deleted", Toast.LENGTH_LONG).show()
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

//        holder.itemView.setOnClickListener{
//            val intent= Intent(requireContext, Set_Wallpaper::class.java)
//            intent.putExtra("link", listOfWallpapers[position].link)
//            requireContext.startActivity(intent)
//        }
    }

    override fun getItemCount() = listOfWallpapers.size

}