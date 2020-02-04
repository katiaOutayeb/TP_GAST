package nonTemporalOperations_v;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;

import utils_v.UtilityFunctions;

import algebraTypes_v.GLine_V;
import algebraTypes_v.GPoint;
import algebraTypes_v.Interval_V;
import algebraTypes_v.Intervals_V;
import algebraTypes_v.Range_V;
import algebraTypes_v.Section;
import algebraTypes_v.Sections_V;
import algebraTypes_v.UGPoint;

/*
 * This class implements the set operations (point/point, point/point_set and point_set/point_set)
 * for gpoint and GLine_V
 * Subject to lifting
 */
public class SetOperations {

	// point/point
	
	public static GPoint intersection (GPoint gp1, GPoint gp2) {
		if (BinaryPredicates.gequals (gp1, gp2) == 1)
			return gp1;
		
		return null;
	}
	
	public static GPoint minus (GPoint gp1, GPoint gp2) {
		if (BinaryPredicates.gequals (gp1, gp2) == 0)
			return gp1;
		
		return null;
	}
	
	// TODO Lifted operations
	
	
	// point/point_set
	
	public static GPoint intersection (GPoint gp, GLine_V gl) {
		if (BinaryPredicates.inside(gp, gl) == 1)
			return gp;
		
		return null;
	}
	
	public static GLine_V union (GPoint gp, GLine_V gl) {
		return gl;
	}
	
	public static GPoint minus (GPoint gp, GLine_V gl) {
		if (BinaryPredicates.inside(gp, gl) == 1)
			return null;
		
		return gp;
	}
	
	public static GLine_V minus (GLine_V gl, GPoint gp) {
		return gl;
	}
	
	// TODO Lifted operations
	
	
	
