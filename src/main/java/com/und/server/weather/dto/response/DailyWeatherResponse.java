package com.und.server.weather.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class DailyWeatherResponse {

    private WeatherResponse weather;
    private AirResponse air;
    private UVResonse uv;

}
