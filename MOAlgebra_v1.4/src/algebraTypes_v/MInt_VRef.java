package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class MInt_VRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "SYSMAN.MINT_V";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final MInt_VRef _MInt_VRefFactory = new MInt_VRef();

  public static ORADataFactory getORADataFactory()
  { return _MInt_VRefFactory; }
  /* constructor */
  public MInt_VRef()
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
    MInt_VRef r = new MInt_VRef();
    r._ref = (REF) d;
    return r;
  }

  public static MInt_VRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (MInt_VRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to MInt_VRef: "+exn.toString()); }
  }

  public MInt_V getValue() throws SQLException
  {
     return (MInt_V) MInt_V.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(MInt_V c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
