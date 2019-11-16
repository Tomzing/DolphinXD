package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Main;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Bruker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class ArrangementController {

    private Main minApplikasjon = Main.getInstance();

    @FXML
    private Label navn, ledigePlasser;

    @FXML
    private Text beskrivelse, pris;

    @FXML
    private ListView<Bruker> deltagere;

    private Arrangement valgtArrangement;

    @FXML
    public void initialize() {
        valgtArrangement = minApplikasjon.getValgtArrangement();
        oppdaterListe();
        System.out.println(deltagere.getItems());

        System.out.println("xD");

        navn.setText(valgtArrangement.getNavn());
        beskrivelse.setText(valgtArrangement.getBeskrivelse());
        pris.setText("Pris: " + String.valueOf(valgtArrangement.getPris()));

    }

    //Betalingsmetode hvor hvis man "betaler" så returnerer den true, hvis ikke false
    @FXML
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
            meldPaa();

            bekreftBetaling.close();

            return true;
        }
        else if(resultat.get() == avbrytBetalingBtn) {
            System.out.println("Betaling feilet, du er ikke meldt på");
        }
        return false;
    }

    @FXML
    public void meldPaa() {
        ArrayList<Bruker> deltagere = DataHandler.hentArrangementBrukerliste(valgtArrangement, "deltagere.csv");
        Bruker aktiv = minApplikasjon.getAktivBruker();

        boolean paameldt = false;

        for (Bruker deltager : deltagere) {
            if (aktiv.getBrukernavn().equals(deltager.getBrukernavn())) {
                paameldt = true;
                break;
            }
        }

        if (!paameldt) {
            valgtArrangement.leggTilNyDeltager(minApplikasjon.getAktivBruker());
            oppdaterListe();
            DataHandler.lagreDeltager(aktiv, valgtArrangement);
        }
    }

    @FXML
    public void meldAv() {
        Bruker aktiv = minApplikasjon.getAktivBruker();
        valgtArrangement.fjernDeltager(aktiv);
        DataHandler.fjernPaameldingTilArrangement(valgtArrangement, aktiv);

        oppdaterListe();
    }

    @FXML
    private void oppdaterListe() {
        ObservableList<Bruker> deltagereObservableList = FXCollections.observableArrayList(valgtArrangement.getDeltakereOppmeldt());
        deltagere.setItems(deltagereObservableList);
        ledigePlasser.setText("Ledige plasser: " + (valgtArrangement.getLedigePlasser()));
    }

    @FXML
    public void tilbakeTilArrangement() {
        minApplikasjon.aapneNyttVindu("arrangementliste");
    }
}
