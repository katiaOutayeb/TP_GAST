package stateTransitions_v;

import java.sql.SQLException;

import algebraTypes_v.GInt_V;

/*
 * This class implements the no_trasitions operation for gmoving (int) [bool;String]
 */
public class StateTransitions {

	public static int no_transitions (GInt_V gi) {
		if (gi == null)
			return 0;
		
		int no_transitions = 0;
		try {
			for (int i=0; i<gi.getUnits().length()-1; i++) {
				if (gi.getUnits().getElement(i).getVal().doubleValue() != gi.getUnits().getElement(i+1).getVal().doubleValue())
					no_transitions++;
			}
		} catch (SQLException e) { e.printStackTrace();	}
		
		return no_transitions;
	}
}
