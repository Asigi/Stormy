package arshsingh93.stormy.weather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Student on 7/8/2015.
 */
public class Day {

    private long myTime;
    private String mySummary;
    private double myTemperatureMax;
    private String myIcon;
    private String myTimezone;

    public long getTime() {
        return myTime;
    }

    public void setTime(long myTime) {
        this.myTime = myTime;
    }

    public String getSummary() {
        return mySummary;
    }

    public void setSummary(String mySummary) {
        this.mySummary = mySummary;
    }

    public int getTemperatureMax() {
        return (int) Math.round(myTemperatureMax);
    }

    public void setTemperatureMax(double myTemperatureMax) {
        this.myTemperatureMax = myTemperatureMax;
    }

    public String getIcon() {
        return myIcon;
    }

    public void setIcon(String myIcon) {
        this.myIcon = myIcon;
    }

    public String getTimezone() {
        return myTimezone;
    }

    public void setTimezone(String myTimezone) {
        this.myTimezone = myTimezone;
    }

    public int getIconId() {
        return Forecast.getIconId(myIcon);
    }

    public String getDayOfTheWeek() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
        formatter.setTimeZone(TimeZone.getTimeZone(myTimezone));
        Date dateTime = new Date(myTime*1000);

        return formatter.format(dateTime);
    }

}
