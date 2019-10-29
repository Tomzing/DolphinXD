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
    @Test
    public void fungererLagNyBruker(){
        NyBrukerController test = new NyBrukerController();
        assertTrue(test.nyBruker("Testy","Test","18","Test","Test","Mann",true));
    }
    @Test
    public void fangerNyBrukerTommeFelt() {
        NyBrukerController test = new NyBrukerController();
        assertFalse(test.nyBruker("", "Test", "18", "Test", "Test", "Mann", true));
        assertFalse(test.nyBruker("Testy","","18","Test","Test","Mann",true));
        assertFalse(test.nyBruker("Testy","Test","","Test","Test","Mann",true));
        assertFalse(test.nyBruker("Testy","Test","18","","Test","Mann",true));
        assertFalse(test.nyBruker("Testy","Test","18","Test","","Mann",true));
    }

}





