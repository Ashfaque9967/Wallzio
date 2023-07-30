package com.example.wallzio.Editable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallzio.Adapter.CategoriesAdapter
import com.example.wallzio.Adapter.CategoriesEditableAdapter
import com.example.wallzio.Model.bomModel
import com.example.wallzio.Model.catModel
import com.example.wallzio.R
import com.example.wallzio.databinding.ActivityCateditableBinding
import com.google.firebase.firestore.FirebaseFirestore

class CATEditable : AppCompatActivity() {

    lateinit var bind : ActivityCateditableBinding
    lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        bind = ActivityCateditableBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        db = FirebaseFirestore.getInstance()

        db.collection("categories").addSnapshotListener { value, error ->

            val listOfCatgories = arrayListOf<catModel>()
            val data = value?.toObjects(catModel::class.java)
            listOfCatgories.addAll(data!!)

            bind.CatVAAvailable.text = "${listOfCatgories.size} CAtegories Available"

            bind.rcvCatVA.layoutManager= GridLayoutManager(this, 2)
            bind.rcvCatVA.adapter= CategoriesEditableAdapter(this, listOfCatgories)
        }

        bind.addbtn.setOnClickListener {

            if(bind.addbtn.text.isEmpty()){
                Toast.makeText(this, "Paste Your Link", Toast.LENGTH_LONG).show()
            }else{
                if (bind.ETbackofcat.text.startsWith("http")){
                    addLinkToDB(bind.ETbackofcat.text.toString(), bind.ETnameofcat.text.toString())
                }else{
                    Toast.makeText(this, "Invalid Link!!!", Toast.LENGTH_LONG).show()
                }
            }

        }

    }


    private fun addLinkToDB(bglink: String, name: String) {

        val uid = db.collection("categories").document().id
        val finaldata = catModel(uid, bglink, name)

        db.collection("categories").document(uid).set(finaldata).addOnCompleteListener{
            if (it.isSuccessful){
                Toast.makeText(this@CATEditable, "Category Added", Toast.LENGTH_LONG).show()
                bind.ETnameofcat.setText("")
                bind.ETbackofcat.setText("")
                bind.ETnameofcat.clearFocus()
                bind.ETbackofcat.clearFocus()
            }else{
                Toast.makeText(this@CATEditable, ""+it.exception?.localizedMessage, Toast.LENGTH_LONG).show()
                bind.ETnameofcat.setText("")
                bind.ETbackofcat.setText("")
                bind.ETnameofcat.clearFocus()
                bind.ETbackofcat.clearFocus()
            }
        }

    }

}