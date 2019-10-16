import Dolphin.DataHandler.DataHandler;
import Dolphin.Model.ArrangementAnnet;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Arrangement {
    @Test
    public void lagNyAnnetArrangement() {

        LocalDateTime fraDato = DataHandler.formaterDato("2002-06-28 20:00");
        LocalDateTime  tilDato = DataHandler.formaterDato("2002-06-30 20:00");

        ArrangementAnnet test1 = new ArrangementAnnet ("Kult Arrangement","Ekstrem stryking",
                1000, fraDato, tilDato, "Stedesen 8",
                "Stryke raskt og fort med utrolige varmer!");
        assertNotNull(test1);
    }
}
