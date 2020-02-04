package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class InGPointRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "SYSMAN.INGPOINT";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final InGPointRef _InGPointRefFactory = new InGPointRef();

  public static ORADataFactory getORADataFactory()
  { return _InGPointRefFactory; }
  /* constructor */
  public InGPointRef()
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
    InGPointRef r = new InGPointRef();
    r._ref = (REF) d;
    return r;
  }

  public static InGPointRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (InGPointRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to InGPointRef: "+exn.toString()); }
  }

  public InGPoint getValue() throws SQLException
  {
     return (InGPoint) InGPoint.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(InGPoint c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
