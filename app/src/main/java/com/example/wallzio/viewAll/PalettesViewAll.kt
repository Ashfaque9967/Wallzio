package com.example.wallzio.viewAll

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallzio.Adapter.colortoneAdapter
import com.example.wallzio.Model.colortone
import com.example.wallzio.databinding.ActivityPalaettesViewAllBinding
import com.google.firebase.firestore.FirebaseFirestore

class PalettesViewAll : AppCompatActivity() {

    lateinit var bind : ActivityPalaettesViewAllBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        bind = ActivityPalaettesViewAllBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        val db = FirebaseFirestore.getInstance()

        db.collection("colortone").document("Palette001").collection("colors").addSnapshotListener { value, error ->

            val listcolortone = arrayListOf<colortone>()
            val data = value?.toObjects(colortone::class.java)
            listcolortone.addAll(data!!)
            bind.rcvRegular.layoutManager=
                GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false)
            bind.rcvRegular.adapter= colortoneAdapter(this, listcolortone)
        }
        db.collection("colortone").document("Palette002").collection("colors").addSnapshotListener { value, error ->

            val listcolortone = arrayListOf<colortone>()
            val data = value?.toObjects(colortone::class.java)
            listcolortone.addAll(data!!)
            bind.rcvAmerican.layoutManager=
                GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false)
            bind.rcvAmerican.adapter= colortoneAdapter(this, listcolortone)
        }
        db.collection("colortone").document("Palette003").collection("colors").addSnapshotListener { value, error ->

            val listcolortone = arrayListOf<colortone>()
            val data = value?.toObjects(colortone::class.java)
            listcolortone.addAll(data!!)
            bind.rcvAussie.layoutManager=
                GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false)
            bind.rcvAussie.adapter= colortoneAdapter(this, listcolortone)
        }

    }
}