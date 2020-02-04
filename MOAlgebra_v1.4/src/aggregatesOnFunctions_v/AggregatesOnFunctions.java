package aggregatesOnFunctions_v;

import java.math.BigDecimal;
import java.sql.SQLException;

import algebraTypes_v.GInt_V;
import algebraTypes_v.GReal_V;
import algebraTypes_v.MInt_V;
import algebraTypes_v.MReal_V;
import algebraTypes_v.UGInt;
import algebraTypes_v.UGReal;
import algebraTypes_v.UInt;
import algebraTypes_v.UReal;

/*
 * This class implements the aggregates on functions operations (mean, max, min) for gmoving types
 */
public class AggregatesOnFunctions {


	public static BigDecimal mean (MReal_V mr) {
		if (mr == null)
			return null;
		double sum = 0, interval = 0;
		
		try {
			for (int i=0; i<mr.getUnits().length(); i++) {
				UReal ur = mr.getUnits().getElement(i);
				double a, b, c, t1, t2;
				a = ur.getA().doubleValue();
				b = ur.getB().doubleValue();
				c = ur.getC().doubleValue();
				t1 = ur.getT1().doubleValue();
				t2 = ur.getT2().doubleValue();
				System.out.println("a = " + a + " b = " + b + " c = " + c);
				interval += Math.abs(t2-t1);
				sum += (a/3)*Math.abs(Math.pow(t2, 3)-Math.pow(t1, 3)) + (b/2)*Math.abs(Math.pow(t2, 2)-Math.pow(t1, 2)) + c*Math.abs(t2 - t1);
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return new BigDecimal(sum/interval);
	}
	
	public static BigDecimal mean (GReal_V gr) {
		if (gr == null)
			return null;
		double sum = 0, interval = 0;
		
		try {
			for (int i=0; i<gr.getUnits().length(); i++) {
				UGReal ugr = gr.getUnits().getElement(i);
				double a, b, c, pos1, pos2;
				a = ugr.getA().doubleValue();
				b = ugr.getB().doubleValue();
				c = ugr.getC().doubleValue();
				pos1 = ugr.getInterval().getPos1().doubleValue();
				pos2 = ugr.getInterval().getPos2().doubleValue();
				
				interval += Math.abs(pos2-pos1);
				sum += (a/3)*Math.abs(Math.pow(pos2, 3)-Math.pow(pos1, 3)) + (b/2)*Math.abs(Math.pow(pos2, 2)-Math.pow(pos1, 2)) + c*Math.abs(pos2 - pos1);
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return new BigDecimal(sum/interval);
	}
	
	public static BigDecimal mean (GInt_V gi) {
		if (gi == null)
			return null;
		double sum = 0, interval = 0;
		
		try {
			for (int i=0; i<gi.getUnits().length(); i++) {
				UGInt ugi = gi.getUnits().getElement(i);
				double val, pos1, pos2;
				val = ugi.getVal().doubleValue();
				pos1 = ugi.getInterval().getPos1().doubleValue();
				pos2 = ugi.getInterval().getPos2().doubleValue();
				
				interval += Math.abs(pos2-pos1);
				sum += val*Math.abs(pos2 - pos1);
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return new BigDecimal(sum/interval);
	}
	
	public static BigDecimal max (MReal_V mr) {
		if (mr == null)
			return null;
		double max = -999999999;
		
		try {
			for (int i=0; i<mr.getUnits().length(); i++) {
				UReal ur = mr.getUnits().getElement(i);
				double a, b, c, t1, t2, temp;
				a = ur.getA().doubleValue();
				b = ur.getB().doubleValue();
				c = ur.getC().doubleValue();
				t1 = ur.getT1().doubleValue();
				t2 = ur.getT2().doubleValue();
				
				if (a != 0) {
					// TODO: 
				}
				else {
					if (b > 0) {
						temp = b*t2 + c;
						if (max < temp)
							max = temp;
					}
					else {
						temp = b*t1 + c;
						if (max < temp)
							max = temp;
					}
				}
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return new BigDecimal(max);
	}
	
	public static BigDecimal max (GReal_V gr) {
		if (gr == null)
			return null;
		double max = -999999999;
		
		try {
			for (int i=0; i<gr.getUnits().length(); i++) {
				UGReal ugr = gr.getUnits().getElement(i);
				double a, b, c, pos1, pos2, temp;
				a = ugr.getA().doubleValue();
				b = ugr.getB().doubleValue();
				c = ugr.getC().doubleValue();
				pos1 = ugr.getInterval().getPos1().doubleValue();
				pos2 = ugr.getInterval().getPos2().doubleValue();
				
				if (pos1 > pos2) {
					temp = pos1;
					pos1 = pos2;
					pos2 = temp;
				}
				if (a != 0) {
					// TODO: 
				}
				else {
					if (b > 0) {
						temp = a*pos2*pos2 + b*pos2 + c;
						if (max < temp)
							max = temp;
					}
					else {
						temp = a*pos1*pos1 + b*pos1 + c;
						if (max < temp)
							max = temp;
					}
				}
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return new BigDecimal(max);
	}
	
	public static BigDecimal min (MReal_V mr) {
		if (mr == null)
			return null;
		double min = 999999999;
		
		try {
			for (int i=0; i<mr.getUnits().length(); i++) {
				UReal ur = mr.getUnits().getElement(i);
				double a, b, c, t1, t2, temp;
				a = ur.getA().doubleValue();
				b = ur.getB().doubleValue();
				c = ur.getC().doubleValue();
				t1 = ur.getT1().doubleValue();
				t2 = ur.getT2().doubleValue();
				
				if (a != 0) {
					// TODO: 
				}
				else {
					if (b > 0) {
						temp = b*t1 + c;
						if (min > temp)
							min = temp;
					}
					else {
						temp = b*t2 + c;
						if (min > temp)
							min= temp;
					}
				}
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return new BigDecimal(min);
	}
	
	public static BigDecimal min (GReal_V gr) {
		if (gr == null)
			return null;
		double min = 999999999;
		
		try {
			for (int i=0; i<gr.getUnits().length(); i++) {
				UGReal ugr = gr.getUnits().getElement(i);
				double a, b, c, pos1, pos2, temp;
				a = ugr.getA().doubleValue();
				b = ugr.getB().doubleValue();
				c = ugr.getC().doubleValue();
				pos1 = ugr.getInterval().getPos1().doubleValue();
				pos2 = ugr.getInterval().getPos2().doubleValue();
				
				if (pos1 > pos2) {
					temp = pos1;
					pos1 = pos2;
					pos2 = temp;
				}
				if (a != 0) {
					// TODO: 
				}
				else {
					if (b < 0) {
						temp = a*pos2*pos2 + b*pos2 + c;
						if (min > temp)
							min = temp;
					}
					else {
						temp = a*pos1*pos1 + b*pos1 + c;
						if (min > temp)
							min = temp;
					}
				}
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return new BigDecimal(min);
	}
	
	public static BigDecimal max (MInt_V mi) {
		if (mi == null)
			return null;
		double max = -999999999;
		
		try {
			for (int i=0; i<mi.getUnits().length(); i++) {
				UInt ui = mi.getUnits().getElement(i);
				int v = ui.getVal().intValue();
				
				if (max < v)
					max = v;
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return new BigDecimal(max);
	}
	
	public static BigDecimal max (GInt_V gi) {
		if (gi == null)
			return null;
		double max = -999999999;
		
		try {
			for (int i=0; i<gi.getUnits().length(); i++) {
				UGInt ugi = gi.getUnits().getElement(i);
				int v = ugi.getVal().intValue();
				if (max < v)
					max = v;
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return new BigDecimal(max);
	}
	
	public static BigDecimal min (MInt_V mi) {
		if (mi == null)
			return null;
		double min = 999999999;
		
		try {
			for (int i=0; i<mi.getUnits().length(); i++) {
				UInt ui = mi.getUnits().getElement(i);
				int v = ui.getVal().intValue();
				
				if (min > v)
					min = v;
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return new BigDecimal(min);
	}
	
	public static BigDecimal min (GInt_V gi) {
		if (gi == null)
			return null;
		double min = 999999999;
		
		try {
			for (int i=0; i<gi.getUnits().length(); i++) {
				UGInt ugi = gi.getUnits().getElement(i);
				int v = ugi.getVal().intValue();
				if (min > v)
					min = v;
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return new BigDecimal(min);
	}
}

