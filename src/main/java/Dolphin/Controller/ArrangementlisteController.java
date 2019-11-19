package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Main;
import Dolphin.Model.Arrangement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

public class ArrangementlisteController {

    private Main minApplikasjon = Main.getInstance();

    @FXML
    private GridPane gpInformasjon;

    @FXML
    private Text txtNavn, txtArrangor, txtVanskelighetsgrad, txtKategori, txtLedigePlasser, txtSted, txtStarttid, txtSluttid, txtPris;

    @FXML
    private ListView<Arrangement> arrangementListView;

    @FXML
    private CheckBox utlopteArrangementerChkBx;

    @FXML
    private ComboBox<String> velgSorteringCB;

    @FXML
    private Button btnMerInfo;

    private Arrangement valgtArrangement;

    private ObservableList<Arrangement> arrangementListe;

    public void initialize() {
        valgtArrangement = minApplikasjon.getValgtArrangement();

        arrangementListe = DataHandler.hentArrangementer();

        velgSorteringCB.setItems(FXCollections.observableArrayList("Sorter alfabetisk på navn",
                "Sorter på type alfabetisk", "Sorter på antall plasser igjen"));

        velgSorteringCB.getSelectionModel().select(0);

        sorterListe();

        arrangementListView.setItems(arrangementListe);

        visInfo();

        arrangementListView.getSelectionModel().selectedItemProperty().addListener((liste, gammel, ny) -> {
            valgtArrangement = arrangementListView.getSelectionModel().getSelectedItem();

            minApplikasjon.setValgtArrangement(valgtArrangement);

            visInfo();
        });

    }

    private ObservableList<Comparator<Arrangement>> hentSorteringsListe() {
        ArrayList<Comparator<Arrangement>> sorteringer = new ArrayList<>();
        sorteringer.add(Arrangement::compareTo);
        sorteringer.add(Comparator.comparing(Arrangement::getType));
        sorteringer.add((a1, a2) -> a2.getLedigePlasser() - a1.getLedigePlasser());

        return FXCollections.observableArrayList(sorteringer);
    }

    //Metode for å "fjerne" utgåtte datoer fra listviewet, returnerer true eller false basert på om sjekkboksen er sjekket av eller ikke.
    public boolean gjemUtgatteArrangementer() {
        arrangementListe = DataHandler.hentArrangementer();
        sorterListe();
        visInfo();

        if (utlopteArrangementerChkBx.isSelected()) {
            arrangementListView.setItems(arrangementListe);
            return false;

        }
        else {
            ObservableList<Arrangement> tempListe = FXCollections.observableArrayList();

            for (Arrangement a : arrangementListe) {
                LocalDateTime naa = LocalDateTime.now();
                LocalDateTime sluttid = a.getSluttid();

                if (sluttid.compareTo(naa) > 0) {
                    tempListe.add(a);
                }
            }

            arrangementListe = tempListe;
            arrangementListView.setItems(arrangementListe);
            if (arrangementListe.contains(valgtArrangement)) {
                arrangementListView.getSelectionModel().select(valgtArrangement);
            }
            return true;
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
    private void visMerInfo() {
        minApplikasjon.setValgtArrangement(valgtArrangement);
        minApplikasjon.aapneArrangement();
    }

    @FXML
    private void sorterListe() {
        visInfo();
        ObservableList<Comparator<Arrangement>> sorteringer = hentSorteringsListe();

        arrangementListe.sort(sorteringer.get(velgSorteringCB.getSelectionModel().getSelectedIndex()));

        arrangementListView.setItems(arrangementListe);

        /*
        if (arrangementListe.contains(valgtArrangement)) {
            arrangementListView.getSelectionModel().select(valgtArrangement);
        }
        */
    }

    private void visInfo() {
        boolean vises = valgtArrangement != null;
        txtNavn.setVisible(vises);
        txtArrangor.setVisible(vises);
        gpInformasjon.setVisible(vises);
        btnMerInfo.setVisible(vises);
        if (vises) {
            arrangementListView.getSelectionModel().select(valgtArrangement);
            fyllUtArrangementInfo(valgtArrangement);
        }
    }
}
