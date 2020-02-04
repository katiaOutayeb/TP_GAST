package interaction_v;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import oracle.spatial.geometry.JGeometry;
import oracle.sql.STRUCT;

import algebraTypes_v.GLine_V;
import SdoGeometryType.SdoElemInfoArray;
import SdoGeometryType.SdoGeometry;
import SdoGeometryType.SdoOrdinateArray;

/*
 * This class implements the interaction functions 
 * between the Network Space and the 2D Space 
 */
public class NetworkSpaceToSpace {

	private static Connection con;
	private static Statement st;
	/*
	 * This function transforms a GLine_V into a 2D Multi-polyline
	 * The transformation uses a 2D LRS network table indicated by (tableName, geomColName, ridColName) 
	 */
	public static SdoGeometry glineToMultiline(GLine_V gl, String tableName, String geomColName, String ridColName) {
		if (gl == null)
			return null;
		
		String statement;
		ResultSet rs;
		int ordinateLength = 0, currentOffset = 0;
		double [] ordinateArray;
		ArrayList<ArrayList<BigDecimal>> ordinateList = new ArrayList<ArrayList<BigDecimal>>();

		// use the existing connection to Oracle
		
	/*	DBConnection dbc = new DBConnection("orcl", "sysman", "iulius777");
		try {
			try {
				dbc.connection();
		} catch (ClassNotFoundException e) { e.printStackTrace(); }
		} catch (SQLException e) {e.printStackTrace();} */ 
		
		try {
			con = DriverManager.getConnection("jdbc:default:connection:");
		} catch (SQLException e) { e.printStackTrace(); } 
		
		/*
		 * for each section of the GLine_V obtain the clipped geometry (a polyline) and add it to the final multiline  
		 */
		try {
			st = con.createStatement();
			
		//	System.out.println(gl.getRints().length());
			for (int i=0; i<gl.getRints().length(); i++) {
				statement = " SELECT SDO_LRS.CLIP_GEOM_SEGMENT( " + geomColName + " , " + 
								gl.getRints().getElement(i).getPos1().setScale(6, BigDecimal.ROUND_HALF_DOWN) + " , " + gl.getRints().getElement(i).getPos2().setScale(6, BigDecimal.ROUND_HALF_DOWN) + " ) " +
							" FROM " + tableName + 
							" WHERE " + ridColName + " = " + gl.getRints().getElement(i).getRid();
				rs = st.executeQuery(statement);
			//	System.out.println(statement);
				
				while (rs.next()) {
					// reading a geometry from database
					STRUCT geomStruct = (oracle.sql.STRUCT) rs.getObject(1);
					// convert STRUCT into geometry
				//	if (geomStruct == null)
				//		break;
					JGeometry j_geom;
					try {
						j_geom = JGeometry.load(geomStruct);
						ordinateList.add(new ArrayList<BigDecimal>());
						ordinateArray = j_geom.getOrdinatesArray();
						if (j_geom.getType() == 1) { // only one poit as the intersection result
							ordinateList.get(ordinateList.size()-1).add(new BigDecimal(ordinateArray[0]));
							ordinateList.get(ordinateList.size()-1).add(new BigDecimal(ordinateArray[1]));
							ordinateLength += 2;
						}
						else { // intersection is a polyline
							for (int j=0; j<ordinateArray.length-2; j+=3) {
							//	System.out.println("i = " + i);
								ordinateList.get(ordinateList.size()-1).add(new BigDecimal(ordinateArray[j]));
								ordinateList.get(ordinateList.size()-1).add(new BigDecimal(ordinateArray[j+1]));
								ordinateLength += 2;
							}
						}
					} catch(NumberFormatException se) {	//se.printStackTrace(); 
						}
					break;
				}
				rs.close();
			}
			st.close();
		} catch (SQLException se) { se.printStackTrace(); }
		
	//	System.out.println("Size = " + ordinateList.size());
		
		SdoGeometry sg = new SdoGeometry();
		BigDecimal [] sei = new BigDecimal[3*ordinateList.size()];
		BigDecimal [] soa = new BigDecimal[ordinateLength];
		for (int i=0; i<ordinateList.size(); i++) {
			sei[3*i + 0] = new BigDecimal(currentOffset + 1);
			if (ordinateList.get(i).size() != 2) 
				sei[3*i + 1] = new BigDecimal(2);
			else // point
				sei[3*i + 1] = new BigDecimal(1);
			sei[3*i + 2] = new BigDecimal(1);
			
			for (int j=0; j<ordinateList.get(i).size(); j++) {
				soa[currentOffset] = new BigDecimal(ordinateList.get(i).get(j).doubleValue());
				currentOffset++;
			}
		}
		
		try {
			sg.setSdoGtype(new BigDecimal(2006));
			sg.setSdoSrid(null);
			sg.setSdoPoint(null);
			sg.setSdoElemInfo(new SdoElemInfoArray(sei));
			sg.setSdoOrdinates(new SdoOrdinateArray(soa));
		} catch (SQLException e) { e.printStackTrace(); }
		
		
		return sg;
	}
}
