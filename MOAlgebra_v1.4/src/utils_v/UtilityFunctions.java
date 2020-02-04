package utils_v;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import algebraTypes_v.GInt_V;
import algebraTypes_v.GLine_V;
import algebraTypes_v.GReal_V;
import algebraTypes_v.Interval_V;
import algebraTypes_v.MGPoint_V;
import algebraTypes_v.MReal_V;
import algebraTypes_v.Range_V;
import algebraTypes_v.Section;
import algebraTypes_v.UGInt;
import algebraTypes_v.UGPoint;
import algebraTypes_v.UGReal;
import algebraTypes_v.UReal;

/*
 * This class implements the some utility functions needed for the implementation of the base functions
 */
public class UtilityFunctions {

	/* This function retuns the time ordered unit sequence of a MGPoint_V
	 * given the fact that the type MGPoint_V is stored (see Oracle data types) 
	 * as a nested table of ugpoint units
	 * 
	 * The nested tables do not maintain an order as for the varrays, but instead, 
	 * they do not have a limited and fixed size 
	 */
	public static SortedSet getTimeOrderedMGPoint (MGPoint_V mgp) {
		SortedSet<UGPoint> sortedMGPoint = new TreeSet (new Comparator () {
									public int compare (Object uga, Object ugb) {
										BigDecimal a = null, b = null;
										try {
											a = ((UGPoint)uga).getT1();	b = ((UGPoint)ugb).getT2();
										} catch (SQLException e) { e.printStackTrace(); } 
										return a.compareTo(b);
									}
								});
		try {
			for (int i=0; i<mgp.getUnits().length(); i++) {
				sortedMGPoint.add(mgp.getUnits().getElement(i));
			}
		} catch (SQLException e) { e.printStackTrace();	}
		
		return sortedMGPoint;
	}
	
	
	/* This function retuns the ordered (after rid, side and pos1) unit sequence of a MGPoint_V
	 * given the fact that the type MGPoint_V is stored (see Oracle data types) 
	 * as a nested table of ugpoint units
	 * 
	 * The nested tables do not maintain an order as for the varrays, but instead, 
	 * they do not have a limited and fixed size
	 * 
	 * Atention !!! This function can inverse the pos1 and pos2 
	 * to ensure that we always have pos1 <= pos2
	 */
	public static SortedSet getSpatialOrderedMGPoint (MGPoint_V mgp) {
		SortedSet<UGPoint> sortedMGPoint = new TreeSet (new Comparator () {
									public int compare (Object uga, Object ugb) {
										Integer ia = null, ib = null;
										BigDecimal ba = null, bb = null;
										try {
											ia = ((UGPoint)uga).getRid(); ib = ((UGPoint)ugb).getRid();
											if (ia.compareTo(ib) != 0)
												return ia.compareTo(ib);
											else {
												ia = ((UGPoint)uga).getSide(); ib = ((UGPoint)ugb).getSide();
												if (ia.compareTo(ib) != 0)
													return ia.compareTo(ib);
												else {
													ba = ((UGPoint)uga).getPos1();	bb = ((UGPoint)ugb).getPos1();
													return ba.compareTo(bb);
												}
											}
										} catch (SQLException e) { e.printStackTrace(); }
										
										return 0;
									}
								});
		try {
			for (int i=0; i<mgp.getUnits().length(); i++) {
				UGPoint temp = mgp.getUnits().getElement(i);
				if (temp.getPos1().compareTo(temp.getPos2()) > 0) {
					BigDecimal bdPos = new BigDecimal(temp.getPos1().doubleValue());
					BigDecimal bdT = new BigDecimal(temp.getT1().doubleValue());
					temp.setPos1(temp.getPos2());
					temp.setT1(temp.getT2());
					temp.setPos2(bdPos);
					temp.setT2(bdT);
				}
				sortedMGPoint.add(mgp.getUnits().getElement(i));
			/*	sortedMGPoint.add( new UGPoint(new Integer(mgp.getUnits().getElement(i).getNid().intValue()), 
						new Integer(mgp.getUnits().getElement(i).getRid().intValue()), 
						new Integer(mgp.getUnits().getElement(i).getSide().intValue()),
						new BigDecimal(mgp.getUnits().getElement(i).getT1().intValue()),
						new BigDecimal(mgp.getUnits().getElement(i).getT2().intValue()), 
						new BigDecimal(mgp.getUnits().getElement(i).getPos1().intValue()), 
						new BigDecimal(mgp.getUnits().getElement(i).getPos2().intValue()) )); */
			}
		} catch (SQLException e) { e.printStackTrace();	}
		
		return sortedMGPoint;
	}
	
