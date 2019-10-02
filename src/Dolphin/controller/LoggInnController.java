package Dolphin.controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.constructors.Bruker;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoggInnController {

    @FXML
    private TextField inputBrukernavn;

    @FXML
    private TextField inputPassord;

    @FXML
    private Button loggInnBrukerKnapp;

    //Få liste med brukere fra DataHandler
    ObservableList<Bruker> listeMedBrukere = DataHandler.hentListeMedBrukere();


    //Dårlig innlogging til programmet
    public void loggInnBruker() {
        for (int i = 0; i < listeMedBrukere.size(); i++) {
            if(listeMedBrukere.get(i).getBrukernavn().equals(inputBrukernavn.getText())) {
                if (listeMedBrukere.get(i).getPassord().equals(inputPassord.getText())) {
                    System.out.println("Gratulerer du er innlogget, " + listeMedBrukere.get(i).getFornavn());
                }
            }
        }
    }
}

