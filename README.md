# Android-to-AWS-IoT-Core

This Project using NodeMCU ESP8266 with Arduino IDE for programming the board. For Application using Android Studio with programming langguage Kotlin. Project using AWS IoT Core to be MQTT Broker for pass data from Board to Application.

[NodeMCU Source Code]((https://github.com/rafeyosa/ESP8266-to-AWS-IoT-Core))

Application using MVVM Pattern with Application using MVVM Pattern with program to **Connect** and **Subscribe** into AWS IoT Core. For additional option to **Publish** you can use this program:

	val topic: String = "topic"
	val msg: String = "message"

	   try {
	       mqttManager.publishString(msg, topic, AWSIotMqttQos.QOS0)
	   } catch (e: Exception) {
	       Log.e(LOG_TAG, "Publish error.", e)
	   }