	// point_set/point_set
	public static Range_V intersection (Range_V r1, Range_V r2) {
		if (r1 == null || r2 == null)
			return null;
		
		// TODO Range Type is int
		
		boolean flag1, flag2;
		Interval_V temp1 = null, temp2 = null;
		
		ArrayList<Interval_V> result = new ArrayList<Interval_V>();
		ArrayList<Interval_V> compactedResult = new ArrayList<Interval_V>();
		SortedSet<Interval_V> sr1 = UtilityFunctions.getOrderedRange(r1);
		SortedSet<Interval_V> sr2 = UtilityFunctions.getOrderedRange(r2);
		Iterator it1 = sr1.iterator();
		Iterator it2 = sr2.iterator();
		
		flag1 = true; flag2 = true;
		try {
			while (it1.hasNext() && it2.hasNext()) {
				if (flag1) {
					temp1 = (Interval_V) it1.next();
					flag1 = false;
				}
				if (flag2) {
					temp2 = (Interval_V) it2.next();
					flag2 = false;
				}
				
				// no intersection
				if (temp1.getE().doubleValue() < temp2.getS().doubleValue()) {
					flag1 = true;
					continue;
				} 
				if (temp1.getS().doubleValue() > temp2.getE().doubleValue()) {
					flag2 = true;
					continue;
				}
				
				// intersection
				if (temp1.getE().doubleValue() >= temp2.getS().doubleValue() && 
								temp1.getE().doubleValue() <= temp2.getE().doubleValue() && 
								temp1.getS().doubleValue() <= temp2.getS().doubleValue()) {
					result.add(new Interval_V(new BigDecimal(temp2.getS().doubleValue()), 
												new BigDecimal(temp1.getE().doubleValue()),
												new BigDecimal(1),
												new BigDecimal(1) ));
					
					flag1 = true;
					continue;
				}
				if (temp2.getS().doubleValue() >= temp1.getS().doubleValue() && 
								temp2.getE().doubleValue() <= temp1.getE().doubleValue()) {
					result.add(new Interval_V(new BigDecimal(temp2.getS().doubleValue()), 
												new BigDecimal(temp2.getE().doubleValue()),
												new BigDecimal(1),
												new BigDecimal(1) ));
					
					flag2 = true;
					continue;
				}
				if (temp1.getS().doubleValue() >= temp2.getS().doubleValue() && 
								temp1.getS().doubleValue() <= temp2.getE().doubleValue() && 
								temp1.getE().doubleValue() >= temp2.getE().doubleValue()) {
					result.add(new Interval_V(new BigDecimal(temp1.getS().doubleValue()), 
												new BigDecimal(temp2.getE().doubleValue()),
												new BigDecimal(1),
												new BigDecimal(1) ));
					
					flag2 = true;
					continue;
				}
				if (temp1.getS().doubleValue() >= temp2.getS().doubleValue() && 
								temp1.getE().doubleValue() <= temp2.getE().doubleValue()) {
					result.add(new Interval_V(new BigDecimal(temp1.getS().doubleValue()), 
												new BigDecimal(temp1.getE().doubleValue()),
												new BigDecimal(1),
												new BigDecimal(1) ));
					
					flag1 = true;
					continue;
				}
			}
			
			if (result.size() == 0)
				return null;
			
			// compact results
			double s = result.get(0).getS().doubleValue(), e = result.get(0).getE().doubleValue();
			for (int i=1; i<result.size(); i++) {
				if (result.get(i).getS().doubleValue() == e)
					e = result.get(i).getE().doubleValue();
				else {
					compactedResult.add(new Interval_V(new BigDecimal(s), new BigDecimal(e),
												new BigDecimal(1), new BigDecimal(1) ));
					s = result.get(i).getS().doubleValue(); 
					e = result.get(i).getE().doubleValue();
				}
			}
			compactedResult.add(new Interval_V(new BigDecimal(s), new BigDecimal(e),
												new BigDecimal(1), new BigDecimal(1) ));
			
			Interval_V [] intvs = new Interval_V[compactedResult.size()];
			for (int i=0; i<compactedResult.size(); i++)
				intvs[i] = compactedResult.get(i);
			return new Range_V(new BigDecimal(2), new Intervals_V(intvs));
			
		}catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static GLine_V intersection (GLine_V gl1, GLine_V gl2) {
		if (gl1 == null || gl2 == null)
			return null;
		
		// TODO Range Type is int
		
		boolean flag1, flag2;
		Section temp1 = null, temp2 = null;
		
		ArrayList<Section> result = new ArrayList<Section>();
		ArrayList<Section> compactedResult = new ArrayList<Section>();
		SortedSet<Section> sgl1 = UtilityFunctions.getSpatialOrderedGLine(gl1);
		SortedSet<Section> sgl2 = UtilityFunctions.getSpatialOrderedGLine(gl2);
		Iterator it1 = sgl1.iterator();
		Iterator it2 = sgl2.iterator();
		
		flag1 = true; flag2 = true;
		try {
		//	int c1 = 0, c2 = 0;
			while (it1.hasNext() && it2.hasNext()) {
			//	System.out.println("c1 = " + c1 + "    c2 = " + c2);
				
				if (flag1) {
				//	c1++;
					temp1 = (Section) it1.next();
					flag1 = false;
				}
				if (flag2) {
				//	c2++;
					temp2 = (Section) it2.next();
					flag2 = false;
				}
				
			//	System.out.println("rid1 = " + temp1.getRid() + "    rid2 = " + temp2.getRid());
			//	System.out.println("pos11 = " + temp1.getPos1() + "    pos12 = " + temp1.getPos2());
			//	System.out.println("pos21 = " + temp2.getPos1() + "    pos22 = " + temp2.getPos2());
				
				if (temp1.getRid().intValue() != temp2.getRid().intValue()) {
					if (temp1.getRid().intValue() < temp2.getRid().intValue()) {
					//	c1++;
						flag1 = true;
						continue;
					}
					else {
					//	c2++;
						flag2 = true;
						continue;
					}
				}
				else if (temp1.getSide().intValue() != temp2.getSide().intValue()) {
					if (temp1.getSide().intValue() < temp2.getSide().intValue()) {
					//	c1++;
						flag1 = true;
						continue;
					}
					else {
					//	c2++;
						flag2 = true;
						continue;
					}
				} 
				else {
					// no intersection
				//	System.out.println("TEST 0 " + (temp1.getPos1().doubleValue() - temp2.getPos2().doubleValue()));
					if (temp1.getPos2().doubleValue() < temp2.getPos1().doubleValue()) {
					//	System.out.println("TEST 1");
					//	c1++;
						flag1 = true;
						continue;
					} 
					if (temp1.getPos1().doubleValue() > temp2.getPos2().doubleValue()) {
					//	System.out.println("TEST 2");
					//	c2++;
						flag2 = true;
						continue;
					}
					
					// intersection
					if (temp1.getPos2().doubleValue() >= temp2.getPos1().doubleValue() && 
									temp1.getPos2().doubleValue() <= temp2.getPos2().doubleValue() && 
									temp1.getPos1().doubleValue() <= temp2.getPos1().doubleValue()) {
						result.add(new Section(new Integer(temp1.getRid().intValue()), 
													new Integer(temp1.getSide().intValue()), 
													new BigDecimal(temp2.getPos1().doubleValue()), 
													new BigDecimal(temp1.getPos2().doubleValue()) ));
					//	c1++;
						flag1 = true;
						continue;
					}
					if (temp2.getPos1().doubleValue() >= temp1.getPos1().doubleValue() && 
									temp2.getPos2().doubleValue() <= temp1.getPos2().doubleValue()) {
						result.add(new Section(new Integer(temp1.getRid().intValue()), 
													new Integer(temp1.getSide().intValue()),
													new BigDecimal(temp2.getPos1().doubleValue()), 
													new BigDecimal(temp2.getPos2().doubleValue()) ));
					//	c2++;
						flag2 = true;
						continue;
					}
					if (temp1.getPos1().doubleValue() >= temp2.getPos1().doubleValue() && 
									temp1.getPos1().doubleValue() <= temp2.getPos2().doubleValue() && 
									temp1.getPos2().doubleValue() >= temp2.getPos2().doubleValue()) {
						result.add(new Section(new Integer(temp1.getRid().intValue()), 
													new Integer(temp1.getSide().intValue()),
													new BigDecimal(temp1.getPos1().doubleValue()), 
													new BigDecimal(temp2.getPos2().doubleValue()) ));
					//	c2++;
						flag2 = true;
						continue;
					}
					if (temp1.getPos1().doubleValue() >= temp2.getPos1().doubleValue() && 
									temp1.getPos2().doubleValue() <= temp2.getPos2().doubleValue()) {
						result.add(new Section(new Integer(temp1.getRid().intValue()), 
													new Integer(temp1.getSide().intValue()),
													new BigDecimal(temp1.getPos1().doubleValue()), 
													new BigDecimal(temp1.getPos2().doubleValue()) ));
					//	c1++;
						flag1 = true;
						continue;
					}
				}
			}
			
			if (result.size() == 0)
				return null;
			
			// compact results
			double s = result.get(0).getPos1().doubleValue(), e = result.get(0).getPos2().doubleValue();
			for (int i=1; i<result.size(); i++) {
				if (result.get(i).getPos1().doubleValue() == e)
					e = result.get(i).getPos2().doubleValue();
				else {
					compactedResult.add(new Section(new Integer(temp1.getRid().intValue()), 
													new Integer(temp1.getSide().intValue()),
													new BigDecimal(s), new BigDecimal(e) ));
					s = result.get(i).getPos1().doubleValue(); 
					e = result.get(i).getPos2().doubleValue();
				}
			}
			compactedResult.add(new Section(new Integer(temp1.getRid().intValue()), 
												new Integer(temp1.getSide().intValue()),
												new BigDecimal(s), new BigDecimal(e) ));
			
			Section [] rints = new Section[compactedResult.size()];
			for (int i=0; i<compactedResult.size(); i++)
				rints[i] = compactedResult.get(i);
			return new GLine_V(new Integer(0), new Sections_V(rints));
			
		}catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	// TODO Lifted operations
}
