package Dolphin.DataHandler;

import Dolphin.Model.Bruker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataHandler {

    private static final ObservableList<Bruker> listeMedBrukere = FXCollections.observableArrayList();
    //private static final ArrayList<Bruker> arrayListeMedBrukere = null;

    //Lese brukere fra fil
    public static ObservableList<Bruker> hentListeMedBrukere() {

        String path = "src\\Dolphin\\databaser\\brukere.csv";
        BufferedReader br = null;
        String line = "";
        String CsvSplittetMed = ";";

        try {
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                String[] verdier = line.split(CsvSplittetMed);

                Bruker brukerObj = new Bruker(verdier[0],verdier[1],Integer.parseInt(verdier[2]),verdier[3],verdier[4],verdier[5]);

                //System.out.println("fornavn er: " + verdier[0]);

                listeMedBrukere.add(brukerObj);
                //arrayListeMedBrukere.add(brukerObj);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listeMedBrukere;
    }
}
