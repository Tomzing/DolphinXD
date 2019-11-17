package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Main;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Bruker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class BrukerprofilController {

    private Main minApplikasjon = Main.getInstance();

    private Bruker valgtBruker;

    private Bruker aktivBruker;

    @FXML
    private Text txtNavn, txtBrukernavn, txtFodselsdato;

    @FXML
    private ListView<Arrangement> lvArrangementer, lvMineArrangementer;

    @FXML
    private Button btnEndre;

    public void initialize() {
        valgtBruker = minApplikasjon.getValgtBruker();

        txtNavn.setText(valgtBruker.getFornavn() + " " + valgtBruker.getEtternavn());
        txtBrukernavn.setText(valgtBruker.getBrukernavn());
        txtFodselsdato.setText(valgtBruker.getFodselsdato().toString());

        ObservableList<Arrangement> alleArrangementer = DataHandler.hentArrangementer();

        lvArrangementer.setItems(hentPaameldteArrangementer(alleArrangementer));
        lvMineArrangementer.setItems(hentMineArrangementer(alleArrangementer));
    }

    private ObservableList<Arrangement> hentMineArrangementer(ObservableList<Arrangement> arrangementer) {
        ObservableList<Arrangement> mineArrangementer = FXCollections.observableArrayList();
        for (Arrangement a : arrangementer) {
            if (a.getArrangor().getBrukernavn().equals(valgtBruker.getBrukernavn())) {
                mineArrangementer.add(a);
            }
        }
        return mineArrangementer;
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

    public void endreValgtArrangement() {
        Arrangement valgtArrangement = lvMineArrangementer.getSelectionModel().getSelectedItem();
        if (valgtArrangement != null) {
            minApplikasjon.setValgtArrangement(valgtArrangement);
            minApplikasjon.aapneNyttVindu("nyttarrangement");
        }
    }
}
