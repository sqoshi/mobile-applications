package com.app.galleryapp

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.io.File

class ImagesAdapter(fa: FragmentActivity, files: ArrayList<File>) : FragmentStateAdapter(fa) {
    val files = files
    override fun getItemCount(): Int {
        return files.size
    }

    override fun createFragment(position: Int): Fragment {
        return ImageFragment(files[position])
    }


}