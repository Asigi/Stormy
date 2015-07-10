package arshsingh93.stormy.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import arshsingh93.stormy.R;
import arshsingh93.stormy.weather.Current;
import arshsingh93.stormy.weather.Day;
import arshsingh93.stormy.weather.Forecast;
import arshsingh93.stormy.weather.Hour;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String DAILY_FORECAST = "DAILY_FORECAST";

    private Forecast myForcast;
    //private Current myCurWeather;

    @InjectView(R.id.timeLabel) TextView myTimeLabel; //using butterknife
    @InjectView(R.id.temperatureLabel) TextView myTemperatureLabel;
    @InjectView(R.id.humidityValue) TextView myHumidityValue;
    @InjectView(R.id.precipValue) TextView myPrecipValue;
    @InjectView(R.id.summaryLabel) TextView mySummaryLabel;
    @InjectView(R.id.iconImageView) ImageView myIconImageView;
    @InjectView(R.id.refreshImageView) ImageView myRefreshImageView;
    @InjectView(R.id.progressBar) ProgressBar myProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this); //wrote this so that butterknife would run

        myProgressBar.setVisibility(View.INVISIBLE); //originally not seen.

        final double latitude = 47.432737; //Singh Sabha coords
        final double longitude = -122.423;
        //final double latitude = 37.8267; alcatraz coords
        //final double longitude = -122.423;

        myRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(latitude, longitude);
            }
        });

        getForecast(latitude, longitude);

        Log.d(TAG, "Main UI code is running");

    }

    private void getForecast(double theLat, double theLong) {
        String apiKey = "18131678eb944e61639caac84cb622b6";

        String forecastURL = "https://api.forecast.io/forecast/" + apiKey +
                "/" + theLat + "," + theLong;

        if (isNetworkAvailable()) {
            toggleRefresh();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forecastURL)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            myForcast = parseForecastDetails(jsonData);
                            //myCurWeather = getCurrentDetails(jsonData);
                            runOnUiThread(new Runnable() { //this code will cause whatever is in the inner run method to run on the main thread.
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });

        } else {
            Toast.makeText(this, R.string.network_unavailable_message, Toast.LENGTH_LONG).show();
        }
    }

    private void toggleRefresh() {
        if(myProgressBar.getVisibility() == View.INVISIBLE) {
            myProgressBar.setVisibility(View.VISIBLE);
            myRefreshImageView.setVisibility(View.INVISIBLE);
        } else {
            myProgressBar.setVisibility(View.INVISIBLE);
            myRefreshImageView.setVisibility(View.VISIBLE);
        }
    }

    private void updateDisplay() {
        Current current = myForcast.getCurrent();
        myTemperatureLabel.setText(current.getMyTemperature() + "");
        myTimeLabel.setText("At " + current.getFormattedTime() + " it will be");
        myHumidityValue.setText(current.getMyHumidity() + "");
        myPrecipValue.setText(current.getMyPrecipChance() + "%");
        mySummaryLabel.setText(current.getMySummary());
        Drawable drawable = getResources().getDrawable(current.getIconId());
        myIconImageView.setImageDrawable(drawable);

    }

    private Forecast parseForecastDetails(String jsonData) throws JSONException {
        Forecast forecast = new Forecast();
        forecast.setCurrent(getCurrentDetails(jsonData));

        forecast.setHourlyForecast(getHourlyForecast(jsonData));
        forecast.setDailyForecast(getDailyForecast(jsonData));

        return forecast;
    }

    private Day[] getDailyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");

        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

        Day[] days = new Day[data.length()];

        for(int i = 0; i<data.length(); i++) {
            JSONObject jsonDay = data.getJSONObject(i);
            Day day = new Day();

            day.setSummary(jsonDay.getString("summary"));;
            day.setTemperatureMax(jsonDay.getDouble("temperatureMax"));
            day.setIcon(jsonDay.getString("icon"));
            day.setTime(jsonDay.getLong("time"));
            day.setTimezone(timezone);

            days[i] = day;
        }

        return days;
    }

    private Hour[] getHourlyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");

        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        Hour[] hours= new Hour[data.length()];

        for(int i =0; i <data.length(); i++) {
            JSONObject jsonHour = data.getJSONObject(i);
            Hour hour = new Hour();

            hour.setSummary(jsonHour.getString("summary"));
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimezone(timezone);

            hours[i] = hour;
        }

        return hours;
    }

    private Current getCurrentDetails(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG, "From JSON: " + timezone);

        JSONObject currently = forecast.getJSONObject("currently");

        Current current = new Current();
        current.setMyHumidity(currently.getDouble("humidity"));
        current.setMyTime(currently.getLong("time"));
        current.setMyIcon(currently.getString("icon"));
        current.setMyPrecipChance(currently.getDouble("precipProbability"));
        current.setMySummary(currently.getString("summary"));
        current.setMyTemperature(currently.getDouble("temperature"));
        current.setMyTimeZone(timezone);

        Log.d(TAG, current.getFormattedTime());

        return current;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (info != null && info.isConnected()) {
            isAvailable = true;
        }
        Log.v(TAG, "net is " + isAvailable);
        return isAvailable;
    }

    private void alertUserAboutError() {
        Log.v(TAG, "alerUserAboutError Log");
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }

    @OnClick(R.id.dailyButton)
    public void startDailyActivity(View view) {
        Intent intent = new Intent(this, DailyForecastActivity.class);
        intent.putExtra(DAILY_FORECAST, myForcast.getDailyForecast());
        startActivity(intent);
    }
}
