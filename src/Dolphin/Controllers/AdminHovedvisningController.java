package Dolphin.Controllers;

import Dolphin.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminHovedvisningController {

    @FXML
    private Button gaaTilLoggInnKnapp;

    @FXML
    private ImageView adminBilde;

    public void initialize() {
        //String path = "src\\Dolphin\\Ressurser\\cooldude.jpg";
        String path = "https://c7.alamy.com/comp/A1WBAM/cool-dude-young-boy-approaching-his-teenage-years-A1WBAM.jpg";
        Image image = new Image(path);
        adminBilde.setImage(image);
    }

    @FXML
    public void gaaTilLoggInn() {

        System.out.println("Du har nå logget ut :)");

        Main minApplikasjon = Main.getInstance();

        minApplikasjon.gaaTilLoggInn();
    }
}
