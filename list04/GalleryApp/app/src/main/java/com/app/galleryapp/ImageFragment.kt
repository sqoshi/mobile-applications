package com.app.galleryapp

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import java.io.File

class ImageFragment(file: File) : Fragment() {
    val file = file

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image, container, false)
//        Constants.verifyStorage(requireActivity())

        val width = Resources.getSystem().displayMetrics.widthPixels
        val height = Resources.getSystem().displayMetrics.heightPixels * 0.8


        val layout = view.findViewById<LinearLayout>(R.id.imageContainer)
        val imageView = ImageView(requireContext())
        imageView.layoutParams = LinearLayout.LayoutParams(width, height.toInt())

        Log.d("FRAGMENT", file.toString())
        Log.d("FRAGMENT", file.absolutePath.toString())
        val myBitmap = BitmapFactory.decodeFile(file.absolutePath)
        imageView.setImageBitmap(myBitmap)

        layout.addView(imageView)


        return view
    }


}