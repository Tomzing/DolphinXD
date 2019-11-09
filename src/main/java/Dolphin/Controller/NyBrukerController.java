package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Main;
import Dolphin.Model.Bruker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class NyBrukerController{

    @FXML
    private TextField inputFornavn;

    @FXML
    private TextField inputEtternavn;

    @FXML
    private DatePicker dpFodselsdato;

    @FXML
    private ChoiceBox<String> valgBoksKjonn;

    @FXML
    private TextField inputBrukernavn;

    @FXML
    private TextField inputPassord;

    ObservableList<Bruker> listeMedBrukere = DataHandler.hentListeMedBrukere();

    public void initialize() {
        valgBoksKjonn.setItems(FXCollections.observableArrayList("Mann", "Kvinne","Annet"));

        valgBoksKjonn.getSelectionModel().selectFirst();
    }

    //Metode for å sjekke om en string er et tall
    public static boolean erTall(final String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        return str.chars().allMatch(Character::isDigit);
    }

    @FXML
    private void lagreNyBruker() {
        //if (erTall(inputAar.getText())) {
            nyBruker(inputFornavn.getText(),inputEtternavn.getText(), dpFodselsdato.getValue(),inputBrukernavn.getText(),inputPassord.getText(),valgBoksKjonn.getValue(), false);
        //}
        //else {
        //    alertError("Feil!","Feil format i antall år", "Bare skriv inn antall år du er, i feltet 'antall år'");
        //}
    }

    public boolean nyBruker(String fornavn, String etternavn, LocalDate fodselsdato, String brukernavn, String passord, String kjonn, boolean testBoolean){
        if (fornavn.equals("") || etternavn.equals("") || fodselsdato.toString().equals("") || brukernavn.equals("") || passord.equals("")) {
            if (!testBoolean){
                alertError("Feil!","Manglende innhold","Et av feltene har ingen innhold.");
                return false;
            }
            else {
                return false;
            }
        }
        else {
            Bruker bruker = new Bruker(fornavn, etternavn, fodselsdato, kjonn, brukernavn, passord);
            listeMedBrukere.add(bruker);

            DataHandler.lagreBruker(bruker);

            if(!testBoolean) {
                //Lagret bruker, returnerer til logg inn
                Main minApplikasjon = Main.getInstance();
                minApplikasjon.gaaTilLoggInn();
                return true;
            }
        }
        return true;
    }
    @FXML
    private void alertError(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
        }

    @FXML
    private void avbryt() {
        //Ikke laget bruker, returnerer til logg inn
        Main minApplikasjon = Main.getInstance();

        minApplikasjon.gaaTilLoggInn();
    }
}
