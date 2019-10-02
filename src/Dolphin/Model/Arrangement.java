package Dolphin.Model;

import java.util.Date;

public class Arrangement {
    private int arrangementId;
    private String navn;
    //TYPE MÅ MEST SANNSYNLIG ENDRES
    private String type;
    private int antallPlasser;
    private Date dato;
    private String plassering;

    public Arrangement(int arrangementId, String navn, String type, int antallPlasser, Date dato, String plassering) {
        this.arrangementId = arrangementId;
        this.navn = navn;
        this.type = type;
        this.antallPlasser = antallPlasser;
        this.dato = dato;
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

    public Date getDato() {
        return dato;
    }

    public void setDato(Date dato) {
        this.dato = dato;
    }

    public String getPlassering() {
        return plassering;
    }

    public void setPlassering(String plassering) {
        this.plassering = plassering;
    }
}
