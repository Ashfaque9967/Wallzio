package com.example.wallzio.viewAll

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallzio.Adapter.DownloadAdapter
import com.example.wallzio.Adapter.bomAdapter
import com.example.wallzio.Editable.BOMEditable
import com.example.wallzio.Model.bomModel
import com.example.wallzio.databinding.ActivityBestOfTheMonthViewallBinding
import com.google.firebase.firestore.FirebaseFirestore

class bestOfTheMonthViewall : AppCompatActivity() {

    lateinit var bind : ActivityBestOfTheMonthViewallBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        bind = ActivityBestOfTheMonthViewallBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        bind.add.setOnClickListener {

            val intent = Intent(this, BOMEditable::class.java)
            startActivity(intent)

        }

        val db = FirebaseFirestore.getInstance()

        db.collection("bestofthemonth").addSnapshotListener { value, error ->

            val list_bestofthemonth = arrayListOf<bomModel>()
            val data = value?.toObjects(bomModel::class.java)
            list_bestofthemonth.addAll(data!!)

            bind.BOMAvailable.text = "${list_bestofthemonth.size} Wallpaper Available"

            bind.rcvBOM.layoutManager=GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
            bind.rcvBOM.adapter = bomAdapter(this, list_bestofthemonth)
        }
    }
}