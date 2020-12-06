package dad.miCv;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application{

	private MainController controller;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		controller=new MainController();
		
		Scene scene=new Scene(controller.getView(),1000,800);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("MiCV");
		primaryStage.getIcons().add(new Image("images/cv64x64.png"));
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}