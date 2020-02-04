package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class URealRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "SYSMAN.UREAL";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final URealRef _URealRefFactory = new URealRef();

  public static ORADataFactory getORADataFactory()
  { return _URealRefFactory; }
  /* constructor */
  public URealRef()
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
    URealRef r = new URealRef();
    r._ref = (REF) d;
    return r;
  }

  public static URealRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (URealRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to URealRef: "+exn.toString()); }
  }

  public UReal getValue() throws SQLException
  {
     return (UReal) UReal.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(UReal c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
