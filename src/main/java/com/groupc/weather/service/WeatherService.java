package com.groupc.weather.service;

import java.net.URLEncoder;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.groupc.weather.dto.request.common.OpenWeatherDto;
import com.groupc.weather.dto.request.common.WeatherDto;
@Service
public class WeatherService {
    private String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    @Value("${open-weather-key}")

    private String openWeatherKey;


    public WeatherDto getWeatherData(String location) {

        WeatherDto dto = null;
        try{
            StringBuilder urlBuilder = new StringBuilder(BASE_URL);
            urlBuilder.append("?" + URLEncoder.encode("q", "UTF-8") + "=" + location);
            urlBuilder.append("&" + URLEncoder.encode("appid", "UTF-8") + "=" + openWeatherKey);
            urlBuilder.append("&" + URLEncoder.encode("lang", "UTF-8") + "=kr");
            urlBuilder.append("&" + URLEncoder.encode("units", "UTF-8") + "=metric");

            RestTemplate restTemplate = new RestTemplate();
            OpenWeatherDto response = restTemplate.getForObject(urlBuilder.toString(), OpenWeatherDto.class);

            JSONObject jsonObject = new JSONObject(response);

            String main_value = jsonObject.getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("main");

            String description_value = jsonObject.getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("description");

            double temp_value = jsonObject.getJSONObject("main")
                    .getDouble("temp");

            dto = new WeatherDto();

            dto.setWeatherMain(main_value);
            dto.setWeatherDescription(description_value);
            dto.setTemperature((int) Math.round(temp_value)); // double -> int
        }
        catch(Exception exception){
            exception.printStackTrace();
        }

        return dto;
    }

    
}
