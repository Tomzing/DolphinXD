package Dolphin.Model;

import java.time.LocalDateTime;

public class ArrangementSykkelritt extends Arrangement {
    private String vanskelighetsgrad;
    public ArrangementSykkelritt(int id, String navn, String type, int antallPlasser, LocalDateTime startDato, LocalDateTime sluttDato,
                                 String plassering, String vanskelighetsgrad) {
        super(id ,navn, type, antallPlasser, startDato, sluttDato, plassering);

        this.vanskelighetsgrad = vanskelighetsgrad;
    }

    public ArrangementSykkelritt(String navn, String type, int antallPlasser, LocalDateTime startDato, LocalDateTime sluttDato,
                                 String plassering, String vanskelighetsgrad) {
        super(navn, type, antallPlasser, startDato, sluttDato, plassering);

        this.vanskelighetsgrad = vanskelighetsgrad;
    }

    public String getVanskelighetsgrad() {
        return vanskelighetsgrad;
    }

    public void setVanskelighetsgrad(String vanskelighetsgrad) {
        this.vanskelighetsgrad = vanskelighetsgrad;
    }
}
