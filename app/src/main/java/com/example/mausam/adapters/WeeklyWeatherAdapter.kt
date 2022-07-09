package com.example.mausam.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mausam.R
import com.example.mausam.data.WeatherPerDay
import com.example.mausam.others.Contants
import kotlinx.android.synthetic.main.hourly_weather.view.*
import kotlinx.android.synthetic.main.weekly_weather.view.*

class WeeklyWeatherAdapter(
    private var weeklyWeatherList: List<WeatherPerDay>
) : RecyclerView.Adapter<WeeklyWeatherAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyWeatherAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.weekly_weather, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeeklyWeatherAdapter.ViewHolder, position: Int) {
        holder.itemView.apply {
            Glide.with(context)
                .load("${Contants.IMAGE_URL}/img/wn/${weeklyWeatherList[position].weatherImg}@2x.png")
                .into(this.weekly_weather_img_view)
            weekly_weather_temperature.text = weeklyWeatherList[position].temperature
            weeky_weather_date.text = weeklyWeatherList[position].dateShort
            weeky_weather_weekday.text = weeklyWeatherList[position].weekDay
        }
    }

    override fun getItemCount(): Int {
        return weeklyWeatherList.size
    }
}