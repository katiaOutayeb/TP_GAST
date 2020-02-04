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
import algebraTypes_v.Interval_V;
import algebraTypes_v.Intervals_V;
import algebraTypes_v.MGPoint_V;
import algebraTypes_v.MInt_V;
import algebraTypes_v.MReal_V;
import algebraTypes_v.Range_V;
import algebraTypes_v.Section;
import algebraTypes_v.Sections_V;
import algebraTypes_v.UGInt;
import algebraTypes_v.UGPoint;
import algebraTypes_v.UGReal;

/*
 * This class implements the projection to domain and Range_V operations for gpoint and GLine_V
 */
public class ProjectionDomainRange {

	public static Range_V deftime (MGPoint_V mgp) {
		if (mgp == null)
			return null;
		try {
			int counter = 0;
			Interval_V intvs[] = new Interval_V[mgp.getUnits().length()];
			SortedSet sortedMGPoint = UtilityFunctions.getTimeOrderedMGPoint(mgp);
			BigDecimal s, e;
			
			Iterator it = sortedMGPoint.iterator();
			UGPoint ugp = (UGPoint) it.next();
			
			s = ugp.getT1();
			e = ugp.getT2();
			if (! it.hasNext())
				intvs[counter++] = new Interval_V(s, e, new BigDecimal(1), new BigDecimal(1));
			else {
				while (it.hasNext()) {
					ugp = (UGPoint) it.next();
					if (e.compareTo(ugp.getT1()) == 0)
						e = ugp.getT2();
					else {
						intvs[counter++] = new Interval_V(s, e, new BigDecimal(1), new BigDecimal(1));
						s = ugp.getT1();
						e = ugp.getT2();
					}
				}
				intvs[counter++] = new Interval_V(s, e, new BigDecimal(1), new BigDecimal(1));
			}
			
			Interval_V finalIntvs[] = new Interval_V[counter];
			for (int i=0; i<counter; i++)
				finalIntvs[i] = intvs[i];
			
			return new Range_V(new BigDecimal(2), new Intervals_V(finalIntvs));
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static Range_V deftime (MReal_V mr) {
		if (mr == null)
			return null;
		try {
			BigDecimal s, e;
			ArrayList<Interval_V> intvs = new ArrayList<Interval_V>();
			
			s = mr.getUnits().getElement(0).getT1();
			e = mr.getUnits().getElement(0).getT2();
			for (int i=1; i<mr.getUnits().length(); i++) {
				if (e.compareTo(mr.getUnits().getElement(i).getT1()) == 0)
					e = mr.getUnits().getElement(i).getT2();
				else {
					intvs.add(new Interval_V(s, e, new BigDecimal(1), new BigDecimal(1)));
					s = mr.getUnits().getElement(i).getT1();
					e = mr.getUnits().getElement(i).getT2();
				}
			}
			intvs.add(new Interval_V(s, e, new BigDecimal(1), new BigDecimal(1)));
			
			System.out.println(intvs.size());
			
			Interval_V finalIntvs[] = new Interval_V[intvs.size()];
			for (int i=0; i<intvs.size(); i++)
				finalIntvs[i] = intvs.get(i);
			
			return new Range_V(new BigDecimal(2), new Intervals_V(finalIntvs));
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static GLine_V trajectory (MGPoint_V mgp) {
		if (mgp == null)
			return null;
		try {
			int counter = 0;
			Section sects[] = new Section[mgp.getUnits().length()];
			SortedSet sortedMGPoint = UtilityFunctions.getSpatialOrderedMGPoint(mgp);
			BigDecimal s, e;
			Integer rid, side;
			
			Iterator it = sortedMGPoint.iterator();
			UGPoint ugp = (UGPoint) it.next();
			
			rid = ugp.getRid();
			side = ugp.getSide();
			s = ugp.getPos1();
			e = ugp.getPos2();
			if (! it.hasNext())
				sects[counter++] = new Section(ugp.getRid(), ugp.getSide(), ugp.getPos1(), ugp.getPos2());
			else {
				while (it.hasNext()) {
					ugp = (UGPoint) it.next();
					
					if (rid.equals(ugp.getRid()) && side.equals(ugp.getSide()) 
							&& (s.compareTo(ugp.getPos1()) <= 0) && (e.compareTo(ugp.getPos1()) >= 0)) {
						if (e.compareTo(ugp.getPos2()) < 0)
							e = ugp.getPos2();
					}
					else {
						sects[counter++] = new Section(rid, side, s, e);
						rid = ugp.getRid();
						side = ugp.getSide();
						s = ugp.getPos1();
						e = ugp.getPos2();
					}
				}
				sects[counter++] = new Section(rid, side, s, e);
			}
			
			Section finalSects[] = new Section[counter];
			for (int i=0; i<counter; i++)
				finalSects[i] = sects[i];
			
			return new GLine_V(mgp.getUnits().getElement(0).getNid(), new Sections_V(finalSects));
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static GLine_V locations (GInt_V mi) {
		if (mi == null)
			return null;
		try {
			ArrayList<Section> secs = new ArrayList<Section>();
			for(int i=0; i<mi.getUnits().length(); i++) {
				if (mi.getUnits().getElement(i).getInterval().getPos1().doubleValue() == 
							mi.getUnits().getElement(i).getInterval().getPos2().doubleValue()) {
					secs.add(new Section(mi.getUnits().getElement(i).getInterval().getRid(), 
								mi.getUnits().getElement(i).getInterval().getSide(),
								mi.getUnits().getElement(i).getInterval().getPos1(),
								mi.getUnits().getElement(i).getInterval().getPos1()));
				}
			}
			Section rints[] = new Section[secs.size()];
			for (int i=0; i<secs.size(); i++)
				rints[i] = secs.get(i);
			
			return new GLine_V(mi.getUnits().getElement(0).getNid(), new Sections_V(rints));
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	// InTime - currently defined only for GPoint
	public static BigDecimal inst (InTime it) {
		if (it == null)
			return null;
		try {
			return it.getT();
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}
	
	public static GPoint pos (InTime it) {
		if (it == null)
			return null;
		try {
			return it.getGp();
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}
	
	public static BigDecimal val (InTime it) {
		if (it == null)
			return null;
		try {
			return it.getVal();
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}
	
	// TODO: Override all the above operations for MGLine type
	
	
	// TODO: Operations for extended algebra types
	
	public static GPoint pos (InGPoint igp) {
		if (igp == null)
			return null;
		try {
			return igp.getGp();
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}
	
	public static BigDecimal val (InGPoint igp) {
		if (igp == null)
			return null;
		try {
			return igp.getVal();
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}
	
	public static GLine_V trajectory (GReal_V gr) {
		if (gr == null)
			return null;
		try {
			int counter = 0;
			Section sects[] = new Section[gr.getUnits().length()];
			SortedSet sortedGReal = UtilityFunctions.getSpatialOrderedGReal(gr);
			double s, e;
			int rid, side;
			
			Iterator it = sortedGReal.iterator();
			UGReal ugr = (UGReal) it.next();
			
			rid = ugr.getInterval().getRid().intValue();
			side = ugr.getInterval().getSide().intValue();
			s = ugr.getInterval().getPos1().doubleValue();
			e = ugr.getInterval().getPos2().doubleValue();
			if (! it.hasNext())
				sects[counter++] = new Section(ugr.getInterval().getRid(), ugr.getInterval().getSide(), 
												ugr.getInterval().getPos1().setScale(4, BigDecimal.ROUND_DOWN), 
												ugr.getInterval().getPos2().setScale(4, BigDecimal.ROUND_DOWN));
			else {
				while (it.hasNext()) {
					ugr = (UGReal) it.next();
					
					if (rid == ugr.getInterval().getRid().intValue() &&	side == ugr.getInterval().getSide().intValue() 
							&& (s <= ugr.getInterval().getPos1().doubleValue()) && (e >= ugr.getInterval().getPos1().doubleValue())) {
						if (e < ugr.getInterval().getPos2().doubleValue())
							e = ugr.getInterval().getPos2().doubleValue();
					}
					else {
						sects[counter++] = new Section(new Integer(rid), new Integer(side), 
								new BigDecimal(s).setScale(4, BigDecimal.ROUND_DOWN), 
								new BigDecimal(e).setScale(4, BigDecimal.ROUND_DOWN));
						rid = ugr.getInterval().getRid().intValue();
						side = ugr.getInterval().getSide().intValue();
						s = ugr.getInterval().getPos1().doubleValue();
						e = ugr.getInterval().getPos2().doubleValue();
					}
				}
				sects[counter++] = new Section(new Integer(rid), new Integer(side), 
						new BigDecimal(s).setScale(4, BigDecimal.ROUND_DOWN),
						new BigDecimal(e).setScale(4, BigDecimal.ROUND_DOWN));
			}
			
		//	counter = 88;
			Section finalSects[] = new Section[counter];
			for (int i=0; i<counter; i++)
				finalSects[i] = sects[i];
			
			return new GLine_V(new Integer(gr.getUnits().getElement(0).getNid().intValue()), 
						new Sections_V(finalSects));
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static GLine_V trajectory (GInt_V gi) {
		if (gi == null)
			return null;
		try {
			int counter = 0;
			Section sects[] = new Section[gi.getUnits().length()];
			SortedSet sortedGInt = UtilityFunctions.getSpatialOrderedGInt(gi);
			BigDecimal s, e;
			Integer rid, side;
			
			Iterator it = sortedGInt.iterator();
			UGInt ugi = (UGInt) it.next();
			
			rid = ugi.getInterval().getRid();
			side = ugi.getInterval().getSide();
			s = ugi.getInterval().getPos1();
			e = ugi.getInterval().getPos2();
			if (! it.hasNext())
				sects[counter++] = new Section(ugi.getInterval().getRid(), ugi.getInterval().getSide(), 
												ugi.getInterval().getPos1(), ugi.getInterval().getPos2());
			else {
				while (it.hasNext()) {
					ugi = (UGInt) it.next();
					
					if (rid.equals(ugi.getInterval().getRid()) && side.equals(ugi.getInterval().getSide()) 
							&& (s.compareTo(ugi.getInterval().getPos1()) <= 0) && (e.compareTo(ugi.getInterval().getPos1()) >= 0)) {
						if (e.compareTo(ugi.getInterval().getPos2()) < 0)
							e = ugi.getInterval().getPos2();
					}
					else {
						sects[counter++] = new Section(rid, side, s, e);
						rid = ugi.getInterval().getRid();
						side = ugi.getInterval().getSide();
						s = ugi.getInterval().getPos1();
						e = ugi.getInterval().getPos2();
					}
				}
				sects[counter++] = new Section(rid, side, s, e);
			}
			
			Section finalSects[] = new Section[counter];
			for (int i=0; i<counter; i++)
				finalSects[i] = sects[i];
			
			return new GLine_V(gi.getUnits().getElement(0).getNid(), new Sections_V(finalSects));
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static int getSequence (GInt_V mi, GPoint gp) {
		if (mi == null)
			return -1;
		int rid, side;
		double pos1, pos2, temp;
		try {
			for(int i=0; i<mi.getUnits().length(); i++) {
				rid = mi.getUnits().getElement(i).getInterval().getRid().intValue();
				side = mi.getUnits().getElement(i).getInterval().getSide().intValue();
				pos1 = mi.getUnits().getElement(i).getInterval().getPos1().doubleValue();
				pos2 = mi.getUnits().getElement(i).getInterval().getPos2().doubleValue();
				if (pos1 > pos2) {
					temp = pos1;
					pos1 = pos2;
					pos2 = temp;
				}
				
				if (rid == gp.getRid() && side == gp.getSide() && 
						pos1 <= gp.getPos().doubleValue() && pos2 >= gp.getPos().doubleValue())
					return mi.getUnits().getElement(i).getSequence().intValue();
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return -1;
	}
}
