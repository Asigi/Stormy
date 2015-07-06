package arshsingh93.stormy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Student on 7/5/2015.
 */
public class CurrentWeather {

    private String myIcon;
    private long myTime;
    private double myTemperature;
    private double myHumidity;
    private double myPrecipChance;
    private String mySummary;
    private String myTimeZone;

    public String getMyTimeZone() {
        return myTimeZone;
    }

    public void setMyTimeZone(String myTimeZone) {
        this.myTimeZone = myTimeZone;
    }



    public long getMyTime() {
        return myTime;
    }

    public String getFormattedTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(getMyTimeZone()));
        Date dateTime = new Date(getMyTime() * 1000); //convert to milliseconds from seconds
        String timeString = formatter.format(dateTime);

        return timeString;
    }

    public void setMyTime(long myTime) {
        this.myTime = myTime;
    }

    public String getMyIcon() {
        return myIcon;
    }

    public int getIconId() {
        //clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night
        int iconId = R.drawable.clear_day;

        if (myIcon.equals("clear-day")) {
            iconId = R.drawable.clear_day;
        } else if(myIcon.equals("clear-night")) {
            iconId = R.drawable.clear_night;
        } else if (myIcon.equals("rain")) {
            iconId = R.drawable.rain;
        }
        else if (myIcon.equals("snow")) {
            iconId = R.drawable.snow;
        }
        else if (myIcon.equals("sleet")) {
            iconId = R.drawable.sleet;
        }
        else if (myIcon.equals("wind")) {
            iconId = R.drawable.wind;
        }
        else if (myIcon.equals("fog")) {
            iconId = R.drawable.fog;
        }
        else if (myIcon.equals("cloudy")) {
            iconId = R.drawable.cloudy;
        }
        else if (myIcon.equals("partly-cloudy-day")) {
            iconId = R.drawable.partly_cloudy;
        }
        else if (myIcon.equals("partly-cloudy-night")) {
            iconId = R.drawable.cloudy_night;
        }

        return iconId;
    }

    public void setMyIcon(String myIcon) {
        this.myIcon = myIcon;
    }

    public double getMyTemperature() {
        return myTemperature;
    }

    public void setMyTemperature(double myTemperature) {
        this.myTemperature = myTemperature;
    }

    public double getMyHumidity() {
        return myHumidity;
    }

    public void setMyHumidity(double myHumidity) {
        this.myHumidity = myHumidity;
    }

    public double getMyPrecipChance() {
        return myPrecipChance;
    }

    public void setMyPrecipChance(double myPrecipChance) {
        this.myPrecipChance = myPrecipChance;
    }

    public String getMySummary() {
        return mySummary;
    }

    public void setMySummary(String mySummary) {
        this.mySummary = mySummary;
    }


}
