package utils_v;

/*
 * This class stores the info of atransformed 2D Query  
 */
public class Query2D {
	public boolean isWindow; // indicates that minMeasure <> maxMeasure
	public boolean isRegrouped; // indicates that this query is the result of grouping multiple queries
	public int id; // the ID of the begining route section
	public int idMax; // the ID of the ending route section resulted by query grouping
	public float minMeasure, maxMeasure; // the measure interval for the specified section
	public float tMin, tMax; // the time interval
	
	public String toString() {
		if (isRegrouped)
			return "" + id + " " + idMax + " " + minMeasure + " " + maxMeasure;
		else
			return "" + id + " " + minMeasure + " " + maxMeasure;
	}
}
