package controlador;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import modelo.Conexion;
import modelo.LectorFunciones;

public class controladorPpal {

	@FXML
	private MenuItem ayudaSintaxis;

	@FXML
	private MenuItem ayudaComoUsar;

	@FXML
	private Menu menuFormulasPred;

	@FXML
	private Menu menuFormulasGuard;

	@FXML
	private MenuItem miGuardF;

	@FXML
	private MenuItem miModF;

	@FXML
	private MenuItem miBorrarF;

    @FXML
    private Menu menuSelecTV;

    @FXML
    private MenuItem miNuevaTV;

	@FXML
	private MenuItem miBorrarTV;

	@FXML
	private VBox Vista;

	@FXML
	private TextField tfFuncion;

	@FXML
	private SplitPane splitpane;

	@FXML
	public ScrollPane scrollpane;

	@FXML
	private AnchorPane graficaPane;

	@FXML
	private Line ejeY;

	@FXML
	private Line ejeX;

	@FXML
	private Label labelRatonX;

	@FXML
	private Label labelRatonY;

	@FXML
	private HBox statusPane;

    @FXML
    private TextField tfTV;

	@FXML
	private LineChart<String, Double> graficaValores;

    @FXML
    private TextField tfValor;

	public static Stage stageAyuda = new Stage();

    @FXML
    private TextArea txtErrores;

