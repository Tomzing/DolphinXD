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
        String navnTest = txtNavn.getText();
        String sportsKategoriTest = String.valueOf(cbSportskategori.getSelectionModel().getSelectedItem());
        String vanskelighetsgradTest = cbVanskelighetsgrad.getSelectionModel().getSelectedItem();
        String antallPlasserTest = String.valueOf(txtAntallPlasser.getText());
        String prisTest = String.valueOf(txtPris.getText());
        String stedTest = txtSted.getText();
        String startTidTest = txtStarttid.getText();
        String sluttTidTest = txtSluttid.getText();
        String beskrivelseTest = txtBeskrivelse.getText();

        boolean erTest = false;

        if(ingenTommeFelter(navnTest,
                sportsKategoriTest,
                vanskelighetsgradTest,
                antallPlasserTest,
                prisTest,
                stedTest,
                startTidTest,
                sluttTidTest,
                beskrivelseTest,
                erTest))
        {
            if (valgtArrangement != null) {

                endreArrangement();
                return true;
            }
            else {
                lagArrangement();
                return true;
            }
        }
        else {
            return false;
        }
    }

    public boolean ingenTommeFelter(String navnTest, String sportsKategori, String vanskelighetsgradTest,
                                   String antallPlasserTest, String prisTest, String stedTest, String startTidTest,
                                   String sluttTidTest, String beskrivelseTest, boolean erTest)
    {
        if(navnTest.equals("") || sportsKategori.equals("") || vanskelighetsgradTest.equals("")
                || antallPlasserTest.equals("") || prisTest.equals("") || stedTest.equals("") || startTidTest.equals("")
                || sluttTidTest.equals("") || beskrivelseTest.equals(""))
        {
            if(!erTest) {
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

    private void endreArrangement() {
        valgtArrangement.setNavn(txtNavn.getText());
        valgtArrangement.setType(cbSportskategori.getSelectionModel().getSelectedItem());
        valgtArrangement.setVanskelighetsgrad(cbVanskelighetsgrad.getSelectionModel().getSelectedItem());
        valgtArrangement.setAntallPlasser(Integer.parseInt(txtAntallPlasser.getText()));
        valgtArrangement.setPris(Integer.parseInt(txtPris.getText()));
        valgtArrangement.setSted(txtSted.getText());
        valgtArrangement.setStarttid(LocalDateTime.parse(txtStarttid.getText()));
        valgtArrangement.setSluttid(LocalDateTime.parse(txtSluttid.getText()));
        valgtArrangement.setBeskrivelse(txtBeskrivelse.getText());
        valgtArrangement.setAdministratorer(new ArrayList<>(adminliste));

        DataHandler.endreArrangement(valgtArrangement);

        if (aktivBruker instanceof SystemAdmin) {
            minApplikasjon.aapneAdminHovedvisning();
        }
        else {
            minApplikasjon.aapneArrangementliste();
        }
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
