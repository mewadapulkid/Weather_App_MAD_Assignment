package com.pulkid.mad_assignment_2_049
import android.os.Bundle
import android.os.StrictMode
import com.pulkid.mad_assignment_2_049.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.getWeatherButton.setOnClickListener {
            val location = binding.locationEditText.text.toString()
            fetchWeather(location)
        }
    }

    private fun fetchWeather(location: String) {
        val apiKey = "b3798bbdd6e117731b783155edc5b655"
        val apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=$location&appid=$apiKey"

        // Allow network operations on the main thread (not recommended for production).
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())

        val url = URL(apiUrl)
        val connection = url.openConnection() as HttpURLConnection

            val inputStream = connection.inputStream
            val reader = BufferedReader(InputStreamReader(inputStream))
            val response = StringBuilder()
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }
            val jsonResponse = JSONObject(response.toString())
            val temperatureInKelvin = jsonResponse.getJSONObject("main").getDouble("temp")

            // Convert from Kelvin to Celsius
            val temperatureInCelsius = temperatureInKelvin - 273.15
val tempInCelsiusInt= temperatureInCelsius.toInt();
            val description = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description")
            val weatherInfo = "${tempInCelsiusInt}°C"


            binding.weatherDescription.text=description
            binding.locationText.text=location
            binding.temperatureText.text = weatherInfo
            }
    }
