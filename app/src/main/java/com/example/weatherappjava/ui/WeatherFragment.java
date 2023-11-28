package com.example.weatherappjava.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.weatherappjava.R;



public class WeatherFragment extends Fragment {
    private MainFragmentViewModel mainFragmentViewModel;
    RadioGroup radioGroup;
    TextView tvTemp,tvTempMax,tvTempMin,tvWindSpeed,tvWindAzimuth,tvMessage;
    ProgressBar progressBar;


    public WeatherFragment() {
        super(R.layout.weather_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        radioGroup = view.findViewById(R.id.radioGroupCities);
        tvTemp = view.findViewById(R.id.tvTemperature);
        tvTempMax = view.findViewById(R.id.tvMaxTemp);
        tvTempMin = view.findViewById(R.id.tvMinTem);
        tvWindSpeed = view.findViewById(R.id.tvSpeedWind);
        tvWindAzimuth = view.findViewById(R.id.tvAzimutWind);
        tvMessage = view.findViewById(R.id.tvMessage);
        progressBar = view.findViewById(R.id.pB);
        mainFragmentViewModel = new MainFragmentViewModel(requireActivity().getApplicationContext());

        mainFragmentViewModel.liveDataViewState.observe(getViewLifecycleOwner(), this::render);


        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbMoscow)
                mainFragmentViewModel.processUIEvent(new OnRbMoscow());
            if (checkedId == R.id.rbSaintPetersburg)
                mainFragmentViewModel.processUIEvent(new OnRbSaintPetersburg());
            if (checkedId == R.id.rbOmsk) mainFragmentViewModel.processUIEvent(new OnRbOmsk());
        });
    }

    private void render(ViewState viewState) {
        String textMessageError;
        if(viewState.temperature == null) textMessageError = getString(R.string.message_text_no_network_and_no_db);
        else textMessageError = getString(R.string.message_text_no_network);

        switch (viewState.city) {
            case "Moscow":
                radioGroup.check(R.id.rbMoscow);
                break;
            case "Saint Petersburg":
                radioGroup.check(R.id.rbSaintPetersburg);
                break;
            case "Omsk":
                radioGroup.check(R.id.rbOmsk);
                break;
        }

        tvTemp.setText(getString(R.string.tv_title_temperature) + " " + viewState.temperature + " C");
        tvTempMax.setText(getString(R.string.tv_title_max_temp) + " " + viewState.temperatureMax+ " C");
        tvTempMin.setText(getString(R.string.tv_title_min_temp) + " " + viewState.temperatureMin+ " C");
        tvWindSpeed.setText(getString(R.string.tv_title_wind_speed) + " " + viewState.spedWind+ " м/с");
        tvWindAzimuth.setText(getString(R.string.tv_title_wind_azimut) + " " + viewState.windAzimuth+ " град.");
        tvMessage.setText(textMessageError + " " + viewState.timeWeather);
        tvMessage.setVisibility(viewState.isShowMessage ? View.VISIBLE : View.GONE);
        progressBar.setVisibility(viewState.isLoading ? View.VISIBLE : View.GONE);

    }




}
