package br.ufpe.cin.siscom.dfsa.util;

import java.io.IOException;
import java.util.List;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

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
		chart.getStyler().setYAxisMin(0.0);
		chart.getStyler().setXAxisMin(0.0);
		chart.getStyler().setYAxisMax(yMax);
		chart.getStyler().setXAxisMax(xMax);
		
		//TODO Método de colocar séries no gráfico
		double[] x = {111, 333};
		double[] y = {532, 234};
		XYSeries series = chart.addSeries("Vog", x, y);
		series.setMarker(SeriesMarkers.SQUARE);
		
		x = new double[] {200, 500};
		y = new double[] {300, 600};
		chart.addSeries("Eom-lee", x, y);
		series.setMarker(SeriesMarkers.CIRCLE);
	
		x = new double[] {876, 444};
		y = new double[] {321, 241};
		chart.addSeries("pitoca", x, y);
		series.setMarker(SeriesMarkers.DIAMOND);
		
		return chart;
	}
	
	public XYChart createNumSlotsChart(){
		
		return null;
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
