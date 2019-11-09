package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Main;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Bruker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ArrangementlisteController {

    private Main minApplikasjon = Main.getInstance();

    @FXML
    private TextField txtNavn, txtVanskelighetsgrad, txtSportskategori, txtAntallPlasser, txtSted, txtStarttid, txtSluttid, txtPris;

    @FXML
    private TextArea txtBeskrivelse;

    @FXML
    private ListView<Arrangement> arrangementListView;

    @FXML
    private CheckBox utlopteArrangementerChkBx;

    @FXML
    private ComboBox<String> velgSorteringCB;

    private Bruker aktivBruker;

    private Arrangement valgtArrangement;

    //private ObservableList<Arrangement> listeMedAlleArrangementer = DataHandler.hentListeMedAlleArrangementer();
    private ObservableList<Arrangement> arrangementListe = DataHandler.hentArrangementer();

    public void initialize() {
        aktivBruker = minApplikasjon.getAktivBruker();

        //arrangementListView.setItems(listeMedAlleArrangementer);
        arrangementListView.setItems(arrangementListe);

        velgSorteringCB.setItems(FXCollections.observableArrayList("Sorter alfabetisk på navn",
                "Sorter på type alfabetisk", "Sorter på antall plasser igjen"));

        arrangementListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Arrangement>() {
                    public void changed(ObservableValue<? extends Arrangement> observable,
                                        Arrangement oldValue, Arrangement newValue) {
                        Arrangement valgteArrangementet = arrangementListView.getSelectionModel().getSelectedItem();

                        if(valgteArrangementet != null) {

                            velgArrangement();

                            txtNavn.setText(valgteArrangementet.getNavn());
                            txtSportskategori.setText(valgteArrangementet.getType());
                            txtAntallPlasser.setText(String.valueOf(valgteArrangementet.getAntallPlasser()));
                            txtSted.setText(valgteArrangementet.getSted());
                            txtStarttid.setText(valgteArrangementet.getStarttid().toString());
                            txtSluttid.setText(valgteArrangementet.getSluttid().toString());
                            txtPris.setText(String.valueOf(valgteArrangementet.getPris()));
                            txtBeskrivelse.setText(valgteArrangementet.getBeskrivelse());
                            txtVanskelighetsgrad.setText(valgteArrangementet.getVanskelighetsgrad());
                        }
                    }
                });
    }

    //Metode for å "fjerne" utgåtte datoer fra listviewet, returnerer true eller false basert på om sjekkboksen
    //er sjekket av eller ikke. Hvis sjekket, filtrer listview. Ikke sjekket, "legg tilbake" forrige oppsett
    public boolean gjemUtgatteArrangementer() {
        ObservableList<Arrangement> tempListeMedAlleArrangementer = FXCollections.observableArrayList();

        if (utlopteArrangementerChkBx.isSelected()) {
            //for (int i = 0; i < listeMedAlleArrangementer.size(); i++) {
            for (int i = 0; i < arrangementListe.size(); i++) {
                LocalDateTime naa = LocalDateTime.now();
                LocalDateTime objTid = arrangementListView.getItems().get(i).getStarttid();

                if (objTid.compareTo(naa) > 0) {
                    //tempListeMedAlleArrangementer.add(listeMedAlleArrangementer.get(i));
                    tempListeMedAlleArrangementer.add(arrangementListe.get(i));
                }
            }
            arrangementListView.setItems(tempListeMedAlleArrangementer);

            return true;
        } else {
            //arrangementListView.setItems(listeMedAlleArrangementer);
            arrangementListView.setItems(arrangementListe);

            arrangementListView.refresh();

            return false;

        }
    }

    @FXML
    public void velgArrangement() {
        valgtArrangement = arrangementListView.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void visMerInfo() {
        if (valgtArrangement != null) {
            minApplikasjon.setValgtArrangement(valgtArrangement);
            minApplikasjon.aapneNyttVindu("arrangement");
        }
    }

    public Arrangement getValgtArrangement() {
        return valgtArrangement;
    }
}
