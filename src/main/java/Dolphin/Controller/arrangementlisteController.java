package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Bruker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class arrangementlisteController extends InnloggetController {

    @FXML
    private ListView<Arrangement> arrangementListView;

    @FXML
    private Text paaloggetSomText;

    @FXML
    private CheckBox utlopteArrangementerChkBx;

    private Bruker aktivBruker;

    private Arrangement valgtArrangement;

    ObservableList<Arrangement> listeMedAlleArrangementer = DataHandler.hentListeMedAlleArrangementer();

    @FXML
    public void initialize() {
        aktivBruker = finnAktivBruker();


        arrangementListView.setItems(listeMedAlleArrangementer);

        paaloggetSomText.setText("Aktiv bruker: " + aktivBruker.toString());

    }

    @FXML
    //Metode for å "fjerne" utgåtte datoer fra listviewet, returnerer true eller false basert på om sjekkboksen
    //er sjekket av eller ikke. Hvis sjekket, filtrer listview. Ikke sjekket, "legg tilbake" forrige oppsett
    public boolean gjemUtgatteArrangementer() {
        ObservableList<Arrangement> tempListeMedAlleArrangementer = FXCollections.observableArrayList();

        if (utlopteArrangementerChkBx.isSelected()) {
            for (int i = 0; i < listeMedAlleArrangementer.size(); i++) {
                LocalDateTime naa = LocalDateTime.now();
                LocalDateTime objTid = arrangementListView.getItems().get(i).getStartDato();
                
                if(objTid.compareTo(naa) > 0) {
                    tempListeMedAlleArrangementer.add(listeMedAlleArrangementer.get(i));
                }
            }
            arrangementListView.setItems(tempListeMedAlleArrangementer);

            return true;
        }
        else {
                arrangementListView.setItems(listeMedAlleArrangementer);

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
            getMinApplikasjon().gaaTilArrangement();
        }
    }

    public Arrangement getValgtArrangement() {
        return valgtArrangement;
    }
}
