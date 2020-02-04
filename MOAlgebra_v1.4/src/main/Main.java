package main;

import interaction_v.NetworkSpaceToSpace;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.SortedSet;

import nonTemporalOperations_v.Aggregation;
import nonTemporalOperations_v.Numeric;
import nonTemporalOperations_v.SetOperations;

import oracle.jdbc.OracleResultSet;
import oracle.jdbc.driver.OracleConnection;
import temporalOperations_v.InteractionDomainRange;
import temporalOperations_v.ProjectionDomainRange;
import utils_v.UtilityFunctions;


import aggregatesOnFunctions_v.AggregatesOnFunctions;
import algebraTypes_v.*;

import compareOperations_v.Compare;
import BasicAlgebraicOperation_v.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			Statement st;
			String statement;
			OracleResultSet rs;
			GReal_V gr1, gr2;
			GLine_V gl1, gl2;
			MGPoint_V mgp;

			DBConnection dbc = new DBConnection("orcl", "sysman", "oracle");
			try {
				dbc.connection();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			// OracleConnection con = (OracleConnection)
			// DriverManager.getConnection("jdbc:default:connection:");
			st = dbc.con.createStatement();
		/*	statement = "SELECT mos.trajectory(mos.greaterThan(mos.toGReal(L.GEAR_G),  GREAL_V(UGREAL_V(ugreal(0, 0, 0, 0, 0, SECTION(-1, -1, 0, 1), -1)))))," +
								" mos.trajectory(mos.equalTo(L.ACC_POS_G,  GREAL_V(UGREAL_V(ugreal(0, 0, 0, 0, 0, SECTION(-1, -1, 0, 1), -1)))))" +
						" FROM LAVIA_ITS_ADT L" +
						" WHERE mo_id = 1"; */
			statement = "SELECT mos.trajectory(speed_g) AS GEOM FROM LAVIA_ITS_ADT WHERE mo_id = 1";
		//	statement = "SELECT trip FROM LAVIA_ITS_ADT WHERE mo_id = 1";
			rs = (OracleResultSet) st.executeQuery(statement);
			rs.next();
			gl1 = (GLine_V) rs.getORAData(1, GLine_V.getORADataFactory());
		//	gl2 = (GLine_V) rs.getORAData(2, GLine_V.getORADataFactory());
		//	SetOperations.intersection(gl1, gl2);
		//	mgp = (MGPoint_V) rs.getORAData(1, MGPoint_V.getORADataFactory());
		//	NetworkSpaceToSpace.glineToMultiline(gl1, "ARCSV2", "GEOM", "NUM_ARC");
		//	InteractionDomainRange.atInstant(mgp, new BigDecimal(5000));
			

			// statement = "SELECT odometer_g FROM BRINKHOFF_GA_CAN WHERE mo_id
			// = 2";
			// statement = "SELECT tt_project FROM BRINKHOFF_GA_TRAJECTORIES
			// WHERE mo_id = 959";
			// statement = "SELECT VEHICLE_SPEED_G FROM BRINKHOFF_GA_CAN WHERE
			// mo_id = 959";
			// statement = "SELECT VEHICLE_SPEED_G FROM DIRCO_POLYC_MEGANE";
			// statement = "SELECT nmo_operations.trajectory(tt) FROM
			// DIRCO_POLYT_MEGANE";
			// statement = "SELECT LEGAL_SPEED_G FROM LAVIA_ITS_ADT WHERE mo_id
			// = 1";
			// statement = "SELECT nmo_operations.greaterThan(SPEED_G, 0,
			// 'ARCSV2', 'VIT_LIM', 'NUM_ARC') FROM LAVIA_ITS_ADT WHERE mo_id =
			// 1";
			// statement = "select SPEED  from  LAVIA_ITS_ADT WHERE mo_id = 1";
			// rs = (OracleResultSet) st.executeQuery(statement);
			// rs.next();
			//MReal_V mr1 = (MReal_V) rs.getORAData(1, MReal_V.getORADataFactory());
			/*
			 * SortedSet<UReal> sortedMGPoint_a =
			 * UtilityFunctions.getTimeOrderedMReal(mr1); Iterator it_a =
			 * sortedMGPoint_a.iterator(); UReal URa = null;
			 * while(it_a.hasNext()){ URa = (UReal) it_a.next();
			 * System.out.println(URa.getT1()+ ", " + URa.getT2());
			 *  }
			 */

		//	statement = "select SPEED  from  LAVIA_ITS_ADT WHERE mo_id = 1";
		/*	statement = "select mos.deftime( mos.smallerThan(L.SPEED,  MREAL_V(UREAL_V(ureal(0,0,2,0,3700, 4900)))))" +
						" from  LAVIA_ITS_ADT L WHERE mo_id = 1";
			statement = "select mos.smallerThan(L.SPEED,  MREAL_V(UREAL_V(ureal(0,0,2,0,4700, 4900))))" +
							" from  LAVIA_ITS_ADT L WHERE mo_id = 1";
			
			rs = (OracleResultSet) st.executeQuery(statement);
			rs.next();
			
			MReal_V mr2 = (MReal_V) rs.getORAData(1, MReal_V.getORADataFactory()); */
			
			
		//	Range_V ra = new Range_V();
		//	Range_V ra = (Range_V) rs.getORAData(1, Range_V.getORADataFactory());
		//	ra = ProjectionDomainRange.deftime(mr1);
		//	System.out.println(Numeric.no_components(ra));
		//	System.out.println(ProjectionDomainRange.deftime(mr2));
			
		/*	BigDecimal tt = new BigDecimal(110);
			tt.add(new BigDecimal(110));
			System.out.println( tt);
			for(int i=0; i< ra.getIntvs().length();i++ ){
				tt.add(ra.getIntvs().getElement(i).getS());
				System.out.println(ra.getIntvs().getElement(i).getS());
			} */
			
			//System.out.println( tt);
			//System.out.println( Aggregation.mean(ra));
		//	MReal_V mr3 = BAO.mul(mr1, mr2);
			//MReal_V mr3 = BAO.equalTo(mr1, mr2);
		/*	if (mr3 != null) {
				try {
					for (int i = 0; i < mr3.getUnits().length(); i++) {
						System.out.println("t1: "
								+ mr3.getUnits().getElement(i).getT1() + ", "
								+ "t2:" + mr3.getUnits().getElement(i).getT2());
						System.out.print("b:"+ mr3.getUnits().getElement(i).getB()
								+ ", ");
						System.out.println("c:"+ mr3.getUnits().getElement(i).getC());
						// sortedMGPoint.add(mr.getUnits().getElement(i));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} */
			// gr1 = (GReal_V) rs.getORAData(1, GReal_V.getORADataFactory());
			// MGPoint_V mgp = (MGPoint_V) rs.getORAData(1,
			// MGPoint_V.getORADataFactory());
			// GLine_V gl = (GLine_V) rs.getORAData(1,
			// GLine_V.getORADataFactory());
			// GReal_V gr = (GReal_V) rs.getORAData(1,
			// GReal_V.getORADataFactory());

			// System.out.println(InteractionDomainRange.atpos(gr1, new
			// GPoint(new Integer(0), new Integer(6520), new BigDecimal(0), new
			// Integer(-1))).getVal().doubleValue());
			// Section [] secs = new Section[1];
			// secs[0] = new Section(new Integer(6520), new Integer(-1), new
			// BigDecimal(0), new BigDecimal(1));
			// System.out.println(AggregatesOnFunctions.max(InteractionDomainRange.atgline(gr1,
			// new GLine_V(new Integer(0), new
			// Sections_V(secs)))).doubleValue());

			// System.out.println(InteractionDomainRange.atpos(gr1, new
			// GPoint(new Integer(0), new Integer(6520), new BigDecimal(0), new
			// Integer(-1))).getVal().doubleValue());
			// System.out.println(InteractionDomainRange.at(mgp, new GPoint(new
			// Integer(0), new Integer(6520), new BigDecimal(0), new
			// Integer(-1))).getUnits().getElement(0).getT1().doubleValue());
			// System.out.println(AggregatesOnFunctions.max(gr1).doubleValue());

			// NetworkSpaceToSpace.glineToMultiline(gl, "ARCSV2", "GEOM",
			// "NUM_ARC");
			// Compare.greaterThan(gr, new BigDecimal(-2), "BRINKHOFF_EDGES",
			// "ID", "ID");
			// GLine_V gl = Compare.greaterThan(gr, new BigDecimal(0), "ARCSV2",
			// "VIT_LIM", "NUM_ARC");
			// NetworkSpaceToSpace.glineToMultiline(Compare.greaterThan(gr, new
			// BigDecimal(0), "ARCSV2", "VIT_LIM", "NUM_ARC"), "ARCSV2", "GEOM",
			// "NUM_ARC");
			Numeric.length(gl1, "ARCSV2_BACK", "GEOM", "NUM_ARC");
			System.out.println(gl1.getRints().length());

			/*
			 * statement = " SELECT gspeed FROM BRINKHOFF_GA_GSPEEDS WHERE mo_id =
			 * 10 OR mo_id = 73 "; rs = (OracleResultSet)
			 * st.executeQuery(statement); rs.next(); gr1 = (GReal_V)
			 * rs.getORAData(1, GReal_V.getORADataFactory()); rs.next(); gr2 =
			 * (GReal_V) rs.getORAData(1, GReal_V.getORADataFactory());
			 */

			/*
			 * UGReal[] ugreals = new UGReal[1]; ugreals[0] = new UGReal(new
			 * BigDecimal(0), new BigDecimal(0), new BigDecimal(50), new
			 * BigDecimal(0), new Integer(0), new Section(new Integer(1), new
			 * Integer(-1), new BigDecimal(0), new BigDecimal(1))); gr1 = new
			 * GReal_V(ugreals); gr2 = new GReal_V(ugreals);
			 * System.out.println("xxx"); Compare.compare(gr1, gr2, 0);
			 * System.out.println("yyy ");
			 */

			/*
			 * SpaceToNetworkSpace.intersection("BRINKHOFF_EDGES", new
			 * Query3D(new BigDecimal(5000), new BigDecimal(6000), new
			 * BigDecimal(8000), new BigDecimal(8000), new BigDecimal(0), new
			 * BigDecimal(20)));
			 */
			/*
			 * Get3DTo2DIntersection g3d = new
			 * Get3DTo2DIntersection("BRINKHOFF_EDGES", new Query3D(new
			 * BigDecimal(5000), new BigDecimal(6000), new BigDecimal(8000), new
			 * BigDecimal(8000), new BigDecimal(0), new BigDecimal(20)));
			 */
			/*
			 * Section [] secs = new Section[1]; secs[0] = new Section(new
			 * Integer(6520), new Integer(-1), new BigDecimal(0), new
			 * BigDecimal(1)); GLine_V aGLine = new GLine_V(new Integer(0), new
			 * Sections_V(secs));
			 * System.out.println(Aggregation.min(aGLine).getPos().doubleValue());
			 * System.out.println(Aggregation.max(aGLine).getPos().doubleValue());
			 * System.out.println(Aggregation.center(aGLine).getPos().doubleValue());
			 */

			try {
				dbc.disconnection();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
