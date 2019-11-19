package Controllers;

import Dolphin.Controller.LoggInnController;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class Login {

    private static LoggInnController testLoggInn;

    @BeforeAll
    static void startController() {
        testLoggInn = new LoggInnController();
    }

    @Test
    public void loggerBrukerInn() {
        assertTrue(testLoggInn.loggInnKjorer("t", "t", true));

        //Test testesen er en bruker som allerede eksister, og er ment til å bare være en del av prototypen
    }



    @Test
    public void loggerBrukerIkkeInn() {
        assertFalse(testLoggInn.loggInnKjorer("", "", true));
    }
}
