package arshsingh93.stormy.ui;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Arrays;

import arshsingh93.stormy.R;
import arshsingh93.stormy.adapters.HourAdapter;
import arshsingh93.stormy.weather.Hour;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class HourlyForecastActivity extends AppCompatActivity {

    private Hour[] myHours;

    @InjectView(R.id.recyclerView) RecyclerView myRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);
        ButterKnife.inject(this);


        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST);
        myHours = Arrays.copyOf(parcelables, parcelables.length, Hour[].class);

        HourAdapter adapter = new HourAdapter(myHours);
        myRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(layoutManager);

        myRecyclerView.setHasFixedSize(true);

    }



















}














