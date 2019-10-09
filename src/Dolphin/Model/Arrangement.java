package Dolphin.Model;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Arrangement {
    private static final AtomicInteger teller = new AtomicInteger(0);
    private int arrangementId;
    private String navn;
    //TYPE MÅ MEST SANNSYNLIG ENDRES
    private String type;
    private int antallPlasser;
    private LocalDateTime startDato;
    private LocalDateTime sluttDato;
    private String plassering;

    public Arrangement(String navn, String type, int antallPlasser, LocalDateTime startDato, LocalDateTime sluttDato, String plassering) {
        this.arrangementId = teller.incrementAndGet();
        this.navn = navn;
        this.type = type;
        this.antallPlasser = antallPlasser;
        this.startDato = startDato;
        this.sluttDato = sluttDato;
        this.plassering = plassering;
    }

    public int getArrangementId() {
        return arrangementId;
    }

    public void setArrangementId(int arrangementId) {
        this.arrangementId = arrangementId;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAntallPlasser() {
        return antallPlasser;
    }

    public void setAntallPlasser(int antallPlasser) {
        this.antallPlasser = antallPlasser;
    }

    public LocalDateTime getStartDato() {
        return startDato;
    }

    public void setStartDato(LocalDateTime startDato) {
        this.startDato = startDato;
    }

    public String getPlassering() {
        return plassering;
    }

    public void setPlassering(String plassering) {
        this.plassering = plassering;
    }

    public LocalDateTime getSluttDato() {
        return sluttDato;
    }

    public void setSluttDato(LocalDateTime sluttDato) {
        this.sluttDato = sluttDato;
    }
}
