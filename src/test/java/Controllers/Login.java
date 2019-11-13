package Controllers;

import Dolphin.Controller.LoggInnController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Login {

    private static LoggInnController testLoggInn;

    @BeforeAll
    static void getController() {
        testLoggInn = new LoggInnController();
    }

    @Test
    public void loggerBrukerInn(){
        //LoggInnController testLoggInn = new LoggInnController();
        assertTrue(testLoggInn.loggInnKjorer("t", "t",true));

        //Test testesen er en bruker som allerede eksister, og er ment til å bare være en del av prototypen
    }
    @Test
    public void loggerBrukerIkkeInn(){
        //LoggInnController testLoggInn = new LoggInnController();
        assertFalse(testLoggInn.loggInnKjorer("", "", true));
    }
}
