package br.ufpe.cin.siscom.dfsa.util;

import java.io.IOException;
import java.util.List;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import br.ufpe.cin.siscom.dfsa.domain.Result;

public class ChartUtils {

	/**
	 * Creates a chart object
	 * @param xTitle Title of the X Axis
	 * @param yTitle Title of the Y Axis
	 * @param xMax Max index of X Axis
	 * @param yMax Max index of Y Axis
	 * @author jvos
	 * */
	public static XYChart createChart(String xTitle, String yTitle, double xMax, double yMax, Result result) throws IOException {
		
		XYChart chart = new XYChartBuilder().xAxisTitle(xTitle).yAxisTitle(yTitle).width(600).height(400).build();
		chart.getStyler().setYAxisMin(xMax);
		chart.getStyler().setYAxisMax(yMax);
		
		//TODO Método de colocar séries no gráfico
		/*XYSeries series = chart.addSeries("" + i, null, getRandomWalk(10));
		series.setMarker(SeriesMarkers.SQUARE);*/

		return chart;
	}
	
	/**
	 * Display single chart on screen as jPanel
	 * @author jvos
	 * */
	public static void displayChart(XYChart chart) {
		new SwingWrapper<XYChart>(chart).displayChart();
	}
	
	/**
	 * Display list of charts on screen as single jPanel
	 * @author jvos
	 * */
	public static void displayChartList(List<XYChart> charts) {
		new SwingWrapper<XYChart>(charts).displayChartMatrix();
	}

}
