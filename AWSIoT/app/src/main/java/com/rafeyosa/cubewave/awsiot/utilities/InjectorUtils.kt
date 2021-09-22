package com.rafeyosa.cubewave.awsiot.utilities

import com.rafeyosa.cubewave.awsiot.data.PlantDatabase
import com.rafeyosa.cubewave.awsiot.data.PlantViewModelFactory
import com.rafeyosa.cubewave.awsiot.data.PubSubRepository

object InjectorUtils {
    fun providePlantsViewModelFactory(): PlantViewModelFactory {
        val quoteRepository = PubSubRepository.getInstance(PlantDatabase.getInstance().quoteDao)
        return PlantViewModelFactory(quoteRepository)
    }
}