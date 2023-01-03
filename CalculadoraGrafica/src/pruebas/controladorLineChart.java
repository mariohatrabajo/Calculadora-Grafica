package pruebas;

import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class controladorLineChart {

    @FXML
    private LineChart<String, Double> grafica;

	@FXML
	private CategoryAxis xAxis;

	@FXML
	private void initialize() {
		Random rd = new Random();
		double[] nums = new double[10];
        for (int i = 0; i < 10; i++) {
            nums[i] = i;
        }

        XYChart.Series<String, Double> series = new XYChart.Series<>();
        
        for (int i = 0; i < nums.length; i+=2) {
        	series.getData().add(new XYChart.Data<>(i + "", nums[i]));
        }
        
        grafica.getData().add(series);
	}
}
