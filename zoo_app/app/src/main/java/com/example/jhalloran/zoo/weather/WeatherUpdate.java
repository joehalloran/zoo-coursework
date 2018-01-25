package com.example.jhalloran.zoo.weather;

import org.openweathermap.api.DataWeatherClient;
import org.openweathermap.api.UrlConnectionDataWeatherClient;
import org.openweathermap.api.model.currentweather.CurrentWeather;
import org.openweathermap.api.query.*;
import org.openweathermap.api.query.currentweather.CurrentWeatherOneLocationQuery;

/**
 * Created by jhalloran on 1/18/18.
 */
public class WeatherUpdate {
  private static final String API_KEY = "18c2ddc15226e8acc7ad7cc14b990871";

  public WeatherUpdate() {}

  public CurrentWeather getCurrentWeather() {
    DataWeatherClient client = new UrlConnectionDataWeatherClient(API_KEY);
    CurrentWeatherOneLocationQuery currentWeatherOneLocationQuery = QueryBuilderPicker.pick()
        .currentWeather()                   // get current weather
        .oneLocation()                      // for one location
        .byCityName("London")               // for London
        .countryCode("UK")                  // in UK
        .type(Type.ACCURATE)                // with Accurate search
        .language(Language.ENGLISH)         // in English language
        .responseFormat(ResponseFormat.JSON)// with JSON response format
        .unitFormat(UnitFormat.METRIC)      // in metric units
        .build();
    return client.getCurrentWeather(currentWeatherOneLocationQuery);
  }
}
