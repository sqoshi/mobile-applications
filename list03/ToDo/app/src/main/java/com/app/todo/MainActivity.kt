package com.app.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBarWithNavController(findNavController(R.id.fragment))
    }


    override fun onSupportNavigateUp(): Boolean {
        val navC = findNavController(R.id.fragment)
        return navC.navigateUp() || super.onSupportNavigateUp()
    }

}