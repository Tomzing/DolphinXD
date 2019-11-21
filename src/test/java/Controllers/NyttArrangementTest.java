package Controllers;
import Dolphin.Controller.NyttArrangementController;
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

public class NyttArrangementTest {
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

    @Test
    public void ingenTommeFelterTest() {

        String navnTest = "";
        String sportsKategoriTest = "";
        String vanskelighetsgradTest = "Medium";
        String antallPlasserTest = "5";
        String prisTest = "";
        String stedTest = "EtSted";
        String startTidTest = "";
        String sluttTidTest = "";
        String beskrivelseTest = "";

        boolean erTest = true;

        NyttArrangementController controller = new NyttArrangementController();

        assertFalse(controller.ingenTommeFelter(navnTest,
                sportsKategoriTest,
                vanskelighetsgradTest,
                antallPlasserTest,
                prisTest,
                stedTest,
                startTidTest,
                sluttTidTest,
                beskrivelseTest,
                erTest));

        navnTest = "E";
        sportsKategoriTest = "F";
        vanskelighetsgradTest = "Medium";
        antallPlasserTest = "5";
        prisTest = "F";
        stedTest = "EtSted";
        startTidTest = "F";
        sluttTidTest = "F";
        beskrivelseTest = "F";

        assertTrue(controller.ingenTommeFelter(navnTest,
                sportsKategoriTest,
                vanskelighetsgradTest,
                antallPlasserTest,
                prisTest,
                stedTest,
                startTidTest,
                sluttTidTest,
                beskrivelseTest,
                erTest));
    }


    @AfterAll
    static void leggTilbakeCSVInnhold() {
        leggInnInnholdICSVFil(filArrangementer, arrangementerCSVInnhold);
        leggInnInnholdICSVFil(filBrukere, brukereCSVInnhold);
        leggInnInnholdICSVFil(filDeltagere, deltagerCSVInnhold);
        leggInnInnholdICSVFil(filAdministratorer, administratorerCSVInnhold);
    }
}
