package Controllers;

import Dolphin.Controller.NyBrukerController;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class BrukerController {

    @Test
    public void sjekkerFungererErTall() {
        assertTrue(NyBrukerController.erTall("24"));
        assertFalse(NyBrukerController.erTall("Hello"));
    }
    @Test
    public void fungererLagNyBruker() throws IOException {
        NyBrukerController test = new NyBrukerController();
        assertTrue(test.nyPerson("Testy","Test", LocalDate.parse("2000-12-12"),"Test","Test","Mann",true));
        /*BufferedReader bufferedReader = new BufferedReader(new FileReader("../../../resources/Database/personer.csv"));
        String input;
        int count = 0;
        while((input = bufferedReader.readLine()) != null)
        {
            count++;
        }

        System.out.println("Count : "+count);*/
    }
    @Test
    public void fangerNyBrukerTommeFelt() {
        NyBrukerController test = new NyBrukerController();
        assertFalse(test.nyPerson("", "Test", LocalDate.parse("2000-12-12"), "Test", "Test", "Mann", true));
        assertFalse(test.nyPerson("Testy","",LocalDate.parse("2000-12-12"),"Test","Test","Mann",true));
        assertFalse(test.nyPerson("Testy","Test",null,"Test","Test","Mann",true));
        assertFalse(test.nyPerson("Testy","Test",LocalDate.parse("2000-12-12"),"","Test","Mann",true));
        assertFalse(test.nyPerson("Testy","Test",LocalDate.parse("2000-12-12"),"Test","","Mann",true));
    }

}





