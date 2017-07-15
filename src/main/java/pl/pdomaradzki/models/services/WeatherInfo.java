package pl.pdomaradzki.models.services;

import pl.pdomaradzki.models.Utils;

/**
 * Created by jaizm on 15/07/2017.
 */
public class WeatherInfo {
    private double temp;
    private int humidity;
    private int pressure;
    private int cloudy;
    private String cityName;

    public WeatherInfo(double temp, int humidity, int pressure, int cloudy, String cityName) {
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.cloudy = cloudy;
        this.cityName = cityName;
    }

    public double getTemperatureKelvin(){
        return temp;
    }

    public double getTemperatureCelsius(){
        return Utils.kelvinToCelsius(temp);
    }

    public double getTemp() {
        return temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public int getCloudy() {
        return cloudy;
    }

    public String getCityName() {
        return cityName;
    }
}
