package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Bruker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
            ledigePlasser.setText("Ledige plasser: " + valgtArrangement.getAntallPlasser());
        }

        System.out.println("xD");

    }

    @FXML
    public void bliMed() {
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
    }

    @FXML
    public void tilbakeTilArrangement() {
        getMinApplikasjon().gaaTilBrukerHovedvisning();
    }

    public void setValgtArrangement() {
        valgtArrangement = getMinApplikasjon().getValgtArrangement();
    }
}
