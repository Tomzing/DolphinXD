package Controllers;

import Dolphin.Controller.LoggInnController;
import Dolphin.Model.Bruker;
//import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class Login {
    @Test
    public void loggerBrukerInn(){
        LoggInnController testLoggInn = new LoggInnController();
        //assertTrue(testLoggInn.loggInnKjorer("t", "t"));
        //Test testesen er en bruker som allerede eksister, og er ment til å bare være en del av prototypen
    }
    @Test
    public void loggerBrukerIkkeInn(){
        LoggInnController testLoggInn = new LoggInnController();
        //assertFalse(testLoggInn.loggInnKjorer("", ""));
    }
}
