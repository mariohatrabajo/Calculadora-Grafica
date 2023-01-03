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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modelo.Conexion;
import modelo.LectorFunciones;

public class controladorBorrarFormula {

	@FXML
	private ComboBox<String> cbID;

	@FXML
	private TextField tfNombre;

	@FXML
	private TextField tfFormula;

	@FXML
	private Button btnOK;

	@FXML
	void handleCBID(ActionEvent event) {
		Conexion c = new Conexion();

		// Crear una declaracion
		try {
			Statement sentencia = c.connection.createStatement();

			ResultSet resultado = sentencia
					.executeQuery("SELECT nombre, formula FROM formulas WHERE IDFORMULA = " + cbID.getValue());
			resultado.next();

			tfNombre.setText(resultado.getString(1));
			tfFormula.setText(resultado.getString(2));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		c.close();
	}

	@FXML
	void handleEnter(KeyEvent event) throws ClassNotFoundException, SQLException {
		if (event.getCode() == KeyCode.ENTER) {
			handleOK(new ActionEvent());
		}
	}

	@FXML
	void handleOK(ActionEvent event) {
		if (!tfNombre.getText().isEmpty() && !tfFormula.getText().isEmpty()) {

			Conexion c = new Conexion();

			try {
				// Crear una declaracion
				Statement sentencia = c.connection.createStatement();

				sentencia.executeUpdate("DELETE FROM FORMULAS WHERE IDFORMULA = '" + cbID.getValue() + "'");

			} catch (SQLException e) {
				e.printStackTrace();
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setHeaderText(null);
				alerta.setTitle("Error");
				alerta.setContentText("Un error ha ocurrido en el SQL.");
				alerta.show();
			}

			c.close();
		} else {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setHeaderText(null);
			alerta.setTitle("Error");
			alerta.setContentText("No puedes dejar campos vacíos.");
			alerta.show();
		}
		controladorPpal.stageFormula.close();
	}

	@FXML
	void initialize() {
		Conexion c = new Conexion();

		// Crear una declaracion
		try {
			Statement sentencia = c.connection.createStatement();

			ResultSet resultado = sentencia.executeQuery("SELECT idFormula FROM formulas WHERE TIPO = 'guardada'");

			while (resultado.next()) {
				cbID.getItems().add(resultado.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		c.close();
	}

}
