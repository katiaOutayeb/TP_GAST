package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class PercentileGRImplRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "SYSMAN.PERCENTILEGRIMPL";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final PercentileGRImplRef _PercentileGRImplRefFactory = new PercentileGRImplRef();

  public static ORADataFactory getORADataFactory()
  { return _PercentileGRImplRefFactory; }
  /* constructor */
  public PercentileGRImplRef()
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
    PercentileGRImplRef r = new PercentileGRImplRef();
    r._ref = (REF) d;
    return r;
  }

  public static PercentileGRImplRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (PercentileGRImplRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to PercentileGRImplRef: "+exn.toString()); }
  }

  public PercentileGRImpl getValue() throws SQLException
  {
     return (PercentileGRImpl) PercentileGRImpl.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(PercentileGRImpl c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
