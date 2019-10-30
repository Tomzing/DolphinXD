package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
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

public class ArrangementController extends InnloggetController {
    @FXML
    private Label navn, ledigePlasser;

    @FXML
    private Text beskrivelse;

    @FXML
    private ListView<Bruker> deltagere;

    private Arrangement valgtArrangement;

    @FXML
    public void initialize() {
        setValgtArrangement();
        oppdaterListe();
        System.out.println(deltagere.getItems());

        if (valgtArrangement != null) {
            navn.setText(valgtArrangement.getNavn());
            beskrivelse.setText("Hei");
            ledigePlasser.setText("Ledige plasser: " + (valgtArrangement.getAntallPlasser() - valgtArrangement.getDeltakereOppmeldt().size()));
        }

        System.out.println("xD");

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
        ArrayList<Bruker> deltagere = DataHandler.hentArrangementDeltagere(valgtArrangement);
        Bruker aktiv = getMinApplikasjon().getAktivBruker();

        boolean paameldt = false;

        for (Bruker deltager : deltagere) {
            if (aktiv.getBrukernavn().equals(deltager.getBrukernavn())) {
                paameldt = true;
                break;
            }
        }

        if (!paameldt) {
            valgtArrangement.leggTilNyDeltager(getMinApplikasjon().getAktivBruker());
            oppdaterListe();
            lagreDeltager();
        }
    }

    @FXML
    public void meldAv() {
        Bruker aktiv = getMinApplikasjon().getAktivBruker();
        valgtArrangement.fjernDeltager(aktiv);
        DataHandler.fjernPaameldingTilArrangement(valgtArrangement, aktiv);

        oppdaterListe();
    }

    private void lagreDeltager() {
        try {
            File file = new File("src/main/resources/Database/deltagere.csv");

            FileWriter filSkriver = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bufretCsvSkriver = new BufferedWriter(filSkriver);
            bufretCsvSkriver.write(valgtArrangement.getArrangementId() + ";" + getMinApplikasjon().getAktivBruker().getBrukernavn() + "\n");

            bufretCsvSkriver.flush();
            bufretCsvSkriver.close();
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    @FXML
    private void oppdaterListe() {
        ObservableList<Bruker> deltagereObservableList = FXCollections.observableArrayList(valgtArrangement.getDeltakereOppmeldt());
        deltagere.setItems(deltagereObservableList);
        ledigePlasser.setText("Ledige plasser: " + (valgtArrangement.getAntallPlasser() - valgtArrangement.getDeltakereOppmeldt().size()));
    }

    @FXML
    public void tilbakeTilArrangement() {
        getMinApplikasjon().gaaTilBrukerHovedvisning();
    }

    public void setValgtArrangement() {
        valgtArrangement = getMinApplikasjon().getValgtArrangement();
    }
}
