package com.app.galleryapp.image_inspection

import android.R.id.text1
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.app.galleryapp.R


class InspectionActivity : FragmentActivity() {
    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_inspection)
        viewPager = findViewById(R.id.inspectionViewPager);
        val index = intent.getIntExtra("index", 0)
        viewPager.adapter = ImagesAdapter(this)
        viewPager.setCurrentItem(index, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("index", intent.getIntExtra("index", 0))
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