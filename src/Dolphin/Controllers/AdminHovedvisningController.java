package Dolphin.Controllers;

import Dolphin.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminHovedvisningController {

    @FXML
    private Button gaaTilLoggInnKnapp;

    @FXML
    public void gaaTilLoggInn() {

        System.out.println("Du har nå logget ut :)");

        Main minApplikasjon = Main.getInstance();

        minApplikasjon.gaaTilLoggInn();
    }
}
