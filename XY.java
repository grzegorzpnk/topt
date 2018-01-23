package topt;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.xml.crypto.Data;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class XY extends JFrame { // the class extends the JFrame class

	 public XY(ArrayList<data> _dataList) {   // the constructor will contain the panel of a certain size and the close operations 
	    super("XY Line Chart Example with JFreechart"); // calls the super class constructor
	    
	    JPanel chartPanel = createChartPanel(_dataList);       
	    add(chartPanel, BorderLayout.CENTER);
	    
	    setSize(640, 480);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
	}

	private JPanel createChartPanel(ArrayList<data> _dataList) { // this method will create the chart panel containin the graph 
	    String chartTitle = "Objects Movement Chart";
	    String xAxisLabel = "X";
	    String yAxisLabel = "Y";
	    
	    XYDataset dataset = createDataset(_dataList);
	    JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);
	    customizeChart(chart);
	    int width = 640;
	    int height = 480;
	      return new ChartPanel(chart);
	}

	private XYDataset createDataset(ArrayList<data> _dataList) {    // this method creates the data as time seris 
	    XYSeriesCollection dataset = new XYSeriesCollection();
	    XYSeries series1 = new XYSeries("Object 1");
	    XYSeries series2 = new XYSeries("Object 2");
	    XYSeries series3 = new XYSeries("Object 3");
	    
	    for(int i=0; i<_dataList.get(0).BER.length;i++){
	    series1.add(_dataList.get(0).MERdB[i],_dataList.get(0).BER[i]);
	    System.out.println("BER: "+_dataList.get(0).BER[i]+" MER: "+_dataList.get(0).MERdB[i]);
	    }
	    for(int i=0; i<_dataList.get(1).BER.length;i++){
	    	series2.add(_dataList.get(1).MERdB[i],_dataList.get(1).BER[i]);
		    System.out.println("BER: "+_dataList.get(1).BER[i]+" MER: "+_dataList.get(1).MERdB[i]);
		    }
	    for(int i=0; i<_dataList.get(2).BER.length;i++){
		    series3.add(_dataList.get(2).MERdB[i],_dataList.get(2).BER[i]);
		    System.out.println("BER: "+_dataList.get(2).BER[i]+" MER: "+_dataList.get(2).MERdB[i]);
		    }
	    
	    dataset.addSeries(series1);
	    dataset.addSeries(series2);
	    dataset.addSeries(series3);
	    
	    return dataset;
	}

	private void customizeChart(JFreeChart chart) {   // here we make some customization
	    XYPlot plot = chart.getXYPlot();
	    LogAxis yAxis = new LogAxis();
        plot.setRangeAxis(yAxis);
	    
	    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

	    // sets paint color for each series
	    renderer.setSeriesPaint(0, Color.RED);
	    renderer.setSeriesPaint(1, Color.GREEN);
	    renderer.setSeriesPaint(2, Color.YELLOW);

	    // sets thickness for series (using strokes)
	    renderer.setSeriesStroke(0, new BasicStroke(4.0f));
	    renderer.setSeriesStroke(1, new BasicStroke(3.0f));
	    renderer.setSeriesStroke(2, new BasicStroke(2.0f));
	    
	    // sets paint color for plot outlines
	    plot.setOutlinePaint(Color.BLUE);
	    plot.setOutlineStroke(new BasicStroke(2.0f));
	    
	    
	    // sets renderer for lines
	    plot.setRenderer(renderer);
	    
	    // sets plot background
	    plot.setBackgroundPaint(Color.DARK_GRAY);
	    
	    // sets paint color for the grid lines
	    plot.setRangeGridlinesVisible(true);
	    plot.setRangeGridlinePaint(Color.BLACK);
	    
	    plot.setDomainGridlinesVisible(true);
	    plot.setDomainGridlinePaint(Color.BLACK);
	    
	}

	
}