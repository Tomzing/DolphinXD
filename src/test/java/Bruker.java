import Dolphin.Controller.NyBrukerController;
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
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Bruker {
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

        brukereCSVInnhold = hentCSVInnhold(filBrukere);
    }

    @BeforeEach
    void gjorKlarTest() {
        leggInnInnholdICSVFil(filBrukere, headerBrukere);



        testbruker = new Person("Test","Testesen", LocalDate.parse("2000-12-12"),"Mann","Test","test");

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
    public void endreBruker(){
        ArrayList<String> info = new ArrayList<String>();
        info.add("Test");
        info.add("Testesen");
        info.add("Annet");
        info.add("Test");
        info.add("Test");
        NyBrukerController controller = new NyBrukerController();
        controller.endreEnPerson(testbruker, info, LocalDate.parse("2000-12-12"));
        assertEquals("Annet", testbruker.getKjonn());
    }
    @AfterAll
    static void leggTilbakeCSVInnhold() {
        leggInnInnholdICSVFil(filBrukere, brukereCSVInnhold);
    }
}