	public static SortedSet getSpatialOrderedGReal (GReal_V gr) {
		SortedSet<UGReal> sortedMGPoint = new TreeSet (new Comparator () {
									public int compare (Object ugra, Object ugrb) {
										Integer ia = null, ib = null;
										BigDecimal ba = null, bb = null;
										try {
											ia = ((UGReal)ugra).getInterval().getRid(); ib = ((UGReal)ugrb).getInterval().getRid();
											if (ia.compareTo(ib) != 0)
												return ia.compareTo(ib);
											else {
												ia = ((UGReal)ugra).getInterval().getSide(); ib = ((UGReal)ugrb).getInterval().getSide();
												if (ia.compareTo(ib) != 0)
													return ia.compareTo(ib);
												else {
													ba = ((UGReal)ugra).getInterval().getPos1();	bb = ((UGReal)ugrb).getInterval().getPos1();
													return ba.compareTo(bb);
												}
											}
										} catch (SQLException e) { e.printStackTrace(); }
										
										return 0;
									}
								});
		try {
			for (int i=0; i<gr.getUnits().length(); i++) {
				Section temp = gr.getUnits().getElement(i).getInterval();
				if (temp.getPos1().compareTo(temp.getPos2()) > 0) {
					BigDecimal bdPos = new BigDecimal(temp.getPos1().doubleValue());
					temp.setPos1(temp.getPos2());
					temp.setPos2(bdPos);
				}
				sortedMGPoint.add(gr.getUnits().getElement(i));
			}
		} catch (SQLException e) { e.printStackTrace();	}
		
		return sortedMGPoint;
	}
	
	public static SortedSet getSpatialOrderedGInt (GInt_V gi) {
		SortedSet<UGInt> sortedMGPoint = new TreeSet (new Comparator () {
									public int compare (Object ugia, Object ugib) {
										Integer ia = null, ib = null;
										BigDecimal ba = null, bb = null;
										try {
											ia = ((UGInt)ugia).getInterval().getRid(); ib = ((UGInt)ugib).getInterval().getRid();
											if (ia.compareTo(ib) != 0)
												return ia.compareTo(ib);
											else {
												ia = ((UGInt)ugia).getInterval().getSide(); ib = ((UGInt)ugib).getInterval().getSide();
												if (ia.compareTo(ib) != 0)
													return ia.compareTo(ib);
												else {
													ba = ((UGInt)ugia).getInterval().getPos1();	bb = ((UGInt)ugib).getInterval().getPos1();
													return ba.compareTo(bb);
												}
											}
										} catch (SQLException e) { e.printStackTrace(); }
										
										return 0;
									}
								});
		try {
			for (int i=0; i<gi.getUnits().length(); i++) {
				Section temp = gi.getUnits().getElement(i).getInterval();
				if (temp.getPos1().compareTo(temp.getPos2()) > 0) {
					BigDecimal bdPos = new BigDecimal(temp.getPos1().doubleValue());
					temp.setPos1(temp.getPos2());
					temp.setPos2(bdPos);
				}
				sortedMGPoint.add(gi.getUnits().getElement(i));
			}
		} catch (SQLException e) { e.printStackTrace();	}
		
		return sortedMGPoint;
	}

