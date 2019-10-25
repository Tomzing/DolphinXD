package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Main;
import Dolphin.Model.Arrangement;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class BrukerHovedvisningController {

    @FXML
    private Button gaaTilLoggInnKnapp;

    @FXML
    private ListView<Arrangement> arrangementListView;

    public void initialize() {
        ObservableList<Arrangement> listeMedAlleArrangementer = DataHandler.hentListeMedAlleArrangementer();

        arrangementListView.setItems(listeMedAlleArrangementer);

        //arrangementListView.getItems().addAll(listeMedAlleArrangementer);
    }


    @FXML
    public void gaaTilLoggInn() {

        System.out.println("Du har nå logget ut :)");

        Main minApplikasjon = Main.getInstance();

        minApplikasjon.gaaTilLoggInn();
    }
}
