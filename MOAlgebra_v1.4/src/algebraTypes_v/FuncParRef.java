package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class FuncParRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "SYSMAN.FUNCPAR";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final FuncParRef _FuncParRefFactory = new FuncParRef();

  public static ORADataFactory getORADataFactory()
  { return _FuncParRefFactory; }
  /* constructor */
  public FuncParRef()
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
    FuncParRef r = new FuncParRef();
    r._ref = (REF) d;
    return r;
  }

  public static FuncParRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (FuncParRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to FuncParRef: "+exn.toString()); }
  }

  public FuncPar getValue() throws SQLException
  {
     return (FuncPar) FuncPar.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(FuncPar c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
