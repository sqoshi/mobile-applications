package com.app.galleryapp.image_inspection

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.galleryapp.R
import com.app.galleryapp.database.model.Image
import com.app.galleryapp.database.viewmodel.ImageViewModel
import kotlinx.android.synthetic.main.fragment_image.view.*


class ImageFragment(val image: Image) : Fragment() {
    private lateinit var mImageViewModel: ImageViewModel

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
//
//    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image, container, false)
        mImageViewModel = ViewModelProvider(this).get(ImageViewModel::class.java)

        val layout = view.findViewById<LinearLayout>(R.id.imageContainer)
        val imageView = ImageView(requireContext())
        imageView.layoutParams = LinearLayout.LayoutParams(
            (Resources.getSystem().displayMetrics.widthPixels * 0.7).toInt(),
            (Resources.getSystem().displayMetrics.heightPixels * 0.7).toInt()
        )

        view.updateButton.setOnClickListener {
            updateImage()
        }

        val myBitmap = BitmapFactory.decodeFile(image.path)
        imageView.setImageBitmap(myBitmap)
        layout.addView(imageView)
        if (image.rating != null)
            view.ratingBar.rating = image.rating
        if (image.description != null)
            view.descriptionText.setText(image.description)


        return view
    }

    private fun updateImage() {
        val desc = view?.descriptionText?.text.toString()
        val rating = view?.ratingBar?.rating
        val img = Image(image.id, image.path, desc, rating)
        mImageViewModel.updateImage(img)
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.back_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }


}