package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class UGPointRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "SYSMAN.UGPOINT";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final UGPointRef _UGPointRefFactory = new UGPointRef();

  public static ORADataFactory getORADataFactory()
  { return _UGPointRefFactory; }
  /* constructor */
  public UGPointRef()
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
    UGPointRef r = new UGPointRef();
    r._ref = (REF) d;
    return r;
  }

  public static UGPointRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (UGPointRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to UGPointRef: "+exn.toString()); }
  }

  public UGPoint getValue() throws SQLException
  {
     return (UGPoint) UGPoint.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(UGPoint c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
