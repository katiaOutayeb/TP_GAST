package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class GReal_VRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "SYSMAN.GREAL_V";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final GReal_VRef _GReal_VRefFactory = new GReal_VRef();

  public static ORADataFactory getORADataFactory()
  { return _GReal_VRefFactory; }
  /* constructor */
  public GReal_VRef()
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
    GReal_VRef r = new GReal_VRef();
    r._ref = (REF) d;
    return r;
  }

  public static GReal_VRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (GReal_VRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to GReal_VRef: "+exn.toString()); }
  }

  public GReal_V getValue() throws SQLException
  {
     return (GReal_V) GReal_V.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(GReal_V c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
