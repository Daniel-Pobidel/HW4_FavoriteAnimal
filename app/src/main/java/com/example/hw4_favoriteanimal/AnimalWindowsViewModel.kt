package com.example.hw4_favoriteanimal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AnimalWindowsViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    val animalIndex = MutableLiveData<Int>()
    val ratingValue = MutableLiveData<Double>()
}
