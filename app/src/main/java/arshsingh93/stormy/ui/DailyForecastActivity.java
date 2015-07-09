package arshsingh93.stormy.ui;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import arshsingh93.stormy.R;
import arshsingh93.stormy.adapters.DayAdapter;
import arshsingh93.stormy.weather.Day;

public class DailyForecastActivity extends ListActivity {
    private Day myDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        DayAdapter adapter = new DayAdapter(this, myDays);

    }




}
