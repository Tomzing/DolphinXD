package Dolphin.Controller;

import Dolphin.Main;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class NyBrukerController{

    @FXML
    private TextField inputFornavn;

    @FXML
    private TextField inputEtternavn;

    @FXML
    private TextField inputAar;

    @FXML
    private ChoiceBox<String> valgBoksKjonn;

    @FXML
    private TextField inputBrukernavn;

    @FXML
    private TextField inputPassord;

    private static final String filnavn = "src/main/resources/Database/brukere.csv";

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
        if (erTall(inputAar.getText())) {
            nyBruker(inputFornavn.getText(),inputEtternavn.getText(),inputAar.getText(),inputBrukernavn.getText(),inputPassord.getText(),valgBoksKjonn.getValue(), false);
        }
        else {
            alertError("Feil!","Feil format i antall år", "Bare skriv inn antall år du er, i feltet 'antall år'");
        }



    }
    private void nyBruker(String fornavn, String etternavn, String aar, String brukernavn, String passord, String kjonn, boolean testBoolean){


        String string = fornavn + ";" + etternavn + ";" + aar + ";" + kjonn
                + ";" + brukernavn + ";" + passord + "\n";

        System.out.println(string);

        if (fornavn.equals("") || etternavn.equals("") || aar.equals("") || brukernavn.equals("") || passord.equals("")) {
            alertError("Feil!","Manglende innhold","Et av feltene har ingen innhold.");
        }
        else {
            try {
                File file = new File(filnavn);

                FileWriter filSkriver = new FileWriter(file.getAbsoluteFile(), true);
                BufferedWriter bufferedCsvSkriver = new BufferedWriter(filSkriver);

                bufferedCsvSkriver.write(string);

                bufferedCsvSkriver.flush();
                bufferedCsvSkriver.close();
                if(!testBoolean) {
                    //Lagret bruker, returnerer til logg inn
                    Main minApplikasjon = Main.getInstance();
                    minApplikasjon.gaaTilLoggInn();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

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
