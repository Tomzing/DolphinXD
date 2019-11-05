package Dolphin.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Arrangement {
    private static final AtomicInteger teller = new AtomicInteger(0);
    private int arrangementId;
    private String navn;
    private Bruker arrangor;
    //TYPE MÅ MEST SANNSYNLIG ENDRES
    private String type;
    private int antallPlasser;
    private long pris;
    private LocalDateTime starttid;
    private LocalDateTime sluttid;
    private String sted;
    private String vanskelighetsgrad;
    private ArrayList<Bruker> deltakereOppmeldt;

    public Arrangement(String navn, Bruker arrangor, String type, String vanskelighetsgrad, int antallPlasser,
                       long pris, LocalDateTime starttid, LocalDateTime sluttid, String sted) {
        this.arrangementId = teller.incrementAndGet();
        this.navn = navn;
        this.arrangor = arrangor;
        this.type = type;
        this.antallPlasser = antallPlasser;
        this.pris = pris;
        this.starttid = starttid;
        this.sluttid = sluttid;
        this.sted = sted;
        this.vanskelighetsgrad = vanskelighetsgrad;
        this.deltakereOppmeldt = new ArrayList<>();
    }

    /*public Arrangement(String navn, String type, int antallPlasser, LocalDateTime startDato, LocalDateTime sluttDato,
                       String plassering) {
        this.arrangementId = teller.incrementAndGet();
        this.navn = navn;
        this.type = type;
        this.antallPlasser = antallPlasser;
        this.startDato = startDato;
        this.sluttDato = sluttDato;
        this.plassering = plassering;
        this.deltakereOppmeldt = new ArrayList<>();
    }*/

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

    public LocalDateTime getStarttid() {
        return starttid;
    }

    public void setStarttid(LocalDateTime starttid) {
        this.starttid = starttid;
    }

    public String getSted() {
        return sted;
    }

    public void setSted(String sted) {
        this.sted = sted;
    }

    public LocalDateTime getSluttid() {
        return sluttid;
    }

    public void setSluttid(LocalDateTime sluttid) {
        this.sluttid = sluttid;
    }

    public ArrayList<Bruker> getDeltakereOppmeldt() {
        return deltakereOppmeldt;
    }

    public void setDeltakereOppmeldt(ArrayList<Bruker> deltakereOppmeldt) {
        this.deltakereOppmeldt = deltakereOppmeldt;
    }

    @Override
    public String toString() {
        return "ID: " + this.arrangementId + " | " + "Navn: " + this.navn + " Antall plasser: " + this.antallPlasser + "| Type " + this.type + " | " + "Startdato: " + this.starttid;
    }

    public Bruker getArrangor() {
        return arrangor;
    }

    public void setArrangor(Bruker arrangor) {
        this.arrangor = arrangor;
    }

    public long getPris() {
        return pris;
    }

    public void setPris(long pris) {
        this.pris = pris;
    }

    public String getVanskelighetsgrad() {
        return vanskelighetsgrad;
    }

    public void setVanskelighetsgrad(String vanskelighetsgrad) {
        this.vanskelighetsgrad = vanskelighetsgrad;
    }
}
