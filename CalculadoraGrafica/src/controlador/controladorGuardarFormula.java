package controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modelo.Conexion;
import modelo.LectorFunciones;

public class controladorGuardarFormula {

	@FXML
	private TextField tfID;

	@FXML
	private TextField tfNombre;

	@FXML
	private TextField tfFormula;

	@FXML
	private Button btnOK;

	@FXML
	void handleEnter(KeyEvent event) throws ClassNotFoundException, SQLException {
		if (event.getCode() == KeyCode.ENTER) {
			handleOK(new ActionEvent());
		}
	}

	@FXML
	void handleOK(ActionEvent event) {
		if(!tfNombre.getText().isEmpty() && !tfFormula.getText().isEmpty()) {
			Conexion c = new Conexion();
	
			// Crear una declaracion
			try {
				Statement sentencia = c.connection.createStatement();
				
				sentencia.executeUpdate("INSERT INTO FORMULAS VALUES(NULL, '" + tfNombre.getText() + "', '"
						+ tfFormula.getText() + "', 'guardada')");
			} catch (SQLIntegrityConstraintViolationException e) {
//				e.printStackTrace();
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setHeaderText(null);
				alerta.setTitle("Error");
				alerta.setContentText("Ése nombre ya existe.");
				alerta.show();
			}catch(MysqlDataTruncation e) {
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setHeaderText(null);
				alerta.setTitle("Error");
				alerta.setContentText("Uno de los campos es demasiado largo.");
				alerta.show();
			}catch(SQLException e) {
				e.printStackTrace();
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setHeaderText(null);
				alerta.setTitle("Error");
				alerta.setContentText("Un error ha ocurrido en el SQL.");
				alerta.show();
			}
			
			c.close();
		}else {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setHeaderText(null);
			alerta.setTitle("Error");
			alerta.setContentText("No puedes dejar campos vacíos.");
			alerta.show();
		}
		controladorPpal.stageFormula.close();
	}

	@FXML
	void initialize() throws ClassNotFoundException, SQLException {
		Conexion c = new Conexion();

		// Crear una declaracion
		Statement sentencia = c.connection.createStatement();
		
		ResultSet resultado = sentencia.executeQuery("SELECT MAX(idFormula)+1 FROM formulas");
		resultado.next();
		
		tfID.setText(resultado.getString(1));
		tfFormula.setText(LectorFunciones.funcion);
		
		c.close();
	}

}
