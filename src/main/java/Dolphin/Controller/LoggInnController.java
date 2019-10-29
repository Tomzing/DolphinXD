package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Main;
import Dolphin.Model.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoggInnController {

    @FXML
    private TextField inputBrukernavn;

    @FXML
    private TextField inputPassord;

    private Main minApplikasjon = Main.getInstance();

    //Få liste med brukere fra DataHandler
    private ObservableList<Bruker> listeMedBrukere = DataHandler.hentListeMedBrukere();

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

       if(loggInnKjorer(inputBrukernavn.getText(),inputPassord.getText(),false)){
           gaaTilBrukerHovedvisning();
           //Tekst for suksessfull login
           return "Du er innlogget :D";
       }
       else {
           //Tekst for failet login
           return "Brukernavnet eller passord er feil";
       }
    }

    public boolean loggInnKjorer(String brukerNavn, String brukerPassord, boolean testBoolean){
        for (Bruker bruker : listeMedBrukere) {
            if (bruker.getBrukernavn().equals(brukerNavn)) {
                if (bruker.getPassord().equals(brukerPassord)) {
                    if(!testBoolean) {
                        setAktivBruker(bruker);
                    }
                    System.out.println("Gratulerer du er innlogget, " + bruker.getFornavn());

                    return true;
                }
            }
        }
        System.out.println("Brukernavnet eller passord er feil");
        return false;
    }

    @FXML
    private void gaaTilBrukerHovedvisning() {
        minApplikasjon.gaaTilBrukerHovedvisning();
    }

    @FXML
    public void loggInnAdmin() {
        System.out.println("Gratulerer du er admin :)");

        minApplikasjon.gaaTilAdminHovedvisning();
    }

    @FXML
    public void gaaTilNyBruker() {
        minApplikasjon.gaaTilNyBruker();
    }

    public void setAktivBruker(Bruker bruker) {
        minApplikasjon.setAktivBruker(bruker);
    }
}

