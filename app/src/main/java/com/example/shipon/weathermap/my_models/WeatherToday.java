package com.example.shipon.weathermap.my_models;

import android.text.format.DateFormat;

import com.example.shipon.weathermap.models.CurrentWeather;
import com.example.shipon.weathermap.weather_api_collection.RectrofitClient;
import com.example.shipon.weathermap.weather_api_collection.WeatherApi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shipon on 9/7/2018.
 */

public class WeatherToday {
    private String tempStr;
    private String dateStr;
    private String dayOfWeekStr;
    private String minTempStr;
    private String maxTempStr;
    private String sunriseStr;
    private String sunsetStr;
    private String humidityStr;
    private String pressureStr;
    Calendar calender;
    Call<CurrentWeather> weatherCall;
    CurrentWeather weather;
    WeatherApi weatherApi;
    private String location;

    public String getLocation() {
        return location;
    }

    public String getTempStr() {
        return tempStr;
    }

    public String getDateStr() {
        return dateStr;
    }

    public String getDayOfWeekStr() {
        return dayOfWeekStr;
    }

    public String getMinTempStr() {
        return minTempStr;
    }

    public String getMaxTempStr() {
        return maxTempStr;
    }

    public String getSunriseStr() {
        return sunriseStr;
    }

    public String getSunsetStr() {
        return sunsetStr;
    }

    public String getHumidityStr() {
        return humidityStr;
    }

    public String getPressureStr() {
        return pressureStr;
    }



    public WeatherToday(String location) {
        this.location = location;
        weatherApi= RectrofitClient.getRetrofitClient().create(WeatherApi.class);
        calender=Calendar.getInstance();
       // weatherCall=weatherApi.getCurrentWeather();
        getWeather();
        set();
    }
    public void set(){
        tempStr= String.valueOf((weather.getMain().getTemp()-273));


        Date c = Calendar.getInstance().getTime();
        //System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        dateStr=formattedDate;

        String weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
        dayOfWeekStr=weekday_name;
        minTempStr= String.valueOf((int)(weather.getMain().getTempMin()-273));
        //defaultMinimumTemp.setText((int)(weather.getMain().getTempMin()-273)+"\u2103");
        maxTempStr= String.valueOf((int)(weather.getMain().getTempMax()-273));
        // defaultMaxTemp.setText((int)(weather.getMain().getTempMax()-273)+"\u2103");
        humidityStr=""+weather.getMain().getHumidity()+"%";
        // defaultHumadity.setText(""+weather.getMain().getHumidity()+"%");
        pressureStr=""+weather.getMain().getPressure()+"hpa";
        //    defaultPressare.setText(""+weather.getMain().getPressure()+"hpa");
        sunriseStr=getDate(weather.getSys().getSunrise().longValue()*1000).toString();
        //    defaultSunrise.setText(getDate(weather.getSys().getSunrise().longValue()*1000));
        sunsetStr=getDate(weather.getSys().getSunset().longValue()*1000).toString();
        //  defaultSunset.setText(getDate(weather.getSys().getSunset().longValue()*1000));
    }

    private void getWeather() {
        weatherCall.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response){
                weather = response.body();

            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {

            }
        });
    }
    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("hh:mm a", cal).toString();
        return date;
    }


}
