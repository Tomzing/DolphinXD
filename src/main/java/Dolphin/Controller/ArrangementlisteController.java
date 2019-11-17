package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Main;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Bruker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.time.LocalDateTime;

public class ArrangementlisteController {

    private Main minApplikasjon = Main.getInstance();

    @FXML
    private Text txtNavn, txtArrangor, txtVanskelighetsgrad, txtKategori, txtLedigePlasser, txtSted, txtStarttid, txtSluttid, txtPris;


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
        valgtArrangement = minApplikasjon.getValgtArrangement();

        //arrangementListView.setItems(listeMedAlleArrangementer);
        arrangementListView.setItems(arrangementListe);

        if (valgtArrangement != null) {
            arrangementListView.getSelectionModel().select(valgtArrangement);
            fyllUtFilmInfo(valgtArrangement);
        }

        velgSorteringCB.setItems(FXCollections.observableArrayList("Sorter alfabetisk på navn",
                "Sorter på type alfabetisk", "Sorter på antall plasser igjen"));


        arrangementListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Arrangement>() {
                    public void changed(ObservableValue<? extends Arrangement> observable,
                                        Arrangement oldValue, Arrangement newValue) {
                        valgtArrangement = arrangementListView.getSelectionModel().getSelectedItem();

                        if(valgtArrangement != null) {

                            velgArrangement();

                            fyllUtFilmInfo(valgtArrangement);

                            minApplikasjon.setValgtArrangement(valgtArrangement);
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

    private void fyllUtFilmInfo(Arrangement arrangement) {
        txtNavn.setText(arrangement.getNavn());
        txtArrangor.setText("Arrangert av " + arrangement.getArrangor());
        txtKategori.setText(arrangement.getType());
        txtLedigePlasser.setText(String.valueOf(arrangement.getLedigePlasser()));
        txtSted.setText(arrangement.getSted());
        txtStarttid.setText(arrangement.getStarttid().toString());
        txtSluttid.setText(arrangement.getSluttid().toString());
        txtPris.setText((arrangement.getPris() == 0 ? "Gratis" : arrangement.getPris() + " kr"));
        txtVanskelighetsgrad.setText(arrangement.getVanskelighetsgrad());
    }

    @FXML
    public void velgArrangement() {
        valgtArrangement = arrangementListView.getSelectionModel().getSelectedItem();
        minApplikasjon.setValgtArrangement(valgtArrangement);
    }

    @FXML
    public void visMerInfo() {
        if (valgtArrangement != null) {
            minApplikasjon.aapneNyttVindu("arrangement");
        }
    }
}