	// Sequence ordered GReal_V
	public static SortedSet getSequenceOrderedGReal (GReal_V gr) {
		SortedSet<UGReal> sortedMGPoint = new TreeSet (new Comparator () {
									public int compare (Object ugra, Object ugrb) {
										Integer ia = null, ib = null;
										try {
											ia = ((UGReal)ugra).getSequence(); ib = ((UGReal)ugrb).getSequence();
											return ia.compareTo(ib);
										} catch (SQLException e) { e.printStackTrace(); }
										return 0;
									}
								});
		try {
			for (int i=0; i<gr.getUnits().length(); i++) {
				if (gr.getUnits().getElement(i).getInterval().getPos1().doubleValue() 
							!= gr.getUnits().getElement(i).getInterval().getPos2().doubleValue())
				sortedMGPoint.add(gr.getUnits().getElement(i));
			}
		} catch (SQLException e) { e.printStackTrace();	}
		
		return sortedMGPoint;
	}
	
	// TODO: Sequence ordered GInt_V
	
	
	public static SortedSet getOrderedRange (Range_V r) {
		SortedSet<Interval_V> sortedRange = new TreeSet (new Comparator () {
									public int compare (Object inta, Object intb) {
										BigDecimal a = null, b = null;
										try {
											a = ((Interval_V)inta).getS();	b = ((Interval_V)intb).getS();
										} catch (SQLException e) { e.printStackTrace(); } 
										return a.compareTo(b);
									}
								});
		try {
			for (int i=0; i<r.getIntvs().length(); i++) {
				sortedRange.add(r.getIntvs().getElement(i));
			}
		} catch (SQLException e) { e.printStackTrace();	}
		
		return sortedRange;
	}
	
	public static SortedSet getSpatialOrderedGLine (GLine_V gl) {
		SortedSet<Section> sortedGLine = new TreeSet (new Comparator () {
									public int compare (Object seca, Object secb) {
										Integer ia = null, ib = null;
										BigDecimal ba = null, bb = null;
										try {
											ia = ((Section)seca).getRid(); ib = ((Section)secb).getRid();
											if (ia.compareTo(ib) != 0)
												return ia.compareTo(ib);
											else {
												ia = ((Section)seca).getSide(); ib = ((Section)secb).getSide();
												if (ia.compareTo(ib) != 0)
													return ia.compareTo(ib);
												else {
													ba = ((Section)seca).getPos1();	bb = ((Section)secb).getPos1();
													return ba.compareTo(bb);
												}
											}
										} catch (SQLException e) { e.printStackTrace(); }
										
										return 0;
									}
								});
		try {
			for (int i=0; i<gl.getRints().length(); i++) {
				Section temp = gl.getRints().getElement(i);
				if (temp.getPos1().compareTo(temp.getPos2()) > 0) {
					BigDecimal bdPos = new BigDecimal(temp.getPos1().doubleValue());
					temp.setPos1(temp.getPos2());
					temp.setPos2(bdPos);
				}
				sortedGLine.add(gl.getRints().getElement(i));
			}
		} catch (SQLException e) { e.printStackTrace();	}
		
		return sortedGLine;
	}
	
	public static SortedSet getTimeOrderedMReal(MReal_V mr) {

		SortedSet<UReal> sortedMGPoint = new TreeSet(new Comparator() {
			public int compare(Object ura, Object urb) {
				BigDecimal BDa_t1 = null, BDa_t2 = null, BDb_t1 = null, BDb_t2 = null;
				try {
					BDa_t1 = ((UReal) ura).getT1();
					BDb_t1 = ((UReal) urb).getT1();
					BDa_t2 = ((UReal) ura).getT2();
					BDb_t2 = ((UReal) urb).getT2();
					// ib = ((UGReal) ugrb).getSequence();

				} catch (SQLException e) {
					e.printStackTrace();
				}
				return BDa_t1.compareTo(BDb_t1);
			}
		});

		try {
			for (int i = 0; i < mr.getUnits().length(); i++) {
				sortedMGPoint.add(mr.getUnits().getElement(i));
				//sortedMGPoint.add(mr.getUnits().getElement(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sortedMGPoint;

	}
}
