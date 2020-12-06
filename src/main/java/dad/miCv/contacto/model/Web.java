package dad.miCv.contacto.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Web {

	private StringProperty url=new SimpleStringProperty();
	
	public Web() {}
	
	public Web(String url) {
		this.url.set(url);	
	}
	public final StringProperty urlProperty() {
		return this.url;
	}
	
	public final String getUrl() {
		return this.urlProperty().get();
	}
	
	public final void setUrl(final String url) {
		this.urlProperty().set(url);
	}
	
}
