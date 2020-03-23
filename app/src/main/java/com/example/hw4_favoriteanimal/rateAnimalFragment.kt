package com.example.hw4_favoriteanimal

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.animal_windows_fragment.view.*
import kotlinx.android.synthetic.main.fragment_rate_animal.*
import kotlinx.android.synthetic.main.fragment_rate_animal.view.*


/**
 * A simple [Fragment] subclass.
 */
class rateAnimalFragment: Fragment() {

    private val TAG = "rateAnimalsFragment"

    private val ANIMAL_NAME_LIST = listOf("Dog", "Cat", "Bear", "Rabbit")


    // Create an instance of our ViewModel
    lateinit var viewModel: AnimalWindowsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_rate_animal, container, false)


        //ViewModelProvider returns an existing ViewModel if one exists,
        // or it creates a new one if it does not already exist.
        // Get an instance of our ViewModel by passing context and AnimalWindowsViewModel class,
        // then put the value (position) in liveData
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(AnimalWindowsViewModel::class.java)

        var position = 0 // default value to select the first index if no position

        // this observer will be updated when the value of position changes
        viewModel.animalIndex.observe(requireActivity(), Observer {
            Log.d(TAG, "position: $it")
            position = it
        })

        // Set the text to textviews after getting the selected position
        view.animalTitleText.text = ANIMAL_NAME_LIST[position]

        // Based on the index of position selected, set the corresponding image
        val imageId = when (position) {
            0 -> R.drawable.dog
            1 -> R.drawable.cat
            2 -> R.drawable.bear
            else -> R.drawable.rabbit
        }

        view.animalImage.setImageResource(imageId)


        view.saveButton.setOnClickListener {
            Toast.makeText(activity, "Rating: ${ratingBar.rating}", Toast.LENGTH_SHORT).show()
            viewModel.ratingValue.value = ratingBar.rating.toDouble()

            // Create a SharedPreferences instance for edit
            val sharedPreferences = activity!!.getSharedPreferences("ratings",MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            // save and apply changes
            editor.putString(ANIMAL_NAME_LIST[position], ratingBar.rating.toString())
            editor.apply()


            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                // We are in portrait orientation
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, animalWindowsFragment())
                    .addToBackStack(null)
                    .commit()
            }
            else{
                // We are in landscape orientation
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container,animalWindowsFragment())
                    .addToBackStack(null)
                    .commit()
            }

        }
        return view
    }



}



