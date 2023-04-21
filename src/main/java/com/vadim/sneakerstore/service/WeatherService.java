package com.vadim.sneakerstore.service;


import com.vadim.sneakerstore.model.WeatherInfo;

public interface WeatherService {


        // API ключ, полученный на сайте OpenWeatherMap

        // метод для получения информации о погоде в Минске
        WeatherInfo getWeatherInfo(String city);

}
