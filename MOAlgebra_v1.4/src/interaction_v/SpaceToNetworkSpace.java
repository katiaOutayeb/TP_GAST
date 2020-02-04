package interaction_v;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Iterator;

import utils_v.Get3DTo2DIntersection;
import utils_v.Query2D;
import utils_v.Query3D;
import algebraTypes_v.GLine_V;
import algebraTypes_v.Section;
import algebraTypes_v.Sections_V;

/*
 * This class implements the interaction functions 
 * between the Space and the Network Space
 */
public class SpaceToNetworkSpace {

	// This function returns the intersection between a 3D query and network given as 
	// a table name (and stocked as road id, road segment) 
	// The intersection result is a GLine_V 
	public static GLine_V intersection (String networkTable, Query3D q3d) {
		if (q3d == null)
			return null;
		
		Get3DTo2DIntersection q3d2di = new Get3DTo2DIntersection(networkTable, q3d);
		
		Section secs[] = new Section[q3d2di.getTQueries().size()];
		Iterator it = q3d2di.getTQueries().iterator();
		int counter = 0;
		while (it.hasNext()) {
			System.out.println(counter);
			Query2D temp = (Query2D)it.next();
			try {
				secs[counter++] = new Section(new Integer(temp.id), new Integer(-1), new BigDecimal(temp.minMeasure), new BigDecimal(temp.maxMeasure));
			} catch (SQLException e) { e.printStackTrace(); }
		}
		
		try {
			return new GLine_V(new Integer(0), new Sections_V(secs));
		} catch (SQLException e) { e.printStackTrace();	}
		
		return null;
	}
}
