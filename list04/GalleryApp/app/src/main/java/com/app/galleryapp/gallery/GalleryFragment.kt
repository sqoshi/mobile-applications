package com.app.galleryapp.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.app.galleryapp.Constants
import com.app.galleryapp.R
import com.app.galleryapp.database.viewmodel.ImageViewModel
import kotlinx.android.synthetic.main.fragment_gallery.view.*
import java.io.File


/**
 * Fragment contains collection of short, compressed images.
 */
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

        mImageViewModel.readAllData.observe(viewLifecycleOwner, { imageElement ->
            adapter.setData(imageElement)
        })

        outputDirectory = getOutputDirectory()

        return view
    }

    /**
     * Refresh recycler view items ( after addition, deletion)
     */
    override fun onResume() {
        super.onResume()
        mImageViewModel.readAllData.observe(viewLifecycleOwner, { imageElement ->
            adapter.setData(imageElement)
        })
    }

    /**
     * Assign data to an adapter of recycler view
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mImageViewModel.readAllData.observe(viewLifecycleOwner, { imageElement ->
            adapter.setData(imageElement)
        })

    }

    /**
     * Find output directory path.
     */
    private fun getOutputDirectory(): File {
        val mediaDir = requireActivity().externalMediaDirs.firstOrNull()?.let { mFile ->
            File(mFile, resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else requireActivity().filesDir
    }


}