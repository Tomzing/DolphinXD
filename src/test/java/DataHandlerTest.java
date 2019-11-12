import Dolphin.DataHandler.DataHandler;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Bruker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataHandlerTest {

    LocalDateTime fraDato = Dolphin.DataHandler.DataHandler.formaterDato("2002-06-28 20:00");
    LocalDateTime  tilDato = Dolphin.DataHandler.DataHandler.formaterDato("2002-06-30 20:00");

    Bruker testbruker = new Bruker("Test","Testesen", LocalDate.parse("2000-12-12"),"Mann","Test","test");

    Arrangement test1 = new Arrangement("Kult Arrangement",testbruker,"Sykkelritt",
            "Vanskelig",1000,200, fraDato, tilDato, "Stedesen 8",
            "Stryke raskt og fort med utrolige varmer!");


    //Sjekker nedover om lister returnert fra DataHandler er tomme. Dette skal aldri være tilfelle, og tyder
    //på en eventuell feil hvis disse feiler.
    @Test
    public void faaArrangementerFraCsv() {
        Assertions.assertFalse(Dolphin.DataHandler.DataHandler.hentArrangementer().isEmpty());
    }

    @Test
    public void faaBrukerlisteFraCsv() {
        Assertions.assertFalse(Dolphin.DataHandler.DataHandler.hentListeMedBrukere().isEmpty());
    }

    //Sjekker om man kan lagre en ny bruker som blir skrevet til csv og sjekker om brukeren kan bli lest fra listen
    //med brukere
    @Test
    public void lagreOgHentBruker() {
        DataHandler.lagreBruker(testbruker);

        int storrelsePaaListen = DataHandler.hentListeMedBrukere().size()-1;

        Assertions.assertEquals(DataHandler.hentListeMedBrukere().get(storrelsePaaListen).getBrukernavn().toLowerCase(),
                testbruker.getBrukernavn().toLowerCase());

    }

    /*@Test
    public void lagreArrangementTest() {
        DataHandler.lagreArrangement(test1);

        int storrelsePaaArray = DataHandler.hentArrangementer().size() - 1;

        assertEquals(DataHandler.hentArrangementer().get(storrelsePaaArray).getNavn(),test1.getNavn());
    }*/

    //Tester om man kan legge til bruker i arrangement brukerlistene
    @Test
    public void faaArrangementBrukerliste() {

        int storrelsePaaArray = DataHandler.hentArrangementer().size() - 1;

        try {
            File file = new File("src/main/resources/Database/deltagere.csv");

            FileWriter filSkriver = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bufretCsvSkriver = new BufferedWriter(filSkriver);
            bufretCsvSkriver.write(test1.getArrangementId() + ";" + testbruker.getBrukernavn() + "\n");

            bufretCsvSkriver.flush();
            bufretCsvSkriver.close();
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        ArrayList<Bruker> liste = DataHandler.hentArrangementBrukerliste(test1,"deltagere.csv");

        assertEquals(liste.get(storrelsePaaArray).getBrukernavn().toLowerCase(), testbruker.getBrukernavn().toLowerCase());
    }

}
