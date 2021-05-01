package com.app.galleryapp

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.io.File


class GalleryFragment : Fragment() {

    private lateinit var outputDirectory: File

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        readImagesFromStorage()
        outputDirectory = getOutputDirectory()
        Log.d("READIMAGES", outputDirectory.toString())
        return view
    }

    fun readImagesFromStorage() {
        val gpath: String = Environment.getExternalStorageDirectory().absolutePath
        val spath = "Android/media/com.app.galleryapp/GalleryApp"
        val fullpath = File(gpath + File.separator + spath)
        Log.d("READIMAGES", fullpath.toString())
        val list = imageReader(fullpath)
        Log.d("READIMAGES", list.toString())
    }

    fun imageReader(root: File) {
        val fileList: ArrayList<File> = ArrayList()
        val listAllFiles = root.listFiles()

        if (listAllFiles != null && listAllFiles.isNotEmpty()) {
            for (currentFile in listAllFiles) {
                if (currentFile.name.endsWith(".jpeg")) {
                    // File absolute path
                    Log.e("downloadFilePath", currentFile.absolutePath)
                    // File Name
                    Log.e("downloadFileName", currentFile.name)
                    fileList.add(currentFile.absoluteFile)
                }
            }
            Log.w("fileList", "" + fileList.size)
        }
    }

    private fun getOutputDirectory(): File {
        val mediaDir = requireActivity().externalMediaDirs.firstOrNull()?.let { mFile ->
            File(mFile, resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else requireActivity().filesDir
    }


}