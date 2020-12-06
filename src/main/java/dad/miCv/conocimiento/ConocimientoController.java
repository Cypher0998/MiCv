package dad.miCv.conocimiento;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.miCv.conocimiento.model.ConocimientoModel;
import dad.miCv.conocimiento.model.Idioma;
import dad.miCv.conocimiento.model.Nivel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConocimientoController implements Initializable {
	//MODELO
	private ListProperty<ConocimientoModel> conocimientos= new SimpleListProperty<ConocimientoModel>(FXCollections.observableArrayList());
	
	//VISTA
	@FXML
	private HBox view;
	
	@FXML
	private TableView<ConocimientoModel> table;
	
	@FXML
	private TableColumn<ConocimientoModel, String> denominacionCol;
	
	@FXML
	private TableColumn<ConocimientoModel, Nivel> nivelCol;
	
	@FXML
	private Button eliminarConocimientoButton;
	
	private Stage stageConocimientoIdioma;
	
	//View de añadir Conocimiento
	@FXML
	private TextField denominacionConocimientoTF;
	
	@FXML
	private ComboBox<Nivel>nivelConocimientoCombo=new ComboBox<>();
	
	//Vista Añadir Idioma
	 @FXML
	 private TextField denominacionIdiomaTF;

	 @FXML
	 private ComboBox<Nivel> nivelIdiomaCombo=new ComboBox<>();

	 @FXML
	 private TextField certificacionTF;
	 
	 
	
	
	public ConocimientoController() throws IOException {
		FXMLLoader carga= new FXMLLoader(getClass().getResource("/ConocimientosView.fxml"));
		carga.setController(this);
		carga.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		table.itemsProperty().bind(conocimientos);

		denominacionCol.setCellValueFactory(v->v.getValue().denominacionProperty());
		nivelCol.setCellValueFactory(v->v.getValue().nivelProperty());
		
		denominacionCol.setCellFactory(TextFieldTableCell.forTableColumn());
		nivelCol.setCellFactory(ComboBoxTableCell.forTableColumn(Nivel.values()));
		
    	nivelConocimientoCombo.getItems().addAll(Nivel.values());
    	nivelIdiomaCombo.getItems().addAll(Nivel.values());

    	eliminarConocimientoButton.setDisable(table.getItems().isEmpty());
    	
	}
	
	public HBox getView() {
		return view;
	}
	
    @FXML
    void onAñadirConocimiento(ActionEvent event) {

    	try {
    		FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/addConocimientoView.fxml"));
    		loader.setController(this);
    		Parent root1=(Parent)loader.load();
    		stageConocimientoIdioma=new Stage();
    		stageConocimientoIdioma.setTitle("Añadir Conocimiento");
    		stageConocimientoIdioma.initModality(Modality.WINDOW_MODAL);
    		stageConocimientoIdioma.initOwner(view.getScene().getWindow());
    		stageConocimientoIdioma.setScene(new Scene(root1));
    		stageConocimientoIdioma.setResizable(false);
    		stageConocimientoIdioma.show();
    		
    	}catch (Exception e) {e.printStackTrace();}
    }
    @FXML
    void onAñadirIdioma(ActionEvent event) {
    	try {
    		FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/addIdiomaView.fxml"));
    		loader.setController(this);
    		Parent root1=(Parent)loader.load();
    		stageConocimientoIdioma=new Stage();
    		stageConocimientoIdioma.setTitle("Añadir idioma");
    		stageConocimientoIdioma.initModality(Modality.WINDOW_MODAL);
    		stageConocimientoIdioma.initOwner(view.getScene().getWindow());
    		stageConocimientoIdioma.setScene(new Scene(root1));
    		stageConocimientoIdioma.setResizable(false);
    		stageConocimientoIdioma.show();
    		
    	}catch (Exception e) {e.printStackTrace();}
    }
    
    @FXML
    void onEliminar(ActionEvent event) {
    	if(!table.getSelectionModel().isEmpty())
    		alertEliminar();
    	eliminarConocimientoButton.setDisable(table.getItems().isEmpty());

    }
    private void alertEliminar() {
    	Stage stage=(Stage)view.getScene().getWindow();
    	
    	Alert alert=new Alert(AlertType.CONFIRMATION);
    	alert.initOwner(stage);
    	alert.setTitle("Eliminar conocimiento");
    	alert.setHeaderText("¿Va a borrar el conocimiento?");
    	alert.setContentText("¿Está seguro de que quiere borrarlo?");  
    	
    	Optional<ButtonType>resultado=alert.showAndWait();
    	if(resultado.get()==ButtonType.OK)
    		table.getItems().removeAll(table.getSelectionModel().getSelectedItems());
    }
    
    @FXML
    private void onCrearButtonConocimiento(ActionEvent event) {

    	ConocimientoModel conocimiento=new ConocimientoModel();
    	conocimiento.setDenominacion(denominacionConocimientoTF.textProperty().get());
    	conocimiento.setNivel(nivelConocimientoCombo.getValue());
    	
    	if(!conocimiento.getDenominacion().isEmpty()) {
	    	conocimientos.add(conocimiento);
	    	eliminarConocimientoButton.setDisable(table.getItems().isEmpty());
	    	stageConocimientoIdioma.close();	
    	}else 
    		alertConocimiento();
    }
    
    private void alertConocimiento() {
    	Stage stage=(Stage)view.getScene().getWindow();
    	
    	Alert alert=new Alert(AlertType.INFORMATION);
    	alert.initModality(Modality.APPLICATION_MODAL);
    	alert.initOwner(stage);
    	alert.setTitle("El formulario no esta completo");
    	alert.setHeaderText("Error al intentar introducir un nuevo conocimiento");
    	alert.setContentText("Debe rellenar al menos el campo de \"Denominacion\"");
    	alert.showAndWait();
    }
    
    @FXML
    private void onCancelarButtonConocimiento(ActionEvent event) {
    	stageConocimientoIdioma.close();
    }

    @FXML
    private void onXButtonConocimiento(ActionEvent event) {
    	
    	nivelConocimientoCombo.valueProperty().set(null);
    	
    }
    
    @FXML
    private void onCrearButtonIdioma(ActionEvent event) {

    	ConocimientoModel conocimiento=new ConocimientoModel();
    	conocimiento.setDenominacion(denominacionIdiomaTF.textProperty().get());
    	conocimiento.setNivel(nivelIdiomaCombo.getValue());
    	Idioma idioma = new Idioma();
    	idioma.setIdioma(certificacionTF.textProperty().get());
    	conocimiento.setIdioma(idioma);
    	
    	if((!conocimiento.getDenominacion().isEmpty()) && (!conocimiento.getIdioma().getIdioma().isEmpty())) {
    		conocimientos.add(conocimiento);
    		stageConocimientoIdioma.close();
    		eliminarConocimientoButton.setDisable(table.getItems().isEmpty());
    	}
    	else
    		alertIdioma();
    }
    
    private void alertIdioma() {
    	Stage stage=(Stage)view.getScene().getWindow();

    	Alert alert=new Alert(AlertType.INFORMATION);	
    	alert.initModality(Modality.APPLICATION_MODAL);
    	alert.initOwner(stage);
    	alert.setTitle("El formulario no esta completo");
    	alert.setHeaderText("Error al intentar introducir un nuevo idioma");
    	alert.setContentText("Debe rellenar al menos los campos campos de \"Denominación\" y \"Certificación\"");
    	alert.showAndWait();
    }
    
    @FXML
    private void onCancelarButtonIdioma(ActionEvent event) {
    	stageConocimientoIdioma.close();
    }
    @FXML
    private void onXIdiomaButtonAction(ActionEvent event) {
    	nivelIdiomaCombo.valueProperty().set(null);
    }
    
	public final ListProperty<ConocimientoModel> conocimientosProperty() {
		return this.conocimientos;
	}
	


	public final ObservableList<ConocimientoModel> getConocimientos() {
		return this.conocimientosProperty().get();
	}
	


	public final void setConocimientos(final ObservableList<ConocimientoModel> conocimientos) {
		this.conocimientosProperty().set(conocimientos);
	}


	public TableView<ConocimientoModel> getTable() {
		return table;
	}

	public Button getEliminarConocimientoButton() {
		return eliminarConocimientoButton;
	}
}
