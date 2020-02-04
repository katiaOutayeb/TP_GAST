package BasicAlgebraicOperation_v;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;

import utils_v.UtilityFunctions;
import algebraTypes_v.GReal_V;
import algebraTypes_v.Interval_V;
import algebraTypes_v.MReal_V;
import algebraTypes_v.Section;
import algebraTypes_v.UGReal;
import algebraTypes_v.UGReal_V;
import algebraTypes_v.UReal;
import algebraTypes_v.UReal_V;

public class Comparisons {
	public static MReal_V greaterThan(MReal_V a, MReal_V b) {
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
					Interval_V itv = intervalGreaterThan(URa.getA(),
							URa.getB(), URa.getC(), URb.getA(), URb.getB(), URb
									.getC(), at1, at2);
					if (itv != null) {
						UReal URr = new UReal();
						URr.setA(URa.getA());
						URr.setB(URa.getB());
						URr.setC(URa.getC());
						URr.setR(new BigDecimal(0));
						URr.setT1(itv.getS());
						URr.setT2(itv.getE());

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
				if (at1.compareTo(bt2) == 1) {
					flag = 2;
					continue;
				}

				// at1 <= bt1 <= at2 <= bt2
				if ((at1.compareTo(bt1) <= 0) && (bt1.compareTo(at2) <= 0)
						&& (at2.compareTo(bt2) <= 0)) {
					// create a new UGReal(bt1, at2)
					Interval_V itv = intervalGreaterThan(URa.getA(),
							URa.getB(), URa.getC(), URb.getA(), URb.getB(), URb
									.getC(), bt1, at2);
					if (itv != null) {
						UReal URr = new UReal();
						URr.setA(URa.getA());
						URr.setB(URa.getB());
						URr.setC(URa.getC());
						URr.setR(new BigDecimal(0));
						URr.setT1(itv.getS());
						URr.setT2(itv.getE());

						URrl.add(URr);
					}
					flag = 1;
					continue;
				}

				// at1 <= bt1 <= bt2 <= at2
				if ((at1.compareTo(bt1) <= 0) && (bt1.compareTo(bt2) <= 0)
						&& (bt2.compareTo(at2) <= 0)) {
					// create a new UGReal(bt1, bt2)

					Interval_V itv = intervalGreaterThan(URa.getA(),
							URa.getB(), URa.getC(), URb.getA(), URb.getB(), URb
									.getC(), bt1, bt2);
					if (itv != null) {
						UReal URr = new UReal();
						URr.setA(URa.getA());
						URr.setB(URa.getB());
						URr.setC(URa.getC());
						URr.setR(new BigDecimal(0));
						URr.setT1(itv.getS());
						URr.setT2(itv.getE());

						URrl.add(URr);
					}

					flag = 2;
					continue;
				}

				// bt1 <= at1 <= at2 <= bt2
				if ((bt1.compareTo(at1) <= 0) && (at1.compareTo(at2) <= 0)
						&& (at2.compareTo(bt2) <= 0)) {
					// create a new UGReal(at1, at2)
					Interval_V itv = intervalGreaterThan(URa.getA(),
							URa.getB(), URa.getC(), URb.getA(), URb.getB(), URb
									.getC(), at1, at2);
					if (itv != null) {
						UReal URr = new UReal();
						URr.setA(URa.getA());
						URr.setB(URa.getB());
						URr.setC(URa.getC());
						URr.setR(new BigDecimal(0));
						URr.setT1(itv.getS());
						URr.setT2(itv.getE());

						URrl.add(URr);
					}

					flag = 1;
					continue;
				}

				// bt1 <= at1 <= bt2 <= at2
				if ((bt1.compareTo(at1) <= 0) && (at1.compareTo(bt2) <= 0)
						&& (bt2.compareTo(at2) <= 0)) {
					// create a new UGReal(at1, bt2)

					Interval_V itv = intervalGreaterThan(URa.getA(),
							URa.getB(), URa.getC(), URb.getA(), URb.getB(), URb
									.getC(), at1, bt2);
					if (itv != null) {
						UReal URr = new UReal();
						URr.setA(URa.getA());
						URr.setB(URa.getB());
						URr.setC(URa.getC());
						URr.setR(new BigDecimal(0));
						URr.setT1(itv.getS());
						URr.setT2(itv.getE());

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
		return null;
	}

	public static MReal_V smallerThan(MReal_V a, MReal_V b) {
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
					Interval_V itv = intervalSmallerThan(URa.getA(),
							URa.getB(), URa.getC(), URb.getA(), URb.getB(), URb
									.getC(), at1, at2);
					if (itv != null) {

						UReal URr = new UReal();
						URr.setA(URa.getA());
						URr.setB(URa.getB());
						URr.setC(URa.getC());
						URr.setR(new BigDecimal(0));
						URr.setT1(itv.getS());
						URr.setT2(itv.getE());

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
				if ((at1.compareTo(bt1) <= 0) && (bt1.compareTo(at2) <= 0)
						&& (at2.compareTo(bt2) <= 0)) {
					// create a new UGReal(bt1, at2)
					Interval_V itv = intervalSmallerThan(URa.getA(),
							URa.getB(), URa.getC(), URb.getA(), URb.getB(), URb
									.getC(), bt1, at2);
					if (itv != null) {
						UReal URr = new UReal();
						URr.setA(URa.getA());
						URr.setB(URa.getB());
						URr.setC(URa.getC());
						URr.setR(new BigDecimal(0));
						URr.setT1(itv.getS());
						URr.setT2(itv.getE());

						URrl.add(URr);
					}
					flag = 1;
					continue;
				}

				// at1 <= bt1 <= bt2 <= at2
				if ((at1.compareTo(bt1) <= 0) && (bt1.compareTo(bt2) <= 0)
						&& (bt2.compareTo(at2) <= 0)) {
					// create a new UGReal(bt1, bt2)

					Interval_V itv = intervalSmallerThan(URa.getA(),
							URa.getB(), URa.getC(), URb.getA(), URb.getB(), URb
									.getC(), bt1, bt2);
					if (itv != null) {
						UReal URr = new UReal();
						URr.setA(URa.getA());
						URr.setB(URa.getB());
						URr.setC(URa.getC());
						URr.setR(new BigDecimal(0));
						URr.setT1(itv.getS());
						URr.setT2(itv.getE());

						URrl.add(URr);
					}

					flag = 2;
					continue;
				}

				// bt1 <= at1 <= at2 <= bt2
				if ((bt1.compareTo(at1) <= 0) && (at1.compareTo(at2) <= 0)
						&& (at2.compareTo(bt2) <= 0)) {
					// create a new UGReal(at1, at2)
					Interval_V itv = intervalSmallerThan(URa.getA(),
							URa.getB(), URa.getC(), URb.getA(), URb.getB(), URb
									.getC(), at1, at2);
					if (itv != null) {
						if (itv.getS().subtract(itv.getS()).abs().compareTo(
								new BigDecimal(0.1)) <= 0) {
							UReal URr = new UReal();
							URr.setA(URa.getA());
							URr.setB(URa.getB());
							URr.setC(URa.getC());
							URr.setR(new BigDecimal(0));
							URr.setT1(itv.getS());
							URr.setT2(itv.getE());

							URrl.add(URr);
						}
					}

					flag = 1;
					continue;
				}

				// bt1 <= at1 <= bt2 <= at2
				if ((bt1.compareTo(at1) <= 0) && (at1.compareTo(bt2) <= 0)
						&& (bt2.compareTo(at2) <= 0)) {
					// create a new UGReal(at1, bt2)

					Interval_V itv = intervalSmallerThan(URa.getA(),
							URa.getB(), URa.getC(), URb.getA(), URb.getB(), URb
									.getC(), at1, bt2);
					if (itv != null) {
						UReal URr = new UReal();
						URr.setA(URa.getA());
						URr.setB(URa.getB());
						URr.setC(URa.getC());
						URr.setR(new BigDecimal(0));
						URr.setT1(itv.getS());
						URr.setT2(itv.getE());

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
		return null;
	}

	public static MReal_V equalTo(MReal_V a, MReal_V b) {
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
					Interval_V itv = intervalEqualTo(URa.getA(), URa.getB(),
							URa.getC(), URb.getA(), URb.getB(), URb.getC(),
							at1, at2);
					if (itv != null) {
						UReal URr = new UReal();
						URr.setA(URa.getA());
						URr.setB(URa.getB());
						URr.setC(URa.getC());
						URr.setR(new BigDecimal(0));
						URr.setT1(itv.getS());
						URr.setT2(itv.getE());

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
				if ((at1.compareTo(bt1) <= 0) && (bt1.compareTo(at2) <= 0)
						&& (at2.compareTo(bt2) <= 0)) {
					// create a new UGReal(bt1, at2)
					Interval_V itv = intervalEqualTo(URa.getA(), URa.getB(),
							URa.getC(), URb.getA(), URb.getB(), URb.getC(),
							bt1, at2);
					if (itv != null) {
						UReal URr = new UReal();
						URr.setA(URa.getA());
						URr.setB(URa.getB());
						URr.setC(URa.getC());
						URr.setR(new BigDecimal(0));
						URr.setT1(itv.getS());
						URr.setT2(itv.getE());

						URrl.add(URr);
					}
					flag = 1;
					continue;
				}

				// at1 <= bt1 <= bt2 <= at2
				if ((at1.compareTo(bt1) <= 0) && (bt1.compareTo(bt2) <= 0)
						&& (bt2.compareTo(at2) <= 0)) {
					// create a new UGReal(bt1, bt2)

					Interval_V itv = intervalEqualTo(URa.getA(), URa.getB(),
							URa.getC(), URb.getA(), URb.getB(), URb.getC(),
							bt1, bt2);
					if (itv != null) {
						UReal URr = new UReal();
						URr.setA(URa.getA());
						URr.setB(URa.getB());
						URr.setC(URa.getC());
						URr.setR(new BigDecimal(0));
						URr.setT1(itv.getS());
						URr.setT2(itv.getE());

						URrl.add(URr);
					}

					flag = 2;
					continue;
				}

				// bt1 <= at1 <= at2 <= bt2
				if ((bt1.compareTo(at1) <= 0) && (at1.compareTo(at2) <= 0)
						&& (at2.compareTo(bt2) <= 0)) {
					// create a new UGReal(at1, at2)
					Interval_V itv = intervalEqualTo(URa.getA(), URa.getB(),
							URa.getC(), URb.getA(), URb.getB(), URb.getC(),
							at1, at2);
					if (itv != null) {
						UReal URr = new UReal();
						URr.setA(URa.getA());
						URr.setB(URa.getB());
						URr.setC(URa.getC());
						URr.setR(new BigDecimal(0));
						URr.setT1(itv.getS());
						URr.setT2(itv.getE());

						URrl.add(URr);
					}

					flag = 1;
					continue;
				}

				// bt1 <= at1 <= bt2 <= at2
				if ((bt1.compareTo(at1) <= 0) && (at1.compareTo(bt2) <= 0)
						&& (bt2.compareTo(at2) <= 0)) {
					// create a new UGReal(at1, bt2)

					Interval_V itv = intervalEqualTo(URa.getA(), URa.getB(),
							URa.getC(), URb.getA(), URb.getB(), URb.getC(),
							at1, bt2);
					if (itv != null) {
						UReal URr = new UReal();
						URr.setA(URa.getA());
						URr.setB(URa.getB());
						URr.setC(URa.getC());
						URr.setR(new BigDecimal(0));
						URr.setT1(itv.getS());
						URr.setT2(itv.getE());

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
			// return MRr;
			return new MReal_V(new UReal_V(URtemp));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static MReal_V between(MReal_V a, MReal_V b, MReal_V c) {
		if ((a == null) || (b == null) || (c == null)) {
			return null;
		}

		return smallerThan(greaterThan(a, b), c);

		// return null;
	}

	public static Interval_V intervalGreaterThan(BigDecimal a1, BigDecimal b1,
			BigDecimal c1, BigDecimal a2, BigDecimal b2, BigDecimal c2,
			BigDecimal t1, BigDecimal t2) {
		if (b1.doubleValue() == b2.doubleValue()) {
			if (c1.doubleValue() > c2.doubleValue()) {

				Interval_V Iv = new Interval_V();
				try {
					Iv.setS(t1);
					Iv.setE(t2);
					Iv.setLc(new BigDecimal(1));
					Iv.setRc(new BigDecimal(1));

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return Iv;
			} else {
				return null;
			}
		} else {
			double temp = (c2.doubleValue() - c1.doubleValue())
					/ (b1.doubleValue() - b2.doubleValue());
			// if (temp >= t1.doubleValue() && temp <= t2.doubleValue()) {
			if ((t1.compareTo(new BigDecimal(temp)) <= 0)
					&& (t2.compareTo(new BigDecimal(temp)) >= 0)) {
				if (b1.doubleValue() > b2.doubleValue()) {
					Interval_V Iv = new Interval_V();
					try {
						Iv.setS(new BigDecimal(temp));
						Iv.setE(t2);
						Iv.setLc(new BigDecimal(1));
						Iv.setRc(new BigDecimal(1));

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return Iv;
				} else {
					Interval_V Iv = new Interval_V();
					try {
						Iv.setS(t1);
						Iv.setE(new BigDecimal(temp));
						Iv.setLc(new BigDecimal(1));
						Iv.setRc(new BigDecimal(1));

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return Iv;
				}

			} else {
				if (t1.compareTo(new BigDecimal(temp)) > 0) {
					// if (t1.doubleValue() > temp) {
					if (b1.doubleValue() < b2.doubleValue()) {
						return null;
					} else {
						Interval_V Iv = new Interval_V();
						try {
							Iv.setS(t1);
							Iv.setE(t2);
							Iv.setLc(new BigDecimal(1));
							Iv.setRc(new BigDecimal(1));

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return Iv;
					}
					// } else if (t2.doubleValue() < temp) {
				} else if (t2.compareTo(new BigDecimal(temp)) < 0) {
					if (b1.doubleValue() < b2.doubleValue()) {
						Interval_V Iv = new Interval_V();
						try {
							Iv.setS(t1);
							Iv.setE(t2);
							Iv.setLc(new BigDecimal(1));
							Iv.setRc(new BigDecimal(1));

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return Iv;

					} else {
						return null;
					}
				} else {
					return null;
				}

			}
		}
		/*
		 * if (b1.compareTo(b2) == 0) { if (c1.compareTo(c2) > 0) { Interval_V
		 * Iv = new Interval_V(); try { Iv.setS(t1); Iv.setE(t2); Iv.setLc(new
		 * BigDecimal(1)); Iv.setRc(new BigDecimal(1)); } catch (SQLException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 * 
		 * return Iv; } else { return null; } } else { BigDecimal temp =
		 * c2.subtract(c1).divide(b1.subtract(b2), RoundingMode.FLOOR); if
		 * ((temp.compareTo(t1) >= 0) && (temp.compareTo(t2) <= 0)) { if
		 * (b1.subtract(b2).compareTo(new BigDecimal(0)) > 0) { Interval_V Iv =
		 * new Interval_V(); try { // RoundingMode.FLOOR Iv.setS(temp);
		 * Iv.setE(t2); Iv.setLc(new BigDecimal(1)); Iv.setRc(new
		 * BigDecimal(1)); } catch (SQLException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); } return Iv; } else { Interval_V Iv =
		 * new Interval_V(); try { Iv.setS(t1); Iv.setE(temp); Iv.setLc(new
		 * BigDecimal(1)); Iv.setRc(new BigDecimal(1)); } catch (SQLException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } return Iv; } }
		 * else { if (b1.subtract(b2).compareTo(new BigDecimal(0)) > 0) { if
		 * (temp.compareTo(t2) > 0) { return null; } else { Interval_V Iv = new
		 * Interval_V(); try { Iv.setS(t1); Iv.setE(t2); Iv.setLc(new
		 * BigDecimal(1)); Iv.setRc(new BigDecimal(1)); } catch (SQLException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } return Iv; } }
		 * else { if (temp.compareTo(t2) < 0) { return null; } else { Interval_V
		 * Iv = new Interval_V(); try { Iv.setS(t1); Iv.setE(t2); Iv.setLc(new
		 * BigDecimal(1)); Iv.setRc(new BigDecimal(1)); } catch (SQLException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } return Iv; } } } } //
		 * return null;
		 */
	}

	public static Interval_V intervalSmallerThan(BigDecimal a1, BigDecimal b1,
			BigDecimal c1, BigDecimal a2, BigDecimal b2, BigDecimal c2,
			BigDecimal t1, BigDecimal t2) {
		if (b1.doubleValue() == b2.doubleValue()) {
			if (c1.doubleValue() < c2.doubleValue()) {

				Interval_V Iv = new Interval_V();
				try {
					Iv.setS(t1);
					Iv.setE(t2);
					Iv.setLc(new BigDecimal(1));
					Iv.setRc(new BigDecimal(1));

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return Iv;
			} else {
				return null;
			}
		} else {
			double temp = (c2.doubleValue() - c1.doubleValue())
					/ (b1.doubleValue() - b2.doubleValue());
			// if (temp >= t1.doubleValue() && temp <= t2.doubleValue()) {
			if ((t1.compareTo(new BigDecimal(temp)) <= 0)
					&& (t2.compareTo(new BigDecimal(temp)) >= 0)) {
				if (b1.doubleValue() > b2.doubleValue()) {
					Interval_V Iv = new Interval_V();
					try {
						Iv.setS(t1);
						Iv.setE(new BigDecimal(temp));
						Iv.setLc(new BigDecimal(1));
						Iv.setRc(new BigDecimal(1));

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return Iv;
				} else {
					Interval_V Iv = new Interval_V();
					try {
						Iv.setS(new BigDecimal(temp));
						Iv.setE(t2);
						Iv.setLc(new BigDecimal(1));
						Iv.setRc(new BigDecimal(1));

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return Iv;
				}

			} else {
				// if (t1.doubleValue() > temp) {
				if (t1.compareTo(new BigDecimal(temp)) > 0) {
					if (b1.doubleValue() > b2.doubleValue()) {
						return null;
					} else {
						Interval_V Iv = new Interval_V();
						try {
							Iv.setS(t1);
							Iv.setE(t2);
							Iv.setLc(new BigDecimal(1));
							Iv.setRc(new BigDecimal(1));

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return Iv;
					}
					// } else if (t2.doubleValue() < temp) {
				} else if (t2.compareTo(new BigDecimal(temp)) < 0) {
					if (b1.doubleValue() > b2.doubleValue()) {
						Interval_V Iv = new Interval_V();
						try {
							Iv.setS(t1);
							Iv.setE(t2);
							Iv.setLc(new BigDecimal(1));
							Iv.setRc(new BigDecimal(1));

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return Iv;

					} else {
						return null;
					}
				} else {
					return null;
				}

			}
		}

		/*
		 * 
		 * if (b1.compareTo(b2) == 0) { if (c1.compareTo(c2) < 0) { Interval_V
		 * Iv = new Interval_V(); try { Iv.setS(t1); Iv.setE(t2); Iv.setLc(new
		 * BigDecimal(1)); Iv.setRc(new BigDecimal(1)); } catch (SQLException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 * 
		 * return Iv; } else { return null; } } else { BigDecimal temp =
		 * c2.subtract(c1).divide(b1.subtract(b2), RoundingMode.FLOOR); if
		 * ((temp.compareTo(t1) >= 0) && (temp.compareTo(t2) <= 0)) {
		 * 
		 * if (b1.subtract(b2).compareTo(new BigDecimal(0)) < 0) {
		 * 
		 * Interval_V Iv = new Interval_V(); try { Iv.setS(temp); Iv.setE(t2);
		 * Iv.setLc(new BigDecimal(1)); Iv.setRc(new BigDecimal(1)); } catch
		 * (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } return Iv; } else { Interval_V Iv = new
		 * Interval_V(); try { Iv.setS(t1); Iv.setE(temp); Iv.setLc(new
		 * BigDecimal(1)); Iv.setRc(new BigDecimal(1)); } catch (SQLException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } return Iv; } }
		 * else { if (b1.subtract(b2).compareTo(new BigDecimal(0)) > 0) { if
		 * (temp.compareTo(t1) < 0) { return null; } else { Interval_V Iv = new
		 * Interval_V(); try { Iv.setS(t1); Iv.setE(t2); Iv.setLc(new
		 * BigDecimal(1)); Iv.setRc(new BigDecimal(1)); } catch (SQLException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } return Iv; } }
		 * else { if (temp.compareTo(t2) > 0) { return null; } else { Interval_V
		 * Iv = new Interval_V(); try { Iv.setS(t1); Iv.setE(t2); Iv.setLc(new
		 * BigDecimal(1)); Iv.setRc(new BigDecimal(1)); } catch (SQLException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } return Iv; } } } }
		 */

		// return null;
	}

	public static Interval_V intervalEqualTo(BigDecimal a1, BigDecimal b1,
			BigDecimal c1, BigDecimal a2, BigDecimal b2, BigDecimal c2,
			BigDecimal t1, BigDecimal t2) {
		if (b1.doubleValue() == b2.doubleValue()) {
			if (c1.doubleValue() == c2.doubleValue()) {
				Interval_V Iv = new Interval_V();
				try {
					Iv.setS(t1);
					Iv.setE(t2);
					Iv.setLc(new BigDecimal(1));
					Iv.setRc(new BigDecimal(1));

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return Iv;
			} else {
				return null;
			}
		} else {
			double temp = (c2.doubleValue() - c1.doubleValue())
					/ (b1.doubleValue() - b2.doubleValue());
			if ((t1.compareTo(new BigDecimal(temp)) <= 0)
					&& (t2.compareTo(new BigDecimal(temp)) >= 0)) {
				// if (temp >= t1.doubleValue() && temp <= t2.doubleValue()) {
				Interval_V Iv = new Interval_V();
				try {
					Iv.setS(new BigDecimal(temp));
					Iv.setE(new BigDecimal(temp));
					Iv.setLc(new BigDecimal(1));
					Iv.setRc(new BigDecimal(1));

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return Iv;
			} else {
				return null;
			}
		}

		/*
		 * if (b1.compareTo(b2) == 0) { if (c1.compareTo(c2) == 0) { Interval_V
		 * Iv = new Interval_V(); try { Iv.setS(t1); Iv.setE(t2); Iv.setLc(new
		 * BigDecimal(1)); Iv.setRc(new BigDecimal(1)); } catch (SQLException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 * System.out.println("a =b "); return Iv; } else { return null; } }
		 * else { BigDecimal temp = c2.subtract(c1).divide(b1.subtract(b2),
		 * RoundingMode.FLOOR);
		 * 
		 * if ((temp.compareTo(t1) >= 0) && (temp.compareTo(t2) <= 0)) {
		 * Interval_V Iv = new Interval_V(); try { Iv.setS(temp); Iv.setE(temp);
		 * Iv.setLc(new BigDecimal(1)); Iv.setRc(new BigDecimal(1)); } catch
		 * (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } System.out.println("a >b "); return Iv; } else {
		 * return null; } }
		 */
		// return null;
	}

	public static GReal_V greaterThan(GReal_V a, GReal_V b) {
		if (a == null || b == null) {
			return null;
		}

		try {
			if (b.getUnits().getElement(0).getInterval().getRid() == -1) {
				SortedSet<UGReal> sortedMGPoint_a = UtilityFunctions
						.getSpatialOrderedGReal(a);
				ArrayList<UGReal> UGRrl = new ArrayList<UGReal>();
				Iterator it_a = sortedMGPoint_a.iterator();
				while (it_a.hasNext()) {
					UGReal UGRa = (UGReal) it_a.next();
					Interval_V itv = intervalGreaterThan_G(UGRa.getA(), UGRa
							.getB(), UGRa.getC(), b.getUnits().getElement(0)
							.getA(), b.getUnits().getElement(0).getB(), b
							.getUnits().getElement(0).getC(), UGRa
							.getInterval().getPos1(), UGRa.getInterval()
							.getPos2());
					if (itv != null) {
						UGReal UGRr = new UGReal();
						UGRr.setA(UGRa.getA());
						UGRr.setB(UGRa.getB());
						UGRr.setC(UGRa.getC());
						UGRr.setNid(UGRa.getNid());
						UGRr.setR(UGRa.getR());
						UGRr.setSequence(UGRa.getSequence());
						Section Ser = new Section();

						Ser.setPos1(itv.getS());
						Ser.setPos2(itv.getE());
						Ser.setRid(UGRa.getInterval().getRid());
						Ser.setSide(UGRa.getInterval().getSide());
						UGRr.setInterval(Ser);

						UGRrl.add(UGRr);
					}

				}

				try {

					if (UGRrl.size() > 0) {
						UGReal_V TUGRr = new UGReal_V();
						UGReal UGRtemp[] = new UGReal[UGRrl.size()];
						for (int i = 0; i < UGRrl.size(); i++) {
							UGRtemp[i] = UGRrl.get(i);
						}
						TUGRr.setArray(UGRtemp);
						GReal_V GRr = new GReal_V();
						GRr.setUnits(TUGRr);

						return GRr;
					} else {
						return null;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		SortedSet<UGReal> sortedMGPoint_a = UtilityFunctions
				.getSpatialOrderedGReal(a);
		SortedSet<UGReal> sortedMGPoint_b = UtilityFunctions
				.getSpatialOrderedGReal(b);

		Iterator it_a = sortedMGPoint_a.iterator();
		Iterator it_b = sortedMGPoint_b.iterator();

		UGReal UGRa = null;
		UGReal UGRb = null;

		ArrayList<UGReal> UGRrl = new ArrayList<UGReal>();

		int flag = 0;
		// int j = 0;
		while (flag >= 0) {
			// System.out.println(j++);
			if (flag == 0) {
				if (it_a.hasNext() && it_b.hasNext()) {
					UGRa = (UGReal) it_a.next();
					UGRb = (UGReal) it_b.next();
				} else {
					// System.out.println("fin");
					flag = -1;
					continue;
				}

			}

			if (flag == 1) {
				if (it_a.hasNext()) {
					UGRa = (UGReal) it_a.next();
					// flag = -1;
				} else {
					// System.out.println("fin");
					flag = -1;
					continue;
				}
			}
			// la prochaine b
			if (flag == 2) {
				if (it_b.hasNext()) {
					UGRb = (UGReal) it_b.next();
					// flag = -1;
				} else {
					// System.out.println("fin");
					flag = -1;
					continue;
				}
			}

			try {
				// BigDecimal RIDa = UGRa.getR();
				// BigDecimal RIDb = UGRb.getR();
				// int NIDa = UGRa.getNid();
				// int NIDb = UGRb.getNid();
				Section intervala = UGRa.getInterval();
				Section intervalb = UGRb.getInterval();

				int rida = intervala.getRid();
				int ridb = intervalb.getRid();
				int sidea = intervala.getSide();
				int sideb = intervalb.getSide();
				BigDecimal Pos1a = intervala.getPos1();
				BigDecimal Pos1b = intervalb.getPos1();
				BigDecimal Pos2a = intervala.getPos2();
				BigDecimal Pos2b = intervalb.getPos2();

				if (rida == ridb) {
					if (sidea == sideb) {
						// intervala == intervalb
						if ((Pos1a.compareTo(Pos1b) == 0)
								&& (Pos2a.compareTo(Pos2b) == 0)) {
							// create a new interval (Pos1a, Pos2a)

							Interval_V itv = intervalGreaterThan_G(UGRa.getA(),
									UGRa.getB(), UGRa.getC(), UGRb.getA(), UGRb
											.getB(), UGRb.getC(), Pos1a, Pos2a);
							if (itv != null) {
								UGReal UGRr = new UGReal();
								UGRr.setA(UGRa.getA());
								UGRr.setB(UGRa.getB());
								UGRr.setC(UGRa.getC());
								UGRr.setNid(UGRa.getNid());
								UGRr.setR(UGRa.getR());
								UGRr.setSequence(UGRa.getSequence());
								Section Ser = new Section();

								Ser.setPos1(itv.getS());
								Ser.setPos2(itv.getE());
								Ser.setRid(intervala.getRid());
								Ser.setSide(intervala.getSide());
								UGRr.setInterval(Ser);

								UGRrl.add(UGRr);
							}
							flag = 0;
							continue;
						}

						// intervala.p1 > intervalb.p2
						if ((Pos1a.compareTo(Pos2b) > 0)) {
							flag = 2;
							continue;
						}

						// intervala.p2 < intervalb.p1
						if ((Pos2a.compareTo(Pos1b) < 0)) {
							flag = 1;
							continue;
						}

						// intervala.p1 <= intervalb.p1 <= intervala.p2 <=
						// intervalb.p2
						if ((Pos1a.compareTo(Pos1b) <= 0)
								&& (Pos1b.compareTo(Pos2a) <= 0)
								&& (Pos2a.compareTo(Pos2b) <= 0)) {
							// create a new interval (Pos1b, Pos2a)
							Interval_V itv = intervalGreaterThan_G(UGRa.getA(),
									UGRa.getB(), UGRa.getC(), UGRb.getA(), UGRb
											.getB(), UGRb.getC(), Pos1b, Pos2a);
							if (itv != null) {
								UGReal UGRr = new UGReal();
								UGRr.setA(UGRa.getA());
								UGRr.setB(UGRa.getB());
								UGRr.setC(UGRa.getC());
								UGRr.setNid(UGRa.getNid());
								UGRr.setR(UGRa.getR());
								UGRr.setSequence(UGRa.getSequence());
								Section Ser = new Section();

								Ser.setPos1(itv.getS());
								Ser.setPos2(itv.getE());
								Ser.setRid(intervala.getRid());
								Ser.setSide(intervala.getSide());
								UGRr.setInterval(Ser);

								UGRrl.add(UGRr);
							}

							flag = 1;
							continue;
						}

						// intervala.p1 <= intervalb.p1 <= intervalb.p2 <=
						// intervala.p2
						if ((Pos1a.compareTo(Pos1b) <= 0)
								&& (Pos1b.compareTo(Pos2b) <= 0)
								&& (Pos2b.compareTo(Pos2a) <= 0)) {
							// create a new interval (Pos1b, Pos2b)
							Interval_V itv = intervalGreaterThan_G(UGRa.getA(),
									UGRa.getB(), UGRa.getC(), UGRb.getA(), UGRb
											.getB(), UGRb.getC(), Pos1b, Pos2b);
							if (itv != null) {
								UGReal UGRr = new UGReal();
								UGRr.setA(UGRa.getA());
								UGRr.setB(UGRa.getB());
								UGRr.setC(UGRa.getC());
								UGRr.setNid(UGRa.getNid());
								UGRr.setR(UGRa.getR());
								UGRr.setSequence(UGRa.getSequence());
								Section Ser = new Section();

								Ser.setPos1(itv.getS());
								Ser.setPos2(itv.getE());
								Ser.setRid(intervala.getRid());
								Ser.setSide(intervala.getSide());
								UGRr.setInterval(Ser);

								UGRrl.add(UGRr);
							}
							flag = 2;
							continue;
						}

						// intervalb.p1 <= intervala.p1 <= intervala.p2 <=
						// intervalb.p2
						if ((Pos1b.compareTo(Pos1a) <= 0)
								&& (Pos1a.compareTo(Pos2a) <= 0)
								&& (Pos2a.compareTo(Pos2b) <= 0)) {
							// create a new interval (Pos1a, Pos2a)
							Interval_V itv = intervalGreaterThan_G(UGRa.getA(),
									UGRa.getB(), UGRa.getC(), UGRb.getA(), UGRb
											.getB(), UGRb.getC(), Pos1a, Pos2a);
							if (itv != null) {
								UGReal UGRr = new UGReal();
								UGRr.setA(UGRa.getA());
								UGRr.setB(UGRa.getB());
								UGRr.setC(UGRa.getC());
								UGRr.setNid(UGRa.getNid());
								UGRr.setR(UGRa.getR());
								UGRr.setSequence(UGRa.getSequence());
								Section Ser = new Section();

								Ser.setPos1(itv.getS());
								Ser.setPos2(itv.getE());
								Ser.setRid(intervala.getRid());
								Ser.setSide(intervala.getSide());
								UGRr.setInterval(Ser);

								UGRrl.add(UGRr);
							}
							flag = 2;
							continue;
						}

						// intervalb.p1 <= intervala.p1 <= intervalb.p2 <=
						// intervala.p2
						if ((Pos1b.compareTo(Pos1a) <= 0)
								&& (Pos1a.compareTo(Pos2b) <= 0)
								&& (Pos2b.compareTo(Pos2a) <= 0)) {
							// create a new interval (Pos1a, Pos2b)

							Interval_V itv = intervalGreaterThan_G(UGRa.getA(),
									UGRa.getB(), UGRa.getC(), UGRb.getA(), UGRb
											.getB(), UGRb.getC(), Pos1a, Pos2b);
							if (itv != null) {
								UGReal UGRr = new UGReal();
								UGRr.setA(UGRa.getA());
								UGRr.setB(UGRa.getB());
								UGRr.setC(UGRa.getC());
								UGRr.setNid(UGRa.getNid());
								UGRr.setR(UGRa.getR());
								UGRr.setSequence(UGRa.getSequence());
								Section Ser = new Section();

								Ser.setPos1(itv.getS());
								Ser.setPos2(itv.getE());
								Ser.setRid(intervala.getRid());
								Ser.setSide(intervala.getSide());
								UGRr.setInterval(Ser);

								UGRrl.add(UGRr);
							}
							flag = 1;
							continue;
						}

					} else if (sidea > sideb) {
						flag = 2;
					} else {
						flag = 1;
					}
				} else if (rida > ridb) {
					flag = 2;
				} else {
					flag = 1;
				}
				// if(intervala.get)
				// verifier tous les cheins

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		try {
			if (UGRrl.size() > 0) {
				UGReal_V TUGRr = new UGReal_V();
				UGReal UGRtemp[] = new UGReal[UGRrl.size()];
				for (int i = 0; i < UGRrl.size(); i++) {
					UGRtemp[i] = UGRrl.get(i);
				}
				TUGRr.setArray(UGRtemp);
				GReal_V GRr = new GReal_V();
				GRr.setUnits(TUGRr);

				return GRr;
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TUGRr.setArray((UGReal[]) UGRrl.toArray());

		return null;

	}

	public static GReal_V smallerThan(GReal_V a, GReal_V b) {
		if (a == null || b == null) {
			return null;
		}

		try {
			if (b.getUnits().getElement(0).getInterval().getRid() == -1) {
				SortedSet<UGReal> sortedMGPoint_a = UtilityFunctions
						.getSpatialOrderedGReal(a);
				ArrayList<UGReal> UGRrl = new ArrayList<UGReal>();
				Iterator it_a = sortedMGPoint_a.iterator();
				while (it_a.hasNext()) {
					UGReal UGRa = (UGReal) it_a.next();
					Interval_V itv = intervalSmallerThan_G(UGRa.getA(), UGRa
							.getB(), UGRa.getC(), b.getUnits().getElement(0)
							.getA(), b.getUnits().getElement(0).getB(), b
							.getUnits().getElement(0).getC(), UGRa
							.getInterval().getPos1(), UGRa.getInterval()
							.getPos2());
					if (itv != null) {
						UGReal UGRr = new UGReal();
						UGRr.setA(UGRa.getA());
						UGRr.setB(UGRa.getB());
						UGRr.setC(UGRa.getC());
						UGRr.setNid(UGRa.getNid());
						UGRr.setR(UGRa.getR());
						UGRr.setSequence(UGRa.getSequence());
						Section Ser = new Section();

						Ser.setPos1(itv.getS());
						Ser.setPos2(itv.getE());
						Ser.setRid(UGRa.getInterval().getRid());
						Ser.setSide(UGRa.getInterval().getSide());
						UGRr.setInterval(Ser);

						UGRrl.add(UGRr);
					}

				}

				try {

					if (UGRrl.size() > 0) {
						UGReal_V TUGRr = new UGReal_V();
						UGReal UGRtemp[] = new UGReal[UGRrl.size()];
						for (int i = 0; i < UGRrl.size(); i++) {
							UGRtemp[i] = UGRrl.get(i);
						}
						TUGRr.setArray(UGRtemp);
						GReal_V GRr = new GReal_V();
						GRr.setUnits(TUGRr);

						return GRr;
					} else {
						return null;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		SortedSet<UGReal> sortedMGPoint_a = UtilityFunctions
				.getSpatialOrderedGReal(a);
		SortedSet<UGReal> sortedMGPoint_b = UtilityFunctions
				.getSpatialOrderedGReal(b);

		Iterator it_a = sortedMGPoint_a.iterator();
		Iterator it_b = sortedMGPoint_b.iterator();

		UGReal UGRa = null;
		UGReal UGRb = null;

		ArrayList<UGReal> UGRrl = new ArrayList<UGReal>();

		int flag = 0;
		// int j = 0;
		while (flag >= 0) {
			// System.out.println(j++);
			if (flag == 0) {
				if (it_a.hasNext() && it_b.hasNext()) {
					UGRa = (UGReal) it_a.next();
					UGRb = (UGReal) it_b.next();
				} else {
					// System.out.println("fin");
					flag = -1;
					continue;
				}

			}

			if (flag == 1) {
				if (it_a.hasNext()) {
					UGRa = (UGReal) it_a.next();
					// flag = -1;
				} else {
					// System.out.println("fin");
					flag = -1;
					continue;
				}
			}
			// la prochaine b
			if (flag == 2) {
				if (it_b.hasNext()) {
					UGRb = (UGReal) it_b.next();
					// flag = -1;
				} else {
					// System.out.println("fin");
					flag = -1;
					continue;
				}
			}

			try {
				// BigDecimal RIDa = UGRa.getR();
				// BigDecimal RIDb = UGRb.getR();
				// int NIDa = UGRa.getNid();
				// int NIDb = UGRb.getNid();
				Section intervala = UGRa.getInterval();
				Section intervalb = UGRb.getInterval();

				int rida = intervala.getRid();
				int ridb = intervalb.getRid();
				int sidea = intervala.getSide();
				int sideb = intervalb.getSide();
				BigDecimal Pos1a = intervala.getPos1();
				BigDecimal Pos1b = intervalb.getPos1();
				BigDecimal Pos2a = intervala.getPos2();
				BigDecimal Pos2b = intervalb.getPos2();

				if (rida == ridb) {
					if (sidea == sideb) {
						// intervala == intervalb
						if ((Pos1a.compareTo(Pos1b) == 0)
								&& (Pos2a.compareTo(Pos2b) == 0)) {
							// create a new interval (Pos1a, Pos2a)

							Interval_V itv = intervalSmallerThan_G(UGRa.getA(),
									UGRa.getB(), UGRa.getC(), UGRb.getA(), UGRb
											.getB(), UGRb.getC(), Pos1a, Pos2a);
							if (itv != null) {
								UGReal UGRr = new UGReal();
								UGRr.setA(UGRa.getA());
								UGRr.setB(UGRa.getB());
								UGRr.setC(UGRa.getC());
								UGRr.setNid(UGRa.getNid());
								UGRr.setR(UGRa.getR());
								UGRr.setSequence(UGRa.getSequence());
								Section Ser = new Section();

								Ser.setPos1(itv.getS());
								Ser.setPos2(itv.getE());
								Ser.setRid(intervala.getRid());
								Ser.setSide(intervala.getSide());
								UGRr.setInterval(Ser);

								UGRrl.add(UGRr);
							}
							flag = 0;
							continue;
						}

						// intervala.p1 > intervalb.p2
						if ((Pos1a.compareTo(Pos2b) > 0)) {
							flag = 2;
							continue;
						}

						// intervala.p2 < intervalb.p1
						if ((Pos2a.compareTo(Pos1b) < 0)) {
							flag = 1;
							continue;
						}

						// intervala.p1 <= intervalb.p1 <= intervala.p2 <=
						// intervalb.p2
						if ((Pos1a.compareTo(Pos1b) <= 0)
								&& (Pos1b.compareTo(Pos2a) <= 0)
								&& (Pos2a.compareTo(Pos2b) <= 0)) {
							// create a new interval (Pos1b, Pos2a)
							Interval_V itv = intervalSmallerThan_G(UGRa.getA(),
									UGRa.getB(), UGRa.getC(), UGRb.getA(), UGRb
											.getB(), UGRb.getC(), Pos1b, Pos2a);
							if (itv != null) {
								UGReal UGRr = new UGReal();
								UGRr.setA(UGRa.getA());
								UGRr.setB(UGRa.getB());
								UGRr.setC(UGRa.getC());
								UGRr.setNid(UGRa.getNid());
								UGRr.setR(UGRa.getR());
								UGRr.setSequence(UGRa.getSequence());
								Section Ser = new Section();

								Ser.setPos1(itv.getS());
								Ser.setPos2(itv.getE());
								Ser.setRid(intervala.getRid());
								Ser.setSide(intervala.getSide());
								UGRr.setInterval(Ser);

								UGRrl.add(UGRr);
							}

							flag = 1;
							continue;
						}

						// intervala.p1 <= intervalb.p1 <= intervalb.p2 <=
						// intervala.p2
						if ((Pos1a.compareTo(Pos1b) <= 0)
								&& (Pos1b.compareTo(Pos2b) <= 0)
								&& (Pos2b.compareTo(Pos2a) <= 0)) {
							// create a new interval (Pos1b, Pos2b)
							Interval_V itv = intervalSmallerThan_G(UGRa.getA(),
									UGRa.getB(), UGRa.getC(), UGRb.getA(), UGRb
											.getB(), UGRb.getC(), Pos1b, Pos2b);
							if (itv != null) {
								UGReal UGRr = new UGReal();
								UGRr.setA(UGRa.getA());
								UGRr.setB(UGRa.getB());
								UGRr.setC(UGRa.getC());
								UGRr.setNid(UGRa.getNid());
								UGRr.setR(UGRa.getR());
								UGRr.setSequence(UGRa.getSequence());
								Section Ser = new Section();

								Ser.setPos1(itv.getS());
								Ser.setPos2(itv.getE());
								Ser.setRid(intervala.getRid());
								Ser.setSide(intervala.getSide());
								UGRr.setInterval(Ser);

								UGRrl.add(UGRr);
							}
							flag = 2;
							continue;
						}

						// intervalb.p1 <= intervala.p1 <= intervala.p2 <=
						// intervalb.p2
						if ((Pos1b.compareTo(Pos1a) <= 0)
								&& (Pos1a.compareTo(Pos2a) <= 0)
								&& (Pos2a.compareTo(Pos2b) <= 0)) {
							// create a new interval (Pos1a, Pos2a)
							Interval_V itv = intervalSmallerThan_G(UGRa.getA(),
									UGRa.getB(), UGRa.getC(), UGRb.getA(), UGRb
											.getB(), UGRb.getC(), Pos1a, Pos2a);
							if (itv != null) {
								UGReal UGRr = new UGReal();
								UGRr.setA(UGRa.getA());
								UGRr.setB(UGRa.getB());
								UGRr.setC(UGRa.getC());
								UGRr.setNid(UGRa.getNid());
								UGRr.setR(UGRa.getR());
								UGRr.setSequence(UGRa.getSequence());
								Section Ser = new Section();

								Ser.setPos1(itv.getS());
								Ser.setPos2(itv.getE());
								Ser.setRid(intervala.getRid());
								Ser.setSide(intervala.getSide());
								UGRr.setInterval(Ser);

								UGRrl.add(UGRr);
							}
							flag = 2;
							continue;
						}

						// intervalb.p1 <= intervala.p1 <= intervalb.p2 <=
						// intervala.p2
						if ((Pos1b.compareTo(Pos1a) <= 0)
								&& (Pos1a.compareTo(Pos2b) <= 0)
								&& (Pos2b.compareTo(Pos2a) <= 0)) {
							// create a new interval (Pos1a, Pos2b)

							Interval_V itv = intervalSmallerThan_G(UGRa.getA(),
									UGRa.getB(), UGRa.getC(), UGRb.getA(), UGRb
											.getB(), UGRb.getC(), Pos1a, Pos2b);
							if (itv != null) {
								UGReal UGRr = new UGReal();
								UGRr.setA(UGRa.getA());
								UGRr.setB(UGRa.getB());
								UGRr.setC(UGRa.getC());
								UGRr.setNid(UGRa.getNid());
								UGRr.setR(UGRa.getR());
								UGRr.setSequence(UGRa.getSequence());
								Section Ser = new Section();

								Ser.setPos1(itv.getS());
								Ser.setPos2(itv.getE());
								Ser.setRid(intervala.getRid());
								Ser.setSide(intervala.getSide());
								UGRr.setInterval(Ser);

								UGRrl.add(UGRr);
							}
							flag = 1;
							continue;
						}

					} else if (sidea > sideb) {
						flag = 2;
					} else {
						flag = 1;
					}
				} else if (rida > ridb) {
					flag = 2;
				} else {
					flag = 1;
				}
				// if(intervala.get)
				// verifier tous les cheins

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		try {
			if (UGRrl.size() > 0) {
				UGReal_V TUGRr = new UGReal_V();
				UGReal UGRtemp[] = new UGReal[UGRrl.size()];
				for (int i = 0; i < UGRrl.size(); i++) {
					UGRtemp[i] = UGRrl.get(i);
				}
				TUGRr.setArray(UGRtemp);
				GReal_V GRr = new GReal_V();
				GRr.setUnits(TUGRr);

				return GRr;
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TUGRr.setArray((UGReal[]) UGRrl.toArray());

		return null;
	}

	public static GReal_V equalTo(GReal_V a, GReal_V b) {
		if (a == null || b == null) {
			return null;
		}

		try {
			if (b.getUnits().getElement(0).getInterval().getRid() == -1) {
				SortedSet<UGReal> sortedMGPoint_a = UtilityFunctions
						.getSpatialOrderedGReal(a);
				ArrayList<UGReal> UGRrl = new ArrayList<UGReal>();
				Iterator it_a = sortedMGPoint_a.iterator();
				while (it_a.hasNext()) {
					UGReal UGRa = (UGReal) it_a.next();
					Interval_V itv = intervalEqualTo_G(UGRa.getA(), UGRa
							.getB(), UGRa.getC(), b.getUnits().getElement(0)
							.getA(), b.getUnits().getElement(0).getB(), b
							.getUnits().getElement(0).getC(), UGRa
							.getInterval().getPos1(), UGRa.getInterval()
							.getPos2());
					if (itv != null) {
						UGReal UGRr = new UGReal();
						UGRr.setA(UGRa.getA());
						UGRr.setB(UGRa.getB());
						UGRr.setC(UGRa.getC());
						UGRr.setNid(UGRa.getNid());
						UGRr.setR(UGRa.getR());
						UGRr.setSequence(UGRa.getSequence());
						Section Ser = new Section();

						Ser.setPos1(itv.getS());
						Ser.setPos2(itv.getE());
						Ser.setRid(UGRa.getInterval().getRid());
						Ser.setSide(UGRa.getInterval().getSide());
						UGRr.setInterval(Ser);

						UGRrl.add(UGRr);
					}

				}

				try {
					if (UGRrl.size() > 0) {
						UGReal_V TUGRr = new UGReal_V();
						UGReal UGRtemp[] = new UGReal[UGRrl.size()];
						for (int i = 0; i < UGRrl.size(); i++) {
							UGRtemp[i] = UGRrl.get(i);
						}
						TUGRr.setArray(UGRtemp);
						GReal_V GRr = new GReal_V();
						GRr.setUnits(TUGRr);

						return GRr;
					} else {
						return null;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		SortedSet<UGReal> sortedMGPoint_a = UtilityFunctions
				.getSpatialOrderedGReal(a);
		SortedSet<UGReal> sortedMGPoint_b = UtilityFunctions
				.getSpatialOrderedGReal(b);

		Iterator it_a = sortedMGPoint_a.iterator();
		Iterator it_b = sortedMGPoint_b.iterator();

		UGReal UGRa = null;
		UGReal UGRb = null;

		ArrayList<UGReal> UGRrl = new ArrayList<UGReal>();

		int flag = 0;
		// int j = 0;
		while (flag >= 0) {
			// System.out.println(j++);
			if (flag == 0) {
				if (it_a.hasNext() && it_b.hasNext()) {
					UGRa = (UGReal) it_a.next();
					UGRb = (UGReal) it_b.next();
				} else {
					// System.out.println("fin");
					flag = -1;
					continue;
				}

			}

			if (flag == 1) {
				if (it_a.hasNext()) {
					UGRa = (UGReal) it_a.next();
					// flag = -1;
				} else {
					// System.out.println("fin");
					flag = -1;
					continue;
				}
			}
			// la prochaine b
			if (flag == 2) {
				if (it_b.hasNext()) {
					UGRb = (UGReal) it_b.next();
					// flag = -1;
				} else {
					// System.out.println("fin");
					flag = -1;
					continue;
				}
			}

			try {
				// BigDecimal RIDa = UGRa.getR();
				// BigDecimal RIDb = UGRb.getR();
				// int NIDa = UGRa.getNid();
				// int NIDb = UGRb.getNid();
				Section intervala = UGRa.getInterval();
				Section intervalb = UGRb.getInterval();

				int rida = intervala.getRid();
				int ridb = intervalb.getRid();
				int sidea = intervala.getSide();
				int sideb = intervalb.getSide();
				BigDecimal Pos1a = intervala.getPos1();
				BigDecimal Pos1b = intervalb.getPos1();
				BigDecimal Pos2a = intervala.getPos2();
				BigDecimal Pos2b = intervalb.getPos2();

				if (rida == ridb) {
					if (sidea == sideb) {
						// intervala == intervalb
						if ((Pos1a.compareTo(Pos1b) == 0)
								&& (Pos2a.compareTo(Pos2b) == 0)) {
							// create a new interval (Pos1a, Pos2a)

							Interval_V itv = intervalEqualTo_G(UGRa.getA(),
									UGRa.getB(), UGRa.getC(), UGRb.getA(), UGRb
											.getB(), UGRb.getC(), Pos1a, Pos2a);
							if (itv != null) {
								UGReal UGRr = new UGReal();
								UGRr.setA(UGRa.getA());
								UGRr.setB(UGRa.getB());
								UGRr.setC(UGRa.getC());
								UGRr.setNid(UGRa.getNid());
								UGRr.setR(UGRa.getR());
								UGRr.setSequence(UGRa.getSequence());
								Section Ser = new Section();

								Ser.setPos1(itv.getS());
								Ser.setPos2(itv.getE());
								Ser.setRid(intervala.getRid());
								Ser.setSide(intervala.getSide());
								UGRr.setInterval(Ser);

								UGRrl.add(UGRr);
							}
							flag = 0;
							continue;
						}

						// intervala.p1 > intervalb.p2
						if ((Pos1a.compareTo(Pos2b) > 0)) {
							flag = 2;
							continue;
						}

						// intervala.p2 < intervalb.p1
						if ((Pos2a.compareTo(Pos1b) < 0)) {
							flag = 1;
							continue;
						}

						// intervala.p1 <= intervalb.p1 <= intervala.p2 <=
						// intervalb.p2
						if ((Pos1a.compareTo(Pos1b) <= 0)
								&& (Pos1b.compareTo(Pos2a) <= 0)
								&& (Pos2a.compareTo(Pos2b) <= 0)) {
							// create a new interval (Pos1b, Pos2a)
							Interval_V itv = intervalEqualTo_G(UGRa.getA(),
									UGRa.getB(), UGRa.getC(), UGRb.getA(), UGRb
											.getB(), UGRb.getC(), Pos1b, Pos2a);
							if (itv != null) {
								UGReal UGRr = new UGReal();
								UGRr.setA(UGRa.getA());
								UGRr.setB(UGRa.getB());
								UGRr.setC(UGRa.getC());
								UGRr.setNid(UGRa.getNid());
								UGRr.setR(UGRa.getR());
								UGRr.setSequence(UGRa.getSequence());
								Section Ser = new Section();

								Ser.setPos1(itv.getS());
								Ser.setPos2(itv.getE());
								Ser.setRid(intervala.getRid());
								Ser.setSide(intervala.getSide());
								UGRr.setInterval(Ser);

								UGRrl.add(UGRr);
							}

							flag = 1;
							continue;
						}

						// intervala.p1 <= intervalb.p1 <= intervalb.p2 <=
						// intervala.p2
						if ((Pos1a.compareTo(Pos1b) <= 0)
								&& (Pos1b.compareTo(Pos2b) <= 0)
								&& (Pos2b.compareTo(Pos2a) <= 0)) {
							// create a new interval (Pos1b, Pos2b)
							Interval_V itv = intervalEqualTo_G(UGRa.getA(),
									UGRa.getB(), UGRa.getC(), UGRb.getA(), UGRb
											.getB(), UGRb.getC(), Pos1b, Pos2b);
							if (itv != null) {
								UGReal UGRr = new UGReal();
								UGRr.setA(UGRa.getA());
								UGRr.setB(UGRa.getB());
								UGRr.setC(UGRa.getC());
								UGRr.setNid(UGRa.getNid());
								UGRr.setR(UGRa.getR());
								UGRr.setSequence(UGRa.getSequence());
								Section Ser = new Section();

								Ser.setPos1(itv.getS());
								Ser.setPos2(itv.getE());
								Ser.setRid(intervala.getRid());
								Ser.setSide(intervala.getSide());
								UGRr.setInterval(Ser);

								UGRrl.add(UGRr);
							}
							flag = 2;
							continue;
						}

						// intervalb.p1 <= intervala.p1 <= intervala.p2 <=
						// intervalb.p2
						if ((Pos1b.compareTo(Pos1a) <= 0)
								&& (Pos1a.compareTo(Pos2a) <= 0)
								&& (Pos2a.compareTo(Pos2b) <= 0)) {
							// create a new interval (Pos1a, Pos2a)
							Interval_V itv = intervalEqualTo_G(UGRa.getA(),
									UGRa.getB(), UGRa.getC(), UGRb.getA(), UGRb
											.getB(), UGRb.getC(), Pos1a, Pos2a);
							if (itv != null) {
								UGReal UGRr = new UGReal();
								UGRr.setA(UGRa.getA());
								UGRr.setB(UGRa.getB());
								UGRr.setC(UGRa.getC());
								UGRr.setNid(UGRa.getNid());
								UGRr.setR(UGRa.getR());
								UGRr.setSequence(UGRa.getSequence());
								Section Ser = new Section();

								Ser.setPos1(itv.getS());
								Ser.setPos2(itv.getE());
								Ser.setRid(intervala.getRid());
								Ser.setSide(intervala.getSide());
								UGRr.setInterval(Ser);

								UGRrl.add(UGRr);
							}
							flag = 2;
							continue;
						}

						// intervalb.p1 <= intervala.p1 <= intervalb.p2 <=
						// intervala.p2
						if ((Pos1b.compareTo(Pos1a) <= 0)
								&& (Pos1a.compareTo(Pos2b) <= 0)
								&& (Pos2b.compareTo(Pos2a) <= 0)) {
							// create a new interval (Pos1a, Pos2b)

							Interval_V itv = intervalEqualTo_G(UGRa.getA(),
									UGRa.getB(), UGRa.getC(), UGRb.getA(), UGRb
											.getB(), UGRb.getC(), Pos1a, Pos2b);
							if (itv != null) {
								UGReal UGRr = new UGReal();
								UGRr.setA(UGRa.getA());
								UGRr.setB(UGRa.getB());
								UGRr.setC(UGRa.getC());
								UGRr.setNid(UGRa.getNid());
								UGRr.setR(UGRa.getR());
								UGRr.setSequence(UGRa.getSequence());
								Section Ser = new Section();

								Ser.setPos1(itv.getS());
								Ser.setPos2(itv.getE());
								Ser.setRid(intervala.getRid());
								Ser.setSide(intervala.getSide());
								UGRr.setInterval(Ser);

								UGRrl.add(UGRr);
							}
							flag = 1;
							continue;
						}

					} else if (sidea > sideb) {
						flag = 2;
					} else {
						flag = 1;
					}
				} else if (rida > ridb) {
					flag = 2;
				} else {
					flag = 1;
				}
				// if(intervala.get)
				// verifier tous les cheins

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		try {
			if (UGRrl.size() > 0) {
				UGReal_V TUGRr = new UGReal_V();
				UGReal UGRtemp[] = new UGReal[UGRrl.size()];
				for (int i = 0; i < UGRrl.size(); i++) {
					UGRtemp[i] = UGRrl.get(i);
				}
				TUGRr.setArray(UGRtemp);
				GReal_V GRr = new GReal_V();
				GRr.setUnits(TUGRr);

				return GRr;
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	// TUGRr.setArray((UGReal[]) UGRrl.toArray());

		return null;
	}

	public static GReal_V between(GReal_V a, GReal_V b, GReal_V c) {
		if (a == null || b == null || c == null) {
			return null;
		}
		return smallerThan(greaterThan(a, b), c);

	}

	public static Interval_V intervalGreaterThan_G(BigDecimal a1,
			BigDecimal b1, BigDecimal c1, BigDecimal a2, BigDecimal b2,
			BigDecimal c2, BigDecimal pos1, BigDecimal pos2) {
		if (b1.doubleValue() == b2.doubleValue()) {
			if (c1.doubleValue() > c2.doubleValue()) {

				Interval_V Iv = new Interval_V();
				try {
					Iv.setS(pos1);
					Iv.setE(pos2);
					Iv.setLc(new BigDecimal(1));
					Iv.setRc(new BigDecimal(1));

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return Iv;
			} else {
				return null;
			}
		} else {
			double temp = (c2.doubleValue() - c1.doubleValue())
					/ (b1.doubleValue() - b2.doubleValue());
			// if (temp >= t1.doubleValue() && temp <= t2.doubleValue()) {
			if ((pos1.compareTo(new BigDecimal(temp)) <= 0)
					&& (pos2.compareTo(new BigDecimal(temp)) >= 0)) {
				if (b1.doubleValue() > b2.doubleValue()) {
					Interval_V Iv = new Interval_V();
					try {
						Iv.setS(new BigDecimal(temp));
						Iv.setE(pos2);
						Iv.setLc(new BigDecimal(1));
						Iv.setRc(new BigDecimal(1));

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return Iv;
				} else {
					Interval_V Iv = new Interval_V();
					try {
						Iv.setS(pos1);
						Iv.setE(new BigDecimal(temp));
						Iv.setLc(new BigDecimal(1));
						Iv.setRc(new BigDecimal(1));

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return Iv;
				}

			} else {
				if (pos1.compareTo(new BigDecimal(temp)) > 0) {
					// if (t1.doubleValue() > temp) {
					if (b1.doubleValue() < b2.doubleValue()) {
						return null;
					} else {
						Interval_V Iv = new Interval_V();
						try {
							Iv.setS(pos1);
							Iv.setE(pos2);
							Iv.setLc(new BigDecimal(1));
							Iv.setRc(new BigDecimal(1));

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return Iv;
					}
					// } else if (t2.doubleValue() < temp) {
				} else if (pos2.compareTo(new BigDecimal(temp)) < 0) {
					if (b1.doubleValue() < b2.doubleValue()) {
						Interval_V Iv = new Interval_V();
						try {
							Iv.setS(pos1);
							Iv.setE(pos2);
							Iv.setLc(new BigDecimal(1));
							Iv.setRc(new BigDecimal(1));

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return Iv;

					} else {
						return null;
					}
				} else {
					return null;
				}

			}
		}

	}

	public static Interval_V intervalSmallerThan_G(BigDecimal a1,
			BigDecimal b1, BigDecimal c1, BigDecimal a2, BigDecimal b2,
			BigDecimal c2, BigDecimal pos1, BigDecimal pos2) {
		if (b1.doubleValue() == b2.doubleValue()) {
			if (c1.doubleValue() < c2.doubleValue()) {

				Interval_V Iv = new Interval_V();
				try {
					Iv.setS(pos1);
					Iv.setE(pos2);
					Iv.setLc(new BigDecimal(1));
					Iv.setRc(new BigDecimal(1));

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return Iv;
			} else {
				return null;
			}
		} else {
			double temp = (c2.doubleValue() - c1.doubleValue())
					/ (b1.doubleValue() - b2.doubleValue());
			// if (temp >= t1.doubleValue() && temp <= t2.doubleValue()) {
			if ((pos1.compareTo(new BigDecimal(temp)) <= 0)
					&& (pos2.compareTo(new BigDecimal(temp)) >= 0)) {
				if (b1.doubleValue() > b2.doubleValue()) {
					Interval_V Iv = new Interval_V();
					try {
						Iv.setS(pos1);
						Iv.setE(new BigDecimal(temp));
						Iv.setLc(new BigDecimal(1));
						Iv.setRc(new BigDecimal(1));

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return Iv;
				} else {
					Interval_V Iv = new Interval_V();
					try {
						Iv.setS(new BigDecimal(temp));
						Iv.setE(pos2);
						Iv.setLc(new BigDecimal(1));
						Iv.setRc(new BigDecimal(1));

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return Iv;
				}

			} else {
				// if (t1.doubleValue() > temp) {
				if (pos1.compareTo(new BigDecimal(temp)) > 0) {
					if (b1.doubleValue() > b2.doubleValue()) {
						return null;
					} else {
						Interval_V Iv = new Interval_V();
						try {
							Iv.setS(pos1);
							Iv.setE(pos2);
							Iv.setLc(new BigDecimal(1));
							Iv.setRc(new BigDecimal(1));

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return Iv;
					}
					// } else if (t2.doubleValue() < temp) {
				} else if (pos2.compareTo(new BigDecimal(temp)) < 0) {
					if (b1.doubleValue() > b2.doubleValue()) {
						Interval_V Iv = new Interval_V();
						try {
							Iv.setS(pos1);
							Iv.setE(pos2);
							Iv.setLc(new BigDecimal(1));
							Iv.setRc(new BigDecimal(1));

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return Iv;

					} else {
						return null;
					}
				} else {
					return null;
				}

			}
		}

	}

	public static Interval_V intervalEqualTo_G(BigDecimal a1, BigDecimal b1,
			BigDecimal c1, BigDecimal a2, BigDecimal b2, BigDecimal c2,
			BigDecimal pos1, BigDecimal pos2) {
		if (b1.doubleValue() == b2.doubleValue()) {
			if (c1.doubleValue() == c2.doubleValue()) {
				Interval_V Iv = new Interval_V();
				try {
					Iv.setS(pos1);
					Iv.setE(pos2);
					Iv.setLc(new BigDecimal(1));
					Iv.setRc(new BigDecimal(1));

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return Iv;
			} else {
				return null;
			}
		} else {
			double temp = (c2.doubleValue() - c1.doubleValue())
					/ (b1.doubleValue() - b2.doubleValue());
			if ((pos1.compareTo(new BigDecimal(temp)) <= 0)
					&& (pos2.compareTo(new BigDecimal(temp)) >= 0)) {
				// if (temp >= t1.doubleValue() && temp <= t2.doubleValue()) {
				Interval_V Iv = new Interval_V();
				try {
					Iv.setS(new BigDecimal(temp));
					Iv.setE(new BigDecimal(temp));
					Iv.setLc(new BigDecimal(1));
					Iv.setRc(new BigDecimal(1));

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return Iv;
			} else {
				return null;
			}
		}

	}

}
