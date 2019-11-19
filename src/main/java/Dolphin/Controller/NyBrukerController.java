package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Main;
import Dolphin.Model.Person;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class NyBrukerController{

    private Main minApplikasjon = Main.getInstance();

    @FXML
    private Text txtOverskrift;

    @FXML
    private TextField txtFornavn;

    @FXML
    private TextField txtEtternavn;

    @FXML
    private DatePicker dpFodselsdato;

    @FXML
    private ChoiceBox<String> cbKjonn;

    @FXML
    private TextField txtBrukernavn;

    @FXML
    private TextField txtPassord;

    private Person valgtBruker;

    public void initialize() {
        valgtBruker = minApplikasjon.getValgtBruker();

        if (valgtBruker != null) {
            txtOverskrift.setText("Endre bruker");
            fyllUtFeltene();
        }

        cbKjonn.setItems(FXCollections.observableArrayList("Mann", "Kvinne","Annet"));

        cbKjonn.getSelectionModel().selectFirst();
    }

    private void fyllUtFeltene() {
        txtFornavn.setText(valgtBruker.getFornavn());
        txtEtternavn.setText(valgtBruker.getEtternavn());
        dpFodselsdato.setValue(valgtBruker.getFodselsdato());
        cbKjonn.getSelectionModel().select(valgtBruker.getKjonn());
        txtBrukernavn.setText(valgtBruker.getBrukernavn());
        txtPassord.setText(valgtBruker.getPassord());
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
        if (valgtBruker != null) {
            endrePerson();
        }
        else {
            nyPerson(txtFornavn.getText(), txtEtternavn.getText(), dpFodselsdato.getValue(), txtBrukernavn.getText(), txtPassord.getText(), cbKjonn.getValue(), false);
        }
    }

    public boolean nyPerson(String fornavn, String etternavn, LocalDate fodselsdato, String brukernavn, String passord, String kjonn, boolean testBoolean){
        if (fornavn.equals("") || etternavn.equals("") || fodselsdato == null || brukernavn.equals("") || passord.equals("")) {
            if (!testBoolean){
                alertError();
                return false;
            }
            else {
                return false;
            }
        }
        else {
            Person bruker = new Person(fornavn, etternavn, fodselsdato, kjonn, brukernavn, passord);

            if(!testBoolean) {
                //Lagret bruker, returnerer til logg inn
                DataHandler.lagrePerson(bruker);
                Main minApplikasjon = Main.getInstance();
                minApplikasjon.aapneLoggInn();
                return true;
            }
        }
        return true;
    }

    private void endrePerson() {
        valgtBruker.setFornavn(txtFornavn.getText());
        valgtBruker.setEtternavn(txtEtternavn.getText());
        valgtBruker.setFodselsdato(dpFodselsdato.getValue());
        valgtBruker.setKjonn(cbKjonn.getValue());
        valgtBruker.setBrukernavn(txtBrukernavn.getText());
        valgtBruker.setPassord(txtPassord.getText());

        DataHandler.endrePerson(valgtBruker);

        minApplikasjon.aapneAdminHovedvisning();
    }

    @FXML
    private void alertError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Feil!");
        alert.setHeaderText("Manglende innhold");
        alert.setContentText("Et av feltene har ingen innhold.");

        alert.showAndWait();
        }

    @FXML
    private void avbryt() {
        //Ikke laget bruker, returnerer til logg inn
        Main minApplikasjon = Main.getInstance();

        minApplikasjon.aapneLoggInn();
    }
}
