package dad.miCv.conocimiento.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Idioma {
	private StringProperty idioma= new SimpleStringProperty();

	public Idioma() {
	}


	public Idioma(String idioma) {
		this.idioma.set(idioma);
	}


	public final StringProperty idiomaProperty() {
		return this.idioma;
	}
	

	public final String getIdioma() {
		return this.idiomaProperty().get();
	}
	

	public final void setIdioma(final String idioma) {
		this.idiomaProperty().set(idioma);
	}


	@Override
	public String toString() {
		return "Idioma [idioma=" + idioma + "]";
	}
}
