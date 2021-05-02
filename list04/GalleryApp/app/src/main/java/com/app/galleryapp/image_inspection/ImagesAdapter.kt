package com.app.galleryapp.image_inspection

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.galleryapp.database.viewmodel.ImageViewModel

class ImagesAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    private var mImageViewModel: ImageViewModel = ViewModelProvider(fa).get(ImageViewModel::class.java)

    private var files = mImageViewModel.readImages()

    override fun getItemCount(): Int {
        return files?.size!!
    }

    override fun createFragment(position: Int): Fragment {
        return ImageFragment(files?.get(position)!!)
    }


}