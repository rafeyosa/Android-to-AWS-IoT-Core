package com.rafeyosa.cubewave.awsiot.data

class PlantDatabase private constructor() {
    var quoteDao = PlantDAO()
        private set

    companion object {
        @Volatile private var instance: PlantDatabase? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: PlantDatabase().also { instance = it }
            }
    }
}