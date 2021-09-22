package com.rafeyosa.cubewave.awsiot.data

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class ResponseModel {
    @SerializedName("data")
    var data: List<PlantModel> = arrayListOf<PlantModel>()

    companion object {
        fun fromJson(json: String?): ResponseModel {
            return Gson().fromJson(json, ResponseModel::class.java)
        }
    }
}