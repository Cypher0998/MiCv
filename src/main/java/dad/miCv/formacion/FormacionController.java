package dad.miCv.formacion;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.miCv.formacion.model.Formacion;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

public class FormacionController implements Initializable {

	private ListProperty<Formacion>formaciones=new SimpleListProperty<Formacion>(FXCollections.observableArrayList());
	
	@FXML
    private HBox view;

    @FXML
    private TableView<Formacion> table;
    
    @FXML
    private TableColumn<Formacion, LocalDate> desdeCol;

    @FXML
    private TableColumn<Formacion, LocalDate> hastaCol;

    @FXML
    private TableColumn<Formacion, String> denominacionCol;

    @FXML
    private TableColumn<Formacion, String> organizadorCol;
    
    @FXML
    private Button eliminarButton;

    //View de añadir nueva formación
	
    private Stage stageFormacion;

    @FXML
    private TextField denominacionTF,organizadorTF;

    @FXML
    private DatePicker desdeDP,hastaDP;
	
	public FormacionController() throws IOException {
		FXMLLoader loader=new FXMLLoader(getClass().getResource("/FormacionView.fxml"));
		loader.setController(this);
		loader.load();	
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		table.itemsProperty().bind(formaciones);
		
		desdeCol.setCellValueFactory(v->v.getValue().desdeProperty());
		hastaCol.setCellValueFactory(v->v.getValue().hastaProperty());
		denominacionCol.setCellValueFactory(v->v.getValue().denominacionProperty());
		organizadorCol.setCellValueFactory(v->v.getValue().organizadorProperty());
		
		desdeCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		hastaCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		denominacionCol.setCellFactory(TextFieldTableCell.forTableColumn());
		organizadorCol.setCellFactory(TextFieldTableCell.forTableColumn());	
		
		eliminarButton.setDisable(table.getItems().isEmpty());	
	}
	public HBox getView() {
		return view;
	}

    @FXML
    private void onAñadir(ActionEvent event) {
    	try {
    		
    		FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/addFormacionView.fxml"));
    		loader.setController(this);
        	
    		Parent root1=(Parent)loader.load();
        	stageFormacion=new Stage();
    		stageFormacion.setTitle("Añadir Formación");
    		//Las dos siguientes líneas evitan que se pueda clickar en la ventana padre mientras esta está abierta
    		stageFormacion.initModality(Modality.WINDOW_MODAL);
    		stageFormacion.initOwner(view.getScene().getWindow());
    		stageFormacion.setScene(new Scene(root1));
    		stageFormacion.setResizable(false);
    		stageFormacion.show();
    		
		} catch (Exception e) { System.out.println("No se puede abrir la ventana de añadir formación"+e); }
    }

    @FXML
    private void onEliminar(ActionEvent event) {
    	if(!table.getSelectionModel().isEmpty())
    		alertEliminar();
    	eliminarButton.setDisable(table.getItems().isEmpty());
    }
      
    private void alertEliminar() {
    	Stage stage=(Stage)view.getScene().getWindow();
    	
    	Alert alert=new Alert(AlertType.CONFIRMATION);
    	alert.initModality(Modality.APPLICATION_MODAL);
    	alert.initOwner(stage);
    	alert.setTitle("Eliminar formación");
    	alert.setHeaderText("Va a borrar la formación");
    	alert.setContentText("¿Está seguro de que quiere borrarla?");
    	
    	Optional<ButtonType>resultado=alert.showAndWait();
    	if(resultado.get()==ButtonType.OK)
    		table.getItems().removeAll(table.getSelectionModel().getSelectedItem());	
    }
    
    
    //View de añadir formación
    
    @FXML
    private void onCancelarButton(ActionEvent event) {
    	stageFormacion.close();
    }

    @FXML
    private void onCrearButton(ActionEvent event) {
    	
    	Formacion formacion=new Formacion();
    	
    	formacion.setDenominacion(denominacionTF.textProperty().get());
    	formacion.setOrganizador(organizadorTF.textProperty().get());
    	formacion.setDesde(desdeDP.getValue());
    	formacion.setHasta(hastaDP.getValue());
    	
    	if((!formacion.getDenominacion().isEmpty()) && (!formacion.getOrganizador().isEmpty())
    		&& (formacion.getDesde()!=null) && (formacion.getHasta()!=null)) {

    		formaciones.add(formacion);
        	stageFormacion.close();
        	eliminarButton.setDisable(table.getItems().isEmpty());
    	}
    	else
    		alertFormacion();
    }
    
    private void alertFormacion() {
    	Stage stage=(Stage)view.getScene().getWindow();

    	Alert alert=new Alert(AlertType.INFORMATION);	
    	alert.initModality(Modality.APPLICATION_MODAL);
    	alert.initOwner(stage);
    	alert.setTitle("El formulario no esta completo");
    	alert.setHeaderText("Error al intentar introducir una nueva formación");
    	alert.setContentText("Debe rellenar todos los campos");
    	alert.showAndWait();
    }

	public final ListProperty<Formacion> formacionesProperty() {
		return this.formaciones;
	}
	

	public final ObservableList<Formacion> getFormaciones() {
		return this.formacionesProperty().get();
	}
	

	public final void setFormaciones(final ObservableList<Formacion> formaciones) {
		this.formacionesProperty().set(formaciones);
	}

	public Button getEliminarButton() {
		return eliminarButton;
	}

	public TableView<Formacion> getTable() {
		return table;
	}

	
}