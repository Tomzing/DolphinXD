package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Main;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AdminHovedvisningController {

    private static final String bildefil = "Bilder/cooldude.jpg";

    private Main minApplikasjon = Main.getInstance();

    @FXML
    private ImageView adminBilde;

    @FXML
    private ListView<Person> brukerListView;

    @FXML
    private ListView<Arrangement> arrangementListView;

    public void initialize() {

        Image image = new Image(bildefil);
        adminBilde.setImage(image);

        brukerListView.setItems(DataHandler.hentListeMedPersoner());
        arrangementListView.setItems(DataHandler.hentArrangementer());
    }

    @FXML
    private void endreBruker() {
        Person valgtBruker = brukerListView.getSelectionModel().getSelectedItem();
        minApplikasjon.setValgtBruker(valgtBruker);
        minApplikasjon.aapneNyBruker();
    }

    @FXML
    private void slettBruker() {
        Person valgtBruker = brukerListView.getSelectionModel().getSelectedItem();
        DataHandler.slettPerson(valgtBruker);
        brukerListView.setItems(DataHandler.hentListeMedPersoner());
    }

    @FXML
    private void endreArrangement() {
        Arrangement valgtArrangement = arrangementListView.getSelectionModel().getSelectedItem();
        minApplikasjon.setValgtArrangement(valgtArrangement);
        minApplikasjon.aapneNyttArrangement();
    }

    @FXML
    private void slettArrangement() {
        Arrangement valgtArrangement = arrangementListView.getSelectionModel().getSelectedItem();
        DataHandler.slettArrangement(valgtArrangement);
        arrangementListView.setItems(DataHandler.hentArrangementer());
    }
}

