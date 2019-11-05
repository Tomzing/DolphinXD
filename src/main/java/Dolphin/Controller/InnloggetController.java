package Dolphin.Controller;

import Dolphin.Main;
import Dolphin.Model.Bruker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

public class InnloggetController {

    private Main minApplikasjon = Main.getInstance();

    @FXML
    private Text txtBruker;

    @FXML
    private Pane vindu;

    public void initialize() {
        lastInnVindu("arrangementliste");

        String fornavn = minApplikasjon.getAktivBruker().getFornavn();
        String etternavn = minApplikasjon.getAktivBruker().getEtternavn();
        txtBruker.setText("Du er innlogget som: \n" + fornavn + " " + etternavn);
    }

    public void lastInnVindu(String filnavn) {
        vindu.getChildren().clear();
        try {
            vindu.getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/" + filnavn + ".fxml")));
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    @FXML
    public void gaaTilNyttArrangement() {
        lastInnVindu("nyttarrangement");
    }

    @FXML
    public void gaaTilDinProfil() {
        lastInnVindu("brukerprofil");
    }

    @FXML
    public void gaaTilLoggInn() {

        System.out.println("Du har nå logget ut :)");
        minApplikasjon.gaaTilLoggInn();
    }
}
