package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Main;
import Dolphin.Model.ArrangementAnnet;
import Dolphin.Model.ArrangementSykkelritt;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AdminHovedvisningController {

    @FXML
    private Button gaaTilLoggInnKnapp;

    @FXML
    private ImageView adminBilde;

    public void initialize() {

        //Få liste med arrangementer fra Datahandler
        ObservableList<ArrangementSykkelritt> listeMedSykkelrittArrangementer = DataHandler.hentListeMedSykkelrittArrangementer();
        ObservableList<ArrangementAnnet> listeMedAnnetArrangementer = DataHandler.hentListeMedAnnetArrangementer();

        //System.out.println(listeMedSykkelrittArrangementer.get(1).getNavn());
        //System.out.println(listeMedAnnetArrangementer.get(1).getNavn());

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