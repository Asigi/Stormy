package arshsingh93.stormy;

import android.content.Context;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private CurrentWeather myCurWeather;

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

        final double latitude = 47.432737;
        final double longitude = -122.423;

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
                            myCurWeather = getCurrentDetails(jsonData);
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
        myTemperatureLabel.setText(myCurWeather.getMyTemperature() + "");
        myTimeLabel.setText("At " + myCurWeather.getFormattedTime() + " it will be");
        myHumidityValue.setText(myCurWeather.getMyHumidity() + "");
        myPrecipValue.setText(myCurWeather.getMyPrecipChance() + "%");
        mySummaryLabel.setText(myCurWeather.getMySummary());
        Drawable drawable = getResources().getDrawable(myCurWeather.getIconId());
        myIconImageView.setImageDrawable(drawable);

    }

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG, "From JSON: " + timezone);

        JSONObject currently = forecast.getJSONObject("currently");

        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setMyHumidity(currently.getDouble("humidity"));
        currentWeather.setMyTime(currently.getLong("time"));
        currentWeather.setMyIcon(currently.getString("icon"));
        currentWeather.setMyPrecipChance(currently.getDouble("precipProbability"));
        currentWeather.setMySummary(currently.getString("summary"));
        currentWeather.setMyTemperature(currently.getDouble("temperature"));
        currentWeather.setMyTimeZone(timezone);

        Log.d(TAG, currentWeather.getFormattedTime());

        return currentWeather;
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


}
