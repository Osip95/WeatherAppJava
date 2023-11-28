package com.example.weatherappjava.data.model;

import com.google.gson.annotations.SerializedName;

public class WeatherMain {
    @SerializedName("temp")
    public String temperature;
    @SerializedName("temp_max")
    public String temperatureMax;
    @SerializedName("temp_min")
    public String temperatureMin;
}
