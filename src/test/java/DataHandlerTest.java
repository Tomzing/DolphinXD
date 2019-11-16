import Dolphin.DataHandler.DataHandler;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Bruker;
import org.junit.jupiter.api.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DataHandlerTest {

    private LocalDateTime fraDato = DataHandler.formaterDato("2002-06-28 20:00");
    private LocalDateTime  tilDato = DataHandler.formaterDato("2002-06-30 20:00");

    private Bruker testbruker;

    private Arrangement testArrangement;

    private static final String filArrangementer = "src/main/resources/Database/arrangementer.csv";
    private static final String filBrukere = "src/main/resources/Database/brukere.csv";
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

        testbruker = new Bruker("Test","Testesen", LocalDate.parse("2000-12-12"),"Mann","Test","test");
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

    //Sjekker nedover om lister returnert fra DataHandler er tomme. Dette skal aldri være tilfelle, og tyder
    //på en eventuell feil hvis disse feiler.
    @Test
    void faaArrangementerFraCsv() {
        assertTrue(DataHandler.hentArrangementer().isEmpty());
    }

    @Test
    void faaBrukerlisteFraCsv() {
        System.out.println(DataHandler.hentListeMedBrukere());
        assertTrue(DataHandler.hentListeMedBrukere().isEmpty());
    }

    //Sjekker om man kan lagre en ny bruker som blir skrevet til csv og sjekker om brukeren kan bli lest fra listen
    //med brukere
    @Test
    void lagreOgHentBruker() {
        DataHandler.lagreBruker(testbruker);

        assertEquals(DataHandler.hentListeMedBrukere().get(0).getBrukernavn(), testbruker.getBrukernavn());

    }

    @Test
    void lagreOgHentArrangement() {
        DataHandler.lagreArrangement(testArrangement);

        assertEquals(DataHandler.hentArrangementer().get(0).getNavn(),testArrangement.getNavn());
    }

    //Tester om man kan legge til bruker i arrangement brukerlistene
    @Test
    void faaArrangementDeltagerliste() {

        DataHandler.lagreArrangement(testArrangement);
        DataHandler.lagreBruker(testbruker);
        DataHandler.lagreDeltager(testbruker, testArrangement);

        ArrayList<Bruker> liste = DataHandler.hentArrangementBrukerliste(testArrangement,"deltagere.csv");

        assertEquals(liste.get(0).getBrukernavn(), testbruker.getBrukernavn());
    }


    @AfterAll
    static void leggTilbakeCSVInnhold() {
        leggInnInnholdICSVFil(filArrangementer, arrangementerCSVInnhold);
        leggInnInnholdICSVFil(filBrukere, brukereCSVInnhold);
        leggInnInnholdICSVFil(filDeltagere, deltagerCSVInnhold);
        leggInnInnholdICSVFil(filAdministratorer, administratorerCSVInnhold);
    }

}
