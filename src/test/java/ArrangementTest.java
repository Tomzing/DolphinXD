import Dolphin.Controller.SpesifiktArrangementController;
import Dolphin.DataHandler.DataHandler;
import Dolphin.Model.Arrangement;

import Dolphin.Model.Person;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ArrangementTest {

    @Test
    public void lagNyAnnetArrangement() {
        LocalDateTime fraDato = DataHandler.formaterDato("2002-06-28 20:00");
        LocalDateTime  tilDato = DataHandler.formaterDato("2002-06-30 20:00");

        Person testbruker = new Person("Roger","Rogersen", LocalDate.parse("2000-12-12"),"Mann","test","test");

        Arrangement test1 = new Arrangement("Kult Arrangement",testbruker,"Sykkelritt",
                "Vanskelig",1000,200, fraDato, tilDato, "Stedesen 8",
                "Stryke raskt og fort med utrolige varmer!");

        assertNotNull(test1);
    }
    @Test
    public void leggTillBrukerIarrangement(){
        LocalDateTime fraDato = DataHandler.formaterDato("2002-06-28 20:00");
        LocalDateTime  tilDato = DataHandler.formaterDato("2002-06-30 20:00");

        Person testbruker1 = new Person("Test1","Testesen1", LocalDate.parse("2000-12-12"),"Mann","test1","test1");
        Person testbruker2 = new Person("Test2","Testesen2", LocalDate.parse("2000-12-12"),"Mann","test2","test2");

        Arrangement testArran = new Arrangement("Kult Arrangement",testbruker1,"Sykkelritt",
                "Vanskelig",1,200, fraDato, tilDato, "Stedesen 8",
                "Stryke raskt og fort med utrolige varmer!");
        assertTrue(testArran.leggTilNyDeltager(testbruker1)); //Denne skal returnere true fordi det fortsatt er plasser
        assertFalse(testArran.leggTilNyDeltager(testbruker2));//Denne blir false fordi siste plassen er opptatt. Er i samme test for å unngå mye redudant kode

    }
    //Sjekker om (det veldig sofistikerte) betalingsystemet fungerer
    @Test
    public void betalingsSystem(){
        SpesifiktArrangementController test = new SpesifiktArrangementController();
        assertTrue(SpesifiktArrangementController.betalingsSystem(true));
        assertFalse(SpesifiktArrangementController.betalingsSystem(false));
    }
}
