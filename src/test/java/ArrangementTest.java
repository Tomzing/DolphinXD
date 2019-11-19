import Dolphin.DataHandler.DataHandler;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Bruker;
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
}
