package com.example.wallzio.viewAll

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallzio.Adapter.CategoriesAdapter
import com.example.wallzio.Adapter.catWallsAdapter
import com.example.wallzio.Editable.CATEditable
import com.example.wallzio.Model.catModel
import com.example.wallzio.Model.catWallsModel
import com.example.wallzio.R
import com.example.wallzio.databinding.ActivityCategoriesViewAllBinding
import com.google.firebase.firestore.FirebaseFirestore

class categoriesViewAll : AppCompatActivity() {

    lateinit var bind : ActivityCategoriesViewAllBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        bind = ActivityCategoriesViewAllBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        val db = FirebaseFirestore.getInstance()

        bind.add.setOnClickListener {

            val intent = Intent(this, CATEditable::class.java)
            startActivity(intent)

        }

        db.collection("categories").addSnapshotListener { value, error ->

            val listOfCatgories = arrayListOf<catModel>()
            val data = value?.toObjects(catModel::class.java)
            listOfCatgories.addAll(data!!)

            bind.CatVAAvailable.text = "${listOfCatgories.size} CAtegories Available"

            bind.rcvCatVA.layoutManager=GridLayoutManager(this, 2)
            bind.rcvCatVA.adapter= CategoriesAdapter(this, listOfCatgories)
        }

    }
}