package com.example.wallzio.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wallzio.Adapter.CategoriesAdapter
import com.example.wallzio.Adapter.bomAdapter
import com.example.wallzio.Adapter.colortoneAdapter
import com.example.wallzio.Model.bomModel
import com.example.wallzio.Model.catModel
import com.example.wallzio.Model.colortone
import com.example.wallzio.databinding.FragmentHomeBinding
import com.example.wallzio.viewAll.PalettesViewAll
import com.example.wallzio.viewAll.bestOfTheMonthViewall
import com.example.wallzio.viewAll.categoriesViewAll
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    lateinit var bind : FragmentHomeBinding
    lateinit var db : FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentHomeBinding.inflate(layoutInflater, container, false)


        db = FirebaseFirestore.getInstance()

        bind.btnBOMVA.setOnClickListener {
            val intent = Intent(requireContext(), bestOfTheMonthViewall::class.java)
            startActivity(intent)
        }

        db.collection("bestofthemonth").addSnapshotListener { value, error ->

            val list_bestofthemonth = arrayListOf<bomModel>()
            val data = value?.toObjects(bomModel::class.java)
            list_bestofthemonth.addAll(data!!)

            bind.rcvBom.layoutManager=LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            bind.rcvBom.adapter = bomAdapter(requireContext(), list_bestofthemonth)
        }

        bind.btnTctVA.setOnClickListener {
            val intent = Intent(requireContext(), PalettesViewAll::class.java)
            startActivity(intent)
        }

        db.collection("colortone").document("Palette001").collection("colors").addSnapshotListener { value, error ->

            val listcolortone = arrayListOf<colortone>()
            val data = value?.toObjects(colortone::class.java)
            listcolortone.addAll(data!!)
            bind.rcvTct.layoutManager=GridLayoutManager(requireContext(), 5, GridLayoutManager.VERTICAL, false)
            bind.rcvTct.adapter=colortoneAdapter(requireContext(), listcolortone)
        }

        bind.btnCatVA.setOnClickListener {
            val intent = Intent(requireContext(), categoriesViewAll::class.java)
            startActivity(intent)
        }

        db.collection("categories").addSnapshotListener { value, error ->

            val listOfCatgories = arrayListOf<catModel>()
            val data = value?.toObjects(catModel::class.java)
            listOfCatgories.addAll(data!!)

            bind.rcvCate.layoutManager=GridLayoutManager(requireContext(), 2)
            bind.rcvCate.adapter=CategoriesAdapter(requireContext(), listOfCatgories)
        }

        return bind.root
    }


}