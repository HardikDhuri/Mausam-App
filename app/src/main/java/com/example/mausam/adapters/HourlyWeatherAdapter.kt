package com.example.mausam.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mausam.R
import com.example.mausam.data.WeatherPerHour
import com.example.mausam.others.Contants
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.hourly_weather.view.*

class HourlyWeatherAdapter(
    private var hourlyWeatherList: List<WeatherPerHour>
) : RecyclerView.Adapter<HourlyWeatherAdapter.WeatherViewHolder>() {
    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hourly_weather, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.itemView.apply {
            Glide.with(context)
                .load("${Contants.IMAGE_URL}/img/wn/${hourlyWeatherList[position].weather_img}@2x.png")
                .into(this.hourly_weather_img_view)
            hourly_weather_time.text = hourlyWeatherList[position].time
            hourly_weather_temperature.text = hourlyWeatherList[position].temperature
        }
    }

    override fun getItemCount(): Int {
        return hourlyWeatherList.size
    }
}