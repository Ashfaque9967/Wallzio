package com.example.wallzio

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.GridLayoutAnimationController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wallzio.Adapter.catWallsAdapter
import com.example.wallzio.Editable.BOMEditable
import com.example.wallzio.Editable.categoriesEditable
import com.example.wallzio.Model.catWallsModel
import com.example.wallzio.databinding.ActivityCategoriesBinding
import com.google.firebase.firestore.FirebaseFirestore

class Categories : AppCompatActivity() {
    lateinit var bind : ActivityCategoriesBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        bind = ActivityCategoriesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        val db = FirebaseFirestore.getInstance()
        val uid = intent.getStringExtra("uid")
        val name = intent.getStringExtra("name")


        bind.add.setOnClickListener {

            val intent = Intent(this, categoriesEditable::class.java)
            intent.putExtra("uidex", uid)
            intent.putExtra("nameex", name)
            startActivity(intent)

        }


        db.collection("categories").document(uid!!).collection("wallpapers").addSnapshotListener { value, error ->

            val listOfWallpaper = arrayListOf<catWallsModel>()
            val data = value?.toObjects(catWallsModel::class.java)
            listOfWallpaper.addAll(data!!)

            bind.CatHead.text=name.toString()
            bind.CatwallsAvailable.text="${listOfWallpaper.size} Wallpapers Available"

            bind.rcvCatwalls.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
            bind.rcvCatwalls.adapter = catWallsAdapter(this, listOfWallpaper)

        }
    }
}