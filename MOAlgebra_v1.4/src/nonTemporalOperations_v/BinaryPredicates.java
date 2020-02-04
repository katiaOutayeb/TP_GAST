package nonTemporalOperations_v;

import java.sql.SQLException;

import algebraTypes_v.GLine_V;
import algebraTypes_v.GPoint;
import algebraTypes_v.Section;

/*
 * This class implements the unary predicates for gpoint and GLine_V
 * Subject to lifting
 */
public class BinaryPredicates {

	// determine if two gpoints are equal
	public static int gequals (GPoint gp1, GPoint gp2) {
		if (gp1 == null || gp2 == null)
			return 0;
		try {
			if ( (gp1.getNid().intValue() == gp2.getNid().intValue()) 
					&& (gp1.getRid().intValue() == gp2.getRid().intValue())
					&& (gp1.getPos().intValue() == gp2.getPos().intValue())
					&& (gp1.getSide().intValue() == gp2.getSide().intValue()) )
				return 1;
		} catch (SQLException e) { e.printStackTrace();	}
		
		return 0;
	}
	
	// determine if a gpoint is inside a GLine_V
	public static int inside (GPoint gp, GLine_V gl) {
		Section s;
		
		if (gp == null || gl == null)
			return 0;
		try {
			if (gp.getNid().intValue() != gl.getNid().intValue())
				return 0;
			
			for (int i=0; i<gl.getRints().length(); i++) {
				s = gl.getRints().getElement(i);
				if ( (gp.getRid().intValue() == s.getRid().intValue()) 
						&& (gp.getSide().intValue() >= s.getSide().intValue())
						&& (gp.getPos().intValue() >= s.getPos1().intValue())
						&& (gp.getPos().intValue() <= s.getPos2().intValue()) )
					return 1;
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return 0;
	}
	
	// determine if the gline2 is inside the gline1
	public static int inside (GLine_V gl1, GLine_V gl2) {
		Section s1, s2;
		boolean inside;
		
		if (gl1 == null || gl2 == null)
			return 0;
		try {
			if (gl1.getNid().intValue() != gl2.getNid().intValue())
				return 0;
			
			for (int i=0; i<gl2.getRints().length(); i++) {
				s2 = gl2.getRints().getElement(i);
				inside = false;
				for (int j=0; j<gl1.getRints().length(); j++) {
					s1 = gl1.getRints().getElement(j);
					if ( (s2.getRid().intValue() == s1.getRid().intValue())
							&& (s2.getSide().intValue() == s1.getSide().intValue()) 
							&& (s2.getPos1().intValue() >= s1.getPos1().intValue()) && (s2.getPos1().intValue() <= s1.getPos2().intValue()) 
							&& (s2.getPos2().intValue() >= s1.getPos1().intValue()) && (s2.getPos2().intValue() <= s1.getPos2().intValue()) )
					{
						inside = true;
						break;
					}
				}
				if ( ! inside )
					return 0;
			}
		} catch (SQLException e) { e.printStackTrace();	}
		
		return 1;
	}
	
	// determine if two glines intersects
	/* ! The glines must be on the same side of the road to intersect
	 * 
	 * ! LIMITATION: we do not consider the case when of the intersection in a jonction point
	 */
	public static int intersects (GLine_V gl1, GLine_V gl2) {
		Section s1, s2;
		if (gl1 == null || gl2 == null)
			return 0;
		try {
			if (gl1.getNid().intValue() != gl2.getNid().intValue())
				return 0;
			
			for (int i=0; i<gl1.getRints().length(); i++) {
				s1 = gl1.getRints().getElement(i);
				for (int j=0; j<gl2.getRints().length(); j++) {
					s2 = gl2.getRints().getElement(j);
					if ( (s1.getRid().intValue() == s2.getRid().intValue()) && (s1.getSide().intValue() == s2.getSide().intValue()) )
					{	
						if ((s1.getPos1().intValue() >= s2.getPos1().intValue()) &&
								s1.getPos1().intValue() <= s2.getPos2().intValue())
							return 1;
						if ( (s2.getPos1().intValue() >= s1.getPos1().intValue())
								&& (s2.getPos1().intValue() <= s1.getPos2().intValue()) )
							return 1;
					}
				}
			}
		} catch (SQLException e) { e.printStackTrace();	}
		
		return 0;
	}
	
	// TODO Lifted operations
}


