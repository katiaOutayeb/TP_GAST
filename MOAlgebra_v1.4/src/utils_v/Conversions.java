package utils_v;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import algebraTypes_v.GInt_V;
import algebraTypes_v.GReal_V;
import algebraTypes_v.Interval_V;
import algebraTypes_v.Intervals_V;
import algebraTypes_v.MInt_V;
import algebraTypes_v.MReal_V;
import algebraTypes_v.Range_V;
import algebraTypes_v.Section;
import algebraTypes_v.UGInt;
import algebraTypes_v.UGInt_V;
import algebraTypes_v.UGReal;
import algebraTypes_v.UGReal_V;
import algebraTypes_v.UInt;
import algebraTypes_v.UInt_V;
import algebraTypes_v.UReal;
import algebraTypes_v.UReal_V;

public class Conversions {

	// Convert an MInt to an MReal
	public static MReal_V toMReal(MInt_V mi) {
		if (mi == null)
			return null;
		
		try {
			UReal [] units = new UReal[mi.getUnits().length()];
			
			for (int i=0; i<mi.getUnits().length(); i++) {
				units[i] = new UReal(new BigDecimal(0), new BigDecimal(0), 
							new BigDecimal(mi.getUnits().getElement(i).getVal().intValue()), new BigDecimal(0), 
							mi.getUnits().getElement(i).getT1(), mi.getUnits().getElement(i).getT2());
			}			
			return new MReal_V(new UReal_V(units));
			
		} catch (SQLException e) { e.printStackTrace(); }
			
		return null;
	}
	
	public static GReal_V toGReal(GInt_V gi) {
		if (gi == null)
			return null;
		
		try {
			UGReal [] units = new UGReal[gi.getUnits().length()];
			
			for (int i=0; i<gi.getUnits().length(); i++) {
				units[i] = new UGReal(new BigDecimal(0), new BigDecimal(0), 
							new BigDecimal(gi.getUnits().getElement(i).getVal().intValue()), new BigDecimal(0), 
							gi.getUnits().getElement(i).getNid(), 
							gi.getUnits().getElement(i).getInterval(), 
							gi.getUnits().getElement(i).getSequence());
			}			
			return new GReal_V(new UGReal_V(units));
			
		} catch (SQLException e) { e.printStackTrace(); }
			
		return null;
	}
	
	// Approximate an MReal with an MInt
	public static MInt_V toMInt(MReal_V mr) {
		if (mr == null)
			return null;
		
		try {
			int val;
			double a, b, c, t1, t2;
			UInt [] units = new UInt[mr.getUnits().length()];
			
			for (int i=0; i<mr.getUnits().length(); i++) {
				a = mr.getUnits().getElement(i).getA().doubleValue();
				b = mr.getUnits().getElement(i).getB().doubleValue();
				c = mr.getUnits().getElement(i).getC().doubleValue();
				t1 = mr.getUnits().getElement(i).getT1().doubleValue();
				t2 = mr.getUnits().getElement(i).getT2().doubleValue();
				val = (int) (a*(t2*t2 + t1*t2 + t1*t1)/3 + b*(t1+t2)/2 + c);
				units[i] = new UInt(new Integer(val),  
							mr.getUnits().getElement(i).getT1(), mr.getUnits().getElement(i).getT2());
			}			
			return new MInt_V(new UInt_V(units));
			
		} catch (SQLException e) { e.printStackTrace(); }
			
		return null;
	}
	
