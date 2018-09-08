package com.example.shipon.weathermap.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shipon.weathermap.R;
import com.example.shipon.weathermap.forecastweather_models.ForecastWeather;
import com.example.shipon.weathermap.weather_api_collection.RectrofitClient;
import com.example.shipon.weathermap.weather_api_collection.WeatherApi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static com.example.shipon.weathermap.MainActivity.tempFormat;
import static com.example.shipon.weathermap.MainActivity.weatherF;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastWeatherFragment extends Fragment {

    TextView DayFirstMinForecastTV;
    TextView FirstDateTV;
    TextView DayFirstMaxForecastTV;

    TextView DaySecondMinForecastTV;
    TextView SecondDateTV;
    TextView DaySecondMaxForecastTV;

    TextView DayThirdTV;
    TextView DayThirdMinForecastTV;
    TextView ThirdDateTV;
    TextView DayThirdMaxForecastTV;

    TextView DayFourthTV;
    TextView DayFourthMinForecastTV;
    TextView FourthDateTV;
    TextView DayFourthMaxForecastTV;

    TextView DayFifthTV;
    TextView DayFifthMinForecastTV;
    TextView FifthDateTV;
    TextView DayFifthMaxForecastTV;

    TextView locationTV;
    WeatherApi weatherApi;


    public ForecastWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast_weather, container, false);
        locationTV = view.findViewById(R.id.LocationTV);
        weatherApi = RectrofitClient.getRetrofitClient().create(WeatherApi.class);
        DayFirstMinForecastTV = view.findViewById(R.id.DayFirstMinForecastTV);
        FirstDateTV = view.findViewById(R.id.FirstDateTV);
        DayFirstMaxForecastTV = view.findViewById(R.id.DayFirstMaxForecastTV);

        DaySecondMinForecastTV = view.findViewById(R.id.DaySecondMinForecastTV);
        SecondDateTV = view.findViewById(R.id.SecondDateTV);
        DaySecondMaxForecastTV = view.findViewById(R.id.DaySecondMaxForecastTV);

        DayThirdTV = view.findViewById(R.id.DayThirdTV);
        DayThirdMinForecastTV = view.findViewById(R.id.DayThirdMinForecastTV);
        ThirdDateTV = view.findViewById(R.id.ThirdDateTV);
        DayThirdMaxForecastTV = view.findViewById(R.id.DayThirdMaxForecastTV);

        DayFourthTV = view.findViewById(R.id.DayFourthTV);
        DayFourthMinForecastTV = view.findViewById(R.id.DayFourthMinForecastTV);
        FourthDateTV = view.findViewById(R.id.FourthDateTV);
        DayFourthMaxForecastTV = view.findViewById(R.id.DayFourthMaxForecastTV);

        DayFifthTV = view.findViewById(R.id.DayFifthTV);
        DayFifthMinForecastTV = view.findViewById(R.id.DayFifthMinForecastTV);
        FifthDateTV = view.findViewById(R.id.FifthDateTV);
        DayFifthMaxForecastTV = view.findViewById(R.id.DayFifthMaxForecastTV);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setForecastTemp(weatherF);

    }

    private void setForecastTemp(ForecastWeather weather) {
        locationTV.setText(weather.getCity().getName());
        setToday(weather);
        setTomorrow(weather);
        setThirdDay(weather);
        setFourthDay(weather);
        setFifthDay(weather);

    }


    private void setFifthDay(ForecastWeather weather) {
        String temp = null, minTemp = null, maxTemp = null;
        String weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(weather.getList().get(32).getDt().longValue() * 1000);
        if (tempFormat == 1) {

            minTemp = String.format("%.0f", weather.getList().get(32).getMain().getTempMinCentigrate()) + "\u2103";
            maxTemp = String.format("%.0f", weather.getList().get(32).getMain().getTempMaxCentigrate()) + "\u2103";
        }
        if (tempFormat == 2) {

            minTemp = String.format("%.0f", weather.getList().get(32).getMain().getTempMinFarenheit()) + "\u2109";
            maxTemp = String.format("%.0f", weather.getList().get(32).getMain().getTempMaxFarenheit()) + "\u2109";
        }
        DayFifthMaxForecastTV.setText(maxTemp);
        DayFifthMinForecastTV.setText(minTemp);

        String formattedDate = getDate(weather.getList().get(32).getDt().longValue() * 1000);
        FifthDateTV.setText(formattedDate);
        DayFifthTV.setText(weekday_name);
    }

    private void setFourthDay(ForecastWeather weather) {
        String temp = null, minTemp = null, maxTemp = null;
        String weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(weather.getList().get(24).getDt().longValue() * 1000);
        if (tempFormat == 1) {

            minTemp = String.format("%.0f", weather.getList().get(24).getMain().getTempMinCentigrate()) + "\u2103";
            maxTemp = String.format("%.0f", weather.getList().get(24).getMain().getTempMaxCentigrate()) + "\u2103";
        }
        if (tempFormat == 2) {

            minTemp = String.format("%.0f", weather.getList().get(24).getMain().getTempMinFarenheit()) + "\u2109";
            maxTemp = String.format("%.0f", weather.getList().get(24).getMain().getTempMaxFarenheit()) + "\u2109";
        }
        DayFourthMaxForecastTV.setText(maxTemp);
        DayFourthMinForecastTV.setText(minTemp);

        String formattedDate = getDate(weather.getList().get(24).getDt().longValue() * 1000);
        FourthDateTV.setText(formattedDate);
        DayFourthTV.setText(weekday_name);
    }

    private void setThirdDay(ForecastWeather weather) {
        String temp = null, minTemp = null, maxTemp = null;
        String weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(weather.getList().get(16).getDt().longValue() * 1000);
        if (tempFormat == 1) {

            minTemp = String.format("%.0f", weather.getList().get(16).getMain().getTempMinCentigrate()) + "\u2103";
            maxTemp = String.format("%.0f", weather.getList().get(16).getMain().getTempMaxCentigrate()) + "\u2103";
        }
        if (tempFormat == 2) {

            minTemp = String.format("%.0f", weather.getList().get(16).getMain().getTempMinFarenheit()) + "\u2109";
            maxTemp = String.format("%.0f", weather.getList().get(16).getMain().getTempMaxFarenheit()) + "\u2109";
        }
        DayThirdMaxForecastTV.setText(maxTemp);
        DayThirdMinForecastTV.setText(minTemp);
        // SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = getDate(weather.getList().get(16).getDt().longValue() * 1000);
        ThirdDateTV.setText(formattedDate);
        DayThirdTV.setText(weekday_name);

    }

    private void setTomorrow(ForecastWeather weather) {
        String temp = null, minTemp = null, maxTemp = null;
        if (tempFormat == 1) {
            //temp=String.format("%.2f",weather.getList().get(0).getMain().getTempMCentigrate())+"\u2103";
            minTemp = String.format("%.0f", weather.getList().get(8).getMain().getTempMinCentigrate()) + "\u2103";
            maxTemp = String.format("%.0f", weather.getList().get(8).getMain().getTempMaxCentigrate()) + "\u2103";
        }
        if (tempFormat == 2) {
            //temp=String.format("%.2f",weather.getMain().getTempFahrenheit())+"\u2109";
            minTemp = String.format("%.0f", weather.getList().get(8).getMain().getTempMinFarenheit()) + "\u2109";
            maxTemp = String.format("%.0f", weather.getList().get(8).getMain().getTempMaxFarenheit()) + "\u2109";
        }
        DaySecondMaxForecastTV.setText(maxTemp);
        DaySecondMinForecastTV.setText(minTemp);
        // SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = getDate(weather.getList().get(8).getDt().longValue() * 1000);
        SecondDateTV.setText(formattedDate);
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = DateFormat.format("dd-MMM-yyyy", cal).toString();
        return date;
    }

    private void setToday(ForecastWeather weather) {
        String temp = null, minTemp = null, maxTemp = null;
        String weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(weather.getList().get(0).getDt().longValue() * 1000);
        if (tempFormat == 1) {
            minTemp = String.format("%.0f", weather.getList().get(0).getMain().getTempMinCentigrate()) + "\u2103";
            maxTemp = String.format("%.0f", weather.getList().get(0).getMain().getTempMaxCentigrate()) + "\u2103";
        }
        if (tempFormat == 2) {
            minTemp = String.format("%.0f", weather.getList().get(0).getMain().getTempMinFarenheit()) + "\u2109";
            maxTemp = String.format("%.0f", weather.getList().get(0).getMain().getTempMaxFarenheit()) + "\u2109";
        }

        DayFirstMaxForecastTV.setText(maxTemp);
        DayFirstMinForecastTV.setText(minTemp);
        String formattedDate = getDate(weather.getList().get(0).getDt().longValue() * 1000);
        FirstDateTV.setText(formattedDate);

    }

}
