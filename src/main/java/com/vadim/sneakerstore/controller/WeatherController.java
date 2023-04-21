package com.vadim.sneakerstore.controller;

import com.vadim.sneakerstore.dto.AddressDto;
import com.vadim.sneakerstore.model.WeatherInfo;
import com.vadim.sneakerstore.service.AddressService;
import com.vadim.sneakerstore.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @Operation(description = "Get weather by city name")
    @ApiResponse(description = "Weather is found", responseCode = "200")
    @GetMapping("/{city}")
    @ResponseStatus(HttpStatus.OK)
    public WeatherInfo getAddress(@Parameter(description = "Id of needed city")
                                 @PathVariable("city") String id) {
        return service.getWeatherInfo(id);
    }
}



