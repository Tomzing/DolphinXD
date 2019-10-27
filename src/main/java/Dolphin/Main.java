package Dolphin;

import Dolphin.Controller.BrukerHovedvisningController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    private Stage primaryStage;

    private static Main minApplikasjon;

    public Main() {
        minApplikasjon = this;
    }

    public static Main getInstance() {
        return minApplikasjon;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.primaryStage = primaryStage;

        gaaTilLoggInn();
    }

    public void gaaTilLoggInn() {
        try {
            URL url = new File("src/main/java/Dolphin/View/logginn.fxml").toURI().toURL();

            Parent root = FXMLLoader.load(url);
            primaryStage.getIcons().add(new Image("Bilder/cooldudeicon.png"));
            primaryStage.setTitle("Logg inn Dolphin");
            primaryStage.setScene(new Scene(root, 600, 480));
            primaryStage.show();
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public void gaaTilBrukerHovedvisning(String brukerNavn) {
        try {

            BrukerHovedvisningController.setBrukerNavn(brukerNavn);

            System.out.println("Brukervisning");
            URL url = new File("src/main/java/Dolphin/View/brukerhovedvisning.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            primaryStage.setTitle("Hovedvisning for bruker");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.show();
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public void gaaTilAdminHovedvisning() {
        try {
            URL url = new File("src/main/java/Dolphin/View/adminhovedvisning.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            primaryStage.setTitle("Hovedvisning for admin");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.show();
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public void gaaTilNyBruker() {
        try {
            URL url = new File("src/main/java/Dolphin/View/nybruker.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            primaryStage.setTitle("Lag ny bruker");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.show();
        }

        catch(IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

}