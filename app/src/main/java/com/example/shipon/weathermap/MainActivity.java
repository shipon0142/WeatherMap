package com.example.shipon.weathermap;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.shipon.weathermap.forecastweather_models.ForecastWeather;
import com.example.shipon.weathermap.fragments.CurrentWeatherFragment;
import com.example.shipon.weathermap.fragments.ForecastWeatherFragment;
import com.example.shipon.weathermap.models.CurrentWeather;
import com.example.shipon.weathermap.weather_api_collection.RectrofitClient;
import com.example.shipon.weathermap.weather_api_collection.WeatherApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    WeatherApi weatherApi;
TextView currentTempTV;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentTempTV=findViewById(R.id.CurrentTempTV);
        currentTempTV.setVisibility(View.GONE);
        weatherApi= RectrofitClient.getRetrofitClient().create(WeatherApi.class);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Current Weather"));
        tabLayout.addTab(tabLayout.newTab().setText("Forecast Weather"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTempTV.setText(tab.getText().toString());
                if(tab.getPosition()==0) {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    CurrentWeatherFragment fragment = new CurrentWeatherFragment();
                    transaction.replace(R.id.MainFragmentLayout, fragment).commit();
                }
                else {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    ForecastWeatherFragment fragment = new ForecastWeatherFragment();
                    transaction.replace(R.id.MainFragmentLayout, fragment).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        TabLayout.Tab tab = tabLayout.getTabAt(1);

        tab.select();
        TabLayout.Tab ta = tabLayout.getTabAt(0);

        ta.select();


       // setTemp();
      //  getForcastWeather();
    }

    private void getForcastWeather() {
        Call<ForecastWeather>forecastWeatherCall=weatherApi.getForcastWeather();
        forecastWeatherCall.enqueue(new Callback<ForecastWeather>() {
            @Override
            public void onResponse(Call<ForecastWeather> call, Response<ForecastWeather> response) {
                ForecastWeather forecastWeather=response.body();
                currentTempTV.setText(forecastWeather.getList().get(10).getMain().getTemp().toString());
            }

            @Override
            public void onFailure(Call<ForecastWeather> call, Throwable t) {
                currentTempTV.setText(t.getMessage());
            }
        });
    }

    private void setTemp() {

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
