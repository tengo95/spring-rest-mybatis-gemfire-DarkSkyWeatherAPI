package com.springrest.rest_controllers;

import com.springrest.exceptions.ForecastNotFoundException;
import com.springrest.model.DayInWeeklyForecast;
import com.springrest.model.Forecast;
import com.springrest.model.HistoricalDaySummary;
import com.springrest.model.HourlyAverage;
import com.springrest.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by tanerali on 26/07/2017.
 */

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    WeatherService weatherService;

    @RequestMapping("/current")
    public Forecast getCurrentForecast(@RequestParam(value="latitude")double latitude,
                                       @RequestParam(value="longitude")double longitude) {
        return weatherService.getCurrentForecast(latitude, longitude);
    }

    @RequestMapping("/current/average")
    public HourlyAverage getCurrentHourlyAverage(@RequestParam(value="latitude")double latitude,
                                                 @RequestParam(value="longitude")double longitude) {
        return weatherService.getCurrentHourlyAverage(latitude, longitude);
    }




    @RequestMapping("/weekly")
    public ArrayList<DayInWeeklyForecast> getWeeklyForecast (@RequestParam(value="latitude")double latitude,
                                                             @RequestParam(value="longitude")double longitude,
                                                             Model model) throws ForecastNotFoundException {

        ArrayList<DayInWeeklyForecast> weeklyForecast = weatherService.getWeeklyForecast(latitude, longitude);

        return weeklyForecast;
    }


//    @ExceptionHandler(ForecastNotFoundException.class)
//    public RestExceptionResponse forecastNotFound (){
//
//        RestExceptionResponse restExceptionResponse = new RestExceptionResponse();
//        restExceptionResponse.setError("error");
//
//        return restExceptionResponse;
//
//    }




    @RequestMapping("/historical")
    public Forecast getHistoricalWeather(@RequestParam(value="latitude")double latitude,
                                         @RequestParam(value="longitude")double longitude,
                                         @RequestParam(value = "timeMM/DD/YYYY")String time) {
        return weatherService.getHistoricalWeather(latitude, longitude, time);
    }

    @RequestMapping("/historical/summary")
    public ArrayList<HistoricalDaySummary> getHistoricalSummary(@RequestParam(value="latitude")double latitude,
                                                                @RequestParam(value="longitude")double longitude,
                                                                @RequestParam(value = "timeMM/DD/YYYY")String time) {
        return weatherService.getHistoricalSummary(latitude, longitude, time);
    }

    //Add weekly forecast to database
    @RequestMapping(method = RequestMethod.GET, value = "/weeklyDB")
    public ArrayList<DayInWeeklyForecast> addNewWeeklyForecast(@RequestParam(value="latitude")double latitude,
                                                               @RequestParam(value="longitude")double longitude)
            throws ForecastNotFoundException, SQLException {

        return weatherService.addNewWeeklyForecast(latitude, longitude);
    }
}
