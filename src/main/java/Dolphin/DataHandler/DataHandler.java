package Dolphin.DataHandler;

import Dolphin.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DataHandler {

    //Lese brukere fra fil
    public static ObservableList<Bruker> hentListeMedBrukere() {

        ObservableList<Bruker> listeMedBrukere = FXCollections.observableArrayList();

        String path = "src/main/resources/Database/brukere.csv";
        BufferedReader br;
        String line;
        String CsvSplittetMed = ";";
        boolean erHeader = true;

        try {
            br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null) {
                if (erHeader) {
                    erHeader = false;
                    continue;
                }
                String[] verdier = line.split(CsvSplittetMed);

                Bruker brukerObj = new Bruker(verdier[0],verdier[1],Integer.parseInt(verdier[2]),verdier[3],verdier[4],verdier[5]);

                //System.out.println("fornavn er: " + verdier[0]);

                listeMedBrukere.add(brukerObj);
                //arrayListeMedBrukere.add(brukerObj);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return listeMedBrukere;
    }

    public static LocalDateTime formaterDato(String dato) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return LocalDateTime.parse(dato, formatter);
    }

    //Lese sykkelritt arrangementer fra fil
    public static ObservableList<ArrangementSykkelritt> hentListeMedSykkelrittArrangementer() {

        ObservableList<ArrangementSykkelritt> listeMedSykkelrittArrangementer = FXCollections.observableArrayList();

        String path = "src\\main\\resources\\Database\\sykkelrittarrangementer.csv";
        BufferedReader br;
        String line;
        String CsvSplittetMed = ";";
        boolean erHeader = true;

        try {

            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                if (erHeader) {
                    erHeader = false;
                    continue;
                }

                String[] arrangementVerdier = line.split(CsvSplittetMed);


                ArrangementSykkelritt arrangementObj = new ArrangementSykkelritt(
                        Integer.parseInt(arrangementVerdier[0]), arrangementVerdier[1], arrangementVerdier[2],
                        Integer.parseInt(arrangementVerdier[3]), formaterDato(arrangementVerdier[4]),
                        formaterDato(arrangementVerdier[5]), arrangementVerdier[6], arrangementVerdier[7]);

                arrangementObj.setDeltakereOppmeldt(hentArrangementDeltagere(arrangementObj));

                //System.out.println("Navn på arrangementet er: " + arrangementVerdier[1]);

                listeMedSykkelrittArrangementer.add(arrangementObj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listeMedSykkelrittArrangementer;
    }

    //Lese Løps arrangementer fra fil
    public static ObservableList<ArrangementLop> hentListeMedLopsArrangementer() {

        ObservableList<ArrangementLop> listeMedLopsArrangementer = FXCollections.observableArrayList();

        String path = "src/main/resources/Database/loparrangementer.csv";
        BufferedReader br;
        String line;
        String CsvSplittetMed = ";";
        boolean erHeader = true;

        try {

            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                if (erHeader) {
                    erHeader = false;
                    continue;
                }

                String[] arrangementVerdier = line.split(CsvSplittetMed);


                ArrangementLop arrangementObj = new ArrangementLop(
                        Integer.parseInt(arrangementVerdier[0]), arrangementVerdier[1],arrangementVerdier[2],
                        Integer.parseInt(arrangementVerdier[3]), formaterDato(arrangementVerdier[4]),
                        formaterDato(arrangementVerdier[5]), arrangementVerdier[6], arrangementVerdier[7]);

                arrangementObj.setDeltakereOppmeldt(hentArrangementDeltagere(arrangementObj));

                //System.out.println("Navn på arrangementet er: " + arrangementVerdier[1]);

                listeMedLopsArrangementer.add(arrangementObj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listeMedLopsArrangementer;
    }

    //Lese annet arrangementer fra fil
    public static ObservableList<ArrangementAnnet> hentListeMedAnnetArrangementer() {

        ObservableList<ArrangementAnnet> listeMedAnnetArrangementer = FXCollections.observableArrayList();

        String path = "src/main/resources/Database/annetarrangementer.csv";
        BufferedReader br;
        String line;
        String CsvSplittetMed = ";";
        boolean erHeader = true;

        try {

            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                if (erHeader) {
                    erHeader = false;
                    continue;
                }

                String[] arrangementVerdier = line.split(CsvSplittetMed);

                ArrangementAnnet arrangementObj = new ArrangementAnnet(
                        Integer.parseInt(arrangementVerdier[0]), arrangementVerdier[1],arrangementVerdier[2],
                        Integer.parseInt(arrangementVerdier[3]), formaterDato(arrangementVerdier[4]),
                        formaterDato(arrangementVerdier[5]), arrangementVerdier[6],arrangementVerdier[7]);

                arrangementObj.setDeltakereOppmeldt(hentArrangementDeltagere(arrangementObj));

                //System.out.println("fornavn er: " + verdier[0]);

                listeMedAnnetArrangementer.add(arrangementObj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listeMedAnnetArrangementer;
    }

    public static ObservableList<Arrangement> hentListeMedAlleArrangementer() {

        ObservableList<Arrangement> listeMedAlleArrangementer = FXCollections.observableArrayList();

        ObservableList<ArrangementSykkelritt>listeSykkelritt = hentListeMedSykkelrittArrangementer();
        ObservableList<ArrangementAnnet> listeAnnet = hentListeMedAnnetArrangementer();
        //ObservableList<ArrangementLop> listeLop = hentListeMedLopsArrangementer();

        listeMedAlleArrangementer.addAll(listeSykkelritt);
        listeMedAlleArrangementer.addAll(listeAnnet);
        //listeMedAlleArrangementer.addAll(listeLop);

        return listeMedAlleArrangementer;
    }

    public static ArrayList<Bruker> hentArrangementDeltagere(Arrangement arrangement) {
        ArrayList<Bruker> deltagere = new ArrayList<>();
        ObservableList<Bruker> brukere = hentListeMedBrukere();

        BufferedReader br;
        String line;
        String CsvSplittetMed = ";";
        boolean erHeader = true;

        try {
            br = new BufferedReader(new FileReader("src/main/resources/Database/deltagere.csv"));
            while ((line = br.readLine()) != null) {
                if (erHeader) {
                    erHeader = false;
                    continue;
                }

                String[] deltagerVerdier = line.split(CsvSplittetMed);

                if (Integer.parseInt(deltagerVerdier[0]) == arrangement.getArrangementId()) {
                    for (Bruker bruker : brukere) {
                        if (bruker.getBrukernavn().equals(deltagerVerdier[1])) {
                            deltagere.add(bruker);
                        }
                    }
                }
            }
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        return deltagere;
    }

}