package Controllers;

import Dolphin.Controller.NyBrukerController;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class BrukerController {

    @Test
    public void sjekkerFungererErTall() {
        assertTrue(NyBrukerController.erTall("24"));
        assertFalse(NyBrukerController.erTall("Hello"));
    }

}





