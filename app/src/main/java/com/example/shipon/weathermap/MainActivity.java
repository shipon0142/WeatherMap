package com.example.shipon.weathermap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.shipon.weathermap.models.CurrentWeather;
import com.example.shipon.weathermap.weather_api_collection.RectrofitClient;
import com.example.shipon.weathermap.weather_api_collection.WeatherApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    WeatherApi weatherApi;
TextView currentTempTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentTempTV=findViewById(R.id.CurrentTempTV);
        setTemp();
    }

    private void setTemp() {
        weatherApi= RectrofitClient.getRetrofitClient().create(WeatherApi.class);
        Call<CurrentWeather>weatherCall=weatherApi.getCurrentWeather();
        weatherCall.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                CurrentWeather weather=response.body();
                currentTempTV.setText(weather.getMain().getTemp().toString());
               // http://api.openweathermap.org/data/2.5/forecast?q=dhaka,bangladesh&appid=b2ed8cc71534bb766df35dbf3bbc0056
            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {

            }
        });
    }
}
