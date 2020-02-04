package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class GInt_VRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "SYSMAN.GINT_V";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final GInt_VRef _GInt_VRefFactory = new GInt_VRef();

  public static ORADataFactory getORADataFactory()
  { return _GInt_VRefFactory; }
  /* constructor */
  public GInt_VRef()
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
    GInt_VRef r = new GInt_VRef();
    r._ref = (REF) d;
    return r;
  }

  public static GInt_VRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (GInt_VRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to GInt_VRef: "+exn.toString()); }
  }

  public GInt_V getValue() throws SQLException
  {
     return (GInt_V) GInt_V.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(GInt_V c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
