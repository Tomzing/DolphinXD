package Dolphin.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class Arrangement implements Comparable<Arrangement> {
    private static final AtomicInteger teller = new AtomicInteger(0);
    private int arrangementId;
    private String navn;
    private Bruker arrangor;
    private String type;
    private int antallPlasser;
    private long pris;
    private LocalDateTime starttid;
    private LocalDateTime sluttid;
    private String sted;
    private String vanskelighetsgrad;
    private String beskrivelse;
    private ArrayList<Bruker> deltakereOppmeldt;
    private ArrayList<Bruker> administratorer;

    public Arrangement(int arrangementId, String navn, Bruker arrangor, String type, String vanskelighetsgrad, int antallPlasser, long pris,
                       LocalDateTime starttid, LocalDateTime sluttid, String sted, String beskrivelse) {
        this.arrangementId = arrangementId;
        teller.set(arrangementId);
        setVerdier(navn, arrangor, type, vanskelighetsgrad, antallPlasser, pris, starttid, sluttid, sted, beskrivelse);
    }

    public Arrangement(String navn, Bruker arrangor, String type, String vanskelighetsgrad, int antallPlasser, long pris,
                       LocalDateTime starttid, LocalDateTime sluttid, String sted, String beskrivelse) {
        this.arrangementId = teller.incrementAndGet();
        setVerdier(navn, arrangor, type, vanskelighetsgrad, antallPlasser, pris, starttid, sluttid, sted, beskrivelse);
    }

    public void setVerdier(String navn, Bruker arrangor, String type, String vanskelighetsgrad, int antallPlasser, long pris,
                            LocalDateTime starttid, LocalDateTime sluttid, String sted, String beskrivelse) {
        this.navn = navn;
        this.arrangor = arrangor;
        this.type = type;
        this.antallPlasser = antallPlasser;
        this.pris = pris;
        this.starttid = starttid;
        this.sluttid = sluttid;
        this.sted = sted;
        this.vanskelighetsgrad = vanskelighetsgrad;
        this.beskrivelse = beskrivelse;
        this.deltakereOppmeldt = new ArrayList<>();
        this.administratorer = new ArrayList<>();
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

    public int getLedigePlasser() {
        return antallPlasser - deltakereOppmeldt.size();
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
        return navn + "\n" + type + " | Ledige plasser: " + getLedigePlasser();
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

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public ArrayList<Bruker> getAdministratorer() {
        return administratorer;
    }

    public void setAdministratorer(ArrayList<Bruker> administratorer) {
        this.administratorer = administratorer;
    }

    @Override
    public int compareTo(Arrangement a) {
        return navn.compareTo(a.navn);
    }
}
