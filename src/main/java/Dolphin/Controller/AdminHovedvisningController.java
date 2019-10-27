package Dolphin.Controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AdminHovedvisningController extends InnloggetController {

    @FXML
    private ImageView adminBilde;

    public void initialize() {

        //System.out.println(listeMedSykkelrittArrangementer.get(1).getNavn());
        //System.out.println(listeMedAnnetArrangementer.get(1).getNavn());

        String path = "Bilder/cooldude.jpg";
        Image image = new Image(path);
        adminBilde.setImage(image);
    }
}
