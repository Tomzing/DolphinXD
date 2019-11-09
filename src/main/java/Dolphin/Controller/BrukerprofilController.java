package Dolphin.Controller;

import Dolphin.Main;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Bruker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class BrukerprofilController {

    private Main minApplikasjon = Main.getInstance();

    private Bruker valgtBruker;

    private Bruker aktivBruker;

    @FXML
    private Text txtNavn, txtBrukernavn, txtFodselsdato;

    @FXML
    private ListView<Arrangement> lvArrangementer;

    @FXML
    private Button btnArrangement;

    public void initialize() {
        valgtBruker = minApplikasjon.getValgtBruker();

        txtNavn.setText(valgtBruker.getFornavn() + " " + valgtBruker.getEtternavn());
        txtBrukernavn.setText(valgtBruker.getBrukernavn());
        txtFodselsdato.setText(valgtBruker.getFodselsdato().toString());
    }

}
