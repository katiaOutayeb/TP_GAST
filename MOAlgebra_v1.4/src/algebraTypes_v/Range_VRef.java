package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class Range_VRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "SYSMAN.RANGE_V";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final Range_VRef _Range_VRefFactory = new Range_VRef();

  public static ORADataFactory getORADataFactory()
  { return _Range_VRefFactory; }
  /* constructor */
  public Range_VRef()
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
    Range_VRef r = new Range_VRef();
    r._ref = (REF) d;
    return r;
  }

  public static Range_VRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (Range_VRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to Range_VRef: "+exn.toString()); }
  }

  public Range_V getValue() throws SQLException
  {
     return (Range_V) Range_V.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(Range_V c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
