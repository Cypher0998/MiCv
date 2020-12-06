package dad.miCv.contacto;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.miCv.contacto.model.Contacto;
import dad.miCv.contacto.model.Email;
import dad.miCv.contacto.model.Telefono;
import dad.miCv.contacto.model.TipoTelefono;
import dad.miCv.contacto.model.Web;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ContactoController implements Initializable {

	private ObjectProperty<Contacto>contacto=new SimpleObjectProperty<>();
	
	@FXML
    private SplitPane view;

    @FXML
    private TableView<Telefono> telefonosTable;

    @FXML
    private TableColumn<Telefono, String> numeroColumn;

    @FXML
    private TableColumn<Telefono, TipoTelefono> tipoColumn;

    @FXML
    private TableView<Email> correoTable;

    @FXML
    private TableColumn<Email, String> emailColumn;
    
    @FXML
    private TableView<Web> webTable;

    @FXML
    private TableColumn<Web, String> urlColumn;
    
    @FXML
    private Button eliminarTelefonoB;
    
    @FXML
    private Button eliminarCorreoB;
    
    @FXML
    private Button eliminarWebB;
    
	private TextInputDialog dialog;
    
	public ContactoController() throws IOException {
		FXMLLoader loader=new FXMLLoader(getClass().getResource("/ContactoView.fxml"));
		loader.setController(this);
		loader.load();	
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		numeroColumn.setCellValueFactory(v->v.getValue().numeroProperty());
		tipoColumn.setCellValueFactory(v->v.getValue().tipoTelefonoProperty());
		emailColumn.setCellValueFactory(v->v.getValue().direccionProperty());
		urlColumn.setCellValueFactory(v->v.getValue().urlProperty());
		
		numeroColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		tipoColumn.setCellFactory(ComboBoxTableCell.forTableColumn(TipoTelefono.values()));
		emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		urlColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		eliminarTelefonoB.setDisable(telefonosTable.getItems().isEmpty());
		eliminarCorreoB.setDisable(correoTable.getItems().isEmpty());
		eliminarWebB.setDisable(webTable.getItems().isEmpty());
		
		contacto.addListener((o,ov,nv)->onContactoChanged(o,ov,nv));
	}
	private void onContactoChanged(ObservableValue<? extends Contacto> o, Contacto ov, Contacto nv) {

		if(ov!=null) {
			telefonosTable.itemsProperty().unbindBidirectional(ov.telefonosProperty());
			correoTable.itemsProperty().unbindBidirectional(ov.emailsProperty());
			webTable.itemsProperty().unbindBidirectional(ov.websProperty());
		}
		if(nv!=null) {
			telefonosTable.itemsProperty().bindBidirectional(nv.telefonosProperty());
			correoTable.itemsProperty().bindBidirectional(nv.emailsProperty());
			webTable.itemsProperty().bindBidirectional(nv.websProperty());
		}
	}

	public SplitPane getView() {
		return view;
	}
	
    @FXML
    private void onAñadirTelefono(ActionEvent event) throws IOException {
    	TelefonoDialogController telefonoDialog=new TelefonoDialogController();
    	Stage stage=(Stage)telefonoDialog.getDialogPane().getScene().getWindow();
    	stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
    	
    	Optional<Telefono> resultado=telefonoDialog.showAndWait();
    	if(resultado.isPresent())
    		contacto.get().getTelefonos().add(resultado.get());
		eliminarTelefonoB.setDisable(telefonosTable.getItems().isEmpty());
    }

	@FXML
	private void onAñadirCorreo(ActionEvent event) {
		
		dialog=new TextInputDialog();
		
    	Stage stage=(Stage)dialog.getDialogPane().getScene().getWindow();
    	stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
		dialog.setTitle("Nuevo e-mail");
		dialog.setHeaderText("Crear una nueva dirección de correo.");
		dialog.setContentText("E-mail: ");
		dialog.setResultConverter(b->onAñadirCorreoAction(b));
		
		Optional<String>resultado=dialog.showAndWait();
		
		if(resultado.isPresent()) {
			Email email=new Email();
			email.setDireccion(resultado.get());
			contacto.get().getEmails().add(email);
		}
    	eliminarCorreoB.setDisable(correoTable.getItems().isEmpty());
    }

    private String onAñadirCorreoAction(ButtonType b) {
    	String numero;
    	if(b.getButtonData()==ButtonData.OK_DONE)
    		if(dialog.getEditor().getText().isEmpty()) {
    			alertAñadirCorreo();
    		}	
    		else {
    			numero=dialog.getEditor().getText();	
    			return numero;
    		}
		return null;
	}
    private void alertAñadirCorreo() {
    	Stage stage=(Stage)view.getScene().getWindow();

    	Alert alert=new Alert(AlertType.INFORMATION);	
    	alert.initModality(Modality.APPLICATION_MODAL);
    	alert.initOwner(stage);
    	alert.setTitle("El formulario no esta completo");
    	alert.setHeaderText("Error al intentar introducir un nuevo correo");
    	alert.setContentText("Debe rellenar todos los campos");
    	alert.showAndWait();
    }

	@FXML
    private void onAñadirWeb(ActionEvent event) {

    	dialog=new TextInputDialog("http://");
    	Stage stage=(Stage)dialog.getDialogPane().getScene().getWindow();
    	stage.getIcons().add(new Image(this.getClass().getResource("/images/cv64x64.png").toString()));
		dialog.setTitle("Nueva web");
		dialog.setHeaderText("Crear una nueva dirección web.");
		dialog.setContentText("URL: ");
		dialog.setResultConverter(b->onAñadirWebAction(b));
		
		Optional<String>resultado=dialog.showAndWait();
		if(resultado.isPresent()) {
			Web web=new Web();
			web.setUrl(resultado.get());
			contacto.get().getWebs().add(web);
		}
    	
    	eliminarWebB.setDisable(webTable.getItems().isEmpty());
    }
    
    private String onAñadirWebAction(ButtonType b) {
    	String web;
    	if(b.getButtonData()==ButtonData.OK_DONE)
    		if((dialog.getEditor().getText().isEmpty()) || (dialog.getEditor().getText().equals(new String("http://")))) {
    			alertAñadirWeb();
    		}	
    		else {
    			web=dialog.getEditor().getText();	
    			return web;
    		}
		return null;
	}
    
    private void alertAñadirWeb() {
    	Stage stage=(Stage)view.getScene().getWindow();

    	Alert alert=new Alert(AlertType.INFORMATION);	
    	alert.initModality(Modality.APPLICATION_MODAL);
    	alert.initOwner(stage);
    	alert.setTitle("El formulario no esta completo");
    	alert.setHeaderText("Error al intentar introducir una nueva web");
    	alert.setContentText("Debe rellenar todos los campos al completo");
    	alert.showAndWait();
    }

	@FXML
    private void onEliminarTelefono(ActionEvent event) {
		
		if(!telefonosTable.getSelectionModel().isEmpty())
    		alertEliminarTelefono();
		eliminarTelefonoB.setDisable(telefonosTable.getItems().isEmpty());
    }
    
    private void alertEliminarTelefono() {
    	Stage stage=(Stage)view.getScene().getWindow();
    	
    	Alert alert=new Alert(AlertType.CONFIRMATION);
    	alert.initModality(Modality.APPLICATION_MODAL);
    	alert.initOwner(stage);
    	alert.setTitle("Eliminar teléfono");
    	alert.setHeaderText("Va a borrar el teléfono");
    	alert.setContentText("¿Está seguro de que quiere borrarlo?");
    	
    	Optional<ButtonType>resultado=alert.showAndWait();
    	if(resultado.get()==ButtonType.OK)
    		telefonosTable.getItems().removeAll(telefonosTable.getSelectionModel().getSelectedItem());
    }
    
    @FXML
    private void onEliminarCorreo(ActionEvent event) {
    	
    	if(!correoTable.getSelectionModel().isEmpty())
    		alertEliminarCorreo();
    	eliminarCorreoB.setDisable(correoTable.getItems().isEmpty());
    }
    private void alertEliminarCorreo() {
    	Stage stage=(Stage)view.getScene().getWindow();
    	
    	Alert alert=new Alert(AlertType.CONFIRMATION);
    	alert.initModality(Modality.APPLICATION_MODAL);
    	alert.initOwner(stage);
    	alert.setTitle("Eliminar correo");
    	alert.setHeaderText("Va a borrar el correo");
    	alert.setContentText("¿Está seguro de que quiere borrarlo?");
    	
    	Optional<ButtonType>resultado=alert.showAndWait();
    	if(resultado.get()==ButtonType.OK)
    		correoTable.getItems().removeAll(correoTable.getSelectionModel().getSelectedItem());
    	
    }

    @FXML
    private void onEliminarWeb(ActionEvent event) {
    	if(!webTable.getSelectionModel().isEmpty())
    		alertEliminarWeb();
    	eliminarWebB.setDisable(webTable.getItems().isEmpty());
    }

    private void alertEliminarWeb() {
    	Stage stage=(Stage)view.getScene().getWindow();
    	
    	Alert alert=new Alert(AlertType.CONFIRMATION);
    	alert.initModality(Modality.APPLICATION_MODAL);
    	alert.initOwner(stage);
    	alert.setTitle("Eliminar Web");
    	alert.setHeaderText("Va a borrar la web");
    	alert.setContentText("¿Está seguro de que quiere borrarla?");
    	
    	Optional<ButtonType>resultado=alert.showAndWait();
    	if(resultado.get()==ButtonType.OK)
    		webTable.getItems().removeAll(webTable.getSelectionModel().getSelectedItem());
	}
    
	public final ObjectProperty<Contacto> contactoProperty() {
		return this.contacto;
	}
	

	public final Contacto getContacto() {
		return this.contactoProperty().get();
	}
	

	public final void setContacto(final Contacto contacto) {
		this.contactoProperty().set(contacto);
	}

	public TableView<Telefono> getTelefonosTable() {
		return telefonosTable;
	}

	public void setTelefonosTable(TableView<Telefono> telefonosTable) {
		this.telefonosTable = telefonosTable;
	}

	public TableView<Email> getCorreoTable() {
		return correoTable;
	}

	public void setCorreoTable(TableView<Email> correoTable) {
		this.correoTable = correoTable;
	}

	public TableView<Web> getWebTable() {
		return webTable;
	}

	public void setWebTable(TableView<Web> webTable) {
		this.webTable = webTable;
	}

	public Button getEliminarTelefonoB() {
		return eliminarTelefonoB;
	}

	public void setEliminarTelefonoB(Button eliminarTelefonoB) {
		this.eliminarTelefonoB = eliminarTelefonoB;
	}

	public Button getEliminarCorreoB() {
		return eliminarCorreoB;
	}

	public void setEliminarCorreoB(Button eliminarCorreoB) {
		this.eliminarCorreoB = eliminarCorreoB;
	}

	public Button getEliminarWebB() {
		return eliminarWebB;
	}

	public void setEliminarWebB(Button eliminarWebB) {
		this.eliminarWebB = eliminarWebB;
	}
	
	
}
