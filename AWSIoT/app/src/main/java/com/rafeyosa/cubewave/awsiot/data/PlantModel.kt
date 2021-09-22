package com.rafeyosa.cubewave.awsiot.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlantModel(
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("suhu")
    @Expose
    var suhu: String,
    @SerializedName("kelembapan_tanah")
    @Expose
    var kelembapanTanah: String,
    @SerializedName("status_penyiraman")
    @Expose
    var statusPenyiraman: String
): Parcelable