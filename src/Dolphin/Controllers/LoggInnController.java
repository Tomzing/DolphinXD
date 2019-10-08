package Dolphin.Controllers;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Main;
import Dolphin.Model.Bruker;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoggInnController {

    @FXML
    private TextField inputBrukernavn;

    @FXML
    private TextField inputPassord;

    @FXML
    private Button loggInnBrukerKnapp;

    @FXML
    private Button loggInnAdminKnapp;

    //Få liste med brukere fra DataHandler
    ObservableList<Bruker> listeMedBrukere = DataHandler.hentListeMedBrukere();


    //Dårlig innlogging til programmet
    public String loggInnBruker() {
        for (int i = 0; i < listeMedBrukere.size(); i++) {
            if(listeMedBrukere.get(i).getBrukernavn().equals(inputBrukernavn.getText())) {
                if (listeMedBrukere.get(i).getPassord().equals(inputPassord.getText())) {
                    System.out.println("Gratulerer du er innlogget, " + listeMedBrukere.get(i).getFornavn());

                    gaaTilBrukerHovedvisning();

                    return "Du er innlogget :D";
                }
            }
        }
        System.out.println("Brukernavnet eller passord er feil");
        return "Brukernavnet eller passord er feil";
    }
    @FXML
    public void gaaTilBrukerHovedvisning() {
        Main minApplikasjon = Main.getInstance();

        minApplikasjon.gaaTilBrukerHovedvisning();
    }

    @FXML
    public void loggInnAdmin() {
        System.out.println("Gratulerer du er admin :)");

        Main minApplikasjon = Main.getInstance();

        minApplikasjon.gaaTilAdminHovedvisning();
    }
}

