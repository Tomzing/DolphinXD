package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Main;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Bruker;
import Dolphin.Model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class ArrangementController {

    private Main minApplikasjon = Main.getInstance();

    @FXML
    private Label navn, ledigePlasser;

    @FXML
    private Text txtVanskelighetsgrad, txtKategori, txtArrangor, txtSted, txtPris, txtStarttid, txtSluttid, txtBeskrivelse;

    @FXML
    private ListView<Bruker> deltagere;

    @FXML
    private Button btnMeldPaa, btnMeldAv, btnMeldAvBruker;

    private Arrangement valgtArrangement;

    private Bruker aktivBruker;

    @FXML
    private void initialize() {
        valgtArrangement = minApplikasjon.getValgtArrangement();
        aktivBruker = minApplikasjon.getAktivBruker();
        oppdaterListe();

        deaktiverKnapper();

        navn.setText(valgtArrangement.getNavn());
        txtArrangor.setText("Arrangert av " + valgtArrangement.getArrangor());
        txtPris.setText((valgtArrangement.getPris() == 0 ? "Gratis" : valgtArrangement.getPris() + " kr"));
        txtKategori.setText(valgtArrangement.getType());
        txtVanskelighetsgrad.setText(valgtArrangement.getVanskelighetsgrad());
        txtSted.setText(valgtArrangement.getSted());
        txtStarttid.setText(valgtArrangement.getStarttid().toString());
        txtSluttid.setText(valgtArrangement.getSluttid().toString());
        txtBeskrivelse.setText(valgtArrangement.getBeskrivelse());
    }

    private void deaktiverKnapper() {
        if (aktivBruker == null) {
            btnMeldAvBruker.setVisible(false);
        }
        else if (aktivBruker instanceof Person) {
            if (erAdmin()) {
                btnMeldPaa.setVisible(false);
                btnMeldAv.setVisible(false);
            } else {
                btnMeldAvBruker.setVisible(false);
            }
        }
        else {
            btnMeldPaa.setVisible(false);
            btnMeldAv.setVisible(false);
        }
    }

    //Betalingsmetode hvor hvis man "betaler" så returnerer den true, hvis ikke false
    private boolean betalForArrangement() {
        Alert bekreftBetaling = new Alert(Alert.AlertType.CONFIRMATION);
        bekreftBetaling.setTitle("Betaling");
        bekreftBetaling.setHeaderText("Bekreft betaling for påmelding");
        bekreftBetaling.setContentText("Her så ville det vært en mulighet for å betale for " +
                "valgte arrangement. Trykk betal for å 'betale', trykk avbryt for å feile 'betalingen'");
        bekreftBetaling.setWidth(250);
        bekreftBetaling.setHeight(250);

        ButtonType bekreftBetalingBtn = new ButtonType("Bekreft betaling");
        ButtonType avbrytBetalingBtn = new ButtonType("Avbryt", ButtonBar.ButtonData.CANCEL_CLOSE);

        bekreftBetaling.getButtonTypes().setAll(bekreftBetalingBtn,avbrytBetalingBtn);

        Optional<ButtonType> resultat = bekreftBetaling.showAndWait();

        if(resultat.get() == bekreftBetalingBtn) {
            bekreftBetaling.close();

            return true;
        }
        else if(resultat.get() == avbrytBetalingBtn) {
            System.out.println("Betaling feilet, du er ikke meldt på");
        }
        return false;
    }

    @FXML
    private void meldPaa() {
        Bruker aktivBruker = minApplikasjon.getAktivBruker();
        if (aktivBruker instanceof Person) {
            if (!erAdmin() && !erPaameldt()) {
                if (!erOpptatt() && !erUtgaatt()) {
                    if (valgtArrangement.getPris() == 0 || betalForArrangement()) {
                        valgtArrangement.leggTilNyDeltager((Person) aktivBruker);
                        oppdaterListe();
                        DataHandler.lagreDeltager(aktivBruker, valgtArrangement);
                    }
                }
            }
        }
        else if (aktivBruker == null) {
            minApplikasjon.setValgtArrangement(valgtArrangement);
            minApplikasjon.aapneLoggInn();
        }
    }

    private boolean erAdmin() {
        Bruker arrangor = valgtArrangement.getArrangor();
        ArrayList<Person> administratorer = valgtArrangement.getAdministratorer();
        if (aktivBruker.getBrukernavn().equals(arrangor.getBrukernavn())) {
            return true;
        }
        for (Bruker admin : administratorer) {
            if (aktivBruker.getBrukernavn().equals(admin.getBrukernavn())) {
                return true;
            }
        }
        return false;
    }

    private boolean erPaameldt() {
        ArrayList<Person> deltagere = valgtArrangement.getDeltakereOppmeldt();

        for (Person deltager : deltagere) {
            if (aktivBruker.getBrukernavn().equals(deltager.getBrukernavn())) {
                return true;
            }
        }
        return false;
    }

    private boolean erUtgaatt() {
        LocalDateTime sluttid = valgtArrangement.getSluttid();
        LocalDateTime naa = LocalDateTime.now();
        return sluttid.compareTo(naa) < 0;
    }

    private boolean erOpptatt() {
        ArrayList<Arrangement> arrangementer = new ArrayList<>(DataHandler.hentArrangementer());

        for (Arrangement a2 : arrangementer) {
            ArrayList<Person> deltagere = a2.getDeltakereOppmeldt();
            for (Person deltager : deltagere) {
                if (!aktivBruker.getBrukernavn().equals(deltager.getBrukernavn())) {
                    continue;
                }
                LocalDateTime a1Start = valgtArrangement.getStarttid();
                LocalDateTime a1Slutt = valgtArrangement.getSluttid();
                LocalDateTime a2Start = a2.getStarttid();
                LocalDateTime a2Slutt = a2.getSluttid();
                if (a1Start.compareTo(a2Slutt) < 0 && a1Slutt.compareTo(a2Start) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @FXML
    private void meldAv() {
        valgtArrangement.fjernDeltager(aktivBruker);
        DataHandler.fjernPaameldingTilArrangement(valgtArrangement, aktivBruker);
        oppdaterListe();
    }

    @FXML
    private void meldAvBruker() {
        Bruker valgtBruker = deltagere.getSelectionModel().getSelectedItem();
        if (valgtBruker != null) {
            valgtArrangement.fjernDeltager(valgtBruker);
            DataHandler.fjernPaameldingTilArrangement(valgtArrangement, valgtBruker);
            oppdaterListe();
        }
    }

    private void oppdaterListe() {
        ObservableList<Bruker> deltagereObservableList = FXCollections.observableArrayList(valgtArrangement.getDeltakereOppmeldt());
        deltagere.setItems(deltagereObservableList);
        ledigePlasser.setText("Ledige plasser: " + (valgtArrangement.getLedigePlasser()));
    }

    @FXML
    public void tilbakeTilArrangementliste() {
        minApplikasjon.aapneArrangementliste();
    }
}