	public static GInt_V toGInt(GReal_V gr) {
		if (gr == null)
			return null;
		
		try {
			int val;
			double a, b, c, pos1, pos2;
			UGInt [] units = new UGInt[gr.getUnits().length()];
			
			for (int i=0; i<gr.getUnits().length(); i++) {
				a = gr.getUnits().getElement(i).getA().doubleValue();
				b = gr.getUnits().getElement(i).getB().doubleValue();
				c = gr.getUnits().getElement(i).getC().doubleValue();
				pos1 = gr.getUnits().getElement(i).getInterval().getPos1().doubleValue();
				pos2 = gr.getUnits().getElement(i).getInterval().getPos2().doubleValue();
				val = (int) (a*(pos2*pos2 + pos1*pos2 + pos1*pos1)/3 + b*(pos1+pos2)/2 + c);
				units[i] = new UGInt(new Integer(val), gr.getUnits().getElement(i).getNid(), 
									gr.getUnits().getElement(i).getInterval(), 
									gr.getUnits().getElement(i).getSequence());
			}			
			return new GInt_V(new UGInt_V(units));
			
		} catch (SQLException e) { e.printStackTrace(); }
			
		return null;
	}
	
	public static MInt_V compactMInt(MInt_V mi) {
		if (mi == null)
			return null;
		try {
			BigDecimal s, e;
			Integer val;
			ArrayList<UInt> units = new ArrayList<UInt>();
			
			s = mi.getUnits().getElement(0).getT1();
			e = mi.getUnits().getElement(0).getT2();
			val = mi.getUnits().getElement(0).getVal();
			for (int i=1; i<mi.getUnits().length(); i++) {
				if (val.compareTo(mi.getUnits().getElement(i).getVal()) == 0 && 
						e.compareTo(mi.getUnits().getElement(i).getT1()) == 0)
					e = mi.getUnits().getElement(i).getT2();
				else {
					units.add(new UInt(val, s, e));
					s = mi.getUnits().getElement(i).getT1();
					e = mi.getUnits().getElement(i).getT2();
					val = mi.getUnits().getElement(i).getVal();
				}
			}
			units.add(new UInt(val, s, e));
			
			UInt finalUnits[] = new UInt[units.size()];
			for (int i=0; i<units.size(); i++)
				finalUnits[i] = units.get(i);
			
			return new MInt_V(new UInt_V(finalUnits));
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
		
	}
	
	public static GInt_V compactGInt(GInt_V gi) {
		if (gi == null)
			return null;
		try {
			int seq = 0;
			BigDecimal pos1, pos2;
			Integer val, rid, side;
			ArrayList<UGInt> units = new ArrayList<UGInt>();
			
			val = gi.getUnits().getElement(0).getVal();
			rid = gi.getUnits().getElement(0).getInterval().getRid();
			side = gi.getUnits().getElement(0).getInterval().getSide();
			pos1 = gi.getUnits().getElement(0).getInterval().getPos1();
			pos2 = gi.getUnits().getElement(0).getInterval().getPos2();
			
			for (int i=1; i<gi.getUnits().length(); i++) {
				if (val.compareTo(gi.getUnits().getElement(i).getVal()) == 0 &&
						rid.compareTo(gi.getUnits().getElement(i).getInterval().getRid()) == 0 &&
						side.compareTo(gi.getUnits().getElement(i).getInterval().getSide()) == 0 &&
						pos2.compareTo(gi.getUnits().getElement(i).getInterval().getPos1()) == 0)
					pos2 = gi.getUnits().getElement(i).getInterval().getPos2();
				else {
					units.add(new UGInt(val, gi.getUnits().getElement(i).getNid(), 
									new Section(rid, side, pos1, pos2), new Integer(seq++)));
					val = gi.getUnits().getElement(i).getVal();
					rid = gi.getUnits().getElement(i).getInterval().getRid();
					side = gi.getUnits().getElement(i).getInterval().getSide();
					pos1 = gi.getUnits().getElement(i).getInterval().getPos1();
					pos2 = gi.getUnits().getElement(i).getInterval().getPos2();
				}
			}
			units.add(new UGInt(val, gi.getUnits().getElement(0).getNid(), 
						new Section(rid, side, pos1, pos2), new Integer(seq++)));
			
			UGInt finalUnits[] = new UGInt[units.size()];
			for (int i=0; i<units.size(); i++)
				finalUnits[i] = units.get(i);
			
			return new GInt_V(new UGInt_V(finalUnits));
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
}
