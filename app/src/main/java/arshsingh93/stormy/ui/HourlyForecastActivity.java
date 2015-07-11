package arshsingh93.stormy.ui;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Arrays;

import arshsingh93.stormy.R;
import arshsingh93.stormy.weather.Hour;

public class HourlyForecastActivity extends AppCompatActivity {

    private Hour[] myHours;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST);
        myHours = Arrays.copyOf(parcelables, parcelables.length, Hour[].class);




    }



















}














