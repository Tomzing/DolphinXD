package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Main;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Bruker;
import Dolphin.Model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class BrukerprofilController {

    private Main minApplikasjon = Main.getInstance();

    private Person valgtBruker;

    @FXML
    private Text txtNavn, txtBrukernavn, txtFodselsdato;

    @FXML
    private ListView<Arrangement> lvArrangementer, lvMineArrangementer;

    @FXML
    private Button btnEndre;

    public void initialize() {
        Bruker aktivBruker = minApplikasjon.getAktivBruker();
        valgtBruker = minApplikasjon.getValgtBruker();

        if (aktivBruker.getBrukerId() != valgtBruker.getBrukerId()) {
            btnEndre.setVisible(false);
        }

        txtNavn.setText(valgtBruker.getFornavn() + " " + valgtBruker.getEtternavn());
        txtBrukernavn.setText(valgtBruker.getBrukernavn());
        txtFodselsdato.setText(valgtBruker.getFodselsdato().toString());

        ObservableList<Arrangement> arrangementer = DataHandler.hentArrangementer();

        lvArrangementer.setItems(hentPaameldteArrangementer(arrangementer));

        lvMineArrangementer.setItems(hentMineArrangementer(arrangementer));
    }

    private ObservableList<Arrangement> hentMineArrangementer(ObservableList<Arrangement> arrangementer) {
        ObservableList<Arrangement> mineArrangementer = FXCollections.observableArrayList();
        for (Arrangement a : arrangementer) {
            if (a.getArrangor().getBrukerId() == valgtBruker.getBrukerId()) {
                mineArrangementer.add(a);
            }
        }
        return mineArrangementer;
    }

    private ObservableList<Arrangement> hentPaameldteArrangementer(ObservableList<Arrangement> arrangementer) {
        ObservableList<Arrangement> brukerArrangementer = FXCollections.observableArrayList();

        for (Arrangement a : arrangementer) {
            for (Person p : a.getDeltakereOppmeldt()) {
                if (p.getBrukerId() == valgtBruker.getBrukerId()) {
                    brukerArrangementer.add(a);
                }
            }
        }

        return brukerArrangementer;
    }

    @FXML
    private void gaaTilValgtArrangement() {
        Arrangement valgtArrangement = lvArrangementer.getSelectionModel().getSelectedItem();

        if (valgtArrangement != null) {
            minApplikasjon.setValgtArrangement(valgtArrangement);
            minApplikasjon.aapneArrangementliste();
        }
    }

    @FXML
    private void endreValgtArrangement() {
        Arrangement valgtArrangement = lvMineArrangementer.getSelectionModel().getSelectedItem();
        if (valgtArrangement != null) {
            minApplikasjon.setValgtArrangement(valgtArrangement);
            minApplikasjon.aapneNyttArrangement();
        }
    }
}
