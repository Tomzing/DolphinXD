package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AdminHovedvisningController {

    @FXML
    private ImageView adminBilde;

    @FXML
    private ListView brukerListView;

    @FXML
    private ListView arrangementListView;

    @FXML
    private Button nyBrukerButton;

    @FXML
    private Button endreBrukerButton;

    @FXML
    private Button slettBrukerButton;

    @FXML
    private Button nyArrangementButton;

    @FXML
    private Button endreArrangementButton;

    @FXML
    private Button slettArrangementButton;

    public void initialize() {

        //System.out.println(listeMedSykkelrittArrangementer.get(1).getNavn());
        //System.out.println(listeMedAnnetArrangementer.get(1).getNavn());

        String path = "Bilder/cooldude.jpg";
        Image image = new Image(path);
        adminBilde.setImage(image);

        brukerListView.setItems(DataHandler.hentListeMedBrukere());

        arrangementListView.setItems(DataHandler.hentListeMedAlleArrangementer());
    }
}
