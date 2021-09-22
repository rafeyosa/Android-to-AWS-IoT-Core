package com.rafeyosa.cubewave.awsiot.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rafeyosa.cubewave.awsiot.databinding.ActivityMainBinding
import com.rafeyosa.cubewave.awsiot.data.PlantViewModel
import com.rafeyosa.cubewave.awsiot.R
import com.rafeyosa.cubewave.awsiot.utilities.InjectorUtils
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var plantViewModel: PlantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeUi()
    }

    private fun initializeUi() {
        val factory = InjectorUtils.providePlantsViewModelFactory()
        val viewModel = ViewModelProviders.of(this, factory)
            .get(PlantViewModel::class.java)

        viewModel.startConnection(baseContext)
        binding.btnNext.setOnClickListener{
            val moveIntent = Intent(this@MainActivity, SecondaryActivity::class.java)
            startActivity(moveIntent)
        }
    }

    override fun onDestroy() {
        plantViewModel.finishConnection()
        super.onDestroy()
    }
}