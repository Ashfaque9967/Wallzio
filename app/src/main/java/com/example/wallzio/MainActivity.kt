package com.example.wallzio

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.wallzio.databinding.ActivityMainBinding
import com.example.wallzio.fragments.DownloadFragment
import com.example.wallzio.fragments.HomeFragment

class MainActivity : AppCompatActivity() {

    lateinit var bind: ActivityMainBinding

    val READ_EXTERNAL_STORAGE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        bind= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        replaceFragment(HomeFragment())

        bind.honeFrag.setOnClickListener {
            replaceFragment(HomeFragment())
        }

        bind.downloadFrag.setOnClickListener {
            checkForPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE, "Storage", READ_EXTERNAL_STORAGE)
        }
    }

    fun replaceFragment(fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainframe, fragment)
        transaction.commit()
    }

    private fun checkForPermission(permission: String, name : String, requestCode : Int){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            when{
                ContextCompat.checkSelfPermission(applicationContext, permission) == PackageManager.PERMISSION_GRANTED-> {
                    replaceFragment(DownloadFragment())
                }
                shouldShowRequestPermissionRationale(permission)-> showDialog(permission, name, requestCode)

                else-> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        fun innerCheck(name : String){
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext, "$name permission refused", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_SHORT).show()
            }

            when(requestCode){
                READ_EXTERNAL_STORAGE->innerCheck("Storage")
            }
        }
    }

    private fun showDialog(permisssion: String, name: String, requestCode: Int) {

        val builder = AlertDialog.Builder(this)
        builder.apply {
            setMessage("Permission to access your storage is required to manage your downloaded walllpapers")
            setTitle("Permission Required")
            setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->

            })
            setPositiveButton("OK"){ dialog, which->
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permisssion), requestCode)
            }
        }
        val dialog = builder.create()
        dialog.show()
    }
}