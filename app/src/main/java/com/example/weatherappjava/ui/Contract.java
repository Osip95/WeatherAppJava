package com.example.weatherappjava.ui;

import com.example.weatherappjava.data.model.Weather;


class ViewState {
    Boolean isLoading = true;
    Boolean isShowMessage = false;
    String city = "Moscow";
    String temperature = "", temperatureMax = "", temperatureMin = "", spedWind = "", windAzimuth = "", timeWeather = "";

    public ViewState clone() {
        ViewState viewStateClone = new ViewState();
        viewStateClone.isShowMessage = this.isShowMessage;
        viewStateClone.city = this.city;
        viewStateClone.temperature = this.temperature;
        viewStateClone.temperatureMax = this.temperatureMax;
        viewStateClone.temperatureMin = this.temperatureMin;
        viewStateClone.spedWind = this.spedWind;
        viewStateClone.windAzimuth = this.windAzimuth;
        viewStateClone.timeWeather = this.timeWeather;
        return viewStateClone;
    }


}


interface Event {
}

class OnRbMoscow implements Event {
}

class OnRbSaintPetersburg implements Event {
}

class OnRbOmsk implements Event {
}

class OnDataEvent implements Event {
    Weather weather;

    public OnDataEvent(Weather weather) {
        this.weather = weather;
    }
}







