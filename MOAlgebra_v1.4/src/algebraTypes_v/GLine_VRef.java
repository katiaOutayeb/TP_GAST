package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class GLine_VRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "SYSMAN.GLINE_V";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final GLine_VRef _GLine_VRefFactory = new GLine_VRef();

  public static ORADataFactory getORADataFactory()
  { return _GLine_VRefFactory; }
  /* constructor */
  public GLine_VRef()
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
    GLine_VRef r = new GLine_VRef();
    r._ref = (REF) d;
    return r;
  }

  public static GLine_VRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (GLine_VRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to GLine_VRef: "+exn.toString()); }
  }

  public GLine_V getValue() throws SQLException
  {
     return (GLine_V) GLine_V.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(GLine_V c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
