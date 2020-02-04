package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class Interval_VRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "SYSMAN.INTERVAL_V";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final Interval_VRef _Interval_VRefFactory = new Interval_VRef();

  public static ORADataFactory getORADataFactory()
  { return _Interval_VRefFactory; }
  /* constructor */
  public Interval_VRef()
  {
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _ref;
  }

  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    Interval_VRef r = new Interval_VRef();
    r._ref = (REF) d;
    return r;
  }

  public static Interval_VRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (Interval_VRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to Interval_VRef: "+exn.toString()); }
  }

  public Interval_V getValue() throws SQLException
  {
     return (Interval_V) Interval_V.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(Interval_V c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
