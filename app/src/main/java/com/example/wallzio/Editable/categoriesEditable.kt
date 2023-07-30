package com.example.wallzio.Editable

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallzio.Adapter.catWallsEditableAdapter
import com.example.wallzio.Model.catWallsModel
import com.example.wallzio.databinding.ActivityCategoriesEditableBinding
import com.google.firebase.firestore.FirebaseFirestore

class categoriesEditable : AppCompatActivity() {

    lateinit var bind : ActivityCategoriesEditableBinding
    lateinit var db : FirebaseFirestore



    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        bind = ActivityCategoriesEditableBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        db = FirebaseFirestore.getInstance()
        val uid = intent.getStringExtra("uidex")
        val name = intent.getStringExtra("nameex")


        db.collection("categories").document(uid!!).collection("wallpapers").addSnapshotListener { value, error ->

            val listOfWallpaper = arrayListOf<catWallsModel>()
            val data = value?.toObjects(catWallsModel::class.java)
            listOfWallpaper.addAll(data!!)

            bind.CatHead.text=name.toString()
            bind.CatwallsAvailable.text="${listOfWallpaper.size} Wallpapers Available"

            bind.rcvCatwalls.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
            bind.rcvCatwalls.adapter = catWallsEditableAdapter(this, listOfWallpaper, uid)

        }

        bind.addbtn.setOnClickListener {

            if(bind.addbtn.text.isEmpty()){
                Toast.makeText(this, "Paste Your Link", Toast.LENGTH_LONG).show()
            }else{
                if (bind.ETinput.text.startsWith("http")) {
                    addLinkToDB(bind.ETinput.text.toString(), uid)
                }
                else{
                    Toast.makeText(this, "Invalid Link!!!", Toast.LENGTH_LONG).show()
                }
            }

        }

    }

    private fun addLinkToDB(wallpaperLink: String, uidex : String) {

        val uid = db.collection("categories").document(uidex!!).collection("wallpapers").document().id
        val finaldata = catWallsModel(uid, wallpaperLink)

        db.collection("categories").document(uidex).collection("wallpapers").document(uid).set(finaldata).addOnCompleteListener{
            if (it.isSuccessful){
                Toast.makeText(this@categoriesEditable, "Wallpaper Added", Toast.LENGTH_LONG).show()
                bind.ETinput.setText("")
                bind.ETinput.clearFocus()
            }else{
                Toast.makeText(this@categoriesEditable, ""+it.exception?.localizedMessage, Toast.LENGTH_LONG).show()
                bind.ETinput.setText("")
                bind.ETinput.clearFocus()
            }
        }

    }

}