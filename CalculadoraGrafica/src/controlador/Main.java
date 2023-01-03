package controlador;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

	@Override
	public void start(Stage stage) {
		try {

			VBox root = FXMLLoader.load(getClass().getResource("/vista/VentanaPpal.fxml"));
//			ScrollPane root = FXMLLoader.load(getClass().getResource("/vista/Grafica.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);

			// Si se cierra la ventana ppal, se cierran todas
			stage.setOnCloseRequest(e -> {
				try {
					controladorPpal.stageAyuda.close();
					controladorPpal.stageFormula.close();
				} catch (NullPointerException ex) {}
			});

			stage.setTitle("Visualizador de funciones matemáticas");
			stage.setMaximized(true);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
