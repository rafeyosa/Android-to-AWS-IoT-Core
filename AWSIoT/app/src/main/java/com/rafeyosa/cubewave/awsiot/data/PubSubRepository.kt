package com.rafeyosa.cubewave.awsiot.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos
import com.amazonaws.regions.Regions
import java.io.UnsupportedEncodingException
import java.lang.Exception
import java.nio.charset.StandardCharsets
import java.util.*
import com.google.gson.Gson
import com.rafeyosa.cubewave.awsiot.ui.MainActivity

class PubSubRepository private constructor(private val plantDao: PlantDAO) {
    private val CUSTOMER_SPECIFIC_ENDPOINT: String = "XXXXX-ats.iot.us-east-1.amazonaws.com"
    private val COGNITO_POOL_ID = "us-east-1:XXXXX"
    private val MY_REGION = Regions.US_EAST_1

    private lateinit var mqttManager: AWSIotMqttManager
    private lateinit var clientId: String
    private lateinit var credentialsProvider: CognitoCachingCredentialsProvider

    private lateinit var mContext: Context
    val LOG_TAG = MainActivity::class.java.canonicalName

    fun awsConnect(context: Context) {
        this.clientId = UUID.randomUUID().toString()
        this.mContext = context

        credentialsProvider = CognitoCachingCredentialsProvider(
            context,
            COGNITO_POOL_ID,
            MY_REGION
        )

        mqttManager = AWSIotMqttManager(clientId, CUSTOMER_SPECIFIC_ENDPOINT)

        Thread { runOnUiThread { awsTryConnect() } }.start()
    }

    fun awsDestroy() {
        try {
            mqttManager.disconnect()
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Disconnect error.", e)
        }
    }

    private fun awsTryConnect() {
        Log.d(LOG_TAG, "clientId = $clientId")

        try {
            mqttManager.connect(
                credentialsProvider
            ) { status, throwable ->
                Log.d(LOG_TAG, "Status = $status")
                runOnUiThread {
                    if (status == AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Connecting) {
                        Log.d(LOG_TAG, "Connecting...")
                    } else if (status == AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Connected) {
                        Log.d(LOG_TAG, "Connected")
                        showSubscribeData()
                    } else if (status == AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Reconnecting) {
                        if (throwable != null) {
                            Log.e(LOG_TAG, "Connection error.", throwable)
                        }
                        Log.d(LOG_TAG, "Reconnecting")
                    } else if (status == AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.ConnectionLost) {
                        if (throwable != null) {
                            Log.e(LOG_TAG, "Connection error.", throwable)
                            throwable.printStackTrace()
                        }
                        Log.d(LOG_TAG, "Disconnected")
                    } else {
                        Log.d(LOG_TAG, "Disconnected")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Connection error.", e)
            Toast.makeText(mContext,"Error! " + e.message ,Toast.LENGTH_SHORT)
        }
    }

    private fun showSubscribeData() {
        val topic1: String = "outTopic"

        Log.d(LOG_TAG, "topic = $topic1")

        try {
            mqttManager.subscribeToTopic(
                topic1, AWSIotMqttQos.QOS0
            ) { topic2, data ->
                runOnUiThread {
                    try {
                        val message = String(data, StandardCharsets.UTF_8)
                        Log.d(LOG_TAG, "Message arrived:")
                        Log.d(LOG_TAG, "   Topic: $topic2")
                        Log.d(LOG_TAG, " Message: $message")

                        val gson = Gson()
                        val responseModel: ResponseModel =
                            gson.fromJson(message, ResponseModel::class.java)

                        clearPlants()

                        //responseModel.data[0].id
                        responseModel.data.forEach {
                            addPlant(it)
                        }

                    } catch (e: UnsupportedEncodingException) {
                        Log.e(LOG_TAG, "Message encoding error.", e)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Subscription error.", e)
        }
    }

    private fun clearPlants() {
        plantDao.clearPlants()
    }

    private fun addPlant(plant: PlantModel) {
        plantDao.addPlant(plant)
    }
    fun getPlants() = plantDao.getPlants()

    companion object {
        // Singleton instantiation you already know and love
        @Volatile private var instance: PubSubRepository? = null

        fun getInstance(quoteDao: PlantDAO) =
            instance ?: synchronized(this) {
                instance ?: PubSubRepository(quoteDao).also { instance = it }
            }
    }
}