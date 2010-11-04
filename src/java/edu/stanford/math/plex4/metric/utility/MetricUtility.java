package edu.stanford.math.plex4.metric.utility;

import edu.stanford.math.plex4.utility.RandomUtility;
import edu.stanford.math.primitivelib.metric.interfaces.AbstractIntMetricSpace;
import edu.stanford.math.primitivelib.metric.interfaces.AbstractObjectMetricSpace;
import edu.stanford.math.primitivelib.utility.Infinity;

public class MetricUtility {
	
	/**
	 * This function estimates the diameter of the supplied metric space. The
	 * diameter is defined as the smallest number r such that d(x, y) <= r for all
	 * x, y in the space. The estimate is obtained by randomly choosing pairs
	 * (i, j), and taking the maximum distance over all pairs. 
	 * 
	 * @param metricSpace the metric space to estimate the diameter of
	 * @param trials the number of random pairs to choose
	 * @return an estimate of the diameter of the metric space
	 */
	public static double estimateDiameter(AbstractIntMetricSpace metricSpace, int trials) {
		double diameterEstimate = 0;
		int n = metricSpace.size();
		
		for (int trial = 0; trial < trials; trial++) {
			int i = RandomUtility.nextUniformInt(0, n - 1);
			int j = RandomUtility.nextUniformIntExcluded(0, n - 1, i);
			double distance = metricSpace.distance(i, j);
			diameterEstimate = Math.max(distance, diameterEstimate);
		}
		
		return diameterEstimate * (((double) trials + 1) / ((double) trials));
	}
	
	public static double estimateDiameter(AbstractIntMetricSpace metricSpace) {
		return estimateDiameter(metricSpace, Math.min(metricSpace.size(), 100));
	}
	
	public static double[] computeMaxima(AbstractObjectMetricSpace<double[]> metricSpace) {
		int dimension = metricSpace.getPoint(0).length;
		int numPoints = metricSpace.size();
		double[] maxima = new double[dimension];
		for (int j = 0; j < dimension; j++) {
			maxima[j] = Infinity.Double.getNegativeInfinity();
		}
		for (int i = 0; i < numPoints; i++) {
			for (int j = 0; j < dimension; j++) {
				maxima[j] = Math.max(maxima[j], metricSpace.getPoint(i)[j]);
			}
		}
		return maxima;
	}
	
	public static double[] computeMinima(AbstractObjectMetricSpace<double[]> metricSpace) {
		int dimension = metricSpace.getPoint(0).length;
		int numPoints = metricSpace.size();
		double[] minima = new double[dimension];
		for (int j = 0; j < dimension; j++) {
			minima[j] = Infinity.Double.getPositiveInfinity();
		}
		for (int i = 0; i < numPoints; i++) {
			for (int j = 0; j < dimension; j++) {
				minima[j] = Math.min(minima[j], metricSpace.getPoint(i)[j]);
			}
		}
		return minima;
	}
	
	public static double[] computeCentroid(AbstractObjectMetricSpace<double[]> metricSpace) {
		int dimension = metricSpace.getPoint(0).length;
		int numPoints = metricSpace.size();
		double[] means = new double[dimension];
		for (int i = 0; i < numPoints; i++) {
			for (int j = 0; j < dimension; j++) {
				means[j] += metricSpace.getPoint(i)[j];
			}
		}
		
		for (int j = 0; j < dimension; j++) {
			means[j] /= numPoints;
		}
		return means;
	}

}