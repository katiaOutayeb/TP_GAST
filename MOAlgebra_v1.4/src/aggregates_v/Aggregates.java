package aggregates_v;

import java.math.BigDecimal;
import java.sql.SQLException;

import algebraTypes_v.GReal_V;
import algebraTypes_v.GReal_VP;
import algebraTypes_v.PercentileGRImpl;
import algebraTypes_v.Section;
import algebraTypes_v.UGReal;
import algebraTypes_v.UGReal_V;



/*
 * This class implements the aggregates operations (mean, max, min) for gmoving types
 */
public class Aggregates {

	public static GReal_VP percentileAggIterate(PercentileGRImpl sctx, GReal_V gr)  {
		return null;
	}
	
	public static GReal_VP percentileAggMerge(PercentileGRImpl sctx1, PercentileGRImpl sctx2)  {
		return null;
	}
	
	public static GReal_V percentileAggTerminate(PercentileGRImpl sctx)  {
		UGReal [] temp = new UGReal[1];
		try {
			temp[0] = new UGReal(new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), 
					new Integer(0), new Section(), new Integer(0));
			
			return new GReal_V(new UGReal_V(temp));
		} catch (SQLException e) { e.printStackTrace();	}
		
		return null;
	}
	
}

