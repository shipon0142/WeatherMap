package com.example.shipon.weathermap.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shipon.weathermap.R;
import com.example.shipon.weathermap.models.CurrentWeather;
import com.example.shipon.weathermap.weather_api_collection.RectrofitClient;
import com.example.shipon.weathermap.weather_api_collection.WeatherApi;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shipon.weathermap.MainActivity.tempFormat;
import static com.example.shipon.weathermap.MainActivity.weatherC;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentWeatherFragment extends Fragment {
    WeatherApi weatherApi;
    Calendar calender;
    ImageView icon;
    public static String API_KEY = "b2ed8cc71534bb766df35dbf3bbc0056";
    TextView defaultTemp, defaultDate, defaultDay, defaultLocation, defaultMinimumTemp, defaultMaxTemp, defaultHumadity, defaultPressare, defaultSunrise, defaultSunset, description;

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        weatherApi = RectrofitClient.getRetrofitClient().create(WeatherApi.class);
        calender = Calendar.getInstance();
        defaultTemp = view.findViewById(R.id.DefaultWeatherTV);
        defaultDate = view.findViewById(R.id.DefaultDateTV);
        defaultDay = view.findViewById(R.id.DefaultDayTV);
        defaultMinimumTemp = view.findViewById(R.id.DefaultMinimumWeatherTV);
        defaultMaxTemp = view.findViewById(R.id.DefaultMaximumWeatherTV);
        defaultHumadity = view.findViewById(R.id.DefaultHumidityPercentTV);
        defaultPressare = view.findViewById(R.id.DefaultPressureTV);
        defaultSunrise = view.findViewById(R.id.DefaultSunriseTimeTV);
        defaultSunset = view.findViewById(R.id.DefaultSunsetTimeTV);
        defaultLocation = view.findViewById(R.id.DefaultLocationTV);
        description = view.findViewById(R.id.DefaultWeatherDescriptionTV);
        icon = view.findViewById(R.id.iconID);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setAll(weatherC);

    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("hh:mm a", cal).toString();
        return date;
    }

    public void setAll(CurrentWeather weather) {
        String temp = null, minTemp = null, maxTemp = null;
        if (tempFormat == 1) {
            temp = String.format("%.0f", weather.getMain().getTempCentigrate()) + "\u2103";
            minTemp = String.format("%.0f", weather.getMain().getTempMinCentigrate()) + "\u2103";
            maxTemp = String.format("%.0f", weather.getMain().getTempMaxCentigrate()) + "\u2103";
        }
        if (tempFormat == 2) {
            temp = String.format("%.0f", weather.getMain().getTempFahrenheit()) + "\u2109";
            minTemp = String.format("%.0f", weather.getMain().getTempMinFahrenheit()) + "\u2109";
            maxTemp = String.format("%.0f", weather.getMain().getTempMaxFahrenheit()) + "\u2109";
        }

        int tempInC = (int) (weather.getMain().getTemp() - 273);
        description.setText(weather.getWeather().get(0).getDescription().toString());
        if(description.getText().toString().contains("ain")){
            icon.setImageResource(R.drawable.rain_icon);
        }
       else if(description.getText().toString().contains("aze")){
            icon.setImageResource(R.drawable.haze_icon);
        }
       else if(description.getText().toString().contains("loud")){
            icon.setImageResource(R.drawable.cloud_icon);
        }
        else {
            icon.setImageResource(R.drawable.sunny);
        }
        defaultLocation.setText(weather.getName().toString());
        defaultTemp.setText(temp);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        defaultDate.setText(formattedDate);
        String weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
        defaultDay.setText(weekday_name);
        defaultMinimumTemp.setText(minTemp);
        defaultMaxTemp.setText(maxTemp);
        defaultHumadity.setText("" + weather.getMain().getHumidity() + "%");
        defaultPressare.setText("" + weather.getMain().getPressure() + "hpa");

        defaultSunrise.setText(getDate(weather.getSys().getSunrise().longValue() * 1000));
        defaultSunset.setText(getDate(weather.getSys().getSunset().longValue() * 1000));
    }

    private void setTemp(String location) {


        Call<CurrentWeather> weatherCall = weatherApi.getCurrentWeather(location, API_KEY);
        weatherCall.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                CurrentWeather weather = response.body();
                weatherC = weather;
                setAll(weatherC);
                //currentTempTV.setText(weather.getMain().getTemp().toString());
                // http://api.openweathermap.org/data/2.5/forecast?q=dhaka,bangladesh&appid=b2ed8cc71534bb766df35dbf3bbc0056
            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {

            }
        });
    }


}
