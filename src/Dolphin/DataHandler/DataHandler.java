package Dolphin.DataHandler;

import Dolphin.Model.Arrangement;
import Dolphin.Model.ArrangementAnnet;
import Dolphin.Model.ArrangementSykkelritt;
import Dolphin.Model.Bruker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataHandler {

    private static final ObservableList<Bruker> listeMedBrukere = FXCollections.observableArrayList();
    //private static final ArrayList<Bruker> arrayListeMedBrukere = null;
    private static final ObservableList<Arrangement> listeMedAlleArrangementer = FXCollections.observableArrayList();
    private static final ObservableList<ArrangementSykkelritt> listeMedSykkelrittArrangementer = FXCollections.observableArrayList();
    private static final ObservableList<ArrangementAnnet> listeMedAnnetArrangementer = FXCollections.observableArrayList();


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

    public static LocalDateTime formaterDato(String dato) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime datoForFormatering = LocalDateTime.parse(dato, formatter);

        return datoForFormatering;
    }

    //Lese sykkelritt arrangementer fra fil
    public static ObservableList<ArrangementSykkelritt> hentListeMedSykkelrittArrangementer() {

        String path = "src\\Dolphin\\databaser\\sykkelrittarrangementer.csv";
        BufferedReader br = null;
        String line = "";
        String CsvSplittetMed = ";";

        try {

            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {

                String[] arrangementVerdier = line.split(CsvSplittetMed);


                ArrangementSykkelritt arrangementObj = new ArrangementSykkelritt(
                        arrangementVerdier[0],arrangementVerdier[1], Integer.parseInt(arrangementVerdier[2]),
                        formaterDato(arrangementVerdier[3]),formaterDato(arrangementVerdier[4]),
                        arrangementVerdier[5],arrangementVerdier[6]);

                //System.out.println("Navn på arrangementet er: " + arrangementVerdier[1]);

                listeMedSykkelrittArrangementer.add(arrangementObj);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return listeMedSykkelrittArrangementer;
    }

    //Lese annet arrangementer fra fil
    public static ObservableList<ArrangementAnnet> hentListeMedAnnetArrangementer() {

        String path = "src\\Dolphin\\databaser\\annetarrangementer.csv";
        BufferedReader br = null;
        String line = "";
        String CsvSplittetMed = ";";

        try {

            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {

                String[] arrangementVerdier = line.split(CsvSplittetMed);

                ArrangementAnnet arrangementObj = new ArrangementAnnet(
                        arrangementVerdier[0],arrangementVerdier[1], Integer.parseInt(arrangementVerdier[2]),
                        formaterDato(arrangementVerdier[3]),formaterDato(arrangementVerdier[4]),
                        arrangementVerdier[5],arrangementVerdier[6]);

                //System.out.println("fornavn er: " + verdier[0]);

                listeMedAnnetArrangementer.add(arrangementObj);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return listeMedAnnetArrangementer;
    }

}
