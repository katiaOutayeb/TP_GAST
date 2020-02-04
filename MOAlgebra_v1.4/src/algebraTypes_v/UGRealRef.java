package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class UGRealRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "SYSMAN.UGREAL";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final UGRealRef _UGRealRefFactory = new UGRealRef();

  public static ORADataFactory getORADataFactory()
  { return _UGRealRefFactory; }
  /* constructor */
  public UGRealRef()
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
    UGRealRef r = new UGRealRef();
    r._ref = (REF) d;
    return r;
  }

  public static UGRealRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (UGRealRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to UGRealRef: "+exn.toString()); }
  }

  public UGReal getValue() throws SQLException
  {
     return (UGReal) UGReal.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(UGReal c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
