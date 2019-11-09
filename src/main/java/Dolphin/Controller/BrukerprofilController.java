package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Main;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Bruker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class BrukerprofilController {

    private Main minApplikasjon = Main.getInstance();

    private Bruker valgtBruker;

    private Bruker aktivBruker;

    @FXML
    private Text txtNavn, txtBrukernavn, txtFodselsdato;

    @FXML
    private ListView<Arrangement> lvArrangementer;

    public void initialize() {
        valgtBruker = minApplikasjon.getValgtBruker();

        txtNavn.setText(valgtBruker.getFornavn() + " " + valgtBruker.getEtternavn());
        txtBrukernavn.setText(valgtBruker.getBrukernavn());
        txtFodselsdato.setText(valgtBruker.getFodselsdato().toString());

        lvArrangementer.setItems(hentPaameldteArrangementer(DataHandler.hentArrangementer()));
    }

    private ObservableList<Arrangement> hentPaameldteArrangementer(ObservableList<Arrangement> arrangementer) {
        ObservableList<Arrangement> brukerArrangementer = FXCollections.observableArrayList();

        for (Arrangement a : arrangementer) {
            for (Bruker b : a.getDeltakereOppmeldt()) {
                if (b.getBrukernavn().equals(valgtBruker.getBrukernavn())) {
                    brukerArrangementer.add(a);
                }
            }
        }

        return brukerArrangementer;
    }

    public void gaaTilValgtArrangement() {
        Arrangement valgtArrangement = lvArrangementer.getSelectionModel().getSelectedItem();

        if (valgtArrangement != null) {
            minApplikasjon.setValgtArrangement(valgtArrangement);
            minApplikasjon.aapneNyttVindu("arrangementliste");
        }
    }

}
