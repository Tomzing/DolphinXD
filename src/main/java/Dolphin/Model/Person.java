package Dolphin.Model;

import java.time.LocalDate;

public class Person extends Bruker {

    private String fornavn;
    private String etternavn;
    private LocalDate fodselsdato;
    private String kjonn;

    public Person(String fornavn, String etternavn, LocalDate fodselsdato, String kjonn, String brukernavn, String passord) {
        super(brukernavn, passord);
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.fodselsdato = fodselsdato;
        this.kjonn = kjonn;
    }

    public Person(int id, String fornavn, String etternavn, LocalDate fodselsdato, String kjonn, String brukernavn, String passord) {
        super(id, brukernavn, passord);
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.fodselsdato = fodselsdato;
        this.kjonn = kjonn;
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

    @Override
    public String toString() {
        return fornavn + " " + etternavn;
    }
}
