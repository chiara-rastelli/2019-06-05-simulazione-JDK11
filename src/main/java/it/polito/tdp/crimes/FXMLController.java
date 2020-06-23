/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Adiacenza;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="boxGiorno"
    private ComboBox<Integer> boxGiorno; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaReteCittadina"
    private Button btnCreaReteCittadina; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaReteCittadina(ActionEvent event) {
    	this.txtResult.clear();
    	Integer anno = this.boxAnno.getValue();
    	if (anno == null) {
    		this.txtResult.setText("Devi scegliere un anno per creare la rete cittadina!\n");
    		return;
    	}
    	model.creaGrafo(anno);
    	for (int i : model.getDistrettiGrafo()) {
			this.txtResult.appendText("Distretto "+i+"\n");
			for (Adiacenza a : model.getAdicenze(i))
				this.txtResult.appendText(a.toString());
		}
    	this.boxGiorno.getItems().addAll(model.getAllDays());
    	this.boxMese.getItems().addAll(model.getAllMonths());
    	this.btnSimula.setDisable(false);
    }

    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	if (this.boxGiorno.getValue() == null || this.boxMese.getValue() == null) {
    		this.txtResult.setText("Per poter effettuare una simulazione devi prima scegliere mese e giorno!\n");
    		return;
    	}
    	try {
    		LocalDate data = LocalDate.of(this.boxAnno.getValue(), this.boxMese.getValue(), this.boxGiorno.getValue());
    	}catch(DateTimeException e) {
    		this.txtResult.appendText("Devi selezionare un giorno che sia valido per il mese e l'anno corrispondenti!\n");
    		return;
    	}
    	
    	int N = 0;
    	try {
    		N = Integer.parseInt(this.txtN.getText());
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Devi inserire un valore N per la simulazione che sia un intero!\n");
    		return;
    	}
    	
    	if (N <= 0 || N > 10) {
    		this.txtResult.setText("Il valore di N per la simulazione deve essere compreso tra 1 e 10!\n");
    		return;
    	}
    	
    	model.simula(this.boxAnno.getValue(), this.boxMese.getValue(), this.boxGiorno.getValue(), N);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaReteCittadina != null : "fx:id=\"btnCreaReteCittadina\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxAnno.getItems().addAll(this.model.getAllYears());
    	this.btnSimula.setDisable(true);
    }
}
