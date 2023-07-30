package com.example.wallzio.Editable

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallzio.Adapter.bomAdapter
import com.example.wallzio.Adapter.bomEditableAdapter
import com.example.wallzio.Model.bomModel
import com.example.wallzio.R
import com.example.wallzio.databinding.ActivityBomeditableBinding
import com.google.firebase.firestore.FirebaseFirestore

class BOMEditable : AppCompatActivity() {

    lateinit var bind : ActivityBomeditableBinding
    lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        bind = ActivityBomeditableBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        db = FirebaseFirestore.getInstance()

        db.collection("bestofthemonth").addSnapshotListener { value, error ->

            val list_bestofthemonth = arrayListOf<bomModel>()
            val data = value?.toObjects(bomModel::class.java)
            list_bestofthemonth.addAll(data!!)

            bind.BOMAvailable.text = "${list_bestofthemonth.size} Wallpaper Available"

            bind.rcvBOM.layoutManager= GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
            bind.rcvBOM.adapter = bomEditableAdapter(this, list_bestofthemonth)
        }

        bind.addbtn.setOnClickListener {

            if(bind.addbtn.text.isEmpty()){
                Toast.makeText(this, "Paste Your Link", Toast.LENGTH_LONG).show()
            }else{
                if (bind.ETinput.text.startsWith("http")) {
                    addLinkToDB(bind.ETinput.text.toString())
                }
                else{
                    Toast.makeText(this, "Invalid Link!!!", Toast.LENGTH_LONG).show()
                }
            }

        }

    }

    private fun addLinkToDB(wallpaperLink: String) {

        val uid = db.collection("bestofthemonth").document().id
        val finaldata = bomModel(uid, wallpaperLink)

        db.collection("bestofthemonth").document(uid).set(finaldata).addOnCompleteListener{
            if (it.isSuccessful){
                Toast.makeText(this@BOMEditable, "Wallpaper Added", Toast.LENGTH_LONG).show()
                bind.ETinput.setText("")
                bind.ETinput.clearFocus()
            }else{
                Toast.makeText(this@BOMEditable, ""+it.exception?.localizedMessage, Toast.LENGTH_LONG).show()
                bind.ETinput.setText("")
                bind.ETinput.clearFocus()
            }
        }

    }
}