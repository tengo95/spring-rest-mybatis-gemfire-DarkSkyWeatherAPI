package com.springrest.services;

import com.springrest.exceptions.ForecastNotFoundException;
import com.springrest.mappers.WeatherMapper;
import com.springrest.model.DayInWeeklyForecast;
import com.springrest.model.Forecast;
import com.springrest.model.HistoricalDaySummary;
import com.springrest.model.HourlyAverage;
import com.springrest.model.Data;
import com.springrest.util.DateUnix;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by tanerali on 26/07/2017.
 */
@Service
@SuppressWarnings("unused")
public class WeatherService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    WeatherMapper weatherMapper;


    //gets current forecast
    public Forecast getCurrentForecast(double latitude, double longitude) {
        Forecast forecast = restTemplate.getForObject("https://api.darksky.net/forecast/" +
                "f35d6277f5c7fe98f42caef12e120890/"+ latitude+","+ longitude, Forecast.class);
        return forecast;
    }

    //averages the values in the hourly/data[] and returns them as one object
    public HourlyAverage getCurrentHourlyAverage(double latitude, double longitude) {
        Forecast forecast = restTemplate.getForObject("https://api.darksky.net/forecast/" +
                "f35d6277f5c7fe98f42caef12e120890/"+ latitude+","+ longitude, Forecast.class);


        double precipIntensity = 0;
        double precipProbability = 0;
        double temperature = 0;
        double apparentTemperature = 0;
        double dewPoint = 0;
        double humidity = 0;
        double windSpeed = 0;
        double windGust = 0;
        int windBearing = 0;
        double cloudCover = 0;
        double pressure = 0;
        double ozone = 0;
        int uvIndex = 0;

        int count = 1;

        Data[] data = forecast.getHourly().getData();

        for (int i=0; i<data.length; i++) {
            precipIntensity += data[i].getPrecipIntensity();
            precipProbability += data[i].getPrecipProbability();
            temperature += data[i].getTemperature();
            apparentTemperature += data[i].getApparentTemperature();
            dewPoint += data[i].getDewPoint();
            humidity += data[i].getHumidity();
            windSpeed += data[i].getWindSpeed();
            windGust += data[i].getWindGust();
            windBearing += data[i].getWindBearing();
            cloudCover += data[i].getCloudCover();
            pressure += data[i].getPressure();
            ozone += data[i].getOzone();
            uvIndex += data[i].getUvIndex();

            count++;
        }

        HourlyAverage hourlyAverage = new HourlyAverage();

        hourlyAverage.setPrecipIntensity(precipIntensity/count);
        hourlyAverage.setPrecipProbability(precipProbability/count);
        hourlyAverage.setTemperature(temperature/count);
        hourlyAverage.setApparentTemperature(apparentTemperature/count);
        hourlyAverage.setDewPoint(dewPoint/count);
        hourlyAverage.setHumidity(humidity/count);
        hourlyAverage.setWindSpeed(windSpeed/count);
        hourlyAverage.setWindGust(windGust/count);
        hourlyAverage.setWindBearing(windBearing/count);
        hourlyAverage.setCloudCover(cloudCover/count);
        hourlyAverage.setPressure(pressure/count);
        hourlyAverage.setOzone(ozone/count);
        hourlyAverage.setUvIndex(uvIndex/count);

        return hourlyAverage;
    }


    //get historical forecasts specifying the time as MM/dd/yyyy
    public Forecast getHistoricalWeather(double latitude, double longitude, String time) {

        long dateInSecs = DateUnix.dateToSeconds(time);

        Forecast historicalWeather = restTemplate.getForObject("https://api.darksky.net/forecast/" +
                "f35d6277f5c7fe98f42caef12e120890/"+ latitude+","+ longitude+","+ dateInSecs, Forecast.class);
        return historicalWeather;
    }


    //return the summary for each day from the day specified as MM/dd/yyyy up until 1 Jan 2017
    public ArrayList<HistoricalDaySummary> getHistoricalSummary(double latitude, double longitude, String time) {

        long dateInSecs = DateUnix.dateToSeconds(time);

        ArrayList<HistoricalDaySummary> responses = new ArrayList<>();

        for (long i = dateInSecs; i<1_483_228_800L; i+=31_556_952L) {
            Forecast historicalWeather = restTemplate.getForObject("https://api.darksky.net/forecast/" +
                    "f35d6277f5c7fe98f42caef12e120890/"+ latitude+","+ longitude+","+ i, Forecast.class);

            HistoricalDaySummary historicalDaySummary = new HistoricalDaySummary(historicalWeather);
            responses.add(historicalDaySummary);
        }

        return responses;
    }


    //return only the daily forecasts for the week ahead
    @Cacheable("weather")
    public ArrayList<DayInWeeklyForecast> getWeeklyForecast(double latitude, double longitude) {
        Forecast forecast = restTemplate.getForObject("https://api.darksky.net/forecast/" +
                "f35d6277f5c7fe98f42caef12e120890/"+ latitude+","+ longitude, Forecast.class);

        ArrayList<DayInWeeklyForecast> dayInWeeklyForecastArrayList = new ArrayList<>();

        DayInWeeklyForecast dayInWeeklyForecast;

        for (int i = 0; i<forecast.getDaily().getData().length; i++) {
            dayInWeeklyForecast = new DayInWeeklyForecast(forecast.getDaily().getData()[i] );
            dayInWeeklyForecastArrayList.add(dayInWeeklyForecast);
        }

        return dayInWeeklyForecastArrayList;
    }


    //return all the daily forecasts from the database
    public ArrayList<DayInWeeklyForecast> getDailyForecastsFromDB(){
        return weatherMapper.getDailyForecastsFromDB();
    }


    /**
     * Adds each daily forecast for the week ahead to the database. It checks which daily forecasts
     * are already in the database. If the database is empty, it will populate it with the entire
     * forecast for the week ahead. If only some daily forecasts are missing, it will append
     * only these to the database
     *
     * @param latitude - specify latitude for forecast
     * @param longitude - specify longitude for forecast
     * @return the contents of the database after any operations have been performed
     */



    public ArrayList<DayInWeeklyForecast> addNewWeeklyForecast (double latitude, double longitude)
            throws ForecastNotFoundException, SQLException {

        //create a date formatter
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        //create a Date object that will receive the date of the last row in the weather
        //database
        Date dateOfLastRowInDB = new Date();

        //if database is empty can throw an ArrayIndexOutOfBoundsException
        try {
            //string that holds the date of the last row in the database
            ArrayList<DayInWeeklyForecast> dayInWeeklyForecastArrayList = weatherMapper.getDailyForecastsFromDB();
            String dateInText = dayInWeeklyForecastArrayList.get( getDailyForecastsFromDB().size()-1 ).getDate();

            //convert date of last row in database from String to Date
            try {
                dateOfLastRowInDB = df.parse(dateInText);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        //if database is empty, populate it with the next week's forecast from the API call
        } catch (ArrayIndexOutOfBoundsException exc) {

            //if API call unsuccessful, throw ForecastNotFoundException
            try {
                //API call
                Forecast forecast = restTemplate.getForObject("https://api.darksky.net/forecast/" +
                        "f35d6277f5c7fe98f42caef12e120890/"+ latitude+","+ longitude, Forecast.class);

                //if cannot access database, throw DatabaseAccessException
                try {
                    //object to receive each daily forecast in next week in the for loop
                    DayInWeeklyForecast dayInWeeklyForecast;
                    for (int i=0; i<8; i++) {
                        dayInWeeklyForecast = new DayInWeeklyForecast(forecast.getDaily().getData()[i] );
                        dayInWeeklyForecast.setLatitude(latitude);
                        dayInWeeklyForecast.setLongitude(longitude);
                        weatherMapper.insertDBWeeklyForecast(dayInWeeklyForecast);
                    }
                    return weatherMapper.getDailyForecastsFromDB();
                } catch (Exception databaseExc) {
                    throw new SQLException();
                }

            } catch (Exception forecastExc) {
                throw new ForecastNotFoundException();
            }

        }


        //Calendar object that holds the date 8 days ahead
        Calendar nextWeekDate = Calendar.getInstance();
        nextWeekDate.add(Calendar.DATE,7);

        //convert Date dateOfLastRowInDB to Calendar object
        Calendar calendarOfLastRowInDB = new GregorianCalendar();
        calendarOfLastRowInDB.setTime(dateOfLastRowInDB);


        //if database does not have the whole forecast for the week ahead, populate it with the remaining
        //daily forecasts; else, return weekly forecast from database instead of from API call
        if (calendarOfLastRowInDB.get(Calendar.DATE) < nextWeekDate.get(Calendar.DATE) ) {

            //if API call unsuccessful, throw ForecastNotFoundException
            try {
                //API call
                Forecast forecast = restTemplate.getForObject("https://api.darksky.net/forecast/" +
                        "f35d6277f5c7fe98f42caef12e120890/"+ latitude+","+ longitude, Forecast.class);


                //object to receive forecast for missing days in the for loop
                DayInWeeklyForecast dayInWeeklyForecast;

                int diffBetweenDatesInDays = DateUnix.diffBetweenDatesInDays(nextWeekDate, calendarOfLastRowInDB);

                //used as index in array which holds the weekly forecast for each day as the element
                int objectIndex = 7 - diffBetweenDatesInDays;


                //if cannot access database, throw DatabaseAccessException
                try {
                    //iterates as many times as the missing daily forecasts to insert the missing ones into
                    //the database
                    for (int i = 0; i < diffBetweenDatesInDays; i++) {

                        //increments each loop to point to the next element in the array (the weekly forecast array)
                        objectIndex++;

                        dayInWeeklyForecast = new DayInWeeklyForecast(forecast.getDaily().getData()[objectIndex] );
                        dayInWeeklyForecast.setLatitude(latitude);
                        dayInWeeklyForecast.setLongitude(longitude);
                        weatherMapper.insertDBWeeklyForecast(dayInWeeklyForecast);
                    }
                    //finally, return the contents of the database after insertions are completed
                    return weatherMapper.getDailyForecastsFromDB();

                } catch (Exception databaseExc) {
                    throw new SQLException();
                }



            } catch (Exception forecastExc) {
                throw new ForecastNotFoundException();
            }


        //if there are no missing daily forecasts in the database, simply return database contents
        } else {
            return weatherMapper.getDailyForecastsFromDB();
        }
    }


}
