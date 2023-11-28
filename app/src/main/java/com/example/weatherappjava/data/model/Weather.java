package com.example.weatherappjava.data.model;

import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("main")
    public WeatherMain main;
    @SerializedName("wind")
    public WeatherWind wind;
    public String timeWeather = "";


}
