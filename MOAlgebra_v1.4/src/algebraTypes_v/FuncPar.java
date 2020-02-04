package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class FuncPar implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.FUNCPAR";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,2,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[3];
  protected static final FuncPar _FuncParFactory = new FuncPar();

  public static ORADataFactory getORADataFactory()
  { return _FuncParFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[3], _sqlType, _factory); }
  public FuncPar()
  { _init_struct(true); }
  public FuncPar(java.math.BigDecimal a, java.math.BigDecimal b, java.math.BigDecimal c) throws SQLException
  { _init_struct(true);
    setA(a);
    setB(b);
    setC(c);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(FuncPar o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new FuncPar();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getA() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setA(java.math.BigDecimal a) throws SQLException
  { _struct.setAttribute(0, a); }


  public java.math.BigDecimal getB() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(1); }

  public void setB(java.math.BigDecimal b) throws SQLException
  { _struct.setAttribute(1, b); }


  public java.math.BigDecimal getC() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(2); }

  public void setC(java.math.BigDecimal c) throws SQLException
  { _struct.setAttribute(2, c); }

}
