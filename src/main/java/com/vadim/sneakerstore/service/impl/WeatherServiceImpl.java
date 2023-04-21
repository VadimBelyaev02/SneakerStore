package com.vadim.sneakerstore.service.impl;

import com.vadim.sneakerstore.model.WeatherInfo;
import com.vadim.sneakerstore.service.WeatherService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Service
public class WeatherServiceImpl implements WeatherService {

    private static final String API_KEY = "e7c378f9fa90c7b0adbf677542fbafaa";

    @Override
    public WeatherInfo getWeatherInfo(String city) {
        WeatherInfo weather = new WeatherInfo();
        try {
            // URL для запроса погоды в Минске
            String urlString = "https://api.openweathermap.org/data/2.5/weather?q="+city+",BY&units=metric&appid=" + API_KEY;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // считываем ответ в формате JSON
            Scanner sc = new Scanner(url.openStream());
            StringBuilder sb = new StringBuilder();
            while (sc.hasNext()) {
                sb.append(sc.nextLine());
            }
            sc.close();
            String json = sb.toString();

            // преобразуем JSON в объект Java
            weather = parseJson(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weather;
    }

    private static WeatherInfo parseJson(String json) {
        JSONObject obj = new JSONObject(json);
        JSONObject main = obj.getJSONObject("main");
        double temperature = main.getDouble("temp");
        double feelsLike = main.getDouble("feels_like");
        int humidity = main.getInt("humidity");
        String description = obj.getJSONArray("weather").getJSONObject(0).getString("description");
        return new WeatherInfo(temperature, feelsLike, humidity, description);
    }
}
