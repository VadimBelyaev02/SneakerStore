package com.vadim.sneakerstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
@NoArgsConstructor
public class WeatherInfo {

    // класс, представляющий информацию о погоде
    private double temperature;
    private double feelsLike;
    private int humidity;
    private String description;

    // конструктор и геттеры/сеттеры


}