    @FXML
    void abrirComoUsar(ActionEvent event) {
    	try {
			TabPane root = FXMLLoader.load(getClass().getResource("/vista/ComoUsar.fxml"));
			Scene scene = new Scene(root);
			stageAyuda.setScene(scene);
			stageAyuda.setTitle("Cómo usar");
			stageAyuda.setResizable(false);
			stageAyuda.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	@FXML
	void abrirSintaxis(ActionEvent event) {
		try {
			ScrollPane root = FXMLLoader.load(getClass().getResource("/vista/Sintaxis.fxml"));
			Scene scene = new Scene(root);
			stageAyuda.setScene(scene);
			stageAyuda.setTitle("Sintaxis");
			stageAyuda.setResizable(false);
			stageAyuda.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Stage stageFormula = new Stage();

	@FXML
	void cargarFormula(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		MenuItem mi = null;

		// Cicla por todas las formulas comprobando cual es la que ha llamado al evento
		boolean encontrado = false;
		for (int i = 0; i < menuFormulasPred.getItems().size() && !encontrado; i++) {
			if (menuFormulasPred.getItems().get(i).equals(event.getSource())) {
				encontrado = true;
				mi = menuFormulasPred.getItems().get(i);
			}
		}
		for (int i = 0; i < menuFormulasGuard.getItems().size() && !encontrado; i++) {
			if (menuFormulasGuard.getItems().get(i).equals(event.getSource())) {
				encontrado = true;
				mi = menuFormulasGuard.getItems().get(i);
			}
		}

		try {
			String formula = "?";
			// Saca la formula a partir del nombre
			Conexion c = new Conexion();
			// Crear una declaracion
			try {
				Statement sentencia = c.connection.createStatement();

				ResultSet resultado = sentencia
						.executeQuery("SELECT formula FROM formulas WHERE nombre = '" + mi.getText() + "'");
				resultado.next();
				formula = resultado.getString(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			c.close();

			tfFuncion.setText(formula);
			LectorFunciones.funcion = formula;
			System.out.println("Fórmula cargada: \"" + mi.getText() + "\" (" + formula + ")");
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	void cargarFormula(String idFormula)  {
		try {
			String formula = "?";
			// Saca la formula a partir del id
			Conexion c = new Conexion();
			// Crear una declaracion
			try {
				Statement sentencia = c.connection.createStatement();

				ResultSet resultado = sentencia
						.executeQuery("SELECT formula FROM formulas WHERE idFormula = '" + idFormula + "'");
				resultado.next();
				formula = resultado.getString(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			c.close();

			tfFuncion.setText(formula);
			LectorFunciones.funcion = formula;
			System.out.println("Fórmula cargada: \"" + idFormula + "\" (" + formula + ")");
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void guardarFormula(ActionEvent event) throws IOException {
		AnchorPane root = FXMLLoader.load(getClass().getResource("/vista/guardarFormula.fxml"));
		Scene scene = new Scene(root);
		stageFormula.setScene(scene);
		stageFormula.setTitle("Guardar Formula");
		stageFormula.showAndWait();
		actualizarFormulasGuard(new ActionEvent());

		// Se le aplica el evento de cargar al nuevo item
		menuFormulasGuard.getItems().get(menuFormulasGuard.getItems().size() - 1).setOnAction(e -> {
			try {
				cargarFormula(e);
			} catch (ClassNotFoundException | IOException | SQLException e1) {
				e1.printStackTrace();
			}
		});
		System.out.println("Fórmula guardada");
	}

	@FXML
	void modificarFormula(ActionEvent event) throws IOException {
		AnchorPane root = FXMLLoader.load(getClass().getResource("/vista/modificarFormula.fxml"));
		Scene scene = new Scene(root);
		stageFormula.setScene(scene);
		stageFormula.setTitle("Modificar Formula");
		stageFormula.showAndWait();
		actualizarFormulasGuard(new ActionEvent());
		System.out.println("Fórmula editada");
	}

	@FXML
	void borrarFormula(ActionEvent event) throws IOException {
		AnchorPane root = FXMLLoader.load(getClass().getResource("/vista/borrarFormula.fxml"));
		Scene scene = new Scene(root);
		stageFormula.setScene(scene);
		stageFormula.setTitle("Borrar Formula");
		stageFormula.showAndWait();
		actualizarFormulasGuard(new ActionEvent());
		System.out.println("Fórmula borrada");
	}

	@FXML
	void actualizarFormulas(ActionEvent event) {
		actualizarFormulasPred(event);
		actualizarFormulasGuard(event);
	}

	@FXML
	void actualizarFormulasPred(ActionEvent event) {
		Conexion c = new Conexion();

		// Crear una declaracion
		try {
			Statement sentencia = c.connection.createStatement();

			ResultSet resultado = sentencia.executeQuery("SELECT nombre FROM formulas WHERE TIPO = 'predeterminada'");
			while (resultado.next()) {
				menuFormulasPred.getItems().add(new MenuItem(resultado.getString(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		c.close();
	}

	@FXML
	void actualizarFormulasGuard(ActionEvent event) {
		menuFormulasGuard.getItems().clear();

		Conexion c = new Conexion();

		// Crear una declaracion
		try {
			Statement sentencia = c.connection.createStatement();

			ResultSet resultado = sentencia.executeQuery("SELECT nombre FROM formulas WHERE TIPO = 'guardada'");
			while (resultado.next()) {
				menuFormulasGuard.getItems().add(new MenuItem(resultado.getString(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		aplicarEventosG();

		c.close();
	}

	// Crea los eventos para los items del menu de formulas predeterminadas
	private void aplicarEventosF() {
		aplicarEventosP();
		aplicarEventosG();
	}

	private void aplicarEventosP() {
		for (int i = 0; i < menuFormulasPred.getItems().size(); i++) {
			menuFormulasPred.getItems().get(i).setOnAction(e -> {
				try {
					cargarFormula(e);
				} catch (ClassNotFoundException | IOException | SQLException e1) {
					e1.printStackTrace();
				}
			});
		}
	}

	private void aplicarEventosG() {
		for (int i = 0; i < menuFormulasGuard.getItems().size(); i++) {
			menuFormulasGuard.getItems().get(i).setOnAction(e -> {
				try {
					cargarFormula(e);
				} catch (ClassNotFoundException | IOException | SQLException e1) {
					e1.printStackTrace();
				}
			});
		}
	}

	@FXML
	void introducirFuncion(ActionEvent event) {
		LectorFunciones.funcion = tfFuncion.getText();
	}

	private final int TAM = 4000;

	@FXML
	void representar(ActionEvent event) {
		try {
		
		introducirFuncion(event);

		Path path = new Path();
		path.setStroke(Color.WHITE);
		path.setStrokeWidth(2);
		MoveTo moveTo = new MoveTo(-TAM / 2, -LectorFunciones.eval(LectorFunciones.funcion.replace("x", 0 + "")) * 20);
		path.getElements().add(moveTo);

		for (double x = -TAM / 2 / 20; x < TAM / 2 / 20; x += 0.1) {
			x = Math.round(x * 10) / 10.0;
			double y = LectorFunciones.eval(LectorFunciones.funcion.replace("x", x + ""));
//        	System.out.println("x = " + x + ", y = " + y);
			if (y > TAM / 2 / 20) {
				y = TAM / 2 / 20;
			} else if (y < -TAM / 2 / 20) {
				y = -TAM / 2 / 20;
			}
			path.getElements().add(new LineTo(x * 20, -y * 20));
		}
		graficaPane.getChildren().add(path);
		}catch (RuntimeException e) {;
			System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
			System.out.println(e.getMessage());
			txtErrores.setText(e.getMessage());
		}
	}

	// Borra todas las lineas de fucnción dibujadas en la gráfica
	@FXML
	void borrar(ActionEvent event) throws IOException {
		for (int i = graficaPane.getChildren().size() - 1; i > 0; i--) {
			if (graficaPane.getChildren().get(i) instanceof Path) {
				graficaPane.getChildren().remove(i);
			}
		}
	}

	// Cambia el label de abajo para que ponga la x del raton y su correspondiente y
	// en la funcion
	@FXML
	void ratonMovido(MouseEvent e) {
		double x = e.getX();
		double y = LectorFunciones.eval(LectorFunciones.funcion.replace("x", e.getX() / 20 + ""));
		x = ((int) ((x / 20) * 10)) / 10.0;
		y = ((int) (y * 10)) / 10.0;
		labelRatonX.setText("x = " + x);
		labelRatonY.setText("y = " + y);
	}
	
	void actualizarTV() {
		for(int i = 1; i < menuSelecTV.getItems().size(); ) {
			menuSelecTV.getItems().remove(i);
		}
		
		Conexion c = new Conexion();

		// Crear una declaracion
		try {
			Statement sentencia = c.connection.createStatement();

			ResultSet resultado = sentencia.executeQuery("SELECT nombre FROM tablavalores order by idTabla");
			while (resultado.next()) {
				menuSelecTV.getItems().add(new MenuItem(resultado.getString(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		c.close();
		aplicarEventosTV();
	}
	
	void actualizarGraficaTV() {
		Conexion c = new Conexion();
		
		graficaValores.getData().clear();

		try {
			// Crear una declaracion
			Statement sentencia = c.connection.createStatement();

			ResultSet resultado = sentencia.executeQuery("SELECT x, y FROM valores WHERE IDTABLA = " + idTVSelec);
			
			XYChart.Series<String, Double> series = new XYChart.Series<>();
			
			while (resultado.next()) {
		        series.getData().add(new XYChart.Data<>(resultado.getString(1) + "", Double.parseDouble(resultado.getString(2))));
			}
		        graficaValores.getData().add(series);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		c.close();
	}

	private void aplicarEventosTV() {
		
//		menuSelecTV.getItems().get(0).setOnAction(e -> {
//			try {
//				handleNuevaTV(e);
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//		});
		for (int i = 1; i < menuSelecTV.getItems().size(); i++) {
			menuSelecTV.getItems().get(i).setOnAction(e -> {
				try {
					cargarTV(e);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
		}
	}

    @FXML
    void handleNuevaTV(ActionEvent event) throws IOException {
    	AnchorPane root = FXMLLoader.load(getClass().getResource("/vista/NuevaTV.fxml"));
		Scene scene = new Scene(root);
		stageFormula.setScene(scene);
		stageFormula.setTitle("Nueva tabla de valores");
		stageFormula.showAndWait();
		actualizarTV();
		
		System.out.println("TV creada");
    }
    
    @FXML
    void borrarTV(ActionEvent event) throws IOException {
    	AnchorPane root = FXMLLoader.load(getClass().getResource("/vista/borrarTV.fxml"));
		Scene scene = new Scene(root);
		stageFormula.setScene(scene);
		stageFormula.setTitle("Borrar tabla de valores");
		stageFormula.showAndWait();
		actualizarTV();
		System.out.println("TV borrada");
    }
    
    public String idTVSelec;
    
    void cargarTV(ActionEvent event) throws IOException{
    	MenuItem mi = null;

		// Cicla por todas las tablas comprobando cual es la que ha llamado al evento
		boolean encontrado = false;
		for (int i = 1; i < menuSelecTV.getItems().size() && !encontrado; i++) {
			if (menuSelecTV.getItems().get(i).equals(event.getSource())) {
				encontrado = true;
				mi = menuSelecTV.getItems().get(i);
			}
		}

		try {
			// Saca el id a partir del nombre
			Conexion c = new Conexion();
			// Crear una declaracion
			try {
				Statement sentencia = c.connection.createStatement();

				ResultSet resultado = sentencia.executeQuery("SELECT idtabla, idFormula FROM tablavalores WHERE nombre = '" + mi.getText() + "'");
				resultado.next();
				idTVSelec = resultado.getString(1);
				tfTV.setText(mi.getText());
				
				cargarFormula(resultado.getString(2));
				actualizarGraficaTV();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			c.close();
			
			System.out.println("TV cargada: \"" + mi.getText() + "\" (id = " + idTVSelec + ")");
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
    }
    
	@FXML
	void añadirValor(ActionEvent event) {
		Conexion c = new Conexion();
		
		try {
			Statement sentencia = c.connection.createStatement();
			
			double x = Double.parseDouble(tfValor.getText());
			
			sentencia.executeUpdate("INSERT INTO VALORES values(" + x + ", " + LectorFunciones.eval(LectorFunciones.funcion.replace("x", x + "")) + ", " + idTVSelec + ")");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(NumberFormatException e) {
//			e.printStackTrace();
		}
		
		c.close();
		actualizarGraficaTV();
	}

    @FXML
    void borrarValor(ActionEvent event) {
    	Conexion c = new Conexion();
		
		try {
			Statement sentencia = c.connection.createStatement();
			
			double x = Double.parseDouble(tfValor.getText());
			
			sentencia.executeUpdate("DELETE FROM VALORES WHERE X = " + x + " AND IDTABLA = " + idTVSelec);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(NumberFormatException e) {
//			e.printStackTrace();
		}
		
		c.close();
		actualizarGraficaTV();
    }

	@FXML
	void initialize() throws IOException {
		actualizarFormulas(new ActionEvent());
		aplicarEventosF();
		actualizarTV();
		aplicarEventosTV();

		for (int i = -TAM / 2; i < TAM / 2; i += 20) {
			// Marcas
			Line l = new Line(i, -10, i, 10);
			l.setStrokeWidth(2);
			graficaPane.getChildren().add(l);

			l = new Line(-10, i, 10, i);
			l.setStrokeWidth(2);
			graficaPane.getChildren().add(l);

			// Grid
			l = new Line(-TAM / 2, i, TAM / 2, i);
			l.setOpacity(.25);
			graficaPane.getChildren().add(l);

			l = new Line(i, -TAM / 2, i, TAM / 2);
			l.setOpacity(.25);
			graficaPane.getChildren().add(l);
		}
		
	}
}
