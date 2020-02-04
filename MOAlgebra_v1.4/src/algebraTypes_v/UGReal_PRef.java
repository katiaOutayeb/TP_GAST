package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class UGReal_PRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "SYSMAN.UGREAL_P";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final UGReal_PRef _UGReal_PRefFactory = new UGReal_PRef();

  public static ORADataFactory getORADataFactory()
  { return _UGReal_PRefFactory; }
  /* constructor */
  public UGReal_PRef()
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
    UGReal_PRef r = new UGReal_PRef();
    r._ref = (REF) d;
    return r;
  }

  public static UGReal_PRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (UGReal_PRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to UGReal_PRef: "+exn.toString()); }
  }

  public UGReal_P getValue() throws SQLException
  {
     return (UGReal_P) UGReal_P.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(UGReal_P c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
