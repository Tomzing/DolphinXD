package Controllers;


import Dolphin.Controller.SpesifiktArrangementController;
import Dolphin.DataHandler.DataHandler;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Person;
import Dolphin.Model.SystemAdmin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class SpesifiktArrangementTest {
    private LocalDateTime fraDato = DataHandler.formaterDato("2002-06-28 20:00");
    private LocalDateTime  tilDato = DataHandler.formaterDato("2100-06-30 20:00");

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
        testArrangement = new Arrangement("Kult Arrangement",testadmin,"Sykkelritt",
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

   @Test
    public void meldPaaArrangement() {

       SpesifiktArrangementController controller = new SpesifiktArrangementController();
       assertEquals("meldPaa",controller.meldPaaBruker(testbruker, testArrangement));
    }
    @Test
    public void meldAavArrangement(){
        DataHandler.lagreDeltager(testbruker, testArrangement);
        SpesifiktArrangementController controller = new SpesifiktArrangementController();
        assertTrue(controller.meldAvBruker(testbruker,testArrangement));
    }
    @Test
    public void meldAavArrangementFeilet(){
        SpesifiktArrangementController controller = new SpesifiktArrangementController();

        //null her vil si at en bruker ikke er blitt selektert i viewet
        assertFalse(controller.meldAvBruker(null,testArrangement));
    }
    @AfterAll
    static void leggTilbakeCSVInnhold() {
        leggInnInnholdICSVFil(filArrangementer, arrangementerCSVInnhold);
        leggInnInnholdICSVFil(filBrukere, brukereCSVInnhold);
        leggInnInnholdICSVFil(filDeltagere, deltagerCSVInnhold);
        leggInnInnholdICSVFil(filAdministratorer, administratorerCSVInnhold);
    }
}
