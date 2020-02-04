package utils_v;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import oracle.spatial.geometry.JGeometry;
import oracle.sql.STRUCT;

/*
 * This class performs the 3D to 2D intersection of a 3D query with a 2D network 
 */
public class Get3DTo2DIntersection {

	private Query3D q3D;
	private SortedSet q2D; // contains Query2D
	
	private ArrayList intersectedEdgeIds; // contains Integer
	
	private Connection con;
	private Statement st, stPrim;
	private String tableName;
	
	public Get3DTo2DIntersection(String tn, Query3D q) {
		tableName = tn;
		q3D = q;
		q2D = new TreeSet (new Comparator () {
							public int compare (Object a, Object b) {
								Integer ia = new Integer(((Query2D)a).id); Integer ib = new Integer(((Query2D)b).id);
								return ia.compareTo(ib);
							}});
		
		// use the existing connection to Oracle
		try {
			con = DriverManager.getConnection("jdbc:default:connection:");
		} catch (SQLException e) { e.printStackTrace(); }
		
		// get the list of edges intersecting the 3D query
		getIntersectedEdgeIds ();
		
		// for each intersected route section get the measure interval
		getIntersectedMeasureInterval (); 
	}

	private void getIntersectedEdgeIds() {
		String statement;
		ResultSet rs;
		int counter;
		
		try {
			st = con.createStatement();
			statement = "SELECT E.ID " +
					" FROM " + tableName+ " E " +
					" WHERE SDO_RELATE ( E.ROUTE_GEOMETRY , " +
					" MDSYS.SDO_GEOMETRY( 2003 , NULL , NULL, " +
					" MDSYS.SDO_ELEM_INFO_ARRAY ( 1, 1003, 1 ) , " +
					" MDSYS.SDO_ORDINATE_ARRAY ( " 
						+ q3D.getXmincoordinate() + " , " + q3D.getYmincoordinate() + " , "
						+ q3D.getXmincoordinate() + " , " + q3D.getYmaxcoordinate() + " , "
						+ q3D.getXmaxcoordinate() + " , " + q3D.getYmaxcoordinate() + " , " 
						+ q3D.getXmaxcoordinate() + " , " + q3D.getYmincoordinate() + " , "
						+ q3D.getXmincoordinate() + " , " + q3D.getYmincoordinate() + " ) ) , " +  
					" 'mask=ANYINTERACT querytype=WINDOW') = 'TRUE'";
			
			rs = st.executeQuery(statement);
		//	System.out.println(statement);
			
			intersectedEdgeIds = new ArrayList();
			counter = 0;
			while (rs.next()) {
				intersectedEdgeIds.add(new Integer (rs.getString(1)));
				counter++;
			}

		/*	if (counter == 0)
				System.out.println("No intersection for query = " + q3D + " !!!");
			else {
				System.out.println("counter = " + counter);
				System.out.println(intersectedEdgeIds);
			} */ 
			
			rs.close();
			st.close();
		} catch (SQLException se) { se.printStackTrace(); }
	}

