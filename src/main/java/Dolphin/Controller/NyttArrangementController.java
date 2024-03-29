package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Main;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Bruker;
import Dolphin.Model.Person;
import Dolphin.Model.SystemAdmin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NyttArrangementController {

    private Main minApplikasjon = Main.getInstance();
    private ObservableList<Person> adminliste = FXCollections.observableArrayList();
    private Bruker aktivBruker;
    private Arrangement valgtArrangement;
    private ObservableList<String> kategorier;

    @FXML
    private Text txtOverskrift;

    @FXML
    private TextField txtNavn, txtKategoriAnnet, txtAntallPlasser, txtSted, txtStarttid, txtSluttid, txtPris, txtAdminBrukernavn;

    @FXML
    private ComboBox<String> cbSportskategori;

    @FXML
    private ComboBox<String> cbVanskelighetsgrad;

    @FXML
    private TextArea txtBeskrivelse;

    @FXML
    private ListView<Person> lvAdminliste;

    public void initialize() {
        aktivBruker =  minApplikasjon.getAktivBruker();
        valgtArrangement = minApplikasjon.getValgtArrangement();

        kategorier = FXCollections.observableArrayList("Fotballkamp", "Sykkelritt", "Løp", "Annet");

        cbSportskategori.setItems(kategorier);
        cbVanskelighetsgrad.setItems(FXCollections.observableArrayList("Lett", "Middels", "Vanskelig"));

        if (valgtArrangement != null) {
            txtOverskrift.setText("Endre arrangement");
            fyllUtFeltene();
        }
        else {
            txtOverskrift.setText("Nytt arrangement");
        }
    }

    private void fyllUtFeltene() {
        txtNavn.setText(valgtArrangement.getNavn());
        if (erEgendefinertKategori()) {
            txtKategoriAnnet.setText(valgtArrangement.getType());
            cbSportskategori.getSelectionModel().select("Annet");
        }
        else {
            cbSportskategori.getSelectionModel().select(valgtArrangement.getType());
        }
        cbVanskelighetsgrad.getSelectionModel().select(valgtArrangement.getVanskelighetsgrad());
        txtAntallPlasser.setText(Integer.toString(valgtArrangement.getAntallPlasser()));
        txtPris.setText(Long.toString(valgtArrangement.getPris()));
        txtSted.setText(valgtArrangement.getSted());
        txtStarttid.setText(valgtArrangement.getStarttid().toString());
        txtSluttid.setText(valgtArrangement.getSluttid().toString());
        txtBeskrivelse.setText(valgtArrangement.getBeskrivelse());
        adminliste.setAll(valgtArrangement.getAdministratorer());
        lvAdminliste.setItems(adminliste);
    }

    private boolean erEgendefinertKategori() {
        if (valgtArrangement != null) {
            for (String s : kategorier) {
                if (valgtArrangement.getType().equals(s)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void avbryt() {
        if (aktivBruker instanceof SystemAdmin) {
            minApplikasjon.aapneAdminHovedvisning();
        }
        else {
            minApplikasjon.aapneBrukerprofil();
        }
    }

    public boolean lagre() {
        String navn = txtNavn.getText();
        String sportsKategori;
        if (cbSportskategori.getSelectionModel().getSelectedItem().equals("Annet")) {
            sportsKategori = txtKategoriAnnet.getText();
        }
        else {
            sportsKategori = cbSportskategori.getSelectionModel().getSelectedItem();
        }
        String vanskelighetsgrad = cbVanskelighetsgrad.getSelectionModel().getSelectedItem();
        String antallPlasser = String.valueOf(txtAntallPlasser.getText());
        String pris = String.valueOf(txtPris.getText());
        String sted = txtSted.getText();
        String startTid = txtStarttid.getText();
        String sluttTid = txtSluttid.getText();
        String beskrivelse = txtBeskrivelse.getText();

        boolean erTest = false;

        if(ingenTommeFelter(navn,
                sportsKategori,
                vanskelighetsgrad,
                antallPlasser,
                pris,
                sted,
                startTid,
                sluttTid,
                beskrivelse,
                erTest))
        {
            if (valgtArrangement != null) {

                endreArrangement(valgtArrangement, navn, sportsKategori, vanskelighetsgrad,
                        Integer.parseInt(antallPlasser), pris,
                        sted, LocalDateTime.parse(startTid),
                        LocalDateTime.parse(sluttTid), beskrivelse);
                return true;
            }
            else {
                lagArrangement();
                return true;
            }
        }
        else
            {
            return false;
        }
    }

    public boolean ingenTommeFelter(String navnTest, String sportsKategori, String vanskelighetsgradTest,
                                   String antallPlasserTest, String prisTest, String stedTest, String startTidTest,
                                   String sluttTidTest, String beskrivelseTest, boolean testBoolean)
    {
        if(navnTest.equals("") || sportsKategori.equals("") || vanskelighetsgradTest.equals("")
                || antallPlasserTest.equals("") || prisTest.equals("") || stedTest.equals("") || startTidTest.equals("")
                || sluttTidTest.equals("") || beskrivelseTest.equals(""))
        {
            if(!testBoolean) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Feil!");
                alert.setHeaderText("Tomme felter oppdaget!");
                alert.setContentText("Vennligst fyll inn alle felt i tekstfeltene");

                alert.showAndWait();
            }
            return false;
        }
        return true;
    }

    //Stub av å parse et gyldig localdatetime
    public static boolean ldtGyldigFormat(String localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");;

        LocalDateTime formatertLDT = LocalDateTime.parse(localDateTime, formatter);

        System.out.println(formatertLDT);

        return true;
    }

    //Henter informasjon fra JavaFX og sender videre til en allerede testet metode i datahandler
    private void lagArrangement() {
        String navn = txtNavn.getText();
        Bruker arrangor = aktivBruker;
        String kategori;
        if (cbSportskategori.getSelectionModel().getSelectedItem().equals("Annet")) {
            kategori = txtKategoriAnnet.getText();
        }
        else {
            kategori = cbSportskategori.getSelectionModel().getSelectedItem();
        }
        int antallPlasser = Integer.parseInt(txtAntallPlasser.getText());
        String sted = txtSted.getText();
        String vanskelighetsgrad = cbVanskelighetsgrad.getSelectionModel().getSelectedItem();

        DateTimeFormatter formatering = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime starttid = LocalDateTime.parse(txtStarttid.getText(), formatering);
        LocalDateTime sluttid = LocalDateTime.parse(txtSluttid.getText(), formatering);
        long pris = Long.parseLong(txtPris.getText());
        String beskrivelse = txtBeskrivelse.getText();

        Arrangement arrangement = new Arrangement(navn, arrangor, kategori,  vanskelighetsgrad, antallPlasser, pris,
                starttid, sluttid, sted, beskrivelse);

        arrangement.setAdministratorer(new ArrayList<>(adminliste));

        DataHandler.lagreArrangement(arrangement);

        minApplikasjon.setValgtArrangement(arrangement);
        minApplikasjon.aapneArrangementliste();
    }

    static public void endreArrangement(Arrangement arrangement, String navn, String kategori,
                                  String vansklighetsgrad, int plasser, String pris,
                                  String sted, LocalDateTime startTid, LocalDateTime sluttTid,
                                  String beskrivelse) {
        arrangement.setNavn(navn);
        arrangement.setType(kategori);
        arrangement.setVanskelighetsgrad(vansklighetsgrad);
        arrangement.setAntallPlasser(plasser);
        arrangement.setPris(Integer.parseInt(pris));
        arrangement.setSted(sted);
        arrangement.setStarttid(startTid);
        arrangement.setSluttid(sluttTid);
        arrangement.setBeskrivelse(beskrivelse);
        arrangement.setAdministratorer(arrangement.getAdministratorer());

        DataHandler.endreArrangement(arrangement);
    }

    public void leggTilAdmin() {
        String navn = txtAdminBrukernavn.getText();
        Person person = hentPerson(navn);
        if (person != null) {
            adminliste.add(person);
            lvAdminliste.setItems(adminliste);
        }
    }

    private Person hentPerson(String brukernavn) {
        ObservableList<Person> personer = DataHandler.hentListeMedPersoner();
        for (Person p : personer) {
            if (brukernavn.equals(p.getBrukernavn()) && !brukernavn.equals(aktivBruker.getBrukernavn()) && !adminliste.contains(p)) {
                return p;
            }
        }
        return null;
    }

    public void fjernAdmin() {
        Person valgt = lvAdminliste.getSelectionModel().getSelectedItem();
        adminliste.remove(valgt);
        //lvAdminliste.refresh();
    }
}
