//Rishabh Kapoor 
//Mina Barsoum
//Sesh Venugopal
//Software Methodology

package songLib;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
//app connects to fxml file
//and fxml connects to controller
//controller handles, app brings it together, and fxml handles UI
public class SongLib extends Application {
	@Override
	public void start(Stage primaryStage)
	throws IOException {
		
		
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/songLib/songLibUI.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		Controller controller=loader.getController();
		controller.start(primaryStage);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Song Library");
		primaryStage.setResizable(false);  
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
