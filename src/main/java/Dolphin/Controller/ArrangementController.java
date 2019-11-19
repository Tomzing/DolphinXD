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

    //Fjerner knapper som ikke er relevant fra viewet til innlogget person
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
    private boolean  betalForArrangement() {
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
        if(resultat.get() == bekreftBetalingBtn){
            betalingsSystem(true);
            bekreftBetaling.close();
            return true;
        }
        else if(resultat.get() == avbrytBetalingBtn){
            betalingsSystem(false);

        }
        return false;
    }

    //I et ferdig produkt ville dette systemet vært ekstremt utvidet, derfor er det en egen metode
    public static boolean betalingsSystem(boolean bekreftelse){
        if(bekreftelse) {
            return true;
        }
        else  {
            System.out.println("Betaling feilet, du er ikke meldt på");
        }
        return false;
    }

    //Melder en bruker på et arrangament
    // bør ble reformatert for å splitte metode og JavaFX
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
                else if(erUtgaatt()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Feil!");
                    alert.setHeaderText("Utgått arrangement");
                    alert.setContentText("Arrangementet du prøver å melde deg på er utgått dessverre.");

                    alert.showAndWait();
                }
            }
        }
        else if (aktivBruker == null) {
            if (skalGaaTilLoggInn()) {
                minApplikasjon.setValgtArrangement(valgtArrangement);
                minApplikasjon.aapneLoggInn();
            }
        }
    }

    private boolean skalGaaTilLoggInn() {
        ButtonType btnJa = new ButtonType("Ja", ButtonBar.ButtonData.YES);
        ButtonType btnNei = new ButtonType("Nei", ButtonBar.ButtonData.NO);

        Alert bekreft = new Alert(Alert.AlertType.CONFIRMATION);
        bekreft.setTitle("Ikke innlogget");
        bekreft.setHeaderText("Du er ikke innlogget!");
        bekreft.setContentText("Du må være innlogget for å melde deg på et arrangement.\nVil du gå til siden for å logge inn?");
        bekreft.getButtonTypes().clear();
        bekreft.getButtonTypes().addAll(btnJa, btnNei);
        bekreft.showAndWait();

        return bekreft.getResult() == btnJa;
    }

    //Sjekker om en bruker er administrator
    // kan bli reformatert for å splitte metode og JavaFX
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

    //Sjekker om en bruker er allerede påmeldt til et arrangement
    // kan reformatert for å splitte metode og JavaFX
    private boolean erPaameldt() {
        ArrayList<Person> deltagere = valgtArrangement.getDeltakereOppmeldt();

        for (Person deltager : deltagere) {
            if (aktivBruker.getBrukernavn().equals(deltager.getBrukernavn())) {
                return true;
            }
        }
        return false;
    }

    //Sjekker om et arrangament allerede har skjedd
    // kan bli reformatert for å splitte metode og JavaFX
    private boolean erUtgaatt() {
        LocalDateTime sluttid = valgtArrangement.getSluttid();
        LocalDateTime naa = LocalDateTime.now();
        return sluttid.compareTo(naa) < 0;
    }

    //Sjekker om bruker er allerede påmeldt et arrangement som foregår på samme tid som det de prøver å melde seg på
    //Bør bli reformatert for å splitte metode og JavaFX
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

    //Melder en bruker av et arrangement
    //kan bli reformatert for å splitte metode og JavaFX
    @FXML
    private void meldAv() {
        if (aktivBruker != null) {
            valgtArrangement.fjernDeltager(aktivBruker);
            DataHandler.fjernPaameldingTilArrangement(valgtArrangement, aktivBruker);
            oppdaterListe();
        }
    }

    //Melder en bruker av et arrangement
    //kan bli reformatert for å splitte metode og JavaFX
    @FXML
    private void meldAvBruker() {
        Bruker valgtBruker = deltagere.getSelectionModel().getSelectedItem();
        if (valgtBruker != null) {
            valgtArrangement.fjernDeltager(valgtBruker);
            DataHandler.fjernPaameldingTilArrangement(valgtArrangement, valgtBruker);
            oppdaterListe();
        }
    }

    //Oppdaterer listviweet i JavaFX med valgt objekt
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
