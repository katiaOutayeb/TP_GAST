package nonTemporalOperations_v;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import algebraTypes_v.GLine_V;
import algebraTypes_v.Range_V;

/*
 * This class implements the numeric operations for GLine_V
 * Subject to lifting
 */
public class Numeric {

	private static Connection con;
	private static Statement st;
	
	public static int no_components (GLine_V gl) {
		try {
			return gl.getRints().length();
		} catch (SQLException e) { e.printStackTrace();	}
		return 0;
	}
	public static int no_components (Range_V r) {
		try {
			if (r != null)
				return r.getIntvs().length();
		} catch (SQLException e) { e.printStackTrace();	}
		return 0;
	}
	
	public static BigDecimal duration (Range_V r) {
		double temp = 0;
		if (r == null)
			return null;
		try {
			for (int i=0; i<r.getIntvs().length(); i++) {
				temp += r.getIntvs().getElement(i).getE().doubleValue();
				temp -= r.getIntvs().getElement(i).getS().doubleValue();
			//	len.add(r.getIntvs().getElement(i).getE());
			//	len.subtract(r.getIntvs().getElement(i).getS());
			}
		} catch (SQLException e) { e.printStackTrace();	}
		
		return new BigDecimal(temp);
	}
	
	// TODO
	public static BigDecimal size (GLine_V gl) {
		BigDecimal len = new BigDecimal(0);
		try {
			for (int i=0; i<gl.getRints().length(); i++) {
				len.add(gl.getRints().getElement(i).getPos2());
				len.subtract(gl.getRints().getElement(i).getPos1());
			}
		} catch (SQLException e) { e.printStackTrace();	}
		
		return len;
	}
	// TODO
	public static BigDecimal length (GLine_V gl) {
		BigDecimal len = new BigDecimal(0);
		try {
			for (int i=0; i<gl.getRints().length(); i++) {
				len.add(gl.getRints().getElement(i).getPos2());
				len.subtract(gl.getRints().getElement(i).getPos1());
			}
		} catch (SQLException e) { e.printStackTrace();	}
		
		return len;
	}
	
	public static BigDecimal length (GLine_V gl, String tableName, String geomColName, String ridColName) {
		double len = 0;
		String statement;
		ResultSet rs;

		// use the existing connection to Oracle
		
	/*	DBConnection dbc = new DBConnection("orcl", "sysman", "iulius");
		try {
			try {
				dbc.connection();
		} catch (ClassNotFoundException e) { e.printStackTrace(); }
		} catch (SQLException e) {e.printStackTrace();} 
	*/	
		try {
			con = DriverManager.getConnection("jdbc:default:connection:");
			st = con.createStatement();
			
			for (int i=0; i<gl.getRints().length(); i++) {
				statement = " SELECT SDO_GEOM.SDO_LENGTH ( SDO_LRS.CLIP_GEOM_SEGMENT( " + geomColName + " , " + 
								gl.getRints().getElement(i).getPos1().setScale(6, BigDecimal.ROUND_HALF_DOWN) + " , " + 
								gl.getRints().getElement(i).getPos2().setScale(6, BigDecimal.ROUND_HALF_DOWN) + " ), M.DIMINFO, 'unit=M' ) " +
							" FROM " + tableName + ", USER_SDO_GEOM_METADATA M " +
							" WHERE " + ridColName + " = " + gl.getRints().getElement(i).getRid() + 
								" AND M.TABLE_NAME = '" + tableName + "' AND M.COLUMN_NAME = '" + geomColName +"'";
			//	System.out.println(statement);
				rs = st.executeQuery(statement);
				
				while (rs.next()) {
					len += rs.getBigDecimal(1).doubleValue();
				//	System.out.println(len);
					break;
				}
				rs.close();
			}
			st.close();
		} catch (SQLException e) { e.printStackTrace();	}
		
	//	System.out.println(len);
		return new BigDecimal(len);
	}
}
