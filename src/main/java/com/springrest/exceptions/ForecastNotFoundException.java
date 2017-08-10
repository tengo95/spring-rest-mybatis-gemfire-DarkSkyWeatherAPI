package com.springrest.exceptions;

/**
 * Created by tanerali on 09/08/2017.
 */
public class ForecastNotFoundException extends Exception {

    String msg;

    @Override
    public String toString() {
        return "ForecastNotFoundException{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
