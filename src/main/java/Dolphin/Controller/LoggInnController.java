package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Main;
import Dolphin.Model.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class LoggInnController {

    @FXML
    private TextField txtBrukernavn;

    @FXML
    private TextField txtPassord;

    private Main minApplikasjon = Main.getInstance();

    private Arrangement valgtArrangement;

    //Alt inne i initialize kan slettes, blitt brukt til å teste innhenting av alle arrangementene
    public void initialize() {
        valgtArrangement = minApplikasjon.getValgtArrangement();
    }

    //Dårlig innlogging til programmet
    public String loggInnBruker() {

       if(loggInnKjorer(txtBrukernavn.getText(),txtPassord.getText(),false)){
           if (valgtArrangement != null) {
               minApplikasjon.aapneArrangement();
           }
           else {
               minApplikasjon.aapneArrangementliste();
           }
           //Tekst for suksessfull login
           return "Du er innlogget :D";
       }
       else {
           //Tekst for failet login
           return "Brukernavnet eller passord er feil";
       }
    }

    public boolean loggInnKjorer(String brukerNavn, String brukerPassord, boolean testBoolean){
        ObservableList<Person> brukere = DataHandler.hentListeMedPersoner();
        for (Person bruker : brukere) {
            if (bruker.getBrukernavn().equals(brukerNavn)) {
                if (bruker.getPassord().equals(brukerPassord)) {
                    if(!testBoolean) {
                        minApplikasjon.setAktivBruker(bruker);
                    }
                    return true;
                }
            }
        }
        System.out.println("Brukernavnet eller passord er feil");
        return false;
    }

    /*
    @FXML
    private void gaaTilBrukerHovedvisning() {
        minApplikasjon.gaaTilBrukerHovedvisning();
    }
     */

    @FXML
    public void loggInnAdmin() {
        ObservableList<SystemAdmin> administratorer = DataHandler.hentListeMedSysAdmin();
        for (SystemAdmin admin : administratorer) {
            if (admin.getBrukernavn().equals(txtBrukernavn.getText()) && admin.getPassord().equals(txtPassord.getText())) {
                minApplikasjon.setAktivBruker(admin);
                minApplikasjon.aapneAdminHovedvisning();
            }
        }
    }

    @FXML
    public void gaaTilNyBruker() {
        minApplikasjon.aapneNyBruker();
    }

    @FXML
    private void skrivInnAdminInfo() {
        ObservableList<SystemAdmin> sysAdministratorer = DataHandler.hentListeMedSysAdmin();
        String brukernavn = sysAdministratorer.get(0).getBrukernavn();
        String passord = sysAdministratorer.get(0).getPassord();

        txtBrukernavn.setText(brukernavn);
        txtPassord.setText(passord);
    }

    @FXML
    private void skrivInnPersonInfo() {
        ObservableList<Person> personer = DataHandler.hentListeMedPersoner();
        if (personer.isEmpty()) {
            DataHandler.hentListeMedAlleBrukere();
            DataHandler.lagrePerson(new Person("Test", "Testesen", LocalDate.parse("1999-06-09"), "Mann", "tester123", "passord"));
            skrivInnPersonInfo();
        }
        else {
            String brukernavn = personer.get(0).getBrukernavn();
            String passord = personer.get(0).getPassord();
            txtBrukernavn.setText(brukernavn);
            txtPassord.setText(passord);
        }
    }
}

