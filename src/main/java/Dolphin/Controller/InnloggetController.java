package Dolphin.Controller;

import Dolphin.Main;
import Dolphin.Model.Bruker;
import javafx.fxml.FXML;

public abstract class InnloggetController {

    private Main minApplikasjon = Main.getInstance();

    @FXML
    public void gaaTilLoggInn() {

        System.out.println("Du har nå logget ut :)");
        minApplikasjon.gaaTilLoggInn();
    }

    Bruker finnAktivBruker() {
        return minApplikasjon.getAktivBruker();
    }

    Main getMinApplikasjon() {
        return minApplikasjon;
    }
}
