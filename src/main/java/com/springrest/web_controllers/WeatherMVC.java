package com.springrest.web_controllers;

import com.springrest.model.Latlong;
import com.springrest.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by tanerali on 02/08/2017.
 */
@Controller
public class WeatherMVC {

    @Autowired
    WeatherService weatherService;

    @RequestMapping("/MVC/weeklyDB")
    public String getDBWeeklyForecast (Model model) {

        model.addAttribute("weeklyForecasts", weatherService.getDailyForecastsFromDB());

        return "forecast";
    }




    @RequestMapping(value = "/MVC/weekly", method = RequestMethod.GET)
    public String getWeeklyForecast (@RequestParam(value="latitude")double latitude,
                                     @RequestParam(value="longitude")double longitude,
                                     Model model) {

        model.addAttribute("weeklyForecasts", weatherService.getWeeklyForecast(latitude, longitude) );

        return "forecast";

    }

    @RequestMapping(value = "/MVC/form", method = RequestMethod.GET)
    public String showForm (Model model) {


        model.addAttribute("latlong", new Latlong() );

        return "form";
    }


    @RequestMapping(value = "/MVC/weekly", method = RequestMethod.POST)
    public String submitToForm (Model model, Latlong latlong) {

//        com.springrest.model.addAttribute("latlong", latlong);

        return "redirect:/MVC/weekly?latitude=" + latlong.getLatitude()+ "&longitude="+ latlong.getLongitude();
    }

}
