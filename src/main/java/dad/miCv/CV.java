package dad.miCv;


import dad.miCv.conocimiento.model.ConocimientoModel;
import dad.miCv.contacto.model.Contacto;
import dad.miCv.experiencia.model.Experiencia;
import dad.miCv.formacion.model.Formacion;
import dad.miCv.personal.model.Personal;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CV {
	
	private ObjectProperty<Personal> personal=new SimpleObjectProperty<Personal>(new Personal());
	private ObjectProperty<Contacto> contacto=new SimpleObjectProperty<Contacto>(new Contacto());
	private ListProperty <Formacion> formaciones=new SimpleListProperty<Formacion>(FXCollections.observableArrayList());
	private ListProperty <ConocimientoModel> conocimiento=new SimpleListProperty<ConocimientoModel>(FXCollections.observableArrayList()); 
	private ListProperty<Experiencia> experiencia=new SimpleListProperty<Experiencia>(FXCollections.observableArrayList());
	
	public final ObjectProperty<Personal> personalProperty() {
		return this.personal;
	}
	

	public final Personal getPersonal() {
		return this.personalProperty().get();
	}
	

	public final void setPersonal(final Personal personal) {
		this.personalProperty().set(personal);
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


	public final ListProperty<Experiencia> experienciaProperty() {
		return this.experiencia;
	}
	


	public final ObservableList<Experiencia> getExperiencia() {
		return this.experienciaProperty().get();
	}
	


	public final void setExperiencia(final ObservableList<Experiencia> experiencia) {
		this.experienciaProperty().set(experiencia);
	}


	public final ListProperty<ConocimientoModel> conocimientoProperty() {
		return this.conocimiento;
	}
	


	public final ObservableList<ConocimientoModel> getConocimiento() {
		return this.conocimientoProperty().get();
	}
	


	public final void setConocimiento(final ObservableList<ConocimientoModel> conocimiento) {
		this.conocimientoProperty().set(conocimiento);
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
		
}