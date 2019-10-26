package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Main;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Bruker;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class BrukerHovedvisningController {

    @FXML
    private Button gaaTilLoggInnKnapp;

    @FXML
    private ListView<Arrangement> arrangementListView;

    @FXML
    private Text paaloggetSomText;

    public static String brukerNavn;

    Bruker aktivBruker;

    ObservableList<Bruker> listeMedBrukere = DataHandler.hentListeMedBrukere();

    public void initialize() {

        ObservableList<Arrangement> listeMedAlleArrangementer = DataHandler.hentListeMedAlleArrangementer();

        arrangementListView.setItems(listeMedAlleArrangementer);

        paaloggetSomText.setText("Aktiv bruker: " + finnAktivBrukerObjekt().toString());

    }

    //Finner brukeren som er pålogget
    public Bruker finnAktivBrukerObjekt() {
        for(int i = 0; i < listeMedBrukere.size(); i++) {
            if(brukerNavn.equals(listeMedBrukere.get(i).getBrukernavn())) {
                aktivBruker = listeMedBrukere.get(i);
            }
        }
        return aktivBruker;
    }

    static public void setBrukerNavn(String brukerNavnet) {
        brukerNavn = brukerNavnet;
    }



    @FXML
    public void gaaTilLoggInn() {

        System.out.println("Du har nå logget ut :)");

        Main minApplikasjon = Main.getInstance();

        minApplikasjon.gaaTilLoggInn();
    }
}
