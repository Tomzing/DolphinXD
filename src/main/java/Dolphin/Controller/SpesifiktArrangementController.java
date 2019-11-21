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

public class SpesifiktArrangementController {

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
            btnMeldPaa.setVisible(false);
            btnMeldAv.setVisible(false);
        } else if (aktivBruker instanceof Person) {
            if (erArrangor(valgtArrangement, aktivBruker)) {
                btnMeldPaa.setVisible(false);
                btnMeldAv.setVisible(false);
            } else {
                btnMeldAvBruker.setVisible(false);
            }
        } else {
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

        bekreftBetaling.getButtonTypes().setAll(bekreftBetalingBtn, avbrytBetalingBtn);

        Optional<ButtonType> resultat = bekreftBetaling.showAndWait();
        if (resultat.get() == bekreftBetalingBtn) {
            betalingsSystem(true);
            bekreftBetaling.close();
            return true;
        } else if (resultat.get() == avbrytBetalingBtn) {
            betalingsSystem(false);

        }
        return false;
    }

    //I et ferdig produkt ville dette systemet vært ekstremt utvidet, derfor er det en egen metode
    public static boolean betalingsSystem(boolean bekreftelse) {
        if (bekreftelse) {
            return true;
        } else {
            System.out.println("Betaling feilet, du er ikke meldt på");
        }
        return false;
    }

    //Melder en bruker på et arrangament
    // bør ble reformatert for å splitte metode og JavaFX
    @FXML
    private void meldPaa() {
        Bruker aktivBruker = minApplikasjon.getAktivBruker();
        Arrangement aktivtArrangement = valgtArrangement;
        String meldPaa = meldPaaBruker(aktivBruker, aktivtArrangement, false);


        if (meldPaa.equals("meldPaa") && (valgtArrangement.getPris() == 0 || betalForArrangement())) {
            valgtArrangement.leggTilNyDeltager((Person) aktivBruker);
            oppdaterListe();
            DataHandler.lagreDeltager(aktivBruker, valgtArrangement);
        }

        else {
            //Åpner for mulige meldingsbugs, men lar oss gi riktig feilmelding hvor arrangement ikke er betalt for, ettersom switch case ikke lett lar oss gjøre dette
            //Hvis betalForArrangement() var i meldPaaBruker ville vi ikke kunnet lage en test pga. JavaFX
            String headerText = "Ikke betalt";
            String contentText = "Du har ikke betalt for arrangementet";
            switch (meldPaa) {
                case "erUtgaatt":
                    headerText ="Utgått arrangement";
                    contentText ="Arrangementet du prøver å melde deg på er utgått dessverre.";
                    break;
                case "erArrangor":
                    headerText = "Du er Arrangor";
                    contentText = "Du kan ikke melde deg på et arrangement når du er Admin. Bytt bruker til en non-admin bruker";
                    break;
                case "erPaameldt":
                    headerText = "Du er allerede påmeldt";
                    contentText = "Du har allerede meldt deg på dette arrangementet";
                    break;
                case "erFullt":
                    headerText = "Det er fullt!";
                    contentText = "Dette arrangementet har desverre ingen plasser igjen";
                    break;
                case "erOpptatt":
                    headerText = "Du er opptatt";
                    contentText = "Du er allerede påmeldt et arrangement i denne perioden. Kontakt arrangør for å bli meldt på manuelt";
            }

            if(!meldPaa.equals("null")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Feil!");
                alert.setHeaderText(headerText);
                alert.setContentText(contentText);
                alert.showAndWait();
            }
        }
        if (meldPaa.equals("null")) {
            if (skalGaaTilLoggInn()) {
                minApplikasjon.setValgtArrangement(valgtArrangement);
                minApplikasjon.aapneLoggInn();
            }
        }
    }

    //testboolean åpner for mulighet til å unngå betaling via "hacking, men er bare ment som midlertidig testbarhet til et større betalingsystem ville vært implentert
    public String meldPaaBruker(Bruker bruker, Arrangement arrangement, boolean testBoolean) {

        String returnMelding = "feilMelding";
        boolean erAdmin = erArrangor(arrangement, bruker);
        boolean erOpptatt = erOpptatt(arrangement, bruker);
        boolean erPaameldt = erPaameldt(arrangement, bruker);
        boolean erFullt = erFullt(arrangement);
        boolean erUtgaatt = erUtgaatt(arrangement);


        if (bruker instanceof Person) {
            if (!erAdmin && !erPaameldt && !erFullt && !erOpptatt && !erUtgaatt) {
                returnMelding = "meldPaa";
            } else {
                if (erAdmin) {
                    returnMelding = "erArrangor";
                } else if (erUtgaatt) {
                    returnMelding = "erUtgatt";
                } else if (erPaameldt) {
                    returnMelding = "erPaameldt";
                } else if (erOpptatt) {
                    returnMelding = "erOpptatt";
                } else if (erFullt) {
                    returnMelding = "erFullt";
                }
            }
        }
        return returnMelding;
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
    private boolean erArrangor(Arrangement arrangement, Bruker bruker) {
        Bruker arrangor = arrangement.getArrangor();
        ArrayList<Person> administratorer = arrangement.getAdministratorer();
        if (bruker.getBrukernavn().equals(arrangor.getBrukernavn())) {
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
    private boolean erPaameldt(Arrangement arrangement, Bruker bruker) {
        ArrayList<Person> deltagere = arrangement.getDeltakereOppmeldt();

        for (Person deltager : deltagere) {
            if (bruker.getBrukernavn().equals(deltager.getBrukernavn())) {
                return true;
            }
        }
        return false;
    }
    private boolean erFullt(Arrangement arrangement){
        int plasser = arrangement.getLedigePlasser();
        return plasser <= 0;
    }

    //Sjekker om et arrangament allerede har skjedd
    // kan bli reformatert for å splitte metode og JavaFX
    private boolean erUtgaatt(Arrangement arrangement) {
        LocalDateTime sluttid = arrangement.getSluttid();
        LocalDateTime naa = LocalDateTime.now();
        return sluttid.compareTo(naa) < 0;
    }

    //Sjekker om bruker er allerede påmeldt et arrangement som foregår på samme tid som det de prøver å melde seg på
    //Bør bli reformatert for å splitte metode og JavaFX
    private boolean erOpptatt(Arrangement arrangement, Bruker bruker) {
        ArrayList<Arrangement> arrangementer = new ArrayList<>(DataHandler.hentArrangementer());

        for (Arrangement a2 : arrangementer) {
            ArrayList<Person> deltagere = a2.getDeltakereOppmeldt();
            for (Person deltager : deltagere) {
                if (!bruker.getBrukernavn().equals(deltager.getBrukernavn())) {
                    continue;
                }
                LocalDateTime a1Start = arrangement.getStarttid();
                LocalDateTime a1Slutt = arrangement.getSluttid();
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
