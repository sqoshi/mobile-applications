package com.app.galleryapp.image_inspection

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.galleryapp.database.viewmodel.ImageViewModel

/**
 * Adapter allow to treat fragments as part of a list ( Viewpager2 reqruired
 */
class ImagesAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    private var mImageViewModel: ImageViewModel = ViewModelProvider(fa).get(ImageViewModel::class.java)

    private var files = mImageViewModel.readImages()

    /**
     * Fragments list size.
     */
    override fun getItemCount(): Int {
        return files?.size!!
    }

    /**
     * Creates single page of viewpager item.
     */
    override fun createFragment(position: Int): Fragment {
        return ImageFragment(files?.get(position)!!)
    }


}