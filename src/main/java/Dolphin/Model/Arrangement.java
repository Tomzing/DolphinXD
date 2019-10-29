package Dolphin.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Arrangement {
    private static int teller = 0;
    private int arrangementId;
    private String navn;
    //TYPE MÅ MEST SANNSYNLIG ENDRES
    private String type;
    private int antallPlasser;
    private LocalDateTime startDato;
    private LocalDateTime sluttDato;
    private String plassering;
    private ArrayList<Bruker> deltakereOppmeldt;

    public Arrangement(int arrangementId, String navn, String type, int antallPlasser, LocalDateTime startDato, LocalDateTime sluttDato,
                       String plassering) {
        this.arrangementId = arrangementId;
        this.navn = navn;
        this.type = type;
        this.antallPlasser = antallPlasser;
        this.startDato = startDato;
        this.sluttDato = sluttDato;
        this.plassering = plassering;
        this.deltakereOppmeldt = new ArrayList<>();
    }

    public Arrangement(String navn, String type, int antallPlasser, LocalDateTime startDato, LocalDateTime sluttDato,
                       String plassering) {
        this.arrangementId = ++teller;
        this.navn = navn;
        this.type = type;
        this.antallPlasser = antallPlasser;
        this.startDato = startDato;
        this.sluttDato = sluttDato;
        this.plassering = plassering;
        this.deltakereOppmeldt = new ArrayList<>();
    }

    public void leggTilNyDeltager(Bruker bruker) {
        if (deltakereOppmeldt.size() < antallPlasser) {
            boolean erPaameldt = false;
            for (Bruker deltager : deltakereOppmeldt) {
                if (deltager.getBrukernavn().equals(bruker.getBrukernavn())) {
                    erPaameldt = true;
                    break;
                }
            }
            if (!erPaameldt) {
                deltakereOppmeldt.add(bruker);
            }
        }
    }

    public void fjernDeltager(Bruker bruker) {
        for (Bruker deltager : deltakereOppmeldt) {
            if (bruker.getBrukernavn().equals(deltager.getBrukernavn())) {
                deltakereOppmeldt.remove(deltager);
                break;
            }
        }
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

    public ArrayList<Bruker> getDeltakereOppmeldt() {
        return deltakereOppmeldt;
    }

    public void setDeltakereOppmeldt(ArrayList<Bruker> deltakereOppmeldt) {
        this.deltakereOppmeldt = deltakereOppmeldt;
    }

    @Override
    public String toString() {
        return "ID: " + this.arrangementId + " " + "Navn: " + this.navn + " " + this.deltakereOppmeldt;
    }
}
