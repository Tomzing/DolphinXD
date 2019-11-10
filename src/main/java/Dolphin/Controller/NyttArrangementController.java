package Dolphin.Controller;

import Dolphin.DataHandler.DataHandler;
import Dolphin.Main;
import Dolphin.Model.Arrangement;
import Dolphin.Model.Bruker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NyttArrangementController {

    private Main minApplikasjon = Main.getInstance();
    private ObservableList<Bruker> adminliste = FXCollections.observableArrayList();
    private Bruker aktivBruker;

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

        cbSportskategori.setItems(FXCollections.observableArrayList("Fotballkamp", "Sykkelritt", "løp", "Annet"));
        cbVanskelighetsgrad.setItems(FXCollections.observableArrayList("Lett", "Middels", "Vanskelig"));
    }

    public void avbryt() {
        minApplikasjon.aapneNyttVindu("arrangementliste");
    }

    public void lagArrangement() {
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

    public void leggTilAdmin() {
        String navn = txtAdminBrukernavn.getText();
        ObservableList<Bruker> brukere = DataHandler.hentListeMedBrukere();

        for (Bruker b : brukere) {
            if (navn.equals(b.getBrukernavn()) && !navn.equals(aktivBruker.getBrukernavn()) && !adminliste.contains(b)) {
                adminliste.add(b);
                break;
            }
        }
        lvAdminliste.setItems(adminliste);
    }
}
