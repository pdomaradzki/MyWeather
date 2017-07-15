package pl.pdomaradzki.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import pl.pdomaradzki.models.services.IWeatherObserver;
import pl.pdomaradzki.models.services.WeatherInfo;
import pl.pdomaradzki.models.services.WeatherService;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, IWeatherObserver{

    @FXML
    private Button buttonShowWeather;

    @FXML
    private Label textWeather;

    @FXML
    private TextField weatherCity;

    @FXML
    ProgressIndicator progressBar;

    private WeatherService weatherService = WeatherService.getService();

    public void initialize(URL location, ResourceBundle resources) {
        weatherService.addWeatherObserver(this);

        buttonShowWeather.setOnMouseClicked(e -> {
            if(!weatherCity.getText().toString().isEmpty() && weatherCity.getText().length() > 3) {
                progressBar.setVisible(true);
                weatherService.makeCall(weatherCity.getText(), "pl");
            }else{
                //TODO addthis to Utils Class
                Alert alert = new Alert((Alert.AlertType.INFORMATION));
                alert.setTitle("Błąd");
                alert.setContentText("Nieprawidłowa nazwa misata");
                alert.show();
            }
        });

    }

    @Override
    public void updateWeather(WeatherInfo weatherInfo) {
        textWeather.setText("Temperatura: " + weatherInfo.getTemperatureCelsius() + "\n"
        + "Wilgotność: " + weatherInfo.getHumidity() + "\n"
        + "Ciśnienie: " + weatherInfo.getPressure() + "\n"
        + "Zachmurzenie: " + weatherInfo.getCloudy());

        progressBar.setVisible(false);
    }
}
