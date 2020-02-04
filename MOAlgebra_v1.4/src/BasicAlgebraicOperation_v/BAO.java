package BasicAlgebraicOperation_v;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;

import utils_v.UtilityFunctions;
import algebraTypes_v.MReal_V;
import algebraTypes_v.UReal;
import algebraTypes_v.UReal_V;

public class BAO {

	public static MReal_V sum(MReal_V a, MReal_V b) {
		if ((a == null) || (b == null)) {
			return null;
		}

		SortedSet<UReal> sortedMGPoint_a = UtilityFunctions
				.getTimeOrderedMReal(a);
		SortedSet<UReal> sortedMGPoint_b = UtilityFunctions
				.getTimeOrderedMReal(b);

		Iterator it_a = sortedMGPoint_a.iterator();
		Iterator it_b = sortedMGPoint_b.iterator();

		UReal URa = null;
		UReal URb = null;

		ArrayList<UReal> URrl = new ArrayList<UReal>();

		int flag = 0;
		while (flag >= 0) {

			// next a && b
			if (flag == 0) {
				if (it_a.hasNext() && it_b.hasNext()) {
					URa = (UReal) it_a.next();
					URb = (UReal) it_b.next();
				} else {
					flag = -1;
					continue;
				}
			}

			// next a
			if (flag == 1) {
				if (it_a.hasNext()) {
					URa = (UReal) it_a.next();
				} else {
					flag = -1;
					continue;
				}
			}
			// next b
			if (flag == 2) {
				if (it_b.hasNext()) {
					URb = (UReal) it_b.next();
				} else {
					flag = -1;
					continue;
				}
			}

			try {

				BigDecimal at1 = URa.getT1();
				BigDecimal at2 = URa.getT2();
				BigDecimal bt1 = URb.getT1();
				BigDecimal bt2 = URb.getT2();

				// a = b, (at1 =bt1 && at2 =bt2)
				if ((at1.compareTo(bt1) == 0) && (at2.compareTo(bt2) == 0)) {
					// create a new UGReal(at1, at2)
					UReal URr = new UReal();
					URr.setA(URa.getA().add(URb.getA()));
					URr.setB(URa.getB().add(URb.getB()));
					URr.setC(URa.getC().add(URb.getC()));
					URr.setR(new BigDecimal(0));
					URr.setT1(at1);
					URr.setT2(at2);

					URrl.add(URr);

					flag = 0;
					continue;
				}

				// intervala < intervalb, no intersection
				if (at2.compareTo(bt1) == -1) {
					flag = 1;
					continue;
				}

				// intervala > intervalb, no intersection
				if (bt2.compareTo(at1) == -1) {
					flag = 2;
					continue;
				}

				// at1 <= bt1 <= at2 <= bt2
				if ((at1.compareTo(bt1) == -1) && (bt1.compareTo(at2) <= 0)
						&& (at2.compareTo(bt2) == -1)) {
					// create a new UGReal(bt1, at2)
					UReal URr = new UReal();
					URr.setA(URa.getA().add(URb.getA()));
					URr.setB(URa.getB().add(URb.getB()));
					URr.setC(URa.getC().add(URb.getC()));
					URr.setR(new BigDecimal(0));
					URr.setT1(bt1);
					URr.setT2(at2);

					URrl.add(URr);

					flag = 1;
					continue;
				}

				// at1 <= bt1 <= bt2 <= at2
				if ((at1.compareTo(bt1) <= 0) && (bt1.compareTo(bt2) <= 0) &&(bt2.compareTo(at2) <= 0 )) {
					// create a new UGReal(bt1, bt2)

					UReal URr = new UReal();
					URr.setA(URa.getA().add(URb.getA()));
					URr.setB(URa.getB().add(URb.getB()));
					URr.setC(URa.getC().add(URb.getC()));
					URr.setR(new BigDecimal(0));
					URr.setT1(bt1);
					URr.setT2(bt2);

					URrl.add(URr);

					flag = 2;
					continue;
				}

				// bt1 <= at1 <= at2 <= bt2
				if ((bt1.compareTo(at1) <= 0) && (at1.compareTo(at2) <= 0) && (at2.compareTo(bt2) <= 0)) {
					// create a new UGReal(at1, at2)

					UReal URr = new UReal();
					URr.setA(URa.getA().add(URb.getA()));
					URr.setB(URa.getB().add(URb.getB()));
					URr.setC(URa.getC().add(URb.getC()));
					URr.setR(new BigDecimal(0));
					URr.setT1(at1);
					URr.setT2(at2);

					URrl.add(URr);

					flag = 1;
					continue;
				}

				// bt1 <= at1 <= bt2 <= at2
				if ((bt1.compareTo(at1) <=0) && (at1.compareTo(bt2) <= 0 ) && (bt2.compareTo(at2) <=0)) {
					// create a new UGReal(at1, bt2)

					UReal URr = new UReal();
					URr.setA(URa.getA().add(URb.getA()));
					URr.setB(URa.getB().add(URb.getB()));
					URr.setC(URa.getC().add(URb.getC()));
					URr.setR(new BigDecimal(0));
					URr.setT1(at1);
					URr.setT2(bt2);

					URrl.add(URr);

					flag = 2;
					continue;
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (URrl.size() == 0) {
			return null;
		}

		try {
			MReal_V MRr = new MReal_V();
			UReal URtemp[] = new UReal[URrl.size()];
			for (int i = 0; i < URrl.size(); i++) {
				URtemp[i] = URrl.get(i);
			}
			UReal_V UR_V = new UReal_V();

			UR_V.setArray(URtemp);
			MRr.setUnits(UR_V);
			return MRr;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// for(sortedMGPoint_a)

		// in
		// UtilityFunctions.getTimeOrderedMGPoint(a);
		return null;
	}

	public static MReal_V dif(MReal_V a, MReal_V b) {
		if ((a == null) || (b == null)) {
			return null;
		}

		SortedSet<UReal> sortedMGPoint_a = UtilityFunctions
				.getTimeOrderedMReal(a);
		SortedSet<UReal> sortedMGPoint_b = UtilityFunctions
				.getTimeOrderedMReal(b);

		Iterator it_a = sortedMGPoint_a.iterator();
		Iterator it_b = sortedMGPoint_b.iterator();

		UReal URa = null;
		UReal URb = null;

		ArrayList<UReal> URrl = new ArrayList<UReal>();

		int flag = 0;
		while (flag >= 0) {

			// next a && b
			if (flag == 0) {
				if (it_a.hasNext() && it_b.hasNext()) {
					URa = (UReal) it_a.next();
					URb = (UReal) it_b.next();
				} else {
					flag = -1;
					continue;
				}
			}

			// next a
			if (flag == 1) {
				if (it_a.hasNext()) {
					URa = (UReal) it_a.next();
				} else {
					flag = -1;
					continue;
				}
			}
			// next b
			if (flag == 2) {
				if (it_b.hasNext()) {
					URb = (UReal) it_b.next();
				} else {
					flag = -1;
					continue;
				}
			}

			try {

				BigDecimal at1 = URa.getT1();
				BigDecimal at2 = URa.getT2();
				BigDecimal bt1 = URb.getT1();
				BigDecimal bt2 = URb.getT2();

				// a = b, (at1 =bt1 && at2 =bt2)
				if ((at1.compareTo(bt1) == 0) && (at2.compareTo(bt2) == 0)) {
					// create a new UGReal(at1, at2)
					UReal URr = new UReal();
					URr.setA(URa.getA().subtract(URb.getA()));
					URr.setB(URa.getB().subtract(URb.getB()));
					URr.setC(URa.getC().subtract(URb.getC()));
					URr.setR(new BigDecimal(0));
					URr.setT1(at1);
					URr.setT2(at2);

					URrl.add(URr);

					flag = 0;
					continue;
				}

				// intervala < intervalb, no intersection
				if (at2.compareTo(bt1) == -1) {
					flag = 1;
					continue;
				}

				// intervala > intervalb, no intersection
				if (bt2.compareTo(at1) == -1) {
					flag = 2;
					continue;
				}

				// at1 <= bt1 <= at2 <= bt2
				if ((at1.compareTo(bt1) <= 0) && (bt1.compareTo(at2) <=0 ) && (at2.compareTo(bt2) <= 0)) {
					// create a new UGReal(bt1, at2)
					UReal URr = new UReal();
					URr.setA(URa.getA().subtract(URb.getA()));
					URr.setB(URa.getB().subtract(URb.getB()));
					URr.setC(URa.getC().subtract(URb.getC()));
					URr.setR(new BigDecimal(0));
					URr.setT1(bt1);
					URr.setT2(at2);

					URrl.add(URr);

					flag = 1;
					continue;
				}

				// at1 <= bt1 <= bt2 <= at2
				if ((at1.compareTo(bt1) <= 0 ) && (bt1.compareTo(bt2) <= 0) &&(bt2.compareTo(at2) <= 0)) {
					// create a new UGReal(bt1, bt2)

					UReal URr = new UReal();
					URr.setA(URa.getA().subtract(URb.getA()));
					URr.setB(URa.getB().subtract(URb.getB()));
					URr.setC(URa.getC().subtract(URb.getC()));
					URr.setR(new BigDecimal(0));
					URr.setT1(bt1);
					URr.setT2(bt2);

					URrl.add(URr);

					flag = 2;
					continue;
				}

				// bt1 <= at1 <= at2 <= bt2
				if ((bt1.compareTo(at1) <= 0 ) && (at1.compareTo(at2) <= 0 ) &&(at2.compareTo(bt2) <= 0)) {
					// create a new UGReal(at1, at2)

					UReal URr = new UReal();
					URr.setA(URa.getA().subtract(URb.getA()));
					URr.setB(URa.getB().subtract(URb.getB()));
					URr.setC(URa.getC().subtract(URb.getC()));
					URr.setR(new BigDecimal(0));
					URr.setT1(at1);
					URr.setT2(at2);

					URrl.add(URr);

					flag = 1;
					continue;
				}

				// bt1 <= at1 <= bt2 <= at2
				if ((bt1.compareTo(at1) <= 0) && (at1.compareTo(bt2) <=0) && (bt2.compareTo(at2) <= 0)) {
					// create a new UGReal(at1, bt2)

					UReal URr = new UReal();
					URr.setA(URa.getA().subtract(URb.getA()));
					URr.setB(URa.getB().subtract(URb.getB()));
					URr.setC(URa.getC().subtract(URb.getC()));
					URr.setR(new BigDecimal(0));
					URr.setT1(at1);
					URr.setT2(bt2);

					URrl.add(URr);

					flag = 2;
					continue;
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (URrl.size() == 0) {
			return null;
		}

		try {
			MReal_V MRr = new MReal_V();
			UReal URtemp[] = new UReal[URrl.size()];
			for (int i = 0; i < URrl.size(); i++) {
				URtemp[i] = URrl.get(i);
			}
			UReal_V UR_V = new UReal_V();

			UR_V.setArray(URtemp);
			MRr.setUnits(UR_V);
			return MRr;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// for(sortedMGPoint_a)

		// in
		// UtilityFunctions.getTimeOrderedMGPoint(a);
		return null;
	}

	public static MReal_V mul(MReal_V a, MReal_V b) {
		if ((a == null) || (b == null)) {
			return null;
		}

		SortedSet<UReal> sortedMGPoint_a = UtilityFunctions
				.getTimeOrderedMReal(a);
		SortedSet<UReal> sortedMGPoint_b = UtilityFunctions
				.getTimeOrderedMReal(b);

		Iterator it_a = sortedMGPoint_a.iterator();
		Iterator it_b = sortedMGPoint_b.iterator();

		UReal URa = null;
		UReal URb = null;

		ArrayList<UReal> URrl = new ArrayList<UReal>();

		int flag = 0;
		while (flag >= 0) {

			// next a && b
			if (flag == 0) {
				if (it_a.hasNext() && it_b.hasNext()) {
					URa = (UReal) it_a.next();
					URb = (UReal) it_b.next();
				} else {
					flag = -1;
					continue;
				}
			}

			// next a
			if (flag == 1) {
				if (it_a.hasNext()) {
					URa = (UReal) it_a.next();
				} else {
					flag = -1;
					continue;
				}
			}
			// next b
			if (flag == 2) {
				if (it_b.hasNext()) {
					URb = (UReal) it_b.next();
				} else {
					flag = -1;
					continue;
				}
			}

			try {

				BigDecimal at1 = URa.getT1();
				BigDecimal at2 = URa.getT2();
				BigDecimal bt1 = URb.getT1();
				BigDecimal bt2 = URb.getT2();

				// a = b, (at1 =bt1 && at2 =bt2)
				if ((at1.compareTo(bt1) == 0) && (at2.compareTo(bt2) == 0)) {
					// create a new UGReal(at1, at2)
					BigDecimal cc = multoc(URa.getA(), URa.getB(), URa.getC(),
							URb.getA(), URb.getB(), URb.getC(), at1, at2);
					if (cc != null) {
						UReal URr = new UReal();

						URr.setA(new BigDecimal(0));
						URr.setB(new BigDecimal(0));
						URr.setC(cc);
						URr.setR(new BigDecimal(0));
						URr.setT1(at1);
						URr.setT2(at2);

						URrl.add(URr);
					}
					flag = 0;
					continue;
				}

				// intervala < intervalb, no intersection
				if (at2.compareTo(bt1) == -1) {
					flag = 1;
					continue;
				}

				// intervala > intervalb, no intersection
				if (bt2.compareTo(at1) == -1) {
					flag = 2;
					continue;
				}

				// at1 <= bt1 <= at2 <= bt2
				if ((at1.compareTo(bt1) <= 0) && (bt1.compareTo(at2) <=0 ) &&(at2.compareTo(bt2) <=0)) {
					// create a new UGReal(bt1, at2)
					BigDecimal cc = multoc(URa.getA(), URa.getB(), URa.getC(),
							URb.getA(), URb.getB(), URb.getC(), bt1, at2);
					if (cc != null) {
						UReal URr = new UReal();

						URr.setA(new BigDecimal(0));
						URr.setB(new BigDecimal(0));
						URr.setC(cc);
						URr.setR(new BigDecimal(0));
						URr.setT1(bt1);
						URr.setT2(at2);

						URrl.add(URr);
					}

					flag = 1;
					continue;
				}

				// at1 <= bt1 <= bt2 <= at2
				if ((at1.compareTo(bt1) <=0 ) && (bt1.compareTo(bt2) <=0 ) && (bt2.compareTo(at2) <= 0)) {
					// create a new UGReal(bt1, bt2)
					BigDecimal cc = multoc(URa.getA(), URa.getB(), URa.getC(),
							URb.getA(), URb.getB(), URb.getC(), bt1, bt2);
					if (cc != null) {
						UReal URr = new UReal();

						URr.setA(new BigDecimal(0));
						URr.setB(new BigDecimal(0));
						URr.setC(cc);
						URr.setR(new BigDecimal(0));
						URr.setT1(bt1);
						URr.setT2(bt2);

						URrl.add(URr);
					}

					flag = 2;
					continue;
				}

				// bt1 <= at1 <= at2 <= bt2
				if ((bt1.compareTo(at1) <= 0 ) && (at1.compareTo(at2) <=0 ) && (at2.compareTo(bt2) <=0 )) {
					// create a new UGReal(at1, at2)
					BigDecimal cc = multoc(URa.getA(), URa.getB(), URa.getC(),
							URb.getA(), URb.getB(), URb.getC(), at1, at2);
					if (cc != null) {
						UReal URr = new UReal();

						URr.setA(new BigDecimal(0));
						URr.setB(new BigDecimal(0));
						URr.setC(cc);
						URr.setR(new BigDecimal(0));
						URr.setT1(at1);
						URr.setT2(at2);

						URrl.add(URr);
					}

					flag = 1;
					continue;
				}

				// bt1 <= at1 <= bt2 <= at2
				if ((bt1.compareTo(at1) <= 0) && (at1.compareTo(bt2) <=0 ) && (bt2.compareTo(at2) <=0)) {
					// create a new UGReal(at1, bt2)
					BigDecimal cc = multoc(URa.getA(), URa.getB(), URa.getC(),
							URb.getA(), URb.getB(), URb.getC(), at1, bt2);
					if (cc != null) {
						UReal URr = new UReal();

						URr.setA(new BigDecimal(0));
						URr.setB(new BigDecimal(0));
						URr.setC(cc);
						URr.setR(new BigDecimal(0));
						URr.setT1(at1);
						URr.setT2(bt2);

						URrl.add(URr);
					}

					flag = 2;
					continue;
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (URrl.size() == 0) {
			return null;
		}

		try {
			MReal_V MRr = new MReal_V();
			UReal URtemp[] = new UReal[URrl.size()];
			for (int i = 0; i < URrl.size(); i++) {
				URtemp[i] = URrl.get(i);
			}
			UReal_V UR_V = new UReal_V();

			UR_V.setArray(URtemp);
			MRr.setUnits(UR_V);
			return MRr;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// for(sortedMGPoint_a)

		// in
		// UtilityFunctions.getTimeOrderedMGPoint(a);
		return null;
	}

	public static MReal_V div(MReal_V a, MReal_V b) {
		if ((a == null) || (b == null)) {
			return null;
		}
		return null;

	}

	public static BigDecimal multoc1(BigDecimal a1, BigDecimal b1,
			BigDecimal c1, BigDecimal a2, BigDecimal b2, BigDecimal c2,
			BigDecimal t1, BigDecimal t2) {

		if(t1.compareTo(t2) == 0){
			return null;
		}
		double A ,B ,C , CC;
		// A = b1*b2/3
		A = b1.doubleValue()*b2.doubleValue() /3;
		
		// B = (b1*c2+b2*c1)/2
		B = (b1.doubleValue() * c2.doubleValue() + b2.doubleValue()*c1.doubleValue())/2;
		
		// C = c1 + c2
		C = c1.doubleValue() + c2.doubleValue();
		// CC = A*(t1^2+t2^2 + t1*t2) + B*(t1+t2) + C
		CC = A*(t1.doubleValue()*t1.doubleValue() +t2.doubleValue()*t2.doubleValue() + t1.doubleValue()*t2.doubleValue()) + B*(t1.doubleValue()+t2.doubleValue()) + C;
		return new BigDecimal( CC);

	}
	
	public static BigDecimal multoc(BigDecimal a1, BigDecimal b1,
			BigDecimal c1, BigDecimal a2, BigDecimal b2, BigDecimal c2,
			BigDecimal t1, BigDecimal t2) {
		if(t1.compareTo(t2) == 0){
			return null;
		}
		double A1 ,B1 , A2 ,B2 , CC1, CC2, CC ;
		
		A1 = b1.doubleValue()/2;
		B1 = c1.doubleValue();
		CC1 = A1*(t1.doubleValue() + t2.doubleValue()) + B1;
		
		A2 = b2.doubleValue()/2;
		B2 = c2.doubleValue();
		CC2 = A2*(t1.doubleValue() + t2.doubleValue()) + B2;
		
		CC= CC1 * CC2;
		
		 
		return new BigDecimal( CC);
		
	}
}
