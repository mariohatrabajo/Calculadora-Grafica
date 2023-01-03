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

public class controladorNuevaTV {

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfFormula;

    @FXML
    private Button btnOK;

    @FXML
    private ComboBox<String> cbID;

    @FXML
    private TextField tfIDTV;

    @FXML
    private TextField tfNombreTV;

    @FXML
    void handleCBID(ActionEvent event) {
    	Conexion c = new Conexion();

		// Crear una declaracion
		try {
			Statement sentencia = c.connection.createStatement();
			
			ResultSet resultado = sentencia.executeQuery("SELECT nombre, formula FROM formulas WHERE IDFORMULA = " + cbID.getValue());
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
		if(!tfNombreTV.getText().isEmpty() && !tfFormula.getText().isEmpty()) {
		Conexion c = new Conexion();

		try {
			// Crear una declaracion
			Statement sentencia = c.connection.createStatement();
			
			sentencia.executeUpdate("INSERT INTO TABLAVALORES VALUES(NULL, '" + tfNombreTV.getText() + "', '"
					+ cbID.getValue() + "')");
		} catch (SQLIntegrityConstraintViolationException e) {
//			e.printStackTrace();
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
	void initialize()  {
		Conexion c = new Conexion();

		// Crear una declaracion
		try {
			Statement sentencia = c.connection.createStatement();
			
			//Se rellena el combobutton con los ids de las formulas
			ResultSet resultado = sentencia.executeQuery("SELECT idFormula FROM formulas WHERE TIPO = 'guardada'");
			
			while(resultado.next()) {
				cbID.getItems().add(resultado.getString(1));
			}
			
			//Se intenta predecir qué ID tendrá la nueva tabla
			resultado = sentencia.executeQuery("SELECT MAX(idTabla)+1 FROM tablavalores");
			resultado.next();
			
			if(resultado.getString(1) == null) {
				tfIDTV.setText("1");
			}else {
				tfIDTV.setText(resultado.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		c.close();
	}

}
