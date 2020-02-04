package nonTemporalOperations_v;

import algebraTypes_v.GLine_V;
import algebraTypes_v.GPoint;

/*
 * This class implements the unary predicates for gpoint and GLine_V
 * Subject to lifting
 */
public class UnaryPredicates {
	
	public static int isempty (GPoint gp) {
		if (gp == null)
			return 1;
		return 0;
	}
	public static int undefined (GPoint gp) {
		if (gp == null)
			return 1;
		return 0;
	}
	
	public static int isempty (GLine_V gl) {
		if (gl == null)
			return 1;
		return 0;
	}
	public static int undefined (GLine_V gl) {
		if (gl == null)
			return 1;
		return 0;
	}
	
	// TODO Lifted operations
}
