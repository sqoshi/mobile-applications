package com.app.galleryapp.image_inspection

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.app.galleryapp.R
import java.io.File

class InspectionActivity : FragmentActivity() {
    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspection)
        viewPager = findViewById(R.id.inspectionViewPager);
        val index = intent.getIntExtra("index", 0)

        Log.d("INSPECTACT", index.toString())

        viewPager.adapter = ImagesAdapter(this)
        Log.d("INSPECTACTx2", index.toString())

        viewPager.setCurrentItem(index, false)
    }

    override fun onBackPressed() {
        Log.d("backPressed", viewPager.currentItem.toString())
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }
}