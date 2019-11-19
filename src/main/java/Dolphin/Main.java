package Dolphin;

import Dolphin.Controller.HovedVinduController;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Bruker;
import Dolphin.Model.Person;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;

    private static Main minApplikasjon;

    private Bruker aktivBruker;

    private Person valgtBruker;

    private Arrangement valgtArrangement;

    private HovedVinduController hovedVinduController;

    public Main() {
        minApplikasjon = this;
    }

    public static Main getInstance() {
        return minApplikasjon;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        gaaTilBrukerHovedvisning();
        //gaaTilLoggInn();
    }

    /*
    public void gaaTilLoggInn() {
        try {
            FXMLLoader fxmlLaster = new FXMLLoader();
            fxmlLaster.setLocation(getClass().getResource("/fxml/logginn.fxml"));
            primaryStage.getIcons().add(new Image("Bilder/cooldudeicon.png"));
            primaryStage.setTitle("Logg inn Dolphin");
            primaryStage.setScene(new Scene(fxmlLaster.load(), 1000, 600));
            primaryStage.show();
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
     */

    private void gaaTilBrukerHovedvisning() {
        try {
            FXMLLoader fxmlLaster = new FXMLLoader();
            fxmlLaster.setLocation(getClass().getResource("/fxml/hovedvindu.fxml"));
            primaryStage.setTitle("Prosjektoppgave gruppe 17");
            primaryStage.getIcons().add(new Image("Bilder/cooldudeicon.png"));
            primaryStage.setScene(new Scene(fxmlLaster.load(), 900, 600));

            hovedVinduController = fxmlLaster.getController();

            primaryStage.show();
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    /*
    public void gaaTilAdminHovedvisning() {
        try {
            FXMLLoader fxmlLaster = new FXMLLoader();
            fxmlLaster.setLocation(getClass().getResource("/fxml/adminhovedvisning.fxml"));
            primaryStage.setTitle("Hovedvisning for admin");
            primaryStage.setScene(new Scene(fxmlLaster.load(), 1000, 600));
            primaryStage.show();
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
     */

    /*
    public void gaaTilNyBruker() {
        try {
            FXMLLoader fxmlLaster = new FXMLLoader();
            fxmlLaster.setLocation(getClass().getResource("/fxml/nybruker.fxml"));
            primaryStage.setTitle("Lag ny bruker");
            primaryStage.setScene(new Scene(fxmlLaster.load(), 1000, 600));
            primaryStage.show();
        }

        catch(IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
     */

    /*
    public void gaaTilArrangement() {
        try {
            FXMLLoader fxmlLaster = new FXMLLoader();
            fxmlLaster.setLocation(getClass().getResource("/fxml/arrangement.fxml"));
            primaryStage.setTitle("Et arrangement");
            primaryStage.setScene(new Scene(fxmlLaster.load(), 700, 550));
            primaryStage.show();
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
     */

    public void aapneAdminHovedvisning() {
        hovedVinduController.aapneAdminHovedvisning();
    }

    public void aapneArrangement() {
        hovedVinduController.aapneArrangement();
    }

    public void aapneArrangementliste() {
        hovedVinduController.aapneArrangementliste();
    }

    public void aapneBrukerprofil() {
        hovedVinduController.aapneBrukerprofil();
    }

    public void aapneLoggInn() {
        hovedVinduController.aapneLoggInn();
    }

    public void aapneNyBruker() {
        hovedVinduController.aapneNyBruker();
    }

    public void aapneNyttArrangement() {
        hovedVinduController.aapneNyttArrangement();
    }

    public void setAktivBruker(Bruker bruker) {
        aktivBruker = bruker;
        hovedVinduController.oppdaterBruker();
    }

    public Bruker getAktivBruker() {
        return aktivBruker;
    }

    public void setValgtBruker(Person bruker) {
        valgtBruker = bruker;
    }

    public Person getValgtBruker() {
        return valgtBruker;
    }

    public void setValgtArrangement(Arrangement arrangement) {
        valgtArrangement = arrangement;
    }

    public Arrangement getValgtArrangement() {
        return valgtArrangement;
    }

    public static void main(String[] args) {
        launch(args);
    }
}