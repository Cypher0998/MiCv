package dad.miCv.experiencia;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.miCv.experiencia.model.Experiencia;
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

public class ExperienciaController implements Initializable {

	private ListProperty<Experiencia>experiencias=new SimpleListProperty<Experiencia>(FXCollections.observableArrayList());
	//view
	@FXML
    private HBox view;

    @FXML
    private TableView<Experiencia> table;

    @FXML
    private TableColumn<Experiencia, LocalDate> desdeCol;

    @FXML
    private TableColumn<Experiencia, LocalDate> hastaCol;

    @FXML
    private TableColumn<Experiencia, String> denominacionCol;

    @FXML
    private TableColumn<Experiencia, String> empleadorCol;
    
    @FXML
    private Button eliminarButton;
    
    //View de añadir nueva Experiencia
    
    private Stage stageExperiencia;

    @FXML
    private TextField denominacionTF,empleadorTF;

    @FXML
    private DatePicker desdeDP,hastaDP;


	public ExperienciaController() throws IOException {
		FXMLLoader loader=new FXMLLoader(getClass().getResource("/ExperienciaView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		table.itemsProperty().bind(experiencias);
		
		desdeCol.setCellValueFactory(v->v.getValue().desdeProperty());
		hastaCol.setCellValueFactory(v->v.getValue().hastaProperty());
		denominacionCol.setCellValueFactory(v->v.getValue().denominacionProperty());
		empleadorCol.setCellValueFactory(v->v.getValue().empleadorProperty());
		
		desdeCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		hastaCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		denominacionCol.setCellFactory(TextFieldTableCell.forTableColumn());
		empleadorCol.setCellFactory(TextFieldTableCell.forTableColumn());
		
		eliminarButton.setDisable(table.getItems().isEmpty());

	}
    public HBox getView() {
    	return view;
    }
	
	@FXML
    private void onAñadir(ActionEvent event) {
    	try {
    		FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/addExperienciaView.fxml"));
    		loader.setController(this);
        	
    		Parent root1=(Parent)loader.load();
        	stageExperiencia=new Stage();
    		stageExperiencia.setTitle("Añadir Experiencia");
    		//Las dos siguientes líneas evitan que se pueda clickar en la ventana padre mientras esta está abierta
    		stageExperiencia.initModality(Modality.WINDOW_MODAL);
    		stageExperiencia.initOwner(view.getScene().getWindow());
    		stageExperiencia.setScene(new Scene(root1));
    		stageExperiencia.setResizable(false);
    		stageExperiencia.show();
    		
		} catch (Exception e) { System.out.println("No se puede abrir la ventana de añadir experiencia"+e); }

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
    	alert.setTitle("Eliminar experiencia");
    	alert.setHeaderText("¿Va a borrar la experiencia?");
    	alert.setContentText("¿Está seguro de que quiere borrarla?");
    	
    	Optional<ButtonType>resultado=alert.showAndWait();
    	if(resultado.get()==ButtonType.OK)
    		table.getItems().removeAll(table.getSelectionModel().getSelectedItems());
    }
    
    //View de añadir experiencia
    @FXML
    private void onCancelarButton(ActionEvent event) {
    	stageExperiencia.close();
    }

    @FXML
    private void onCrearButton(ActionEvent event) {
    	
    	Experiencia experiencia=new Experiencia();
    	
    	experiencia.setDenominacion(denominacionTF.textProperty().get());
    	experiencia.setEmpleador(empleadorTF.textProperty().get());
    	experiencia.setDesde(desdeDP.getValue());
    	experiencia.setHasta(hastaDP.getValue());
    	
    	if((!experiencia.getDenominacion().isEmpty()) && (!experiencia.getEmpleador().isEmpty())
    		&& (experiencia.getDesde()!=null) && (experiencia.getHasta()!=null)) {

    		experiencias.add(experiencia);
    		stageExperiencia.close();
    		eliminarButton.setDisable(table.getItems().isEmpty());
    	}
    	else
    		alertExperiencia();
    }
    
    private void alertExperiencia() {
    	Stage stage=(Stage)view.getScene().getWindow();

    	Alert alert=new Alert(AlertType.INFORMATION);	
    	alert.initModality(Modality.APPLICATION_MODAL);
    	alert.initOwner(stage);
    	alert.setTitle("El formulario no esta completo");
    	alert.setHeaderText("Error al intentar introducir una nueva experiencia");
    	alert.setContentText("Debe rellenar todos los campos");
    	alert.showAndWait();
    }

	public final ListProperty<Experiencia> experienciasProperty() {
		return this.experiencias;
	}
	

	public final ObservableList<Experiencia> getExperiencias() {
		return this.experienciasProperty().get();
	}
	

	public final void setExperiencias(final ObservableList<Experiencia> experiencias) {
		this.experienciasProperty().set(experiencias);
	}

	public TableView<Experiencia> getTable() {
		return table;
	}

	public Button getEliminarButton() {
		return eliminarButton;
	}

}
