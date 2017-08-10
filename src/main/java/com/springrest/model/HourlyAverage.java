package com.springrest.model;

/**
 * Created by tanerali on 26/07/2017.
 */
public class HourlyAverage {

    double precipIntensity;
    double precipProbability;
    double temperature;
    double apparentTemperature;
    double dewPoint;
    double humidity;
    double windSpeed;
    double windGust;
    int windBearing;
    double cloudCover;
    double pressure;
    double ozone;
    int uvIndex;


//    public HourlyAverage getSumOfValues (Forecast forecast) {
//        HourlyAverage hourlyAverage = new HourlyAverage();
//
//        int count = 1;
//
//        for (int i=0; i<forecast.getHourly().getData().length; i++) {
//            this.precipIntensity += forecast.getHourly().getData()[i].getPrecipIntensity();
//            this.precipProbability += forecast.getHourly().getData()[i].getPrecipProbability();
//            this.temperature += forecast.getHourly().getData()[i].getTemperature();
//            this.apparentTemperature += forecast.getHourly().getData()[i].getApparentTemperature();
//            this.dewPoint += forecast.getHourly().getData()[i].getDewPoint();
//            this.humidity += forecast.getHourly().getData()[i].getHumidity();
//            this.windSpeed += forecast.getHourly().getData()[i].getWindSpeed();
//            this.windGust += forecast.getHourly().getData()[i].getWindGust();
//            this.windBearing += forecast.getHourly().getData()[i].getWindBearing();
//            this.cloudCover += forecast.getHourly().getData()[i].getCloudCover();
//            this.pressure += forecast.getHourly().getData()[i].getPressure();
//            this.ozone += forecast.getHourly().getData()[i].getOzone();
//            this.uvIndex += forecast.getHourly().getData()[i].getUvIndex();
//
//            count++;
//        }
//
//        precipIntensity = precipIntensity/count;
//        precipProbability = precipProbability/count;
//        temperature = temperature/count;
//        apparentTemperature = apparentTemperature/count;
//        dewPoint = dewPoint/count;
//        humidity = humidity/count;
//        windSpeed = windSpeed/count;
//        windGust = windGust/count;
//        windBearing = windBearing/count;
//        cloudCover = cloudCover/count;
//        pressure = pressure/count;
//        ozone = ozone/count;
//        uvIndex = uvIndex/count;
//
//        return hourlyAverage;
//    }


//    public HourlyAverage(Forecast forecast) {
//
//    }

    public HourlyAverage() {
    }

    public double getPrecipIntensity() {
        return precipIntensity;
    }

    public void setPrecipIntensity(double precipIntensity) {
        this.precipIntensity = precipIntensity;
    }

    public double getPrecipProbability() {
        return precipProbability;
    }

    public void setPrecipProbability(double precipProbability) {
        this.precipProbability = precipProbability;
    }


    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getApparentTemperature() {
        return apparentTemperature;
    }

    public void setApparentTemperature(double apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }

    public double getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(double dewPoint) {
        this.dewPoint = dewPoint;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindGust() {
        return windGust;
    }

    public void setWindGust(double windGust) {
        this.windGust = windGust;
    }

    public int getWindBearing() {
        return windBearing;
    }

    public void setWindBearing(int windBearing) {
        this.windBearing = windBearing;
    }

    public double getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(double cloudCover) {
        this.cloudCover = cloudCover;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getOzone() {
        return ozone;
    }

    public void setOzone(double ozone) {
        this.ozone = ozone;
    }

    public int getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(int uvIndex) {
        this.uvIndex = uvIndex;
    }




//    public ArrayList<HourlyAverage> HourlyAverage(Forecast forecast) {
//        for (int i=0; i<.lenght; i++) {
//            this.precipIntensity = forecast.getHourly().getData()[0].getPrecipIntensity();
//            this.precipProbability = forecast.getHourly().getData()[0].getPrecipProbability();
//            this.precipType = forecast.getHourly().getData()[0].getPrecipType();
//            this.temperature = forecast.getHourly().getData()[0].getTemperature();
//            this.apparentTemperature = forecast.getHourly().getData()[0].getApparentTemperature();
//            this.dewPoint = forecast.getHourly().getData()[0].getDewPoint();
//            this.humidity = forecast.getHourly().getData()[0].getHumidity();
//            this.windSpeed = forecast.getHourly().getData()[0].getWindSpeed();
//            this.windGust = forecast.getHourly().getData()[0].getWindGust();
//            this.windBearing = forecast.getHourly().getData()[0].getWindBearing();
//            this.cloudCover = forecast.getHourly().getData()[0].getCloudCover();
//            this.pressure = forecast.getHourly().getData()[0].getPressure();
//            this.ozone = forecast.getHourly().getData()[0].getOzone();
//            this.uvIndex = forecast.getHourly().getData()[0].getUvIndex();
//        }
}
