import Dolphin.Controller.NyBrukerController;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


class Controllers {

    @Test
    public void sjekkerFungererErTall() {
        NyBrukerController controller1 = new NyBrukerController();
        Assert.assertTrue(NyBrukerController.erTall("24") );
        Assert.assertFalse(NyBrukerController.erTall("Hello"));
    }
}





