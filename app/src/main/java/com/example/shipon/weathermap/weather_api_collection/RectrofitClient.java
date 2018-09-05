package com.example.shipon.weathermap.weather_api_collection;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Shipon on 9/5/2018.
 */

public class RectrofitClient {
    public static Retrofit getRetrofitClient(){
        return  new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
