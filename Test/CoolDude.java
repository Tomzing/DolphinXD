import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class CoolDude {
    @Test
    public void eksistererCoolDude(){
        File tmp =new File("../Ressurser/cooldude.jpg");
        Assert.assertNotNull(tmp);
    }
}
