package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Main;
import Dolphin.Model.*;
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

    @FXML
    private Button nyBrukerKnapp;

    String brukerNavnIBruk;

    //Få liste med brukere fra DataHandler
    ObservableList<Bruker> listeMedBrukere = DataHandler.hentListeMedBrukere();

    //Alt inne i initialize kan slettes, blitt brukt til å teste innhenting av alle arrangementene
    public void initialize() {
        /*
        ObservableList<ArrangementSykkelritt> listeMedSykkelrittArrangementer = DataHandler.hentListeMedSykkelrittArrangementer();
        ObservableList<ArrangementAnnet> listeMedAnnetArrangementer = DataHandler.hentListeMedAnnetArrangementer();
        //ObservableList<ArrangementLop> listeMedLopArrangementer = DataHandler.hentListeMedLopsArrangementer();
        ObservableList<Arrangement> listeMedAlleArrangementer = DataHandler.hentListeMedAlleArrangementer();

        System.out.println(listeMedSykkelrittArrangementer.get(0));
        System.out.println(listeMedAnnetArrangementer.get(0));
        System.out.println(listeMedAnnetArrangementer.get(0));
        //System.out.println(listeMedLopArrangementer.get(0));
        System.out.println(listeMedAlleArrangementer.get(0));
        */
    }

    //Dårlig innlogging til programmet
    public String loggInnBruker() {

       if(loggInnKjorer(inputBrukernavn.getText(),inputPassord.getText())){
           gaaTilBrukerHovedvisning();
           //Tekst for suksessfull login
           return "Du er innlogget :D";
       }
       else {
           //Tekst for failet login
           return "Brukernavnet eller passord er feil";
       }
    }
    public Boolean loggInnKjorer(String brukerNavn, String brukerPassord){
        for (int i = 0; i < listeMedBrukere.size(); i++) {
            if(listeMedBrukere.get(i).getBrukernavn().equals(brukerNavn)) {
                if (listeMedBrukere.get(i).getPassord().equals(brukerPassord)) {
                    System.out.println("Gratulerer du er innlogget, " + listeMedBrukere.get(i).getFornavn());

                    setBrukernavnIBruk(brukerNavn);

                    return true;
                }
            }
        }
        System.out.println("Brukernavnet eller passord er feil");
        return false;
    }
    //Overføre brukernavnet fra LoggInnController til BrukerHovedVisningController hvis vellykket pålogging
    public void setBrukernavnIBruk(String brukernavn) {
        brukerNavnIBruk = brukernavn;
    }

    public String getBrukernavnIBruk() {
        return brukerNavnIBruk;
    }

    @FXML
    public void gaaTilBrukerHovedvisning() {
        Main minApplikasjon = Main.getInstance();

        minApplikasjon.gaaTilBrukerHovedvisning(getBrukernavnIBruk());
    }

    @FXML
    public void loggInnAdmin() {
        System.out.println("Gratulerer du er admin :)");

        Main minApplikasjon = Main.getInstance();

        minApplikasjon.gaaTilAdminHovedvisning();
    }

    @FXML
    public void gaaTilNyBruker() {
        Main minApplikasjon = Main.getInstance();

        minApplikasjon.gaaTilNyBruker();
    }
}

