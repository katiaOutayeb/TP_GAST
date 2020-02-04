package nonTemporalOperations_v;

import java.math.BigDecimal;
import java.sql.SQLException;

import algebraTypes_v.GLine_V;
import algebraTypes_v.GPoint;
import algebraTypes_v.Range_V;

/*
 * This class implements the aggregation operations for GLine_V
 * These operations are valid only if the GLine_V argument has only one continuous component
 * Subject to lifting
 */
public class Aggregation {

	public static GPoint min (GLine_V gl) {
		GPoint gp = null;
		if (UnaryPredicates.undefined(gl) == 1)
			return null;
		try {
			System.out.println("Aggregation.min --> no sections = " + gl.getRints().length());
			if (gl.getRints().length() > 1)
				return null;
			
			gp = new GPoint (gl.getNid(), gl.getRints().getElement(0).getRid(),
					gl.getRints().getElement(0).getPos1(), gl.getRints().getElement(0).getSide());
		} catch (SQLException e) { e.printStackTrace();	}
		
		return gp;
	}
	
	public static GPoint max (GLine_V gl) {
		GPoint gp = null;
		if (UnaryPredicates.undefined(gl) == 1)
			return null;
		try {
			if (gl.getRints().length() > 1)
				return null;
			
			gp = new GPoint (gl.getNid(), gl.getRints().getElement(0).getRid(),
					gl.getRints().getElement(0).getPos2(), gl.getRints().getElement(0).getSide());
		} catch (SQLException e) { e.printStackTrace();	}
		
		return gp;
	}
	
	// avg[center]
	public static GPoint avg (GLine_V gl) {
		GPoint gp = null;
		if (UnaryPredicates.undefined(gl) == 1)
			return null;
		try {
			if (gl.getRints().length() > 1)
				return null;
			BigDecimal center = new BigDecimal ((gl.getRints().getElement(0).getPos1().doubleValue()+gl.getRints().getElement(0).getPos2().doubleValue())/2);
			
			gp = new GPoint (gl.getNid(), gl.getRints().getElement(0).getRid(), center, gl.getRints().getElement(0).getSide());
		} catch (SQLException e) { e.printStackTrace();	}
		
		return gp;
	}
	public static GPoint center (GLine_V gl) {
		GPoint gp = null;
		if (UnaryPredicates.undefined(gl) == 1)
			return null;
		try {
			if (gl.getRints().length() > 1)
				return null;
			BigDecimal center = new BigDecimal ((gl.getRints().getElement(0).getPos1().doubleValue()+gl.getRints().getElement(0).getPos2().doubleValue())/2);
			
			gp = new GPoint (gl.getNid(), gl.getRints().getElement(0).getRid(), center, gl.getRints().getElement(0).getSide());
		} catch (SQLException e) { e.printStackTrace();	}
		
		return gp;
	}
	
	public static GPoint single (GLine_V gl) {
		GPoint gp = null;
		if (UnaryPredicates.undefined(gl) == 1)
			return null;
		try {
			if (gl.getRints().length() > 1)
				return null;
			if (gl.getRints().getElement(0).getPos1().intValue() != gl.getRints().getElement(0).getPos2().intValue())
				return null;
			
			gp = new GPoint (gl.getNid(), gl.getRints().getElement(0).getRid(), 
					gl.getRints().getElement(0).getPos1(), gl.getRints().getElement(0).getSide());
		} catch (SQLException e) { e.printStackTrace();	}
		
		return gp;
	}
	
	public static BigDecimal mean (Range_V r) {
		double temp = 0;
		if (r == null)
			return null;
		try {
			for (int i=0; i<r.getIntvs().length(); i++) {
				temp += r.getIntvs().getElement(i).getE().doubleValue();
				temp -= r.getIntvs().getElement(i).getS().doubleValue();
			}
			return new BigDecimal(temp/(2*r.getIntvs().length()));
		} catch (SQLException e) { e.printStackTrace();	}
		
		return null;
	}
}
