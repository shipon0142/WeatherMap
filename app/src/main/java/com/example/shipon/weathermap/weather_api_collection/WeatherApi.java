package com.example.shipon.weathermap.weather_api_collection;

import com.example.shipon.weathermap.forecastweather_models.ForecastWeather;
import com.example.shipon.weathermap.models.CurrentWeather;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Shipon on 9/5/2018.
 */

public interface WeatherApi {
    @GET("weather?q=dhaka,bangladesh&appid=b2ed8cc71534bb766df35dbf3bbc0056")
    Call<CurrentWeather> getCurrentWeather();
    @GET("forecast?q=dhaka,bangladesh&appid=b2ed8cc71534bb766df35dbf3bbc0056")
    Call<ForecastWeather> getForcastWeather();
}
