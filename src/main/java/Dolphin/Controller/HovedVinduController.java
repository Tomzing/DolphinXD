package Dolphin.Controller;

import Dolphin.Main;
import Dolphin.Model.Bruker;
import Dolphin.Model.Person;
import Dolphin.Model.SystemAdmin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

public class HovedVinduController {

    private static final String fxmlAdminHovedVisning = "adminhovedvisning";
    private static final String fxmlArrangement = "arrangement";
    private static final String fxmlArrangementliste = "arrangementliste";
    private static final String fxmlBrukerprofil = "brukerprofil";
    private static final String fxmlLoggInn = "logginn";
    private static final String fxmlNyBruker = "nybruker";
    private static final String fxmlNyttArrangement = "nyttarrangement";

    private Main minApplikasjon = Main.getInstance();

    @FXML
    private Text txtBruker;

    @FXML
    private Button btnNyttArrangement, btnAapneVindu, btnInnlogging;

    @FXML
    private Pane vindu;

    public void initialize() {
        aapneArrangementliste();
        oppdaterBruker();
    }

    private void lastInnVindu(String filnavn) {
        vindu.getChildren().clear();
        try {
            vindu.getChildren().add(FXMLLoader.load(getClass().getResource("/fxml/" + filnavn + ".fxml")));
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public void gaaTilNyttArrangement() {
        minApplikasjon.setValgtArrangement(null);
        aapneNyttArrangement();
    }

    public void gaaTilArrangementliste() {
        minApplikasjon.setValgtArrangement(null);
        aapneArrangementliste();
    }

    @FXML
    private void gaaTilVindu() {
        Bruker aktiv = minApplikasjon.getAktivBruker();
        if (aktiv instanceof Person) {
            gaaTilDinProfil();
        }
        else if (aktiv instanceof SystemAdmin) {
            aapneAdminHovedvisning();
        }
    }

    private void gaaTilDinProfil() {
        minApplikasjon.setValgtBruker((Person) minApplikasjon.getAktivBruker());
        aapneBrukerprofil();
    }

    public void gaaTilInnlogging() {
        minApplikasjon.setAktivBruker(null);
        minApplikasjon.setValgtArrangement(null);
        oppdaterBruker();
        aapneLoggInn();
    }

    public void oppdaterBruker() {
        Bruker aktiv = minApplikasjon.getAktivBruker();

        if (aktiv == null) {
            txtBruker.setText("");
            btnNyttArrangement.setVisible(false);
            btnAapneVindu.setVisible(false);
            btnInnlogging.setText("Logg inn");
        }
        else {
            if (aktiv instanceof Person) {
                String fornavn = ((Person) aktiv).getFornavn();
                String etternavn = ((Person) aktiv).getEtternavn();
                txtBruker.setText("Du er innlogget som:\n" + fornavn + " " + etternavn);
                btnAapneVindu.setText("Gå til profilen din");
            }
            else if (aktiv instanceof SystemAdmin) {
                String brukernavn = aktiv.getBrukernavn();
                txtBruker.setText("Du er innlogget som:\n" + brukernavn);
                btnAapneVindu.setText("Gå til adminpanel");
            }
            btnNyttArrangement.setVisible(true);
            btnAapneVindu.setVisible(true);
            btnInnlogging.setText("Logg ut");
        }
    }

    public void aapneAdminHovedvisning() {
        lastInnVindu(fxmlAdminHovedVisning);
    }

    public void aapneArrangement() {
        lastInnVindu(fxmlArrangement);
    }

    public void aapneArrangementliste() {
        lastInnVindu(fxmlArrangementliste);
    }

    public void aapneBrukerprofil() {
        lastInnVindu(fxmlBrukerprofil);
    }

    public void aapneLoggInn() {
        lastInnVindu(fxmlLoggInn);
    }

    public void aapneNyBruker() {
        lastInnVindu(fxmlNyBruker);
    }

    public void aapneNyttArrangement() {
        lastInnVindu(fxmlNyttArrangement);
    }
}
