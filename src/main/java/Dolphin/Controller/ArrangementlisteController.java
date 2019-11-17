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
import java.util.Collection;
import java.util.Comparator;

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

    private Arrangement valgtArrangement;

    //private ObservableList<Arrangement> listeMedAlleArrangementer = DataHandler.hentListeMedAlleArrangementer();
    private ObservableList<Arrangement> arrangementListe;

    public void initialize() {
        valgtArrangement = minApplikasjon.getValgtArrangement();

        arrangementListe = DataHandler.hentArrangementer();

        velgSorteringCB.setItems(FXCollections.observableArrayList("Sorter alfabetisk på navn",
                "Sorter på type alfabetisk", "Sorter på antall plasser igjen"));

        velgSorteringCB.getSelectionModel().select(0);

        sorterListe();

        arrangementListView.setItems(arrangementListe);

        if (valgtArrangement != null) {
            arrangementListView.getSelectionModel().select(valgtArrangement);
            fyllUtArrangementInfo(valgtArrangement);
        }

        arrangementListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Arrangement>() {
                    public void changed(ObservableValue<? extends Arrangement> observable,
                                        Arrangement oldValue, Arrangement newValue) {
                        valgtArrangement = arrangementListView.getSelectionModel().getSelectedItem();

                        if(valgtArrangement != null) {

                            velgArrangement();

                            fyllUtArrangementInfo(valgtArrangement);

                            minApplikasjon.setValgtArrangement(valgtArrangement);
                        }
                    }
                });

    }

    private ObservableList<Comparator<Arrangement>> hentSorteringsListe() {
        return FXCollections.observableArrayList(
                new Comparator<Arrangement>() {
                    @Override
                    public int compare(Arrangement a1, Arrangement a2) {
                        return a1.compareTo(a2);
                    }
                },
                new Comparator<Arrangement>() {
                    @Override
                    public int compare(Arrangement a1, Arrangement a2) {
                        return a1.getType().compareTo(a2.getType());
                    }
                },
                new Comparator<Arrangement>() {
                    @Override
                    public int compare(Arrangement a1, Arrangement a2) {
                        return a2.getLedigePlasser() - a1.getLedigePlasser();
                    }
                }
        );
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

    private void fyllUtArrangementInfo(Arrangement arrangement) {
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

    @FXML
    private void sorterListe() {
        ObservableList<Comparator<Arrangement>> sorteringer = hentSorteringsListe();

        arrangementListe.sort(sorteringer.get(velgSorteringCB.getSelectionModel().getSelectedIndex()));

        arrangementListView.setItems(arrangementListe);
    }
}
