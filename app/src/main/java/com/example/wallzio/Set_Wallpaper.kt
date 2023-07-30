package com.example.wallzio

import android.app.WallpaperManager
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.wallzio.databinding.ActivitySetWallpaperBinding
import com.google.android.engage.common.datamodel.Image
import com.google.firebase.inject.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception
import java.net.URL
import java.util.Objects
import kotlin.random.Random
import kotlin.random.nextInt
import java.io.OutputStream as OutputStream

@Suppress("UNNECESSARY_SAFE_CALL")
class Set_Wallpaper : AppCompatActivity() {
    lateinit var bind :ActivitySetWallpaperBinding
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        bind = ActivitySetWallpaperBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(bind.root)

        val url = intent.getStringExtra("link")

        val urlImage = URL(url)
        Glide.with(this).load(url).into(bind.Wallpaper)

        bind.btndownload.setOnClickListener{

            val result : kotlinx.coroutines.Deferred<Bitmap?> = GlobalScope.async {
                urlImage.toBitmap()
            }

            GlobalScope.launch(Dispatchers.Main) {
                saveImage(result.await())
            }


        }
        bind.btnsetwallpaper.setOnClickListener{

            val result : kotlinx.coroutines.Deferred<Bitmap?> = GlobalScope.async {
                urlImage.toBitmap()
            }

            GlobalScope.launch (Dispatchers.Main){

                val WallpaperManager = WallpaperManager.getInstance(applicationContext)
                WallpaperManager.setBitmap(result.await())

            }
            Toast.makeText(this, "Wallpaper updated", Toast.LENGTH_SHORT).show()

        }
    }

    fun URL.toBitmap(): Bitmap?{
        return try {
            BitmapFactory.decodeStream(openStream())
        }catch (e :Exception){
            null
        }
    }
    private fun saveImage(image : Bitmap?){


        val random1 = Random.nextInt(0, 12345)
        val random2 = Random.nextInt(0, 12345)

        val name = "Wallzio-${random1+random2}"

        val data: OutputStream
        try {
            val resolver = contentResolver
            val contentValue = ContentValues()
            contentValue.put(MediaStore.MediaColumns.DISPLAY_NAME, "$name.jpg")
            contentValue.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            contentValue.put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + File.separator + "Wallzio"
            )
            val imageuri =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValue)
            data = Objects.requireNonNull(imageuri)?.let { resolver.openOutputStream(it) }!!
            image?.compress(Bitmap.CompressFormat.JPEG, 100, data)
            Objects.requireNonNull<OutputStream>(data)
            Toast.makeText(this, "Image Saved", Toast.LENGTH_SHORT).show()

        }catch (e :Exception){
           Toast.makeText(this, "Image not Saved", Toast.LENGTH_SHORT).show()
        }

    }

}