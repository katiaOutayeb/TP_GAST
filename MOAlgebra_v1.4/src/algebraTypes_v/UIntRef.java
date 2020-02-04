package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class UIntRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "SYSMAN.UINT";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final UIntRef _UIntRefFactory = new UIntRef();

  public static ORADataFactory getORADataFactory()
  { return _UIntRefFactory; }
  /* constructor */
  public UIntRef()
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
    UIntRef r = new UIntRef();
    r._ref = (REF) d;
    return r;
  }

  public static UIntRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (UIntRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to UIntRef: "+exn.toString()); }
  }

  public UInt getValue() throws SQLException
  {
     return (UInt) UInt.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(UInt c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
