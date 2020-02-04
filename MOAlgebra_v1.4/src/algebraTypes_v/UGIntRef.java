package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class UGIntRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "SYSMAN.UGINT";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final UGIntRef _UGIntRefFactory = new UGIntRef();

  public static ORADataFactory getORADataFactory()
  { return _UGIntRefFactory; }
  /* constructor */
  public UGIntRef()
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
    UGIntRef r = new UGIntRef();
    r._ref = (REF) d;
    return r;
  }

  public static UGIntRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (UGIntRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to UGIntRef: "+exn.toString()); }
  }

  public UGInt getValue() throws SQLException
  {
     return (UGInt) UGInt.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(UGInt c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
