package com.app.galleryapp.gallery

import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.galleryapp.Constants
import com.app.galleryapp.R
import com.app.galleryapp.database.viewmodel.ImageViewModel
import com.app.galleryapp.image_inspection.InspectionActivity
import kotlinx.android.synthetic.main.fragment_gallery.view.*
import java.io.File


class GalleryFragment : Fragment() {
    private lateinit var mImageViewModel: ImageViewModel
    private lateinit var adapter: GalleryAdapter

    private lateinit var outputDirectory: File

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        mImageViewModel = ViewModelProvider(this).get(ImageViewModel::class.java)

        Constants.verifyAll(requireActivity())
        adapter = GalleryAdapter(requireContext())

        val recView = view.recyclerView
        recView.adapter = adapter
        recView.layoutManager = GridLayoutManager(requireContext(), 3)

        mImageViewModel.readAllData.observe(viewLifecycleOwner, { task ->
            adapter.setData(task)
        })
//        insertImagesToGrid(view)

        outputDirectory = getOutputDirectory()
        Log.d("READIMAGES", outputDirectory.toString())

        return view
    }

    override fun onResume() {
        super.onResume()
        mImageViewModel.readAllData.observe(viewLifecycleOwner, { task ->
            adapter.setData(task)
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mImageViewModel.readAllData.observe(viewLifecycleOwner, { task ->
            adapter.setData(task)
        })

    }

//    private fun insertImagesToGrid(view: View) {
//        val grid = view.findViewById<GridLayout>(R.id.gridPreview)
//        val files = readImagesFromStorage()
//        val width = Resources.getSystem().displayMetrics.widthPixels
//
//        files.forEachIndexed { i, imgFile ->
//            val imageView = ImageView(requireContext())
//            val params = LinearLayout.LayoutParams((width - 24) / 3, width / 3 + 110)
//            params.setMargins(4, 4, 4, 4)
//            imageView.layoutParams = params
//
//            if (imgFile.exists()) {
//                val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
//                imageView.setImageBitmap(myBitmap)
//                imageView.tag = i
//            }
//
//            imageView.setOnClickListener {
//                val intent = Intent(view.context, InspectionActivity::class.java)
//                Log.d("INTENT", i.toString())
//                intent.putExtra("index", i)
//                intent.putExtra("files", files)//FIxME:
//                view.context.startActivity(intent);
//            }
//
//            grid.addView(imageView)
//        }
//
//    }


//    private fun readImagesFromStorage(): ArrayList<File> {
//        val gpath: String = Environment.getExternalStorageDirectory().absolutePath
//        val spath = "Android/media/com.app.galleryapp/GalleryApp/"
//        val fullpath = File(gpath + File.separator + spath)
//        Log.d("READIMAGES", fullpath.toString())
//        val list = imageReader(fullpath)
//        Log.d("READIMAGES", list.toString())
//        return list
//    }
//
//    private fun imageReader(root: File): ArrayList<File> {
//        val fileList: ArrayList<File> = ArrayList()
//        val listAllFiles = root.listFiles()
//
//        if (listAllFiles != null && listAllFiles.isNotEmpty()) {
//            for (currentFile in listAllFiles) {
//                if (currentFile.name.endsWith(".jpg")) {
//                    // File absolute path
//                    Log.e("READIMAGES", currentFile.absolutePath)
//                    // File Name
//                    Log.e("READIMAGES", currentFile.name)
//                    fileList.add(currentFile.absoluteFile)
//                }
//            }
//            Log.w("READIMAGES", "" + fileList.size)
//        }
//        return fileList
//    }

    private fun getOutputDirectory(): File {
        val mediaDir = requireActivity().externalMediaDirs.firstOrNull()?.let { mFile ->
            File(mFile, resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else requireActivity().filesDir
    }


}