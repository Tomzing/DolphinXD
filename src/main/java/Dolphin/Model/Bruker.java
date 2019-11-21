package Dolphin.Model;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Bruker {
    private static final AtomicInteger teller = new AtomicInteger(0);

    private int brukerId;
    private String brukernavn;
    private String passord;

    public Bruker(String brukernavn, String passord) {
        this.brukerId = teller.incrementAndGet();
        this.brukernavn = brukernavn;
        this.passord = passord;
    }

    public Bruker(int brukerId, String brukernavn, String passord) {
        this.brukerId = brukerId;
        if (brukerId > teller.get()) {
            teller.set(brukerId);
        }
        this.brukernavn = brukernavn;
        this.passord = passord;
    }

    public int getBrukerId() {
        return brukerId;
    }

    public void setBrukerId(int brukerId) {
        this.brukerId = brukerId;
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
        return brukernavn;
    }

}
