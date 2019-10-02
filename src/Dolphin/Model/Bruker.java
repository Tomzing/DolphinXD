package Dolphin.Model;

public class Bruker {

    private String fornavn;
    private String etternavn;
    private int alder;
    private String kjonn;
    private String brukernavn;
    private String passord;

    public Bruker(String fornavn, String etternavn, int alder, String kjonn, String brukernavn, String passord) {
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.alder = alder;
        this.kjonn = kjonn;
        this.brukernavn = brukernavn;
        this.passord = passord;
    }

    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getEtternavn() {
        return etternavn;
    }

    public void setEtternavn(String etternavn) {
        this.etternavn = etternavn;
    }

    public int getAlder() {
        return alder;
    }

    public void setAlder(int alder) {
        this.alder = alder;
    }

    public String getKjonn() {
        return kjonn;
    }

    public void setKjonn(String kjonn) {
        this.kjonn = kjonn;
    }

    public String getBrukernavn() {
        return brukernavn;
    }

    public void setBrukernavn(String brukernavn) {
        this.brukernavn = brukernavn;
    }

    public String getPassord() {
        return passord;
    }

    public void setPassord(String passord) {
        this.passord = passord;
    }
}
