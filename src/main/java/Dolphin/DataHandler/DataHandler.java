package Dolphin.DataHandler;

import Dolphin.Main;
import Dolphin.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DataHandler {

    private static final String filsti = "src/main/resources/Database/";
    private static final String CsvSplittetMed = ";";

    private static ObservableList<Bruker> listeMedBrukere = FXCollections.observableArrayList();

    private static ObservableList<ArrangementSykkelritt>listeMedSykkelrittArrangementer = FXCollections.observableArrayList();
    private static ObservableList<ArrangementLop> listeMedLopsArrangementer = FXCollections.observableArrayList();
    private static ObservableList<ArrangementAnnet> listeMedAnnetArrangementer = FXCollections.observableArrayList();

    //Lese brukere fra fil
    public static ObservableList<Bruker> hentListeMedBrukere() {
        String filnavn = "brukere.csv";
        BufferedReader br;
        String line;
        boolean erHeader = true;

        try {
            if(listeMedBrukere.isEmpty()) {
                br = new BufferedReader(new FileReader(filsti + filnavn));
                while ((line = br.readLine()) != null) {
                    if (erHeader) {
                        erHeader = false;
                        continue;
                    }
                    String[] verdier = line.split(CsvSplittetMed);

                    Bruker brukerObj = new Bruker(verdier[0], verdier[1], Integer.parseInt(verdier[2]), verdier[3], verdier[4], verdier[5]);

                    listeMedBrukere.add(brukerObj);
                }
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
        String filnavn = "sykkelrittarrangementer.csv";
        BufferedReader br;
        String line;
        boolean erHeader = true;

        try {
            if(listeMedSykkelrittArrangementer.isEmpty()) {
                br = new BufferedReader(new FileReader(filsti + filnavn));
                while ((line = br.readLine()) != null) {
                    if (erHeader) {
                        erHeader = false;
                        continue;
                    }

                    String[] arrangementVerdier = line.split(CsvSplittetMed);

                    String arrangementNavn = arrangementVerdier[0];
                    String arrangementType = arrangementVerdier[1];
                    int arrangementAntallPlasser = Integer.parseInt(arrangementVerdier[2]);
                    LocalDateTime arrangementStartTid = formaterDato(arrangementVerdier[3]);
                    LocalDateTime arrangementSluttTid = formaterDato(arrangementVerdier[4]);
                    String arrangementPlassering = arrangementVerdier[5];
                    String arrangementVanskelighetsgrad = arrangementVerdier[6];

                    ArrangementSykkelritt arrangementObj = new ArrangementSykkelritt(
                            arrangementNavn, arrangementType, arrangementAntallPlasser,
                            arrangementStartTid, arrangementSluttTid,
                            arrangementPlassering, arrangementVanskelighetsgrad);

                    arrangementObj.setDeltakereOppmeldt(hentArrangementDeltagere(arrangementObj));

                    listeMedSykkelrittArrangementer.add(arrangementObj);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listeMedSykkelrittArrangementer;
    }

    //Lese Løps arrangementer fra fil
    public static ObservableList<ArrangementLop> hentListeMedLopsArrangementer() {
        String filnavn = "loparrangementer.csv";
        BufferedReader br;
        String line;
        boolean erHeader = true;

        try {
            if(listeMedLopsArrangementer.isEmpty()) {
                br = new BufferedReader(new FileReader(filsti + filnavn));
                while ((line = br.readLine()) != null) {
                    if (erHeader) {
                        erHeader = false;
                        continue;
                    }

                    String[] arrangementVerdier = line.split(CsvSplittetMed);

                    String arrangementNavn = arrangementVerdier[0];
                    String arrangementType = arrangementVerdier[1];
                    int arrangementAntallPlasser = Integer.parseInt(arrangementVerdier[2]);
                    LocalDateTime arrangementStartTid = formaterDato(arrangementVerdier[3]);
                    LocalDateTime arrangementSluttTid = formaterDato(arrangementVerdier[4]);
                    String arrangementPlassering = arrangementVerdier[5];
                    String arrangementVanskelighetsgrad = arrangementVerdier[6];

                    ArrangementLop arrangementObj = new ArrangementLop(
                            arrangementNavn, arrangementType, arrangementAntallPlasser,
                            arrangementStartTid, arrangementSluttTid, arrangementPlassering,
                            arrangementVanskelighetsgrad);

                    arrangementObj.setDeltakereOppmeldt(hentArrangementDeltagere(arrangementObj));

                    listeMedLopsArrangementer.add(arrangementObj);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listeMedLopsArrangementer;
    }

    //Lese annet arrangementer fra fil
    public static ObservableList<ArrangementAnnet> hentListeMedAnnetArrangementer() {
        String filnavn = "annetarrangementer.csv";
        BufferedReader br;
        String line;
        boolean erHeader = true;

        try {
            if(listeMedAnnetArrangementer.isEmpty()) {
                br = new BufferedReader(new FileReader(filsti + filnavn));
                while ((line = br.readLine()) != null) {
                    if (erHeader) {
                        erHeader = false;
                        continue;
                    }

                    String[] arrangementVerdier = line.split(CsvSplittetMed);

                    String arrangementNavn = arrangementVerdier[0];
                    String arrangementType = arrangementVerdier[1];
                    int arrangementAntallPlasser = Integer.parseInt(arrangementVerdier[2]);
                    LocalDateTime arrangementStartTid = formaterDato(arrangementVerdier[3]);
                    LocalDateTime arrangementSluttTid = formaterDato(arrangementVerdier[4]);
                    String arrangementPlassering = arrangementVerdier[5];
                    String arrangementAltText = arrangementVerdier[6];

                    ArrangementAnnet arrangementObj = new ArrangementAnnet(
                            arrangementNavn, arrangementType, arrangementAntallPlasser,
                            arrangementStartTid, arrangementSluttTid, arrangementPlassering,
                            arrangementAltText);

                    arrangementObj.setDeltakereOppmeldt(hentArrangementDeltagere(arrangementObj));

                    listeMedAnnetArrangementer.add(arrangementObj);
                }
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
        ArrayList<String> deltagerListe = hentDeltagerListe();
        ObservableList<Bruker> brukere = hentListeMedBrukere();

        boolean erHeader = true;

        for (String deltagerInfo : deltagerListe) {
            if (erHeader) {
                erHeader = false;
                continue;
            }
            String[] deltagerVerdier = deltagerInfo.split(CsvSplittetMed);
            if (Integer.parseInt(deltagerVerdier[0]) == arrangement.getArrangementId()) {
                for (Bruker bruker : brukere) {
                    if (bruker.getBrukernavn().equals(deltagerVerdier[1])) {
                        deltagere.add(bruker);
                    }
                }
            }
        }

        /*
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

         */

        return deltagere;
    }

    public static void fjernPaameldingTilArrangement(Arrangement arrangement, Bruker bruker) {
        ArrayList<String> deltagerListe = hentDeltagerListe();

        boolean erHeader = true;

        StringBuilder deltagere = new StringBuilder();

        for (String deltagerInfo : deltagerListe) {
            if (erHeader) {
                erHeader = false;
                deltagere.append(deltagerInfo).append("\n");
                continue;
            }
            String[] deltagerVerdier = deltagerInfo.split(";");
            if (arrangement.getArrangementId() == Integer.parseInt(deltagerVerdier[0]) && bruker.getBrukernavn().equals(deltagerVerdier[1])) {
                continue;
            }
            deltagere.append(deltagerInfo).append("\n");
        }

        String filnavn = "deltagere.csv";

        try {
            FileWriter filSkriver = new FileWriter(new File(filsti + filnavn));
            BufferedWriter bufferedCsvSkriver = new BufferedWriter(filSkriver);

            bufferedCsvSkriver.write(deltagere.toString());

            bufferedCsvSkriver.flush();
            bufferedCsvSkriver.close();

            System.out.println(arrangement.getDeltakereOppmeldt());
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public static ArrayList<String> hentDeltagerListe() {
        ArrayList<String> deltagerListe = new ArrayList<>();

        BufferedReader br;
        String line;

        try {
            br = new BufferedReader(new FileReader(filsti + "deltagere.csv"));
            while ((line = br.readLine()) != null) {
                deltagerListe.add(line);
            }
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        return deltagerListe;
    }

}