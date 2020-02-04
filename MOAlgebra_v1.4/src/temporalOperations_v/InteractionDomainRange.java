package temporalOperations_v;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;

import utils_v.UtilityFunctions;

import algebraTypes_v.GInt_V;
import algebraTypes_v.GLine_V;
import algebraTypes_v.GPoint;
import algebraTypes_v.GReal_V;
import algebraTypes_v.InGPoint;
import algebraTypes_v.InTime;
import algebraTypes_v.MGPoint_V;
import algebraTypes_v.MInt_V;
import algebraTypes_v.MReal_V;
import algebraTypes_v.Range_V;
import algebraTypes_v.Section;
import algebraTypes_v.UGInt;
import algebraTypes_v.UGInt_V;
import algebraTypes_v.UGPoint;
import algebraTypes_v.UGPoint_V;
import algebraTypes_v.UGReal;
import algebraTypes_v.UGReal_V;
import algebraTypes_v.UInt;
import algebraTypes_v.UInt_V;
import algebraTypes_v.UReal;

/*
 * This class implements the interaction with domain and Range_V operations for gpoint and GLine_V, and gmoving types
 */
public class InteractionDomainRange {

	public static InTime atInstant (MGPoint_V mgp, BigDecimal t) {
		if (mgp == null)
			return null;
		BigDecimal val = new BigDecimal(0);
		UGPoint ugp;
		try {
		//	System.out.println("size = " + mgp.getUnits().length());
			for (int i=0; i<mgp.getUnits().length(); i++) {
				ugp = mgp.getUnits().getElement(i);
				if ((ugp.getT1().doubleValue()<=t.doubleValue()) && (ugp.getT2().doubleValue()>=t.doubleValue())) {
				/*	System.out.println("i = " + i);
					System.out.println("t1 = " + ugp.getT1().doubleValue());
					System.out.println("t2 = " + ugp.getT2().doubleValue());
					System.out.println("t = " + t.doubleValue()); */
					
					double p2p1 = ugp.getPos2().doubleValue()-ugp.getPos1().doubleValue();
					double t2t1 = ugp.getT2().doubleValue()-ugp.getT1().doubleValue();
					if (t2t1 != 0) {
						double a = p2p1/t2t1;
						double b = (ugp.getPos1().doubleValue()*ugp.getT2().doubleValue() - ugp.getPos2().doubleValue()*ugp.getT1().doubleValue()) / t2t1;
						BigDecimal pos = new BigDecimal(a*t.doubleValue() + b);
						
						return new InTime (new GPoint(ugp.getNid(), new Integer(ugp.getRid().intValue()), pos, ugp.getSide()), val, t);
					}
					else 
						return new InTime (new GPoint(ugp.getNid(), new Integer(ugp.getRid().intValue()), ugp.getPos1(), ugp.getSide()), val, t);
				} 
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static InTime atInstant (MReal_V mr, BigDecimal t) {
		if (mr == null)
			return null;
		BigDecimal val = new BigDecimal(0);
		try {
			for (int i=0; i<mr.getUnits().length(); i++) {
				UReal ur = mr.getUnits().getElement(i);
				if ((ur.getT1().compareTo(t) <= 0) && (ur.getT2().compareTo(t) >= 0)) {
					
					val = new BigDecimal(ur.getB().doubleValue()*t.doubleValue() + ur.getC().doubleValue());
					return new InTime (new GPoint(new Integer(0), new Integer(0), new BigDecimal(0), new Integer(0)), 
															val, t);
				}
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	// TODO:
	public static MGPoint_V atperiods (MGPoint_V mgp, Range_V rng) {
		return null;
	}
	
	public static InTime initial (MGPoint_V mgp) {
		if (mgp == null)
			return null;
		BigDecimal val = new BigDecimal(0);
		SortedSet sortedMGPoint = UtilityFunctions.getTimeOrderedMGPoint(mgp);
		Iterator it = sortedMGPoint.iterator();
		UGPoint ugp = (UGPoint) it.next();
		try {
			return new InTime(new GPoint(ugp.getNid(), ugp.getRid(), ugp.getPos1(), ugp.getSide()), val, ugp.getT1());
		} catch (SQLException e1) {	e1.printStackTrace(); }
		
		return null;
	}
	
	public static InGPoint initialPos (GReal_V gr) {
		if (gr == null)
			return null;
		try {
			double a, b, c, pos;
			a = gr.getUnits().getElement(0).getA().doubleValue();
			b = gr.getUnits().getElement(0).getB().doubleValue();
			c = gr.getUnits().getElement(0).getC().doubleValue();
			pos = gr.getUnits().getElement(0).getInterval().getPos1().doubleValue();
			double val = a*Math.pow(pos, 2) + b*pos +c;
			return new InGPoint(new BigDecimal(val),
					new GPoint(gr.getUnits().getElement(0).getNid(), 
										gr.getUnits().getElement(0).getInterval().getRid(), 
										gr.getUnits().getElement(0).getInterval().getPos1(), 
										gr.getUnits().getElement(0).getInterval().getSide()));
		} catch (SQLException e1) {	e1.printStackTrace(); }
		
		return null;
	}
	
	public static InTime final_ (MGPoint_V mgp) {
		if (mgp == null)
			return null;
		BigDecimal val = new BigDecimal(0);
		UGPoint ugp = null;
		SortedSet sortedMGPoint = UtilityFunctions.getTimeOrderedMGPoint(mgp);
		Iterator it = sortedMGPoint.iterator();
		while (it.hasNext()) {
			ugp = (UGPoint) it.next();
		}
		try {
			return new InTime(new GPoint(ugp.getNid(), ugp.getRid(), ugp.getPos2(), ugp.getSide()), val, ugp.getT2());
		} catch (SQLException e1) {	e1.printStackTrace(); }
		
		return null;
	}
	
	/* Is it defined at the given time/period ?
	 * 1 - true, 0 - false
	 */
	public static int present (MGPoint_V mgp, BigDecimal t) {
		if (mgp == null)
			return 0;
		try {
			for (int i=0; i<mgp.getUnits().length(); i++) {
				UGPoint ugp = mgp.getUnits().getElement(i);
				if ((ugp.getT1().compareTo(t) <= 0) && (ugp.getT2().compareTo(t) >= 0)) {
					return 1;
				}
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return 0;
	}
	
	// TODO:
	public static int present (MGPoint_V mgp, Range_V rng) {
		return 0;
	}
	
	// Güting semantics, i.e., MGPoint_V is not injective: it can pass several time by the same location
	public static MGPoint_V at (MGPoint_V mgp, GPoint gp) {
		if (mgp == null)
			return null;
		try {
			if (! mgp.getUnits().getElement(0).getNid().equals(gp.getNid()))
				return null;
			
			UGPoint ugps[] = new UGPoint[mgp.getUnits().length()];
			int counter = 0;
			
			for (int i=0; i<mgp.getUnits().length(); i++) {
				UGPoint ugp = mgp.getUnits().getElement(i);
				if ( ugp.getRid().equals(gp.getRid()) && ugp.getSide().equals(gp.getSide()) && 
						( (ugp.getPos1().compareTo(gp.getPos()) <= 0) && (ugp.getPos2().compareTo(gp.getPos()) >= 0) 
								|| (ugp.getPos1().compareTo(gp.getPos()) >= 0) && (ugp.getPos2().compareTo(gp.getPos()) <= 0) ) ) {
					BigDecimal p2p1 = ugp.getPos2().subtract(ugp.getPos1());
					BigDecimal t2t1 = ugp.getT2().subtract(ugp.getT1());
					if (t2t1.compareTo(new BigDecimal(0)) != 0) {
						double a = p2p1.doubleValue()/t2t1.doubleValue();
						double b = (ugp.getPos1().doubleValue()*ugp.getT2().doubleValue() - ugp.getPos2().doubleValue()*ugp.getT1().doubleValue()) / t2t1.doubleValue();
						BigDecimal t = new BigDecimal((gp.getPos().doubleValue()-b) / a);
						
						ugps[counter++] = new UGPoint(ugp.getNid(), ugp.getRid(), ugp.getSide(), t, t, gp.getPos(), gp.getPos());
					}
					else {
						ugps[counter++] = new UGPoint(ugp.getNid(), ugp.getRid(), ugp.getSide(), ugp.getT1(), ugp.getT2(), gp.getPos(), gp.getPos());
					}
				}
			}
			
			if (counter > 0) {
				UGPoint tempUgps[] = new UGPoint[counter];
				for (int i=0; i<counter; i++)
					tempUgps[i] = ugps[i];
				ugps = tempUgps;
				
				return new MGPoint_V(new UGPoint_V(ugps));
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}
	
	public static int passes (MGPoint_V mgp, GPoint gp) {
		if (mgp == null)
			return 0;
		try {
			if (! mgp.getUnits().getElement(0).getNid().equals(gp.getNid()))
				return 0;
			for (int i=0; i<mgp.getUnits().length(); i++) {
				UGPoint ugp = mgp.getUnits().getElement(i);
				if (ugp.getRid().equals(gp.getRid()) && ugp.getSide().equals(gp.getSide()) && 
						(ugp.getPos1().compareTo(gp.getPos()) <= 0) && (ugp.getPos2().compareTo(gp.getPos()) >= 0)) {
					return 1;
				}
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return 0;
	}
	
	// TODO: Override all the above operations for MGLine type
	
	
	
	// TODO: Operations for extended algebra types
	
	
	public static InGPoint atPos (GReal_V gr, GPoint gp) {
		if (gr == null || gp == null)
			return null;
		
		try {
			for (int i=0; i<gr.getUnits().length(); i++) {
				UGReal ugp = gr.getUnits().getElement(i);
				Section s = ugp.getInterval();
				
				if(s.getRid().equals(gp.getRid()) && s.getSide().equals(gp.getSide()) && 
						((s.getPos1().compareTo(s.getPos2()) < 0 && (s.getPos1().compareTo(gp.getPos()) <= 0) && (s.getPos2().compareTo(gp.getPos()) >= 0)) 
								|| (s.getPos1().compareTo(s.getPos2()) >= 0 && 
										(s.getPos1().compareTo(gp.getPos()) >= 0) && (s.getPos2().compareTo(gp.getPos()) <= 0))) ) {
					
					BigDecimal val = new BigDecimal(ugp.getA().doubleValue()*gp.getPos().doubleValue()*gp.getPos().doubleValue() 
							+ ugp.getB().doubleValue()*gp.getPos().doubleValue() + ugp.getC().doubleValue());
					
					return new InGPoint (val, gp);
				}
			}
		} catch (SQLException e) { e.printStackTrace();	}
		return null;
	}
	
	public static InGPoint atPos (GInt_V gi, GPoint gp) {
		if (gi == null || gp == null)
			return null;
		
		try {
			for (int i=0; i<gi.getUnits().length(); i++) {
				UGInt ugi = gi.getUnits().getElement(i);
				Section s = ugi.getInterval();
				
				if(s.getRid().equals(gp.getRid()) && s.getSide().equals(gp.getSide()) && 
						((s.getPos1().compareTo(s.getPos2()) < 0 && (s.getPos1().compareTo(gp.getPos()) <= 0) && (s.getPos2().compareTo(gp.getPos()) >= 0)) 
								|| (s.getPos1().compareTo(s.getPos2()) >= 0 && 
										(s.getPos1().compareTo(gp.getPos()) >= 0) && (s.getPos2().compareTo(gp.getPos()) <= 0))) ) {
					
					int val = ugi.getVal().intValue();
					
					return new InGPoint (new BigDecimal(val), gp);
				}
			}
		} catch (SQLException e) { e.printStackTrace();	}
		
		return null;
	}
	
	/*	public static InGPoint atPos (GReal_V gr, GPoint gp) {
			if (gr == null || gp == null)
				return null;
			BigDecimal val = new BigDecimal(0);
			try {
				for (int i=0; i<gr.getUnits().length(); i++) {
					UGReal ugr = gr.getUnits().getElement(i);
					if (ugr.getInterval().getRid().compareTo(gp.getRid()) == 0 &&
							ugr.getInterval().getSide().compareTo(gp.getSide()) == 0 &&
							(ugr.getInterval().getPos1().compareTo(gp.getPos()) <= 0) && 
							(ugr.getInterval().getPos2().compareTo(gp.getPos()) >= 0)) {
						
						val = new BigDecimal(ugr.getB().doubleValue()*gp.getPos().doubleValue() + ugr.getC().doubleValue());
						return new InGPoint (val, gp);
					}
				}
			} catch (SQLException e) { e.printStackTrace(); }
			
			return null;
		}
		
		public static InGPoint atPos (GInt_V gi, GPoint gp) {
			if (gi == null || gp == null)
				return null;
			try {
				for (int i=0; i<gi.getUnits().length(); i++) {
					UGInt ugi = gi.getUnits().getElement(i);
					if (ugi.getInterval().getRid().compareTo(gp.getRid()) == 0 &&
							ugi.getInterval().getSide().compareTo(gp.getSide()) == 0 &&
							(ugi.getInterval().getPos1().compareTo(gp.getPos()) <= 0) && 
							(ugi.getInterval().getPos2().compareTo(gp.getPos()) >= 0)) {
						
						return new InGPoint (new BigDecimal(ugi.getVal().intValue()), gp);
					}
				}
			} catch (SQLException e) { e.printStackTrace(); }
			
			return null;
		} */
	
	public static int passes (GReal_V gr, BigDecimal val) {
		if (gr == null || val == null)
			return 0;
		
		try {
			for (int i=0; i<gr.getUnits().length(); i++) {
				UGReal ugr = gr.getUnits().getElement(i);
				double a, b, c, x1, x2;
				a = ugr.getA().doubleValue();
				b = ugr.getB().doubleValue();
				c = ugr.getC().doubleValue();
				
				// if (a <> 0)
				if (a != 0) {
					// delta >= 0
					if ((b*b - 4*a*(c-val.doubleValue())) >= 0) {
						x1 = (-b+Math.sqrt(b*b-4*a*(c-val.doubleValue()))) / (2*a);
						x2 = (-b-Math.sqrt(b*b-4*a*(c-val.doubleValue()))) / (2*a);
						if ((x1>=ugr.getInterval().getPos1().doubleValue() && x1<=ugr.getInterval().getPos2().doubleValue()) || 
								(x2>=ugr.getInterval().getPos1().doubleValue() && x2<=ugr.getInterval().getPos2().doubleValue()))
							return 1;
					} 
				}
				// else if (b <> 0)
				else if (b != 0) {
					x1 = (val.doubleValue()-c) / b;
					if ( x1>=ugr.getInterval().getPos1().doubleValue() && x1<=ugr.getInterval().getPos2().doubleValue() )
						return 1;
				}
				else {
					if (c == val.doubleValue())
						return 1;
				}
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return 0;
	}
	
	public static int passes (GInt_V gi, BigDecimal val) {
		if (gi == null || val == null)
			return 0;
		
		try {
			for (int i=0; i<gi.getUnits().length(); i++) {
				UGInt ugi = gi.getUnits().getElement(i);
				
				if (ugi.getVal().intValue() == val.doubleValue())
					return 1;
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return 0;
	}
	
	/*
	 * Reduction by the intersection between the GLine_V and the domain of the gmoving
	 */
	public static GReal_V atgline (GReal_V gr, GLine_V gl) {
		if (gr == null || gl == null)
			return null;
		
		ArrayList<UGReal> ugrs = new ArrayList<UGReal>();
		double pos1a, pos2a, pos1b, pos2b;
		
		try {
			if (gr.getUnits().length() == 0 || gl.getRints().length() == 0)
				return null;
			if (gr.getUnits().getElement(0).getNid().doubleValue() != gl.getNid().doubleValue())
				return null;
			
			for (int i=0; i<gr.getUnits().length(); i++) {
				UGReal ugr = gr.getUnits().getElement(i);
				Section ugrSec = gr.getUnits().getElement(i).getInterval();
				
				for (int j=0; j<gl.getRints().length(); j++) {
					Section s = gl.getRints().getElement(j);
					
					if ((ugrSec.getRid().doubleValue() == s.getRid().doubleValue()) && 
							(ugrSec.getSide().doubleValue() == s.getSide().doubleValue())) {
						pos1a = ugrSec.getPos1().doubleValue();
						pos2a = ugrSec.getPos2().doubleValue();
						pos1b = s.getPos1().doubleValue();
						pos2b = s.getPos2().doubleValue();
						
						if (pos1a <= pos2a) {
							if (( (pos1b <= pos1a) && (pos2b >= pos1a) ) || 
									( (pos1b >= pos1a) && (pos1b <= pos2a) )) {
								double pos1, pos2;
								pos1 = Math.max(pos1a, pos1b);
								pos2 = Math.min(pos2a, pos2b);
								
								ugrs.add(new UGReal(ugr.getA(), ugr.getB(), ugr.getC(), ugr.getR(), ugr.getNid(), 
										new Section(ugrSec.getRid(), ugrSec.getSide(), new BigDecimal(pos1), new BigDecimal(pos2)), new Integer(ugr.getSequence().intValue())));
								
							//	System.out.println("Adding 1");
							}
						}
						else {
							if (( (pos1b <= pos2a) && (pos2b >= pos2a) ) || 
									( (pos1b >= pos2a) && (pos1b <= pos1a) )) {
								double pos1, pos2;
								pos1 = Math.max(pos2a, pos1b);
								pos2 = Math.min(pos1a, pos2b);
								
								ugrs.add(new UGReal(ugr.getA(), ugr.getB(), ugr.getC(), ugr.getR(), ugr.getNid(), 
										new Section(ugrSec.getRid(), ugrSec.getSide(), new BigDecimal(pos1), new BigDecimal(pos2)), new Integer(ugr.getSequence().intValue())));
							//	System.out.println("Adding 2");
							}
						}
					}
				}
			}
			if (ugrs.size() == 0)
				return null;
			
			UGReal [] newUgrs = new UGReal[ugrs.size()];
			for (int i=0; i<ugrs.size(); i++)
				newUgrs[i] = (UGReal) ugrs.get(i);
			
			System.out.println(ugrs.size());
			
			return new GReal_V(new UGReal_V(newUgrs));
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static GInt_V atgline (GInt_V gi, GLine_V gl) {
		if (gi == null || gl == null)
			return null;
		
		ArrayList<UGInt> ugis = new ArrayList<UGInt>();
		double pos1a, pos2a, pos1b, pos2b;
		
		try {
			if (gi.getUnits().length() == 0 || gl.getRints().length() == 0)
				return null;
			if (gi.getUnits().getElement(0).getNid().doubleValue() != gl.getNid().doubleValue())
				return null;
			
			for (int i=0; i<gi.getUnits().length(); i++) {
				UGInt ugi = gi.getUnits().getElement(i);
				Section ugiSec = gi.getUnits().getElement(i).getInterval();
				
				for (int j=0; j<gl.getRints().length(); j++) {
					Section s = gl.getRints().getElement(j);
					
					if ((ugiSec.getRid().doubleValue() == s.getRid().doubleValue()) && 
							(ugiSec.getSide().doubleValue() == s.getSide().doubleValue())) {
						pos1a = ugiSec.getPos1().doubleValue();
						pos2a = ugiSec.getPos2().doubleValue();
						pos1b = s.getPos1().doubleValue();
						pos2b = s.getPos2().doubleValue();
						
						if (pos1a <= pos2a) {
							if (( (pos1b <= pos1a) && (pos2b >= pos1a) ) || 
									( (pos1b >= pos1a) && (pos1b <= pos2a) )) {
								double pos1, pos2;
								pos1 = Math.max(pos1a, pos1b);
								pos2 = Math.min(pos2a, pos2b);
								
								ugis.add(new UGInt(ugi.getVal(), ugi.getNid(), 
										new Section(ugiSec.getRid(), ugiSec.getSide(), new BigDecimal(pos1), new BigDecimal(pos2)), new Integer(ugi.getSequence().intValue())));
							}
						}
						else {
							if (( (pos1b <= pos2a) && (pos2b >= pos2a) ) || 
									( (pos1b >= pos2a) && (pos1b <= pos1a) )) {
								double pos1, pos2;
								pos1 = Math.max(pos2a, pos1b);
								pos2 = Math.min(pos1a, pos2b);
								
								ugis.add(new UGInt(ugi.getVal(), ugi.getNid(), 
										new Section(ugiSec.getRid(), ugiSec.getSide(), new BigDecimal(pos1), new BigDecimal(pos2)), new Integer(ugi.getSequence().intValue())));
							}
						}
					}
				}
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		if (ugis.size() == 0)
			return null;
		UGInt [] newUgis = new UGInt[ugis.size()];
		for (int i=0; i<ugis.size(); i++)
			newUgis[i] = (UGInt) ugis.get(i); 
		
		try {
			return new GInt_V(new UGInt_V(newUgis));
		} catch (SQLException e) { e.printStackTrace();	}
		return null;
	}
	
	public static GReal_V at (GReal_V gr, BigDecimal val) {
		if (gr == null || val == null)
			return null;
		
		ArrayList<UGReal> ugrs = new ArrayList<UGReal>();
		
		try {
			for (int i=0; i<gr.getUnits().length(); i++) {
				UGReal ugr = gr.getUnits().getElement(i);
				double a, b, c, x1, x2;
				a = ugr.getA().doubleValue();
				b = ugr.getB().doubleValue();
				c = ugr.getC().doubleValue();
				
				// if (a <> 0)
				if (a != 0) {
					// delta >= 0
					if ((b*b - 4*a*(c-val.doubleValue())) >= 0) {
						x1 = (-b+Math.sqrt(b*b-4*a*(c-val.doubleValue()))) / (2*a);
						x2 = (-b-Math.sqrt(b*b-4*a*(c-val.doubleValue()))) / (2*a);
						if (x1>=ugr.getInterval().getPos1().doubleValue() && x1<=ugr.getInterval().getPos2().doubleValue())
							ugrs.add(new UGReal(new BigDecimal(a), new BigDecimal(b), new BigDecimal(c), new BigDecimal(ugr.getR().doubleValue()),
									new Integer(ugr.getNid().intValue()), 
									new Section(new Integer(ugr.getInterval().getRid().intValue()), 
											new Integer(ugr.getInterval().getSide().intValue()), 
											new BigDecimal(x1), new BigDecimal(x1)),
									new Integer(ugr.getSequence().intValue())));
						
						if (x2>=ugr.getInterval().getPos1().doubleValue() && x2<=ugr.getInterval().getPos2().doubleValue())							
							ugrs.add(new UGReal(new BigDecimal(a), new BigDecimal(b), new BigDecimal(c), new BigDecimal(ugr.getR().doubleValue()),
									new Integer(ugr.getNid().intValue()), 
									new Section(new Integer(ugr.getInterval().getRid().intValue()), 
											new Integer(ugr.getInterval().getSide().intValue()), 
											new BigDecimal(x2), new BigDecimal(x2)), 
									new Integer(ugr.getSequence().intValue()) ));
						
					} 
				}
				// else if (b <> 0)
				else if (b != 0) {
					x1 = (val.doubleValue()-c) / b;
					if ( x1>=ugr.getInterval().getPos1().doubleValue() && x1<=ugr.getInterval().getPos2().doubleValue() )
						ugrs.add(new UGReal(new BigDecimal(a), new BigDecimal(b), new BigDecimal(c), new BigDecimal(ugr.getR().doubleValue()),
								new Integer(ugr.getNid().intValue()), 
								new Section(new Integer(ugr.getInterval().getRid().intValue()), 
										new Integer(ugr.getInterval().getSide().intValue()), 
										new BigDecimal(x1), new BigDecimal(x1)), 
								new Integer(ugr.getSequence().intValue()) ));
				}
				else {
					if (c == val.doubleValue())
						ugrs.add(new UGReal(new BigDecimal(a), new BigDecimal(b), new BigDecimal(c), new BigDecimal(ugr.getR().doubleValue()),
								new Integer(ugr.getNid().intValue()), 
								new Section(new Integer(ugr.getInterval().getRid().intValue()), 
										new Integer(ugr.getInterval().getSide().intValue()), 
										new BigDecimal(c), new BigDecimal(c)), 
								new Integer(ugr.getSequence().intValue()) ));
				}
			}
			
			if (ugrs.size() == 0)
				return null;
			
			UGReal [] newUgrs = new UGReal[ugrs.size()];
			for (int i=0; i<ugrs.size(); i++)
				newUgrs[i] = (UGReal) ugrs.get(i);
			
			return new GReal_V(new UGReal_V(newUgrs));
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static GInt_V at (GInt_V gi, BigDecimal val) {
		if (gi == null || val == null)
			return null;
		
		ArrayList<UGInt> ugis = new ArrayList<UGInt>();
		try {
			for (int i=0; i<gi.getUnits().length(); i++) {
				UGInt ugi = gi.getUnits().getElement(i);
				
				if (ugi.getVal().intValue() == val.intValue())
					ugis.add(new UGInt(new Integer(val.intValue()), 
									new Integer(ugi.getNid().intValue()), 
							new Section(new Integer(ugi.getInterval().getRid().intValue()), 
									new Integer(ugi.getInterval().getSide().intValue()), 
									ugi.getInterval().getPos1(), ugi.getInterval().getPos2()),
							new Integer(ugi.getSequence().intValue())));
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		if (ugis.size() == 0)
			return null;
		UGInt [] newUgis = new UGInt[ugis.size()];
		for (int i=0; i<ugis.size(); i++)
			newUgis[i] = (UGInt) ugis.get(i); 
		
		try {
			return new GInt_V(new UGInt_V(newUgis));
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}
	
	public static GInt_V atSequence (GInt_V gi, int seq) {
		if (gi == null || seq < 0)
			return null;
		
		try {
			UGInt [] newUgis = new UGInt[1];
			for (int i=0; i<gi.getUnits().length(); i++)
				if (gi.getUnits().getElement(i).getSequence().intValue() == seq) {
					newUgis[0] = gi.getUnits().getElement(i);
					return new GInt_V(new UGInt_V(newUgis));
				}
			return null;
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}
	
	public static MInt_V atTransitions (MInt_V mi) {
		if (mi == null)
			return null;
		
		ArrayList<UInt> umis = new ArrayList<UInt>();
		Integer val;
		try {
			val = mi.getUnits().getElement(0).getVal();
			for (int i=1; i<mi.getUnits().length(); i++) {
				UInt ui = mi.getUnits().getElement(i);
				
				if (val.compareTo(ui.getVal()) != 0) {
					umis.add(new UInt(ui.getVal(), ui.getT1(), ui.getT1()));
					val = ui.getVal();
				}
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		if (umis.size() == 0)
			return null;
		UInt [] newUmis = new UInt[umis.size()];
		for (int i=0; i<umis.size(); i++)
			newUmis[i] = (UInt) umis.get(i); 
		
		try {
			return new MInt_V(new UInt_V(newUmis));
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}
	
	public static GInt_V atTransitions (GInt_V gi) {
		if (gi == null)
			return null;
		
		ArrayList<UGInt> ugis = new ArrayList<UGInt>();
		Integer val;
		try {
			val = gi.getUnits().getElement(0).getVal();
			for (int i=1; i<gi.getUnits().length(); i++) {
				UGInt ugi = gi.getUnits().getElement(i);
				
				if (val.compareTo(ugi.getVal()) != 0) {
					ugis.add(new UGInt(ugi.getVal(), ugi.getNid(), 
							new Section(ugi.getInterval().getRid(), ugi.getInterval().getSide(), 
											ugi.getInterval().getPos1(), ugi.getInterval().getPos1()),
								ugi.getSequence()));
					val = gi.getUnits().getElement(i).getVal();
				}
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		if (ugis.size() == 0)
			return null;
		UGInt [] newUgis = new UGInt[ugis.size()];
		for (int i=0; i<ugis.size(); i++)
			newUgis[i] = (UGInt) ugis.get(i); 
		
		try {
			return new GInt_V(new UGInt_V(newUgis));
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}
}
