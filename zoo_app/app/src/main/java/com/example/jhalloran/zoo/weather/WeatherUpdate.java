package com.example.jhalloran.zoo.weather;

import org.openweathermap.api.DataWeatherClient;
import org.openweathermap.api.UrlConnectionDataWeatherClient;
import org.openweathermap.api.model.currentweather.CurrentWeather;
import org.openweathermap.api.query.Language;
import org.openweathermap.api.query.QueryBuilderPicker;
import org.openweathermap.api.query.ResponseFormat;
import org.openweathermap.api.query.Type;
import org.openweathermap.api.query.UnitFormat;
import org.openweathermap.api.query.currentweather.CurrentWeatherOneLocationQuery;

/**
 * Handles interaction with {@link #"https://openweathermap.org/api"} weather API.
 */
public class WeatherUpdate {

  private static final String API_KEY = "18c2ddc15226e8acc7ad7cc14b990871";

  public WeatherUpdate() {
  }

  /**
   * Queries weather server for Current weather data in London, UK. Uses Metric units.
   *
   * @return current weather for London, UK
   */
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
