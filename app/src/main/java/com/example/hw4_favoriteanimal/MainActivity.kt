package com.example.hw4_favoriteanimal

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Load the List fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, animalWindowsFragment())
            .addToBackStack(null)
            .commit()


        // If we are in landscape orientation, Load the rate fragment into the rate_container
        // so that both fragments are shown side by side
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.rate_container, rateAnimalFragment())
                .addToBackStack(null)
                .commit()
        }
        Log.d("MainActivity", "main activity in oncreate ")


    }





}





