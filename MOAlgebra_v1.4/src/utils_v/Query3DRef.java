package utils_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class Query3DRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "SYSMAN.QUERY3D";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final Query3DRef _Query3DRefFactory = new Query3DRef();

  public static ORADataFactory getORADataFactory()
  { return _Query3DRefFactory; }
  /* constructor */
  public Query3DRef()
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
    Query3DRef r = new Query3DRef();
    r._ref = (REF) d;
    return r;
  }

  public static Query3DRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (Query3DRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to Query3DRef: "+exn.toString()); }
  }

  public Query3D getValue() throws SQLException
  {
     return (Query3D) Query3D.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(Query3D c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
