package com.rafeyosa.cubewave.awsiot.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PlantDAO {
    private val plantList = mutableListOf<PlantModel>()
    private val plants = MutableLiveData<List<PlantModel>>()

    init {
        plants.value = plantList
    }

    fun clearPlants() {
        plantList.clear()
    }

    fun addPlant(plant: PlantModel) {
        plantList.add(plant)
        plants.value = plantList
    }
    fun getPlants() = plants as LiveData<List<PlantModel>>
}
