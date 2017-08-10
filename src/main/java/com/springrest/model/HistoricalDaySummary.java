package com.springrest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.springrest.util.DateUnix;

/**
 * Created by tanerali on 26/07/2017.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties
public class HistoricalDaySummary {


    String date;
    String summary;
    String timeSunrise;
    String timeSunset;
    double precipProbability;
    double temperatureMax;
    double windSpeed;

    public HistoricalDaySummary(Forecast forecast) {
        this.date =  DateUnix.secondsToDate( forecast.getDaily().getData()[0].getTime() );
        this.summary = forecast.getDaily().getData()[0].getSummary();
        this.timeSunrise = DateUnix.secondsToSpecificTime( forecast.getDaily().getData()[0].getSunriseTime() );
        this.timeSunset = DateUnix.secondsToSpecificTime( forecast.getDaily().getData()[0].getSunsetTime() );
        this.precipProbability = forecast.getDaily().getData()[0].getPrecipProbability();
        this.temperatureMax = forecast.getDaily().getData()[0].getTemperatureMax();
        this.windSpeed = forecast.getDaily().getData()[0].getWindSpeed();
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTimeSunrise() {
        return timeSunrise;
    }

    public void setTimeSunrise(String timeSunrise) {
        this.timeSunrise = timeSunrise;
    }

    public String getTimeSunset() {
        return timeSunset;
    }

    public void setTimeSunset(String timeSunset) {
        this.timeSunset = timeSunset;
    }

    public double getPrecipProbability() {
        return precipProbability;
    }

    public void setPrecipProbability(double precipProbability) {
        this.precipProbability = precipProbability;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
}
