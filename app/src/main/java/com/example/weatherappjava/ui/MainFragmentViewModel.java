package com.example.weatherappjava.ui;
import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.weatherappjava.data.Repository;
import com.example.weatherappjava.data.model.Weather;



public class MainFragmentViewModel extends ViewModel {

    Repository repository;

    public MainFragmentViewModel(Context context) {
        repository = new Repository(context, this);
    }

    MutableLiveData<ViewState> liveDataViewState = new MutableLiveData<ViewState>(new ViewState());

    void processUIEvent(Event event) {
        updateState(event);
    }


    public void acceptData(Weather weather){
        updateState(new OnDataEvent(weather));
    }


    void updateState(Event event) {
        ViewState newViewState = reduce(event, liveDataViewState.getValue());
        liveDataViewState.postValue(newViewState);

    }

    ViewState reduce(Event event, ViewState previousState) {
        ViewState newViewState = previousState.clone();

        if(event instanceof OnDataEvent){
            Weather weather = ((OnDataEvent) event).weather;
           newViewState.temperature = weather.main.temperature;
           newViewState.temperatureMax = weather.main.temperatureMax;
           newViewState.temperatureMin = weather.main.temperatureMin;
           newViewState.spedWind = weather.wind.spedWind;
           newViewState.windAzimuth = weather.wind.windAzimuth;
           newViewState.timeWeather = weather.timeWeather;
           newViewState.isShowMessage = !weather.timeWeather.equals("") || weather.main.temperature == null;
           newViewState.isLoading = false;
        }

        if (event instanceof OnRbMoscow) {
            newViewState.city = "Moscow";
            newViewState.isLoading = true;
            repository.getWeatherInfo("Moscow");
        }
        if (event instanceof OnRbSaintPetersburg) {
            newViewState.city = "Saint Petersburg";
            newViewState.isLoading = true;
            repository.getWeatherInfo("Saint Petersburg");
        }
        if (event instanceof OnRbOmsk) {
            newViewState.city = "Omsk";
            newViewState.isLoading = true;
            repository.getWeatherInfo("Omsk");
        }
        return newViewState;
    }




}
