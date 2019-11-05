package Dolphin.Model;

import java.time.LocalDateTime;

public class ArrangementAnnet /*extends Arrangement*/ {
    private String annetArrangementTekst;

    /*public ArrangementAnnet(String navn, String type, int antallPlasser, LocalDateTime startDato, LocalDateTime sluttDato,
                            String plassering, String annetArrangementTekst) {
        super(navn, type, antallPlasser, startDato, sluttDato, plassering);

        this.annetArrangementTekst = annetArrangementTekst;
    }

    /*public ArrangementAnnet(String navn, String type, int antallPlasser, LocalDateTime startDato, LocalDateTime sluttDato,
                            String plassering, String annetArrangementTekst) {
        super(navn, type, antallPlasser, startDato, sluttDato, plassering);

        this.annetArrangementTekst = annetArrangementTekst;
    }*/

    public String getAnnetArrangementTekst() {
        return annetArrangementTekst;
    }

    public void setAnnetArrangementTekst(String annetArrangementTekst) {
        this.annetArrangementTekst = annetArrangementTekst;
    }
}
