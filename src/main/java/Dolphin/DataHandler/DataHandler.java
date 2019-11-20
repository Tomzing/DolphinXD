package Dolphin.DataHandler;

import Dolphin.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DataHandler {

    private static final String filnavnPerson = "src/main/resources/Database/personer.csv";
    private static final String filnavnSysAdmin = "src/main/resources/Database/systemadministratorer.csv";

    private static final String filnavnArrangementer = "src/main/resources/Database/arrangementer.csv";

    private static final String filnavnAdmin = "src/main/resources/Database/administratorer.csv";
    private static final String filnavnDeltagere = "src/main/resources/Database/deltagere.csv";

    private static final Charset karaktersett = StandardCharsets.UTF_8;

    // Indexen til arrangement- og brukerverdiene i deltagerlisten og adminlisten.
    private static final int arrangementIndex = 0;
    private static final int brukerIndex = 1;

    private static final String CsvSplittetMed = ";";

    public static ObservableList<Bruker> hentListeMedAlleBrukere() {
        ObservableList<Bruker> brukere = FXCollections.observableArrayList();
        brukere.addAll(hentListeMedPersoner());
        brukere.addAll(hentListeMedSysAdmin());

        return brukere;
    }

    public static ObservableList<Person> hentListeMedPersoner() {
        ObservableList<Person> personer = FXCollections.observableArrayList();
        for (Bruker person : hentListeMedBrukere(filnavnPerson)) {
            personer.add((Person)person);
        }
        return personer;
    }

    public static ObservableList<SystemAdmin> hentListeMedSysAdmin() {
        ObservableList<SystemAdmin> sysAdministratorer = FXCollections.observableArrayList();
        for (Bruker sysAdmin : hentListeMedBrukere(filnavnSysAdmin)) {
            sysAdministratorer.add((SystemAdmin)sysAdmin);
        }
        return sysAdministratorer;
    }

    //Lese brukere fra fil
    public static ObservableList<Bruker> hentListeMedBrukere(String filnavn) {
        ObservableList<Bruker> listeMedBrukere = FXCollections.observableArrayList();

        BufferedReader br;
        String line;
        boolean erHeader = true;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filnavn)), karaktersett));
            while ((line = br.readLine()) != null) {
                if (erHeader) {
                    erHeader = false;
                    continue;
                }
                String[] verdier = line.split(CsvSplittetMed);

                switch (filnavn) {
                    case filnavnPerson:
                        Person person = new Person(Integer.parseInt(verdier[0]), verdier[1], verdier[2], LocalDate.parse(verdier[3]), verdier[4], verdier[5], verdier[6]);
                        listeMedBrukere.add(person);
                        break;
                    case filnavnSysAdmin:
                        SystemAdmin sysAdmin = new SystemAdmin(Integer.parseInt(verdier[0]), verdier[1], verdier[2]);
                        listeMedBrukere.add(sysAdmin);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listeMedBrukere;
    }

    public static void lagrePerson(Person bruker) {
        try {
            File file = new File(filnavnPerson);

            FileWriter filSkriver = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bufferedCsvSkriver = new BufferedWriter(filSkriver);

            bufferedCsvSkriver.write(bruker.getBrukerId() + CsvSplittetMed +
                    bruker.getFornavn() + CsvSplittetMed +
                    bruker.getEtternavn() + CsvSplittetMed +
                    bruker.getFodselsdato() + CsvSplittetMed +
                    bruker.getKjonn() + CsvSplittetMed +
                    bruker.getBrukernavn() + CsvSplittetMed +
                    bruker.getPassord() + "\n"
            );

            bufferedCsvSkriver.flush();
            bufferedCsvSkriver.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LocalDateTime formaterDato(String dato) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return LocalDateTime.parse(dato, formatter);
    }

    public static ObservableList<Arrangement> hentArrangementer() {
        ObservableList<Arrangement> arrangementer = FXCollections.observableArrayList();

        BufferedReader br;
        String line;
        boolean erHeader = true;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filnavnArrangementer)), karaktersett));
            while ((line = br.readLine()) != null) {
                if (erHeader) {
                    erHeader = false;
                    continue;
                }

                String[] arrangementVerdier = line.split(CsvSplittetMed);

                //NAVN;ARRANGØR;TYPE;VANSKELIGHETSGRAD;ANTALL PLASSER;PRIS;STARTTID;SLUTTID;STED;BESKRIVELSE

                int id = Integer.parseInt(arrangementVerdier[0]);
                String navn = arrangementVerdier[1];
                Bruker arrangor = null;
                for (Person p : hentListeMedPersoner()) {
                    if (p.getBrukerId() == Integer.parseInt(arrangementVerdier[2])) {
                        arrangor = p;
                        break;
                    }
                }
                String type = arrangementVerdier[3];
                String vanskelighetsgrad = arrangementVerdier[4];
                int antallPlasser = Integer.parseInt(arrangementVerdier[5]);
                long pris = Long.parseLong(arrangementVerdier[6]);
                LocalDateTime starttid = LocalDateTime.parse(arrangementVerdier[7]);
                LocalDateTime sluttid = LocalDateTime.parse(arrangementVerdier[8]);
                String sted = arrangementVerdier[9];
                String beskrivelse = arrangementVerdier[10];

                Arrangement arrangementObj = new Arrangement(id, navn, arrangor, type, vanskelighetsgrad,
                        antallPlasser, pris, starttid, sluttid, sted, beskrivelse);

                arrangementObj.setDeltakereOppmeldt(hentArrangementDeltagere(arrangementObj));
                arrangementObj.setAdministratorer(hentArrangementAdministratorer(arrangementObj));

                arrangementer.add(arrangementObj);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return arrangementer;
    }

    public static ArrayList<Person> hentArrangementAdministratorer(Arrangement arrangement) {
        return hentArrangementBrukerliste(arrangement, filnavnAdmin);
    }

    public static ArrayList<Person> hentArrangementDeltagere(Arrangement arrangement) {
        return hentArrangementBrukerliste(arrangement, filnavnDeltagere);
    }

    private static ArrayList<Person> hentArrangementBrukerliste(Arrangement arrangement, String filnavn) {
        ArrayList<Person> brukerListe = new ArrayList<>();
        ArrayList<String> adminListe = hentListe(filnavn);
        ObservableList<Person> personer = hentListeMedPersoner();

        boolean erHeader = true;

        for (String deltagerInfo : adminListe) {
            if (erHeader) {
                erHeader = false;
                continue;
            }
            String[] deltagerVerdier = deltagerInfo.split(CsvSplittetMed);
            if (Integer.parseInt(deltagerVerdier[0]) == arrangement.getArrangementId()) {
                for (Person person : personer) {
                    if (person.getBrukerId() == Integer.parseInt(deltagerVerdier[1])) {
                        brukerListe.add(person);
                    }
                }
            }
        }

        return brukerListe;
    }

    public static void lagreArrangement(Arrangement arrangement) {
        try {
            File file = new File(filnavnArrangementer);

            FileWriter filSkriver = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bufferedCsvSkriver = new BufferedWriter(filSkriver);

            bufferedCsvSkriver.write(arrangement.getArrangementId() + CsvSplittetMed +
                    arrangement.getNavn() + CsvSplittetMed +
                    arrangement.getArrangor().getBrukerId() + CsvSplittetMed +
                    arrangement.getType() + CsvSplittetMed +
                    arrangement.getVanskelighetsgrad() + CsvSplittetMed +
                    arrangement.getAntallPlasser() + CsvSplittetMed +
                    arrangement.getPris() + CsvSplittetMed +
                    arrangement.getStarttid() + CsvSplittetMed +
                    arrangement.getSluttid() + CsvSplittetMed +
                    arrangement.getSted() + CsvSplittetMed +
                    arrangement.getBeskrivelse() + "\n"
            );

            bufferedCsvSkriver.flush();
            bufferedCsvSkriver.close();

            if (!arrangement.getAdministratorer().isEmpty()) {
                lagreAdministratorer(arrangement);
            }
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    private static void lagreAdministratorer(Arrangement arrangement) {
        ArrayList<String> adminString = new ArrayList<>();
        for (Bruker admin : arrangement.getAdministratorer()) {
            adminString.add(arrangement.getArrangementId() + CsvSplittetMed + admin.getBrukerId());
        }

        String nyAdminliste = lagNyAdminListe(arrangement, adminString);

        skrivTilFil(nyAdminliste, filnavnAdmin);
    }

    private static String lagNyAdminListe(Arrangement arrangement, ArrayList<String> administratorer) {
        ArrayList<String> adminlisteGammel = hentListe(filnavnAdmin);
        StringBuilder adminlisteNy = new StringBuilder();

        boolean erHeader = true;

        for (String admin : adminlisteGammel) {
            if (erHeader) {
                erHeader = false;
                adminlisteNy.append(admin).append("\n");
                continue;
            }
            int id = Integer.parseInt(admin.split(CsvSplittetMed)[0]);
            if (id != arrangement.getArrangementId()) {
                adminlisteNy.append(admin).append("\n");
                continue;
            }
            if (administratorer.contains(admin)) {
                adminlisteNy.append(admin).append("\n");
            }
        }
        for (String admin : administratorer) {
            if (!adminlisteNy.toString().contains(admin)) {
                adminlisteNy.append(admin).append("\n");
            }
        }
        return adminlisteNy.toString();
    }

    public static void fjernPaameldingTilArrangement(Arrangement arrangement, Bruker bruker) {
        ArrayList<String> deltagerListe = hentListe(filnavnDeltagere);

        boolean erHeader = true;

        StringBuilder deltagere = new StringBuilder();

        for (String deltagerInfo : deltagerListe) {
            if (erHeader) {
                erHeader = false;
                deltagere.append(deltagerInfo).append("\n");
                continue;
            }
            String[] deltagerVerdier = deltagerInfo.split(CsvSplittetMed);
            if (arrangement.getArrangementId() == Integer.parseInt(deltagerVerdier[0]) && bruker.getBrukerId() == Integer.parseInt(deltagerVerdier[1])) {
                continue;
            }
            deltagere.append(deltagerInfo).append("\n");
        }

        skrivTilFil(deltagere.toString(), filnavnDeltagere);
    }

    private static ArrayList<String> hentListe(String filnavn) {
        ArrayList<String> liste = new ArrayList<>();

        BufferedReader br;
        String line;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filnavn)), karaktersett));
            while ((line = br.readLine()) != null) {
                liste.add(line);
            }
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        return liste;
    }

    public static void endreArrangement(Arrangement arrangement) {
        ArrayList<String> arrangementliste = hentListe(filnavnArrangementer);

        boolean erHeader = true;

        StringBuilder arrangementer = new StringBuilder();

        for (String arrangementInfo : arrangementliste) {
            if (erHeader) {
                erHeader = false;
                arrangementer.append(arrangementInfo).append("\n");
                continue;
            }
            String[] deltagerVerdier = arrangementInfo.split(CsvSplittetMed);
            if (arrangement.getArrangementId() == Integer.parseInt(deltagerVerdier[0])) {
                String endretArrangement = arrangement.getArrangementId() + CsvSplittetMed +
                        arrangement.getNavn() + CsvSplittetMed +
                        arrangement.getArrangor().getBrukerId() + CsvSplittetMed +
                        arrangement.getType() + CsvSplittetMed +
                        arrangement.getVanskelighetsgrad() + CsvSplittetMed +
                        arrangement.getAntallPlasser() + CsvSplittetMed +
                        arrangement.getPris() + CsvSplittetMed +
                        arrangement.getStarttid().toString() + CsvSplittetMed +
                        arrangement.getSluttid().toString() + CsvSplittetMed +
                        arrangement.getSted() + CsvSplittetMed +
                        arrangement.getBeskrivelse();

                arrangementer.append(endretArrangement).append("\n");

                lagreAdministratorer(arrangement);
                continue;
            }
            arrangementer.append(arrangementInfo).append("\n");
        }

        skrivTilFil(arrangementer.toString(), filnavnArrangementer);
    }

    public static void lagreDeltager(Bruker bruker, Arrangement arrangement) {
        try {
            File file = new File(filnavnDeltagere);

            FileWriter filSkriver = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bufretCsvSkriver = new BufferedWriter(filSkriver);
            bufretCsvSkriver.write(arrangement.getArrangementId() + CsvSplittetMed + bruker.getBrukerId() + "\n");

            bufretCsvSkriver.flush();
            bufretCsvSkriver.close();
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public static void endrePerson(Person bruker) {
        ArrayList<String> personliste = hentListe(filnavnPerson);

        boolean erHeader = true;

        StringBuilder personer = new StringBuilder();

        for (String personInfo : personliste) {
            if (erHeader) {
                erHeader = false;
                personer.append(personInfo).append("\n");
                continue;
            }
            String[] deltagerVerdier = personInfo.split(CsvSplittetMed);
            if (bruker.getBrukerId() == Integer.parseInt(deltagerVerdier[0])) {
                String endretPerson = bruker.getBrukerId() + CsvSplittetMed +
                        bruker.getFornavn() + CsvSplittetMed +
                        bruker.getEtternavn() + CsvSplittetMed +
                        bruker.getFodselsdato() + CsvSplittetMed +
                        bruker.getKjonn() + CsvSplittetMed +
                        bruker.getBrukernavn() + CsvSplittetMed +
                        bruker.getPassord();

                personer.append(endretPerson).append("\n");
                continue;
            }
            personer.append(personInfo).append("\n");
        }

        skrivTilFil(personer.toString(), filnavnPerson);
    }

    public static void slettPerson(Person bruker) {
        ObservableList<Arrangement> arrangementer = hentArrangementer();
        for (Arrangement a : arrangementer) {
            if (bruker.getBrukerId() == a.getArrangor().getBrukerId()) {
                slettArrangement(a);
            }
        }
        slettFraListe(bruker.getBrukerId(), brukerIndex, filnavnDeltagere);
        slettFraListe(bruker.getBrukerId(), brukerIndex, filnavnAdmin);
        slettFraListe(bruker.getBrukerId(), 0, filnavnPerson);
    }

    public static void slettArrangement(Arrangement arrangement) {
        slettFraListe(arrangement.getArrangementId(), arrangementIndex, filnavnDeltagere);
        slettFraListe(arrangement.getArrangementId(), arrangementIndex, filnavnAdmin);
        slettFraListe(arrangement.getArrangementId(), arrangementIndex, filnavnArrangementer);
    }

    private static void slettFraListe(int id, int index, String filnavn) {
        ArrayList<String> liste = hentListe(filnavn);

        boolean erHeader = true;

        StringBuilder nyListe = new StringBuilder();

        for (String linje : liste) {
            if (erHeader) {
                erHeader = false;
                nyListe.append(linje).append("\n");
                continue;
            }
            String[] verdier = linje.split(CsvSplittetMed);
            if (id == Integer.parseInt(verdier[index])) {
                continue;
            }
            nyListe.append(linje).append("\n");
        }

        skrivTilFil(nyListe.toString(), filnavn);
    }

    private static void skrivTilFil(String tekst, String filnavn) {
        try {
            FileWriter filSkriver = new FileWriter(new File(filnavn));
            BufferedWriter bufferedCsvSkriver = new BufferedWriter(filSkriver);

            bufferedCsvSkriver.write(tekst);

            bufferedCsvSkriver.flush();
            bufferedCsvSkriver.close();
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}