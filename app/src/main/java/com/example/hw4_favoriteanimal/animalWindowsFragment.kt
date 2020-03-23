package com.example.hw4_favoriteanimal

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.res.Configuration
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.animal_windows_fragment.*
import kotlinx.android.synthetic.main.animal_windows_fragment.view.*
import java.util.zip.Inflater


class animalWindowsFragment : Fragment() {

    private val TAG = "MainActivity"
    private val FILE_NAME = "ratingsList"
    //private val ratingsList = ArrayList<String>()
    //lateinit var myAdapter: ArrayAdapter<String>
    private val MY_KEY = "ratingsList"


    private lateinit var viewModel: AnimalWindowsViewModel
    var animalRatings = mutableListOf(0.0,0.0,0.0,0.0)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.animal_windows_fragment, container, false)
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(AnimalWindowsViewModel::class.java)


        var index = 0 //default

        view.dogButton.setOnClickListener {
            viewModel.animalIndex.value = 0
            changeOrientation()
        }

        view.catButton.setOnClickListener {
            viewModel.animalIndex.value = 1
            changeOrientation()
        }

        view.bearButton.setOnClickListener {
            viewModel.animalIndex.value = 2
            changeOrientation()
        }
        view.rabbitButton.setOnClickListener {
            viewModel.animalIndex.value = 3
            changeOrientation()
        }

        var rating = 0.0 //default


        viewModel.ratingValue.observe(requireActivity(), Observer {
            rating = it
        })

        viewModel.animalIndex.observe(requireActivity(), Observer {
           index = it
        })


        load()
        animalRatings[index] = rating

        save()

        view.dogRating.text = ("Rating: ${animalRatings[0]}")
        view.catRating.text = ("Rating: ${animalRatings[1]}")
        view.bearRating.text = ("Rating: ${animalRatings[2]}")
        view.rabbitRating.text = ("Rating: ${animalRatings[3]}")

        return view
    }



    fun changeOrientation()
    {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            // We are in portrait orientation
            // Load rate fragment
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, rateAnimalFragment())
                .addToBackStack(null)
                .commit()
        }
        else{
            // We are in landscape orientation
            // replace the current rate fragment
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.rate_container,rateAnimalFragment())
                .addToBackStack(null)
                .commit()
        }
    }


    fun save() {
        // Create an instance of getSharedPreferences for edit
        val sharedPreferences = context?.getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        val editor = sharedPreferences?.edit()

        // Create an instance of Gson
        val gson = Gson()

        // toJson() method serializes the specified object into its equivalent Json representation.
        val ratingsListJson = gson.toJson(animalRatings)

        // Put the  Json representation, which is a string, into sharedPreferences
        editor?.putString(MY_KEY, ratingsListJson)

        editor?.apply() // Apply the changes

    }

    fun load() {
        // Create an instance of getSharedPreferences for retrieve the data
        val sharedPreferences = activity?.getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        // Retrieve data using the key, default value is empty string in case no saved data in there
        val tasks = sharedPreferences?.getString(MY_KEY, "") ?: ""

        if (tasks.isNotEmpty()) {
            val gson = Gson()
            // create an object expression that descends from TypeToken
            // and then get the Java Type from that
            val sType = object : TypeToken<List<Double>>() {}.type
            val savedTaskList = gson.fromJson<List<Double>>(tasks, sType)

            Log.d(TAG, savedTaskList.toString())
            animalRatings.addAll(savedTaskList)

        }
    }



}













