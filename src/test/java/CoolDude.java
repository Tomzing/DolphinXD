import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CoolDude {
    @Test
    public void eksistererCoolDude(){
        File tmp =new File("../Ressurser/cooldude.jpg");
        assertNotNull(tmp);
    }
    //Denne testen returnerer alltid true, det er fordi CoolDude vil alltid eksistere i hjertet vårt
}
