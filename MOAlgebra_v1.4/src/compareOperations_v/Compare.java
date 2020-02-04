package compareOperations_v;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import algebraTypes_v.GInt_V;
import algebraTypes_v.GLine_V;
import algebraTypes_v.GReal_V;
import algebraTypes_v.Section;
import algebraTypes_v.Sections_V;
import algebraTypes_v.UGInt;
import algebraTypes_v.UGInt_V;
import algebraTypes_v.UGReal;

/*
 * This class implements compare operations for the algebra types
 */
public class Compare {

	private static Connection con;
	private static Statement st;
	
	/*
	 * This function compares the two input functions 
	 * and generates a GInt_V funtion defined over the domain intersection
	 * of the input functions and to {-1, 0, 1} as for a common given section 
	 * the first function is less, equal or greater then the second one with 
	 * the given precision parameter. 
	 * 
	 * ! The granularity is the section because we made the below assumption ! 
	 */
	public static GInt_V compare (GReal_V gr1, GReal_V gr2, float precision) {
		/*
		 * ! Currently, for a GReal_V, for each given section the associated value
		 * is constant (the function that approximates the unit real is a constant).
		 * For example, we suppose that the speed of a MO is constant for a given road section !
		 * 
		 *  ! No subsection case is considered, i.e., all units imply the hole section (pos1 = 0 && pos2 = 1) !
		 */
		
		if (gr1 == null || gr2 == null)
			return null;
		if (! (precision >= 0 && precision <= 1))
			return null;
		
		int counter = 0;
		try {
			UGInt[] ugints = new UGInt[gr1.getUnits().length()<gr2.getUnits().length()?gr1.getUnits().length():gr2.getUnits().length()];
			UGReal currentUGR1, currentUGR2;
			for (int i=0; i<gr1.getUnits().length(); i++) {
				currentUGR1 = gr1.getUnits().getElement(i);
				for (int j=0; j<gr2.getUnits().length(); j++) {
					currentUGR2 = gr2.getUnits().getElement(j);
					if (currentUGR1.getNid().intValue() == currentUGR2.getNid().intValue() && 
							currentUGR1.getInterval().getRid().intValue() == currentUGR2.getInterval().getRid().intValue() &&
							currentUGR1.getInterval().getSide().intValue() == currentUGR2.getInterval().getSide().intValue())
					{
						int c1 = currentUGR1.getC().intValue(), c2 = currentUGR2.getC().intValue();
						Integer compareVal;
						if (c1*(1+precision) < c2)
							compareVal = new Integer(-1);
						else if(c1*(1-precision) > c2)
							compareVal = new Integer(1);
						else 
							compareVal = new Integer(0);
						ugints[counter++] = new UGInt(compareVal, new Integer(currentUGR1.getNid().intValue()), 
								new Section(new Integer(currentUGR1.getInterval().getRid().intValue()), 
										new Integer(currentUGR1.getInterval().getSide().intValue()), 
										new BigDecimal(currentUGR1.getInterval().getPos1().intValue()),
										new BigDecimal(currentUGR1.getInterval().getPos2().intValue())), 
								new Integer(currentUGR1.getSequence().intValue()));
					}
				}
			}
			
			System.out.println("counter = " + counter);
			
			UGInt[] lastUgints = new UGInt[counter];
			for (int i=0; i<counter; i++)
				lastUgints[i] = ugints[i];
			return new GInt_V(new UGInt_V(lastUgints));
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public static GInt_V isGreaterThan (GReal_V gr1, GReal_V gr2) {
		
		
		return null;
	}
	
	/*
	 * Given a GReal_V and a table where each row represents a route with an associated CONSTANT value (e.g. Legal Speed),
	 * this function will return the GLine_V where the GReal_V is greater than the corresponding CONSTANT * (1+percentage)
	 * 
	 * This version of the function only considers linear interpolation of a GReal_V (i.e A = 0) 
	 */
	public static GLine_V greaterThan(GReal_V gr, BigDecimal percentage, String tableName, String measureColName, String ridColName) {
		if (gr == null)
			return null;
		
		String statement;
		ResultSet rs;
		double constVal = 0;
		int resultUnitsCounter = 0, lastRID = -999999999;
		
		BigDecimal p1, p2, p0, s, e;
		Integer rid, side;
		UGReal ugr;
		
		// use the existing connection to Oracle
	/*	DBConnection dbc = new DBConnection("orcl", "sysman", "iulius");
		try {
			try {
				dbc.connection();
		} catch (ClassNotFoundException sqle) { sqle.printStackTrace(); }
		} catch (SQLException sqle) {sqle.printStackTrace();} */ 
		
		try {
			con = DriverManager.getConnection("jdbc:default:connection:");
		} catch (SQLException sqle) { sqle.printStackTrace(); }
		
		/*
		 * for each unit of the GReal_V obtain the corresponding with its section CONSTANT value from the table   
		 */
		try {
			st = con.createStatement();
			
			int counter = 0;
			
			Section sects[] = new Section[gr.getUnits().length()];
			for (int i=0; i<gr.getUnits().length(); i++) {
				counter++;
				ugr = gr.getUnits().getElement(i);
				
				if (ugr.getInterval().getRid() != lastRID) { // the measure is constant for a given road segment
					statement = " SELECT " + measureColName + 
								" FROM " + tableName + 
								" WHERE " + ridColName + " = " + ugr.getInterval().getRid();
					rs = st.executeQuery(statement);
					System.out.println(statement);
					
					lastRID = ugr.getInterval().getRid();
					constVal = 0;
					while (rs.next()) {
						constVal = rs.getDouble(1);
					//	System.out.println(" CONSTVAL c=" + constVal + " val = " + constVal*(1+percentage.doubleValue())); 
						break;
					}
					rs.close();
				}
				
				rid = ugr.getInterval().getRid();
				side = ugr.getInterval().getSide();
				p1 = ugr.getInterval().getPos1();
				p2 = ugr.getInterval().getPos2();
				
				// b = 0
				if (ugr.getB().doubleValue() == 0.0) {
					if (ugr.getC().doubleValue() > constVal*(1+percentage.doubleValue())) {
						sects[resultUnitsCounter++] = new Section(rid, side, p1, p2);
					}
				}
				else {
					p0 = new BigDecimal( (constVal*(1+percentage.doubleValue()) - ugr.getC().doubleValue())/ugr.getB().doubleValue() );
					
					if (ugr.getB().doubleValue() > 0) {
						if ( (p1.doubleValue() < p2.doubleValue()) && (p0.doubleValue() < p2.doubleValue()) ) {
							sects[resultUnitsCounter++] = new Section(rid, side, new BigDecimal(Math.max(p0.doubleValue(), p1.doubleValue())), p2);
						}
						else if ( (p1.doubleValue() > p2.doubleValue()) && (p0.doubleValue() < p1.doubleValue()) ) {
							sects[resultUnitsCounter++] = new Section(rid, side, p1, new BigDecimal(Math.max(p0.doubleValue(), p2.doubleValue())));
						}
					}
					else {
						if ( (p1.doubleValue() < p2.doubleValue()) && (p0.doubleValue() > p2.doubleValue()) ) {
							sects[resultUnitsCounter++] = new Section(rid, side, p1, new BigDecimal(Math.min(p0.doubleValue(), p2.doubleValue())));
						}
						else if ( (p1.doubleValue() > p2.doubleValue()) && (p0.doubleValue() > p1.doubleValue()) ) {
							sects[resultUnitsCounter++] = new Section(rid, side, new BigDecimal(Math.min(p0.doubleValue(), p1.doubleValue())), p2);
						}
					}
				}
				
			//	if (counter == 200)
			//		break;
			}
			st.close(); 
			
			if (resultUnitsCounter == 0) { // nothing found
				System.out.println("No units greater than");
				return null;
			}
			
			Section finalCompactedSects[] = new Section[resultUnitsCounter];
			int compactedCounter = 0;
			if (resultUnitsCounter != 1) {
				rid = sects[0].getRid();
				side = sects[0].getSide();
				s = sects[0].getPos1();
				e = sects[0].getPos2();
				int i = 1;
				while (i < resultUnitsCounter) {
					if (rid.equals(sects[i].getRid()) && side.equals(sects[i].getSide()) 
							&& ( (Math.floor(e.doubleValue() * 100)/100) ==  (Math.floor(sects[i].getPos1().doubleValue() * 100)/100) )) { 
									//e.compareTo(sects[i].getPos1()) == 0) ) {
							e = sects[i].getPos2();
							i++;
					}
					else {
						finalCompactedSects[compactedCounter++] = new Section(rid, side, s, e);
						rid = sects[i].getRid();
						side = sects[i].getSide();
						s = sects[i].getPos1();
						e = sects[i].getPos2();
						i++;
					}
				}
				finalCompactedSects[compactedCounter++] = new Section(rid, side, s, e);
			}
			else { // only one unit
				return new GLine_V(gr.getUnits().getElement(0).getNid(), new Sections_V(sects));
			}
			
			System.out.println("Compacted length = " + compactedCounter);
			System.out.println("Initial length = " + gr.getUnits().length());
			Section finalSects[] = new Section[compactedCounter];
			for (int i=0; i<compactedCounter; i++) {
				finalSects[i] = finalCompactedSects[i];
				System.out.println(finalSects[i].getRid() + " " + finalSects[i].getPos1() + " " + finalSects[i].getPos2());
			}
			
			return new GLine_V(gr.getUnits().getElement(0).getNid(), new Sections_V(finalSects));
		} catch (SQLException se) { se.printStackTrace(); }
		
		
		return null;
	}
}
