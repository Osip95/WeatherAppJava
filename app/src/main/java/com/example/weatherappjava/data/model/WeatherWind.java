package com.example.weatherappjava.data.model;

import com.google.gson.annotations.SerializedName;

public class WeatherWind {
    @SerializedName("speed")
    public String spedWind;
    @SerializedName("deg")
    public String windAzimuth;
}
