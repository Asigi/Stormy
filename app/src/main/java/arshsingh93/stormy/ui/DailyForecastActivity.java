package arshsingh93.stormy.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;

import arshsingh93.stormy.R;
import arshsingh93.stormy.adapters.DayAdapter;
import arshsingh93.stormy.weather.Day;

public class DailyForecastActivity extends ListActivity {
    private Day[] myDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        myDays = Arrays.copyOf(parcelables, parcelables.length, Day[].class);

        DayAdapter adapter = new DayAdapter(this, myDays);
        setListAdapter(adapter);

    }

    /**This is the code to make a toast pop up when a user clicks on an item.
     *
     * @param l is the listview where the tap occured
     * @param v is the specific item that was clicked
     * @param position is the numerical index of the item in the list
     * @param id is an opitonal item id that we can set though we arent using it.
     */
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l,v,position,id);

        String dayOfTheWeek = myDays[position].getDayOfTheWeek();
        String conditions = myDays[position].getSummary();
        String highTemp = myDays[position].getTemperatureMax() + "";
        String message = String.format("On %s the high will be %s and it will be %s",
                dayOfTheWeek, highTemp, conditions);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();


    }



}
