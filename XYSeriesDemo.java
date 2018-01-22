package topt;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class XYSeriesDemo extends ApplicationFrame {

	    /**
	     * A demonstration application showing an XY series containing a null value.
	     *
	     * @param title  the frame title.
	     */
	    public XYSeriesDemo(final String title, double _MER[], double _BER[], int modulation) {

	        super(title);
	        final XYSeries series = new XYSeries("Random Data");
	        for(int i=0;i<_MER.length;i++)
	        	series.add(_MER[i],_BER[i]);
	       
	        
	        final XYSeriesCollection data = new XYSeriesCollection(series);
	        final JFreeChart chart = ChartFactory.createXYLineChart("Zale¿noœæ BER od MER dla: "+modulation+"QAM","MER[dB]","BER",data, PlotOrientation.VERTICAL,false, true, false);
	        //	ChartFactory.createl
	        
	     // Create an NumberAxis
	        LogAxis yAxis = new LogAxis();
	        // Assign it to the chart
	        XYPlot plot = (XYPlot) chart.getPlot();
//	        plot.setDomainAxis(xAxis);
	      
	        plot.setRangeAxis(yAxis);
	        

	      
	        
	        
	        
	        final ChartPanel chartPanel = new ChartPanel(chart);
	        chartPanel.setPreferredSize(new java.awt.Dimension(600, 600));
	        setContentPane(chartPanel);

	    }


	
}
