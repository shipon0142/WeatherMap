package com.example.shipon.weathermap;

import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shipon.weathermap.forecastweather_models.ForecastWeather;
import com.example.shipon.weathermap.fragments.CurrentWeatherFragment;
import com.example.shipon.weathermap.fragments.ForecastWeatherFragment;
import com.example.shipon.weathermap.models.CurrentWeather;
import com.example.shipon.weathermap.weather_api_collection.RectrofitClient;
import com.example.shipon.weathermap.weather_api_collection.WeatherApi;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shipon.weathermap.fragments.CurrentWeatherFragment.API_KEY;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    WeatherApi weatherApi;
    TextView currentTempTV;
    public static int tempFormat = 1;
    public static int tabindex = 1;
    public static CurrentWeather weatherC;
    public static ForecastWeather weatherF;
    public static String location = "dhaka,bangladesh";
    String queryLocation = null;
    boolean doubleBackToExitPressedOnce = false;
    public static FragmentTransaction transactionDialog;
    MaterialSearchView searchView;
    String[] list;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        currentTempTV = findViewById(R.id.CurrentTempTV);
        currentTempTV.setVisibility(View.GONE);
        weatherApi = RectrofitClient.getRetrofitClient().create(WeatherApi.class);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Current Weather"));
        tabLayout.addTab(tabLayout.newTab().setText("Forecast Weather"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        list = new String[]{"android", "dotnet", "java"};
        FragmentManager manager = getSupportFragmentManager();
        transactionDialog = manager.beginTransaction();
        callSearch();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTempTV.setText(tab.getText().toString());
                if (tab.getPosition() == 0) {
                    tabindex = 1;
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    CurrentWeatherFragment fragment = new CurrentWeatherFragment();
                    transaction.replace(R.id.MainFragmentLayout, fragment).commit();
                } else {
                    tabindex = 2;
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

        callDefaultWeather();

    }

    private void callDefaultWeather() {
        Call<CurrentWeather> weatherCall = weatherApi.getCurrentWeather(location, API_KEY);
        weatherCall.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                CurrentWeather weather = response.body();
                if (weather != null) {
                    weatherC = weather;
                    if (tabindex == 1) {
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
                        transaction.replace(R.id.MainFragmentLayout, fragment).commit();
                    }
                } else {
                    location = queryLocation;
                    Toast.makeText(MainActivity.this, "Location Not Found", Toast.LENGTH_SHORT).show();
                }
                // http://api.openweathermap.org/data/2.5/forecast?q=dhaka,bangladesh&appid=b2ed8cc71534bb766df35dbf3bbc0056
            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {

            }
        });
        Call<ForecastWeather> forecastWeatherCall = weatherApi.getForcastWeather(location, API_KEY);
        forecastWeatherCall.enqueue(new Callback<ForecastWeather>() {
            @Override
            public void onResponse(Call<ForecastWeather> call, Response<ForecastWeather> response) {
                ForecastWeather weather = response.body();
                if (weather != null) {
                    weatherF = weather;
                    if (tabindex == 2) {
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        ForecastWeatherFragment fragment = new ForecastWeatherFragment();
                        transaction.replace(R.id.MainFragmentLayout, fragment).commit();
                    }
                } else {
                    location = queryLocation;
                    Toast.makeText(MainActivity.this, "Location Not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ForecastWeather> call, Throwable t) {
                //  Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callSearch() {
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryLocation = location;
                location = query + ",bangladesh";
                callDefaultWeather();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
                searchView.showSuggestions();
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.MylocationID:
                location = "dhaka,bangladesh";
                callDefaultWeather();
                return true;
            case R.id.CelciousId:
                tempFormat = 1;
                callDefaultWeather();
                return true;
            case R.id.FarenhaitId:
                tempFormat = 2;
                callDefaultWeather();
                return true;
            case R.id.RefreshID:
                callDefaultWeather();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }


}
