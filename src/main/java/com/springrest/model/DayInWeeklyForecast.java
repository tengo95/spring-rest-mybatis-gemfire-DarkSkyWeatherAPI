package com.springrest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.springrest.util.DateUnix;

/**
 * Created by tanerali on 27/07/2017.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties
public class DayInWeeklyForecast {
    String date;
    String summary;
    String sunrise;
    String sunset;
    double precipProbability;
    double temperatureMax;
    double windSpeed;
    double latitude;
    double longitude;


    public DayInWeeklyForecast(Data data) {
        this.date = DateUnix.secondsToDate(data.getTime());
        this.summary = data.getSummary();
        this.sunrise = DateUnix.secondsToSpecificTime(data.getSunriseTime() );
        this.sunset = DateUnix.secondsToSpecificTime(data.getSunsetTime() );
        this.precipProbability = data.getPrecipProbability();
        this.temperatureMax = data.getTemperatureMax();
        this.windSpeed = data.getWindSpeed();
    }

    public DayInWeeklyForecast() {
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

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}