package pruebas;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LineChartP extends Application{
	
	public static int w = 1366;
	public static int h = 768;
	public static Group grupo = new Group();
	
	@Override
	public void start(Stage stage) throws IOException {
		
		
		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("linechartP.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		stage.setTitle("Visualizador de funciones matemáticas");
		stage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}


