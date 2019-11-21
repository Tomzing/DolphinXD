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
}
