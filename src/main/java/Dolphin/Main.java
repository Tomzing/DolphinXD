package Dolphin;

import Dolphin.Controller.arrangementlisteController;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Bruker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;

    private static Main minApplikasjon;

    private Bruker aktivBruker;

    private static arrangementlisteController arrangementlisteController;

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
            fxmlLaster.setLocation(getClass().getResource("/fxml/brukerhovedvisning.fxml"));
            primaryStage.setTitle("Hovedvisning for bruker");
            primaryStage.setScene(new Scene(fxmlLaster.load(), 540, 600));

            arrangementlisteController = fxmlLaster.getController();

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

    public Arrangement getValgtArrangement() {
        return arrangementlisteController.getValgtArrangement();
    }

    public void setAktivBruker(Bruker bruker) {
        aktivBruker = bruker;
    }

    public Bruker getAktivBruker() {
        return aktivBruker;
    }

    public static void main(String[] args) {
        launch(args);
    }

}