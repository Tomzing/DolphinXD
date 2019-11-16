package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Main;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Bruker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NyttArrangementController {

    private Main minApplikasjon = Main.getInstance();
    private ObservableList<Bruker> adminliste = FXCollections.observableArrayList();
    private Bruker aktivBruker;
    private Arrangement valgtArrangement;

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
    private ListView<Bruker> lvAdminliste;

    public void initialize() {
        aktivBruker =  minApplikasjon.getAktivBruker();
        valgtArrangement = minApplikasjon.getValgtArrangement();

        cbSportskategori.setItems(FXCollections.observableArrayList("Fotballkamp", "Sykkelritt", "løp", "Annet"));
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
        cbSportskategori.getSelectionModel().select(valgtArrangement.getType());
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

    public void avbryt() {
        minApplikasjon.aapneNyttVindu("arrangementliste");
    }

    public void lagre() {
        if (valgtArrangement != null) {
            endreArrangement();
        }
        else {
            lagArrangement();
        }
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
        minApplikasjon.aapneNyttVindu("arrangementliste");
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

        minApplikasjon.aapneNyttVindu("arrangementliste");
    }

    public void leggTilAdmin() {
        String navn = txtAdminBrukernavn.getText();
        Bruker bruker = hentBruker(navn);
        if (bruker != null) {
            adminliste.add(bruker);
            lvAdminliste.setItems(adminliste);
        }
    }

    private Bruker hentBruker(String brukernavn) {
        ObservableList<Bruker> brukere = DataHandler.hentListeMedBrukere();
        for (Bruker b : brukere) {
            if (brukernavn.equals(b.getBrukernavn()) && !brukernavn.equals(aktivBruker.getBrukernavn()) && !adminliste.contains(b)) {
                return b;
            }
        }
        return null;
    }

    public void fjernAdmin() {
        Bruker valgt = lvAdminliste.getSelectionModel().getSelectedItem();
        adminliste.remove(valgt);
        //lvAdminliste.refresh();
    }
}
