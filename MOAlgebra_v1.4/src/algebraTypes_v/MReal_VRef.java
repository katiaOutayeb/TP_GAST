package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class MReal_VRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "SYSMAN.MREAL_V";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final MReal_VRef _MReal_VRefFactory = new MReal_VRef();

  public static ORADataFactory getORADataFactory()
  { return _MReal_VRefFactory; }
  /* constructor */
  public MReal_VRef()
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
    MReal_VRef r = new MReal_VRef();
    r._ref = (REF) d;
    return r;
  }

  public static MReal_VRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (MReal_VRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to MReal_VRef: "+exn.toString()); }
  }

  public MReal_V getValue() throws SQLException
  {
     return (MReal_V) MReal_V.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(MReal_V c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
