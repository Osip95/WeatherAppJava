package com.example.weatherappjava.data;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.weatherappjava.data.model.Weather;
import com.example.weatherappjava.data.model.WeatherMain;
import com.example.weatherappjava.data.model.WeatherWind;
import com.example.weatherappjava.ui.MainFragmentViewModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class Repository {
    final String BASE_URL = "https://api.openweathermap.org/data/2.5/", APIKEY = "58b397ef6b2ac4874ee79550212748d0";
    OkHttpClient client;
    Retrofit retrofit;
    WeatherApi weatherApi;
    Context context;
    MainFragmentViewModel mainFragmentViewModel;

    public Repository(Context context, MainFragmentViewModel mainFragmentViewModel) {
        client = new OkHttpClient.Builder().build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        weatherApi = retrofit.create(WeatherApi.class);
        this.context = context;
        this.mainFragmentViewModel = mainFragmentViewModel;
    }

    public void getWeatherInfo(String city) {
         weatherApi.getWeather(city,APIKEY).
                 subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Weather>() {
                    @Override
                    public void onNext(Weather weather) {
                        weather.main.temperature = convertTemperature(weather.main.temperature);
                        weather.main.temperatureMax = convertTemperature(weather.main.temperatureMax);
                        weather.main.temperatureMin = convertTemperature(weather.main.temperatureMin);
                        mainFragmentViewModel.acceptData(weather);
                        saveInfoToDB(weather,city);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Weather weather = getWeatherInfoFromDB(city);
                        mainFragmentViewModel.acceptData(weather);
                    }
                    @Override
                    public void onComplete() {}
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {}
                });

    }


    private void saveInfoToDB(Weather weather, String city) {
        ContentValues cv = new ContentValues();
        cv.put("speed", weather.wind.spedWind);
        cv.put("city", city);
        cv.put("temper", weather.main.temperature);
        cv.put("temperMax", weather.main.temperatureMax);
        cv.put("temperMin", weather.main.temperatureMin);
        cv.put("azimuth", weather.wind.windAzimuth);
        cv.put("time", getTime());
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updCount = db.update("mytable", cv, "city = ?",
                new String[]{city});
        if (updCount == 0) {
            db.insert("mytable", null, cv);
        }
        dbHelper.close();
    }


    private Weather getWeatherInfoFromDB(String city) {
        Weather weather = new Weather();
        weather.wind = new WeatherWind();
        weather.main = new WeatherMain();
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "city = ?";
        String[] selectionArgs = new String[]{city};
        Cursor c = db.query("mytable", null, selection, selectionArgs, null, null, null);

        if (c.moveToFirst()) {
            int tempColIndex = c.getColumnIndex("temper");
            int windColIndex = c.getColumnIndex("speed");
            int timeColIndex = c.getColumnIndex("time");
            int temperMaxColIndex = c.getColumnIndex("temperMax");
            int temperMinColIndex = c.getColumnIndex("temperMin");
            int azimuthColIndex = c.getColumnIndex("azimuth");
            weather.main.temperature = c.getString(tempColIndex);
            weather.main.temperatureMax = c.getString(temperMaxColIndex);
            weather.main.temperatureMin = c.getString(temperMinColIndex);
            weather.wind.spedWind = c.getString(windColIndex);
            weather.wind.windAzimuth = c.getString(azimuthColIndex);
            weather.timeWeather = c.getString(timeColIndex);
        }
        c.close();
        dbHelper.close();
        return weather;
    }

    private String getTime() {
        Date date = new Date(System.currentTimeMillis());
        String stringDateFormat = "dd.MM.yyyy HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(stringDateFormat, Locale.getDefault());
        return format.format(date);
    }

    private String convertTemperature(String tempK) {
        if (tempK == null) return "";
        String tempC;
        double c = Double.parseDouble(tempK) - 273.15;
        tempC = Double.toString(Math.round(c * 100.0) / 100.0);
        return tempC;
    }
}



