package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Bruker;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class arrangementlisteController extends InnloggetController {

    @FXML
    private ListView<Arrangement> arrangementListView;

    @FXML
    private Text paaloggetSomText;

    private Bruker aktivBruker;

    private Arrangement valgtArrangement;

    @FXML
    public void initialize() {
        aktivBruker = finnAktivBruker();

        ObservableList<Arrangement> listeMedAlleArrangementer = DataHandler.hentListeMedAlleArrangementer();

        arrangementListView.setItems(listeMedAlleArrangementer);

        paaloggetSomText.setText("Aktiv bruker: " + aktivBruker.toString());

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
