package com.springrest.mappers;

import com.springrest.model.DayInWeeklyForecast;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * Created by tanerali on 27/07/2017.
 */
@Mapper
public interface WeatherMapper {

    String GET_WEEKLY_FORECAST= "SELECT * FROM `weatherapi`.weather_thailand";

    String GET_SPECIFIC_DAY = "SELECT * FROM `weatherapi`.weather_thailand WHERE date = #{date}";

    String INSERT_WEEKLY_FORECAST = "INSERT INTO `weatherapi`.weather_thailand " +
            "(date, summary, sunrise, sunset, precipProbability, temperatureMax, windSpeed, latitude, longitude) " +
            "VALUES (#{date}, #{summary}, #{sunrise}, #{sunset}, " +
            "#{precipProbability}, #{temperatureMax}, #{windSpeed}, #{latitude}, #{longitude})";

    @Select(GET_WEEKLY_FORECAST)
    public ArrayList<DayInWeeklyForecast> getDailyForecastsFromDB();

    @Select(GET_SPECIFIC_DAY)
    public ArrayList<DayInWeeklyForecast> getSpecificDay();

    @Insert(INSERT_WEEKLY_FORECAST)
    public int insertDBWeeklyForecast (DayInWeeklyForecast dayInWeeklyForecast);
}
