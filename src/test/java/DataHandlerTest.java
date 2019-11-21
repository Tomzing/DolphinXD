import Dolphin.DataHandler.DataHandler;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Person;
import Dolphin.Model.SystemAdmin;
import org.junit.jupiter.api.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DataHandlerTest {

    private LocalDateTime fraDato = DataHandler.formaterDato("2002-06-28 20:00");
    private LocalDateTime  tilDato = DataHandler.formaterDato("2002-06-30 20:00");

    private Person testbruker;

    private SystemAdmin testadmin;

    private Arrangement testArrangement;

    private static final String filArrangementer = "src/main/resources/Database/arrangementer.csv";
    private static final String filBrukere = "src/main/resources/Database/personer.csv";
    private static final String filDeltagere = "src/main/resources/Database/deltagere.csv";
    private static final String filAdministratorer = "src/main/resources/Database/administratorer.csv";

    private static final String headerArrangement = "ID;NAVN;ARRANGØR;TYPE;VANSKELIGHETSGRAD;ANTALL PLASSER;PRIS;STARTTID;SLUTTID;STED;BESKRIVELSE\n";
    private static final String headerBrukere = "FORNAVN;ETTERNAVN;ALDER;KJØNN;BRUKERNAVN;PASSORD\n";
    private static final String headerDeltagereOgAdministratorer = "ARRANGEMENT-ID;BRUKERNAVN\n";

    private static String arrangementerCSVInnhold;
    private static String brukereCSVInnhold;
    private static String deltagerCSVInnhold;
    private static String administratorerCSVInnhold;

    @BeforeAll
    static void hentInnholdFraCSVFilene() {
        arrangementerCSVInnhold = hentCSVInnhold(filArrangementer);
        brukereCSVInnhold = hentCSVInnhold(filBrukere);
        deltagerCSVInnhold = hentCSVInnhold(filDeltagere);
        administratorerCSVInnhold = hentCSVInnhold(filAdministratorer);
    }

    @BeforeEach
    void gjorKlarTest() {
        leggInnInnholdICSVFil(filArrangementer, headerArrangement);
        leggInnInnholdICSVFil(filBrukere, headerBrukere);
        leggInnInnholdICSVFil(filDeltagere, headerDeltagereOgAdministratorer);
        leggInnInnholdICSVFil(filAdministratorer, headerDeltagereOgAdministratorer);

        testadmin = new SystemAdmin(1,"admintest","admintest");
        testbruker = new Person("Test","Testesen", LocalDate.parse("2000-12-12"),"Mann","Test","test");
        testArrangement = new Arrangement("Kult Arrangement",testbruker,"Sykkelritt",
                "Vanskelig",1000,200, fraDato, tilDato, "Stedesen 8",
                "Stryke raskt og fort med utrolige varmer!");
    }

    static String hentCSVInnhold(String filnavn) {
        BufferedReader br;
        String line;
        StringBuilder csv = new StringBuilder();

        try {
            br = new BufferedReader(new FileReader(filnavn));
            while ((line = br.readLine()) != null) {
                csv.append(line).append("\n");
            }
            return csv.toString();
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            return "";
        }
    }

    static void leggInnInnholdICSVFil(String filnavn, String tekst) {
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


    //Sjekker om man kan lagre en ny bruker som blir skrevet til csv og sjekker om brukeren kan bli lest fra listen
    //med brukere
    @Test
    void lagreOgHentBruker() {
        DataHandler.lagrePerson(testbruker);

        assertEquals(DataHandler.hentListeMedPersoner().get(0).getBrukernavn(), testbruker.getBrukernavn());
    }

    @Test
    void lagreOgHentArrangement() {
        DataHandler.lagreArrangement(testArrangement);

        assertEquals(DataHandler.hentArrangementer().get(0).getNavn(),testArrangement.getNavn());
}

    //Sjekker lister returnert fra DataHandler er tomme. Dette skal aldri være tilfelle, og tyder
    //på en eventuell feil hvis disse feiler.
    @Test
    void faaArrangementerFraCsv() {
        DataHandler.lagreArrangement(testArrangement);


        assertFalse(DataHandler.hentArrangementer().isEmpty());
    }

    @Test
    void faaBrukerlisteFraCsv() {
        DataHandler.lagrePerson(testbruker);

        assertFalse(DataHandler.hentListeMedPersoner().isEmpty());
    }

    //Implementasjonen av adminlisten er litt annerledes, litt kronglete
    @Test
    void faaAdminListeFraCsv() {
        leggInnInnholdICSVFil(filAdministratorer,"1;admintest;admintest");

        hentCSVInnhold(filAdministratorer);

        assertFalse(DataHandler.hentListeMedSysAdmin().isEmpty());
    }

    //Tester om man kan legge til bruker i arrangement brukerlistene
    @Test
    void faaArrangementDeltagerliste() {

        DataHandler.lagreArrangement(testArrangement);
        DataHandler.lagrePerson(testbruker);
        DataHandler.lagreDeltager(testbruker, testArrangement);

        ArrayList<Person> liste = DataHandler.hentArrangementDeltagere(testArrangement);

        assertEquals(liste.get(0).getBrukernavn(), testbruker.getBrukernavn());
    }
    @Test
    void slettBruker(){
        DataHandler.lagrePerson(testbruker);
        DataHandler.slettPerson(testbruker);
        assertTrue(DataHandler.hentListeMedPersoner().isEmpty());
    }


    @AfterAll
    static void leggTilbakeCSVInnhold() {
        leggInnInnholdICSVFil(filArrangementer, arrangementerCSVInnhold);
        leggInnInnholdICSVFil(filBrukere, brukereCSVInnhold);
        leggInnInnholdICSVFil(filDeltagere, deltagerCSVInnhold);
        leggInnInnholdICSVFil(filAdministratorer, administratorerCSVInnhold);
    }
}
