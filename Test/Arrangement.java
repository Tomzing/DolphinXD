import Dolphin.Model.ArrangementAnnet;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Arrangement {
    @Test
    void lagNyAnnetArrangement() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2002-06-28 20:15", formatter);

        ArrangementAnnet test1 = new ArrangementAnnet ("Kult Arrangement","Ekstrem stryking",
                1000,dateTime, dateTime, "Stedesen 8",
                "Stryke raskt og fort med utrolige varmer!");
    }
}
