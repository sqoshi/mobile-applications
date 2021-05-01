package com.app.galleryapp

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.app.galleryapp.databinding.FragmentCameraBinding
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class CameraFragment : Fragment() {
    private lateinit var outputDirectory: File
    private lateinit var binding: FragmentCameraBinding
    private var imageCapture: ImageCapture? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val currView = inflater.inflate(R.layout.fragment_camera, container, false)
        binding = FragmentCameraBinding.inflate(layoutInflater)
        requireActivity().setContentView(binding.root)

        outputDirectory = getOutputDirectory()


        // handle camera
        if (allPermissionGranted()) {
            startCamera()
            Toast.makeText(requireContext(), "We have permissions", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                Constants.REQUIRED_PERMISSIONS,
                Constants.REQUEST_CODE_PERMISSIONS
            )
        }


        binding.shootButton.setOnClickListener {
            takePhoto()
        }

        return currView
    }

    private fun getOutputDirectory(): File {
        val mediaDir = requireActivity().externalMediaDirs.firstOrNull()?.let { mFile ->
            File(mFile, resources.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else requireActivity().filesDir
    }

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

        val outputOption = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOption, ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo saved"
                    Toast.makeText(
                        requireActivity(),
                        "$msg $savedUri", Toast.LENGTH_SHORT
                    ).show()

                }

                override fun onError(exception: ImageCaptureException) {
                    Log.d(Constants.TAG, "onError: ${exception.message}", exception)
                }

            }
        )

    }

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

    private fun startCamera() {
        val cameraProviderFeature = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFeature.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFeature.get()
            val preview = Preview.Builder().build().also { mPreview ->
                mPreview.setSurfaceProvider(
                    binding.cameraPreview.surfaceProvider
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

    private fun allPermissionGranted() =
        Constants.REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                requireActivity().baseContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }

}