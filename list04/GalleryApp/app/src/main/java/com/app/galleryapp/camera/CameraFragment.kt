package com.app.galleryapp.camera

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.app.galleryapp.Constants
import com.app.galleryapp.R
import com.app.galleryapp.database.model.Image
import com.app.galleryapp.database.viewmodel.ImageViewModel
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/**
 * Fragment contain cameraX object that allow to make images and store them in
 * responding path.
 */
class CameraFragment : Fragment() {
    private lateinit var outputDirectory: File
    private lateinit var mImageViewModel: ImageViewModel

    private lateinit var currView: View
    private var imageCapture: ImageCapture? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        currView = inflater.inflate(R.layout.fragment_camera, container, false)
        val stbtn = currView.findViewById<Button>(R.id.shootButton)
        mImageViewModel = ViewModelProvider(this).get(ImageViewModel::class.java)

        outputDirectory = getOutputDirectory()


        if (allPermissionGranted()) {
            startCamera()
//            Toast.makeText(requireContext(), "We have permissions", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                Constants.REQUIRED_PERMISSIONS,
                Constants.REQUEST_CODE_PERMISSIONS
            )
        }


        stbtn.setOnClickListener {
            takePhoto()
        }



        return currView
    }

    /**
     * Restart camera on resume.
     */
    override fun onResume() {
        super.onResume()
        startCamera()
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

    /**
     * Takes photo by clicking button and saving image details in database.
     */
    private fun takePhoto() {

        val imageCapture = imageCapture ?: return
        Log.d(Constants.TAG, outputDirectory.toString())
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                Constants.FILE_NAME_FORMAT,
                Locale.getDefault()
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        val savedUri = Uri.fromFile(photoFile)


        val outputOption = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOption, ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val msg = "Photo saved"
                    Toast.makeText(
                        requireActivity(),
                        "$msg ", Toast.LENGTH_SHORT
                    ).show()

                    // insert image to database
                    val img = Image(
                        0,
                        path = savedUri.path!!,
                        description = null,
                        rating = null
                    )
                    mImageViewModel.addImage(img)

                }

                override fun onError(exception: ImageCaptureException) {
                    Log.d(Constants.TAG, "onError: ${exception.message}", exception)
                }

            }
        )

    }

    /**
     * Ask user for permissions for camera/ external storage.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == Constants.REQUEST_CODE_PERMISSIONS) {
            if (allPermissionGranted()) {
                // todo: sth
            } else {
                Toast.makeText(requireContext(), "Permissions not granted.", Toast.LENGTH_SHORT)
                    .show()
                requireActivity().finish()
            }
        }
    }

    /**
     * Shows camera on, binds lifecycle .
     */
    private fun startCamera() {
        val cameraProviderFeature = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFeature.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFeature.get()
            val preview = Preview.Builder().build().also { mPreview ->
                val item = currView.findViewById<PreviewView>(R.id.cameraPreview)

                mPreview.setSurfaceProvider(
                    item.surfaceProvider
                )
            }
            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)

            } catch (e: Exception) {
                Log.d(Constants.TAG, "startCamera failed")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    /**
     * Checks if current permissions are enough to operate with camera
     */
    private fun allPermissionGranted() =
        Constants.REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                requireActivity().baseContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }

}