package Dolphin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
            Parent root = FXMLLoader.load(getClass().getResource("Views/logginn.fxml"));
            primaryStage.setTitle("Logg inn Dolphin");
            primaryStage.setScene(new Scene(root, 600, 490));
            primaryStage.show();
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public void gaaTilBrukerHovedvisning() {
        try {
            System.out.println("Brukervisning");
            Parent root = FXMLLoader.load(getClass().getResource("Views/brukerhovedvisning.fxml"));
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
            Parent root = FXMLLoader.load(getClass().getResource("Views/adminhovedvisning.fxml"));
            primaryStage.setTitle("Hovedvisning for admin");
            primaryStage.setScene(new Scene(root, 1000, 600));
            primaryStage.show();
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

}