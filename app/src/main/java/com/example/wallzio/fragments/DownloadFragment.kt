package com.example.wallzio.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallzio.Adapter.DownloadAdapter
import com.example.wallzio.databinding.FragmentDownloadBinding
import java.io.File

class DownloadFragment : Fragment() {

    lateinit var bind : FragmentDownloadBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind=FragmentDownloadBinding.inflate(layoutInflater, container, false)


        val allFiles:Array<File>
        val imageList = arrayListOf<String>()

        val targetPath = Environment.getExternalStorageDirectory().absolutePath+"/Pictures/Wallzio"

        val targetFile= File(targetPath)
        allFiles = targetFile.listFiles()!!

        for (data in allFiles){
            imageList.add(data.absolutePath)
        }

        bind.availabletext.text = "${imageList.size} Wallpapers Available"


        bind.rcvDownload.layoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL, false)
        bind.rcvDownload.adapter =DownloadAdapter(requireContext(), imageList)

        return bind.root

    }


}