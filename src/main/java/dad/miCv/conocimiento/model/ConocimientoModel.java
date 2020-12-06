package dad.miCv.conocimiento.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ConocimientoModel {
	private StringProperty denominacion=new SimpleStringProperty();
	private ObjectProperty<Idioma>idioma=new SimpleObjectProperty<>();
	private ObjectProperty<Nivel>nivel=new SimpleObjectProperty<>();
	public ConocimientoModel() {
		
	}
	public ConocimientoModel(String denominacion) {
		this.denominacion.set(denominacion); 
	}
	public final StringProperty denominacionProperty() {
		return this.denominacion;
	}
	
	public final String getDenominacion() {
		return this.denominacionProperty().get();
	}
	
	public final void setDenominacion(final String denominacion) {
		this.denominacionProperty().set(denominacion);
	}
	public final ObjectProperty<Idioma> idiomaProperty() {
		return this.idioma;
	}
	
	public final Idioma getIdioma() {
		return this.idiomaProperty().get();
	}
	
	public final void setIdioma(final Idioma idioma) {
		this.idiomaProperty().set(idioma);
	}
	
	public final ObjectProperty<Nivel> nivelProperty() {
		return this.nivel;
	}
	
	public final Nivel getNivel() {
		return this.nivelProperty().get();
	}
	
	public final void setNivel(final Nivel nivel) {
		this.nivelProperty().set(nivel);
	}
	
	
	
	
}
