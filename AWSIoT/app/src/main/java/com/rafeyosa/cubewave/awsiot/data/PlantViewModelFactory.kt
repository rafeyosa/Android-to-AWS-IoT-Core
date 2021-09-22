package com.rafeyosa.cubewave.awsiot.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlantViewModelFactory(private val pubSubRepository: PubSubRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlantViewModel(pubSubRepository) as T
    }
}