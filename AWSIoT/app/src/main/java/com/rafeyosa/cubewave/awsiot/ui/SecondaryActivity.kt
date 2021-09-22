package com.rafeyosa.cubewave.awsiot.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.rafeyosa.cubewave.awsiot.data.PlantModel
import com.rafeyosa.cubewave.awsiot.data.PlantViewModel
import com.rafeyosa.cubewave.awsiot.databinding.ActivitySecondaryBinding
import com.rafeyosa.cubewave.awsiot.utilities.InjectorUtils

class SecondaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondaryBinding
    private var mId = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeUi()
    }

    private fun initializeUi() {
        val factory = InjectorUtils.providePlantsViewModelFactory()
        val viewModel = ViewModelProviders.of(this, factory)
            .get(PlantViewModel::class.java)

        viewModel.updateDataPlant().observe(this, Observer {
            Log.d("TAG", it.toString())
            showData(it)
        })
    }

    fun showData(data: List<PlantModel>) {
        //Log.d("TAG", "showData: $data")
        data.forEach {
            if(it.id.equals(mId)) {
                binding.tvMessages.text = it.kelembapanTanah
            }
        }
    }
}