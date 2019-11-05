package Dolphin;

import Dolphin.Controller.ArrangementlisteController;
import Dolphin.Controller.InnloggetController;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Bruker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;

    private static Main minApplikasjon;

    private Bruker aktivBruker;

    private Arrangement valgtArrangement;

    private InnloggetController innloggetController;

    public Main() {
        minApplikasjon = this;
    }

    public static Main getInstance() {
        return minApplikasjon;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        gaaTilLoggInn();
    }

    public void gaaTilLoggInn() {
        try {
            FXMLLoader fxmlLaster = new FXMLLoader();
            fxmlLaster.setLocation(getClass().getResource("/fxml/logginn.fxml"));
            primaryStage.getIcons().add(new Image("Bilder/cooldudeicon.png"));
            primaryStage.setTitle("Logg inn Dolphin");
            primaryStage.setScene(new Scene(fxmlLaster.load(), 600, 480));
            primaryStage.show();
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public void gaaTilBrukerHovedvisning() {
        try {

            System.out.println("Brukervisning");
            FXMLLoader fxmlLaster = new FXMLLoader();
            fxmlLaster.setLocation(getClass().getResource("/fxml/innlogget.fxml"));
            primaryStage.setTitle("Hovedvisning for bruker");
            primaryStage.setScene(new Scene(fxmlLaster.load(), 1000, 600));

            innloggetController = fxmlLaster.getController();

            primaryStage.show();
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

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

    public void aapneNyttVindu(String filnavn) {
        innloggetController.lastInnVindu(filnavn);
    }

    public void setAktivBruker(Bruker bruker) {
        aktivBruker = bruker;
    }

    public Bruker getAktivBruker() {
        return aktivBruker;
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