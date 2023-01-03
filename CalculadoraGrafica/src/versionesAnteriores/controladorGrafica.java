package versionesAnteriores;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import modelo.LectorFunciones;

public class controladorGrafica {
	
	private final int TAM = 2000;

    @FXML
    private AnchorPane graficaPane;

    @FXML
    private Line ejeY;

    @FXML
    private Line ejeX;
    
    
    void representar() {
    	Path path = new Path();
		path.setStroke(Color.WHITE);
		path.setStrokeWidth(2);
		MoveTo moveTo = new MoveTo(-TAM/2, -LectorFunciones.eval(LectorFunciones.funcion.replace("x", -TAM/2 + ""))*20);
		path.getElements().add(moveTo);
    	
        for(double x = -TAM/2; x < TAM/2; x+=0.1) {
        	x = Math.round(x*10)/10.0;
        	double y = LectorFunciones.eval(LectorFunciones.funcion.replace("x", x + ""));
//        	System.out.println("x = " + x + ", y = " + y);
			if (y > TAM/2) {
				y = TAM/2;
			} else if (y < -TAM/2) {
				y = -TAM/2;
			}
			path.getElements().add(new LineTo(x*20, -y*20));
        }
        graficaPane.getChildren().add(path);
    }
    
    @FXML
    void initialize() {
        
        for(int i = -TAM/2; i < TAM/2; i += 20) {
        	//Marcas
        	Line l = new Line(i, -10, i, 10);
        	l.setStrokeWidth(2);
        	graficaPane.getChildren().add(l);
        	
        	l = new Line(-10, i, 10, i);
        	l.setStrokeWidth(2);
        	graficaPane.getChildren().add(l);
        	
        	//Grid
        	l = new Line(-TAM/2, i, TAM/2, i);
        	l.setOpacity(.25);
        	graficaPane.getChildren().add(l);

        	l = new Line(i, -TAM/2, i, TAM/2);
        	l.setOpacity(.25);
        	graficaPane.getChildren().add(l);
        }
        
        representar();
    }
}
