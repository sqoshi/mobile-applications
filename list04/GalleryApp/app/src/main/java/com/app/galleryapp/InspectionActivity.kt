package com.app.galleryapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import java.io.File

class InspectionActivity : FragmentActivity() {
    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspection)
        viewPager = findViewById(R.id.inspectionViewPager);
        val index = intent.getIntExtra("index", 0)
        Log.d("INTENT", index.toString())
        val files = intent.getSerializableExtra("files") as ArrayList<File>


        viewPager.adapter = ImagesAdapter(this, files)

        viewPager.currentItem = index
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