	private void getIntersectedMeasureInterval() {
		String statement;
		ResultSet rs;
		float minMeasure = -1, maxMeasure = -1, temp;
		double [] ordinateArray;
		
		// for each intersected route section get the measure interval
		for (int i=0; i<intersectedEdgeIds.size(); i++) {
			
			// first, get the geometry resulted from the intersection of the query and the route section
			// then, get the measures for each of the two ends of the resulting segment (if two)
			try {
				st = con.createStatement();
				
				statement = " SELECT SDO_GEOM.SDO_INTERSECTION ( E.ROUTE_GEOMETRY, SDO_GEOMETRY ( 2003, NULL, NULL, " +
						" MDSYS.SDO_ELEM_INFO_ARRAY ( 1, 1003, 1 ) , " +
						" MDSYS.SDO_ORDINATE_ARRAY ( " 
							+ q3D.getXmincoordinate() + " , " + q3D.getYmincoordinate() + " , "
							+ q3D.getXmincoordinate() + " , " + q3D.getYmaxcoordinate() + " , "
							+ q3D.getXmaxcoordinate() + " , " + q3D.getYmaxcoordinate() + " , "
							+ q3D.getXmaxcoordinate() + " , " + q3D.getYmincoordinate() + " , "
							+ q3D.getXmincoordinate() + " , " + q3D.getYmincoordinate() + " ) ) , 0.005 ) " +
						" FROM " + tableName + " E " +
						" WHERE E.ID = " + ((Integer)intersectedEdgeIds.get(i)).intValue() ;
			//	System.out.println(statement);
				rs = st.executeQuery(statement);
				
				while (rs.next()) {
					// reading a geometry from database
					STRUCT geomStruct = (oracle.sql.STRUCT) rs.getObject(1);
					// convert STRUCT into geometry
					JGeometry j_geom = JGeometry.load(geomStruct);
				//	System.out.println(j_geom);
				
					Query2D new2DQ = new Query2D();
					ordinateArray = j_geom.getOrdinatesArray();
					if (j_geom.getType() == 1) { // only one poit as the intersection result
						// compute the measure of the point
						minMeasure = computePointMeasure (((Integer)intersectedEdgeIds.get(i)).intValue(), ordinateArray[0], ordinateArray[1]);
						// create new 2D query
						new2DQ.isWindow = false;
						new2DQ.minMeasure = minMeasure;
						new2DQ.maxMeasure = minMeasure;
					}
					else { // intersection is a segment
						// compute the measure of the points
						minMeasure = computePointMeasure (((Integer)intersectedEdgeIds.get(i)).intValue(), ordinateArray[0], ordinateArray[1]);
						maxMeasure = computePointMeasure (((Integer)intersectedEdgeIds.get(i)).intValue(), ordinateArray[3], ordinateArray[4]);
						if (minMeasure > maxMeasure) {
							temp = minMeasure;
							minMeasure = maxMeasure;
							maxMeasure = temp;
						}
						// create new 2D query
						new2DQ.isWindow = true;
						new2DQ.minMeasure = minMeasure;
						new2DQ.maxMeasure = maxMeasure;
					}
					new2DQ.id = ((Integer)intersectedEdgeIds.get(i)).intValue();
					new2DQ.tMin = q3D.getTmin().floatValue();
					new2DQ.tMax = q3D.getTmax().floatValue();
					q2D.add(new2DQ);
				}
				rs.close();
				st.close();
			//	System.out.println("minMeasure = " + minMeasure + " maxMeasure = " + maxMeasure);
			//	System.out.println("no q2D queries = " + q2D.size());
				
			} catch (SQLException se) { se.printStackTrace(); }
		}
	}

	private float computePointMeasure(int edgeId, double x, double y) {
		String statement;
		ResultSet rs;
		float measure = 0;
		
		try {
			stPrim = con.createStatement();
			statement = "SELECT SDO_LRS.GET_MEASURE ( SDO_LRS.PROJECT_PT ( E.ROUTE_GEOMETRY , M.DIMINFO , " +
					" SDO_GEOMETRY ( 2001, NULL, SDO_POINT_TYPE ( " + x + " , " + y + 
					" , NULL ) , NULL, NULL ) , M.DIMINFO ) ) " +
					" FROM " + tableName + " E , USER_SDO_GEOM_METADATA M " +
					" WHERE M.TABLE_NAME = '" + tableName + "' AND M.COLUMN_NAME = 'ROUTE_GEOMETRY' " +
					" AND E.ID = " + edgeId;
			
		//	System.out.println(statement);
			rs = st.executeQuery(statement);
			
			while (rs.next()) {
			//	System.out.println("Test 1: measure = " + rs.getString(1) + " " + rs.getFloat(1));
				measure = rs.getFloat(1);
			}
			rs.close();
			stPrim.close();
		} catch (SQLException se) { se.printStackTrace(); }
		return measure;
	}
	
	public SortedSet getTQueries() {
		return this.q2D;
	}
}
