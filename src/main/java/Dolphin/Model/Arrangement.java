package Dolphin.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    //Den her blir vrien, for nå må vi vel danne en "database" for denne listen, slik at brukere forblir oppmeldt.
    //Kanskje lage logikk som tar i mot bruker objekter i listen, når man legger til at brukere kan melde seg på arrangementet.
    private ObservableList<Bruker> deltakereOppmeldt = FXCollections.observableArrayList();

    public Arrangement(String navn, String type, int antallPlasser, LocalDateTime startDato, LocalDateTime sluttDato,
                       String plassering) {
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

    public ObservableList<Bruker> getDeltakereOppmeldt() {
        return deltakereOppmeldt;
    }

    public void setDeltakereOppmeldt(ObservableList<Bruker> deltakereOppmeldt) {
        this.deltakereOppmeldt = deltakereOppmeldt;
    }

    @Override
    public String toString() {
        return "ID: " + this.arrangementId + " " + "Navn: " + this.navn + " " + this.deltakereOppmeldt;
    }
}
