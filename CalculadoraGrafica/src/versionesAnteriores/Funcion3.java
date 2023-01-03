package versionesAnteriores;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//TODO Porque se mueve el Label

public class Funcion3 extends Application{
	
	public static int w = 1366;
	public static int h = 768;
	public static Group grupo = new Group();
	
	@Override
	public void start(Stage primaryStage) {
		grupo.setTranslateX(w/2);
		grupo.setTranslateY(h/2);
		grupo.setScaleX(20);
		grupo.setScaleY(20);
		
		Rectangle fondo = new Rectangle(-w/2, -h/2, w, h);
		fondo.setFill(new Color(.5, .5, .5, 1));
		
		//Eje X y Grid X
		Group ejeX = new Group();
		Line lpX = new Line(-w/2, 0, w/2, 0);
		lpX.setStrokeWidth(.2);
		ejeX.getChildren().add(lpX);
		for(int x = -w/2; x < w/2; x++) {
			if(x != 0) {
				Line l = new Line(x, -0.5, x, 0.5);
				l.setStrokeWidth(.1);
				ejeX.getChildren().add(l);
				Line lLarga = new Line(x, -h/2, x, h/2);
				lLarga.setStrokeWidth(.1);
				lLarga.setStroke(new Color(0, 0, 0, .1));
				ejeX.getChildren().add(lLarga);
			}
		}
		
		//Eje Y y grid Y
		Group ejeY = new Group();
		Line lpY = new Line(0, -h/2, 0, h/2);
		lpY.setStrokeWidth(.2);
		ejeY.getChildren().add(lpY);
		for(int y = -h/2; y < h/2; y++) {
			if(y != 0) {
				Line l = new Line(-0.5, y, 0.5, y);
				l.setStrokeWidth(.1);
				ejeY.getChildren().add(l);
				Line lLarga = new Line(-h/2, y, h/2, y);
				lLarga.setStrokeWidth(.1);
				lLarga.setStroke(new Color(0, 0, 0, .1));
				ejeY.getChildren().add(lLarga);
			}
		}
		
		//Linea
		Path path = new Path();
		path.setStroke(Color.WHITE);
		path.setStrokeWidth(.1);
		MoveTo moveTo = new MoveTo(-w/2, 0);
		path.getElements().add(moveTo);
		for(double x = -w/2; x < w/2; x+=0.1) {
			double y = funcion(x); //Funcion
			if(y > h/2) {
				y = h/2;
			}else if(y < -h/2) {
				y = -h/2;
			}
			path.getElements().add(new LineTo(x, -y));
		}
		
		grupo.getChildren().addAll(fondo, ejeX, ejeY, path);
		grupo.setOnMouseMoved(new MoverRatonTexto());
		Scene scene = new Scene(grupo, w, h);
		
		primaryStage.setTitle("Visualizador de funciones matemáticas");
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.show();
		
	}
	
	//Devuelve una y para un valor de x segun un a funcion
	public static double funcion(double x) {
//		return Math.abs(Math.sin(5*x*(Math.PI/42)));
//		return Math.pow(x, Math.sin(x));
//		return Math.abs(Math.pow(x, Math.sin(x))-10);
//		return Math.pow(x, Math.tan(x));
//		return Math.abs(Math.pow(x, Math.tan(x))-10);
//		return -(6.67*Math.exp(-5)*((x*x*2)/(x*3)));//Formula gravitación	Guardar
//		return -(6.67*Math.exp(-4)*((x*(-x))/(x*3)));//Formula gravitación antimateria + materia
//		return Math.sqrt((100-Math.pow(x, 2)));//Formula de punto en una circunferencia	Guardar
//		return x*Math.pow(3*Math.exp(8), 2);//E=mc^2	Guardar
//		return x*Math.pow(3, 2);//E=mc^2 sin exp	Guardar
//		for(int i = (int)x; i > 0; i--)
//			x += i;
//		return 1+(4+1+x*(4+1+2))+(2+1)-30;//Formula Cristian
//		return x/Math.pow(x, 2);
//		return 1+3.322*Math.log(x);//Funcion Mencia
//		return (int)x;//Guardar
//		return Math.random();
//		return Math.tanh(x);
//		return Math.getExponent(x);
//		return((8 * Math.PI * 6.67*Math.exp(-11))/(3*Math.exp(8)))*x;//Einstein's field
//		return((8 * Math.PI * 6.67*Math.exp(-5))/(3))*x;//Einstein's field recortado
//		((8 * PI * G)/C)*x
//		return (1 - Math.sqrt((Math.tan(x) * (Math.log(x) + 1))/2)) * (-1);// "Tiene que acabar reconociendo:"
		
		return Math.sin(x/2);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	class MoverRatonTexto implements EventHandler<MouseEvent>{
		Label txtY = new Label("Y = 0");

		public MoverRatonTexto() {
			txtY.setScaleX(.15);
			txtY.setScaleY(.15);
			txtY.setTranslateX(-45.5);
			txtY.setTranslateY(-26.5);
			txtY.setTextFill(Color.WHITE);
			grupo.getChildren().add(txtY);
		}
		
		@Override
		public void handle(MouseEvent e) {
			txtY.setText("Y = " + ((int)(funcion(e.getX())*100))/100.0);
//			txtY.setText("Y = " + funcion(e.getX()));
//			txtY.setTranslateX(e.getX()-20);
//			txtY.setTranslateY(e.getY()-7);
		}
	}
}


