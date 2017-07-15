package pl.pdomaradzki.models.services;

import javafx.application.Platform;
import org.json.JSONObject;
import pl.pdomaradzki.models.Config;
import pl.pdomaradzki.models.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jaizm on 15/07/2017.
 */
public class WeatherService {
    private static WeatherService ourInstance = new WeatherService();

    public static WeatherService getService() { return ourInstance; }

    //Dane API
    private String appurl;
    private String cityName;

    private double temp;
    private int humidity;
    private int pressure;
    private int cloudy;

    //Observer pattern
    List<IWeatherObserver> observerList = new ArrayList<>();

    private ExecutorService executorService;

    private WeatherService() {
        executorService = Executors.newFixedThreadPool(2);

    }

    public void makeCall(String city, String country) {
            executorService.execute(() -> {
                String link = Config.APPURL + "weather" + "?q=" + city + "," + country + "&appid=" + Config.APPID;
                parseJsonData(Utils.connectAndResponse(link), city);
            });
    }

    private synchronized void parseJsonData(String data, String city) {
        JSONObject rootObject = new JSONObject(data);
        JSONObject mainObject = rootObject.getJSONObject("main");

        temp = mainObject.getDouble("temp");
        humidity = mainObject.getInt("humidity");
        pressure = mainObject.getInt("pressure");

        JSONObject cloudsObject = rootObject.getJSONObject("clouds");
        cloudy = cloudsObject.getInt("all");

        cityName = city;

        Platform.runLater(() -> informObserver());
    }

    public void  addWeatherObserver(IWeatherObserver observer){
        observerList.add(observer);
    }

    public void removeWeatherObserver(IWeatherObserver observer){
        observerList.remove(observer);
    }

    private void informObserver(){
        WeatherInfo weatherInfo = new WeatherInfo(temp, humidity, pressure, cloudy, cityName);
        observerList.forEach(s -> {
            s.updateWeather(weatherInfo);
        });
    }
}
