package com.rafeyosa.cubewave.awsiot.data

import android.content.Context
import androidx.lifecycle.ViewModel
import com.rafeyosa.cubewave.awsiot.ui.MainActivity

class PlantViewModel(private val pubSubRepository: PubSubRepository): ViewModel() {

    fun updateDataPlant() = pubSubRepository.getPlants()

    fun startConnection(context: Context) {
        pubSubRepository.awsConnect(context)
    }

    fun finishConnection() {
        pubSubRepository.awsDestroy()
    }


}