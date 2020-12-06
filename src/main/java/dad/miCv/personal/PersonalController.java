package dad.miCv.personal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.miCv.personal.model.Nacionalidad;
import dad.miCv.personal.model.Personal;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PersonalController implements Initializable {

	//model
	
	private ObjectProperty<Personal> personal=new SimpleObjectProperty<>();
	
	//view
	@FXML
	private GridPane view;
	
	@FXML
	private TextField identificacionTF,nombreTF,apellidosTF,codigoPostalTF,localidadTF;
	
	@FXML
	private DatePicker fechaNacimientoDP;
	
	@FXML
	private TextArea direccionTA;
	
	@FXML
	private ComboBox<String> paisCB;
	
	private ListProperty<String>csvPaisesList=new SimpleListProperty<String>(FXCollections.observableArrayList());
	
	private BufferedReader csvReader; 
	@FXML
	private ListView<Nacionalidad> nacionalidadesLV;
	
	private ListProperty<Nacionalidad>csvNacionalidadesList=new SimpleListProperty<Nacionalidad>(FXCollections.observableArrayList());
	private ObjectProperty<Nacionalidad> nacionalidadseleccionada=new SimpleObjectProperty<>();

	@FXML
	private Button nuevaNacionalidadB,borrarNacionalidadB;
	
	public PersonalController() throws IOException{
		FXMLLoader loader=new FXMLLoader(getClass().getResource("/PersonalView.fxml"));
		loader.setController(this);
		loader.load();		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		nacionalidadseleccionada.bind(nacionalidadesLV.getSelectionModel().selectedItemProperty());
		
		cargaPaisesCsv();
		cargaNacionalidadesCsv();
		personal.addListener((o,ov,nv)->onPersonalChanged(o,ov,nv));	
    	borrarNacionalidadB.setDisable(nacionalidadesLV.getItems().isEmpty());
	}
	
	private void cargaPaisesCsv() {
		 
		try {
			
			String line;
			csvReader=new BufferedReader(new FileReader("src/main/resources/csv/paises.csv",Charset.forName("UTF-8")));
			
			while((line=csvReader.readLine())!=null)
				csvPaisesList.add(line);
			
			csvReader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		paisCB.setItems(csvPaisesList);
	}
	
	private void cargaNacionalidadesCsv() {
		try {
			String line;
			csvReader=new BufferedReader(new FileReader("src/main/resources/csv/nacionalidades.csv",Charset.forName("UTF-8")));
			
			while((line=csvReader.readLine())!=null) {
				Nacionalidad nacionalidad=new Nacionalidad(line);
				csvNacionalidadesList.add(nacionalidad);
			}
			csvReader.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    private void onPersonalChanged(ObservableValue<? extends Personal> o, Personal ov, Personal nv) {
		
    	if(ov!=null) {
    		identificacionTF.textProperty().unbindBidirectional(ov.identificacionProperty());
    		nombreTF.textProperty().unbindBidirectional(ov.nombreProperty());
    		apellidosTF.textProperty().unbindBidirectional(ov.apellidosProperty());
    		codigoPostalTF.textProperty().unbindBidirectional(ov.codigoPostalProperty());
    		localidadTF.textProperty().unbindBidirectional(ov.localidadProperty());
    		fechaNacimientoDP.valueProperty().unbindBidirectional(ov.fechaNacimientoProperty());
    		direccionTA.textProperty().unbindBidirectional(ov.direccionProperty());
    		paisCB.valueProperty().unbindBidirectional(ov.paisProperty());
    		nacionalidadesLV.itemsProperty().unbindBidirectional(ov.nacionalidadesProperty());
    	}	
    	if(nv!=null) {
    		identificacionTF.textProperty().bindBidirectional(nv.identificacionProperty());
    		nombreTF.textProperty().bindBidirectional(nv.nombreProperty());
    		apellidosTF.textProperty().bindBidirectional(nv.apellidosProperty());
    		codigoPostalTF.textProperty().bindBidirectional(nv.codigoPostalProperty());
    		localidadTF.textProperty().bindBidirectional(nv.localidadProperty());
    		fechaNacimientoDP.valueProperty().bindBidirectional(nv.fechaNacimientoProperty());
    		direccionTA.textProperty().bindBidirectional(nv.direccionProperty());
    		paisCB.valueProperty().bindBidirectional(nv.paisProperty());
    		nacionalidadesLV.itemsProperty().bindBidirectional(nv.nacionalidadesProperty());
    	}   	
    	
    	borrarNacionalidadB.setDisable(nacionalidadesLV.getItems().isEmpty());
	}

    @FXML
    private void onNuevaNacionalidadBAction(ActionEvent event) {

    	ChoiceDialog<Nacionalidad> dialog=new ChoiceDialog<>(csvNacionalidadesList.get(0),csvNacionalidadesList);
    	Stage stage=(Stage)dialog.getDialogPane().getScene().getWindow();
    	stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
    	dialog.setTitle("Nueva nacionalidad");
    	dialog.setHeaderText("Añadir nacionalidad");
    	dialog.setContentText("Seleccione una nacionalidad");

    	Optional<Nacionalidad> nacionalidadElegida=dialog.showAndWait();
    	
    	if(nacionalidadElegida.isPresent()) {
    		personal.get().nacionalidadesProperty().add(nacionalidadElegida.get());
        	borrarNacionalidadB.setDisable(nacionalidadesLV.getItems().isEmpty());
    	}
    }
    
	@FXML
    private void onBorrarNacionalidadBAction(ActionEvent event) {
    	
		if(!nacionalidadesLV.getSelectionModel().isEmpty())
			alertBorrarNacionalidad();
		
		borrarNacionalidadB.setDisable(nacionalidadesLV.getItems().isEmpty());
    }
	private void alertBorrarNacionalidad() {
		
		Stage stage=(Stage)view.getScene().getWindow();
    	
    	Alert alert=new Alert(AlertType.CONFIRMATION);
    	alert.initModality(Modality.APPLICATION_MODAL);
    	alert.initOwner(stage);
    	alert.setTitle("Eliminar nacionalidad");
    	alert.setHeaderText("Va a borrar la nacionalidad");
    	alert.setContentText("¿Está seguro de que quiere eliminarla?");
    	
    	Optional<ButtonType>resultado=alert.showAndWait();
    	if(resultado.get()==ButtonType.OK)
    		personal.get().nacionalidadesProperty().remove(nacionalidadseleccionada.get());
	}
	
    
    public GridPane getView() {
    	return this.view;
    }   
   
	public final ObjectProperty<Personal> personalProperty() {
		return this.personal;
	}
	
	public final Personal getPersonal() {
		return this.personalProperty().get();
	}
	
	public final void setPersonal(final Personal personal) {
		this.personalProperty().set(personal);
	}
	
    
    

}
