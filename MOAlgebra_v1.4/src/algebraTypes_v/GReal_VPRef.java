package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class GReal_VPRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "SYSMAN.GREAL_VP";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final GReal_VPRef _GReal_VPRefFactory = new GReal_VPRef();

  public static ORADataFactory getORADataFactory()
  { return _GReal_VPRefFactory; }
  /* constructor */
  public GReal_VPRef()
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
    GReal_VPRef r = new GReal_VPRef();
    r._ref = (REF) d;
    return r;
  }

  public static GReal_VPRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (GReal_VPRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to GReal_VPRef: "+exn.toString()); }
  }

  public GReal_VP getValue() throws SQLException
  {
     return (GReal_VP) GReal_VP.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(GReal_VP c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
