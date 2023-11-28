package com.example.weatherappjava.data;

import com.example.weatherappjava.data.model.Weather;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("weather")
    Observable<Weather> getWeather(
            @Query("q") String query,
            @Query("appid") String apiKey);

}
