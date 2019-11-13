package Dolphin.Model;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Bruker {
    private static final AtomicInteger teller = new AtomicInteger(0);

    private int brukerId;
    private String fornavn;
    private String etternavn;
    private LocalDate fodselsdato;
    private String kjonn;
    private String brukernavn;
    private String passord;

    public Bruker(String fornavn, String etternavn, LocalDate fodselsdato, String kjonn, String brukernavn, String passord) {
        this.brukerId = teller.incrementAndGet();
        setVerdier(fornavn, etternavn, fodselsdato, kjonn, brukernavn, passord);
    }

    public Bruker(int id, String fornavn, String etternavn, LocalDate fodselsdato, String kjonn, String brukernavn, String passord) {
        this.brukerId = id;
        teller.incrementAndGet();
        setVerdier(fornavn, etternavn, fodselsdato, kjonn, brukernavn, passord);
    }

    private void setVerdier(String fornavn, String etternavn, LocalDate fodselsdato, String kjonn, String brukernavn, String passord) {
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.fodselsdato = fodselsdato;
        this.kjonn = kjonn;
        this.brukernavn = brukernavn;
        this.passord = passord;
    }

    public int getBrukerId() {
        return brukerId;
    }

    public void setBrukerId(int brukerId) {
        this.brukerId = brukerId;
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

    public LocalDate getFodselsdato() {
        return fodselsdato;
    }

    public void setFodselsdato(LocalDate fodselsdato) {
        this.fodselsdato = fodselsdato;
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

    @Override
    public String toString() {
        return "ID: " + brukerId + " Navn: " + fornavn + " " + etternavn + " Brukernavn: " + brukernavn;
    }
}
