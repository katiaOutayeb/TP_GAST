package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class USReal implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.USReal";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,2,2,2,2002 };
  protected static ORADataFactory[] _factory = new ORADataFactory[5];
  static
  {
    _factory[5] = Section_S.getORADataFactory();
  }
  protected static final USReal _USRealFactory = new USReal();

  public static ORADataFactory getORADataFactory()
  { return _USRealFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[5], _sqlType, _factory); }
  public USReal()
  { _init_struct(true); }
  public USReal(java.math.BigDecimal a, java.math.BigDecimal b, java.math.BigDecimal c, java.math.BigDecimal r, Section_S interval) throws SQLException
  { _init_struct(true);
    setA(a);
    setB(b);
    setC(c);
    setR(r);
    setInterval(interval);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(USReal o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new USReal();
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


  public java.math.BigDecimal getR() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(3); }

  public void setR(java.math.BigDecimal r) throws SQLException
  { _struct.setAttribute(3, r); }


  public Section_S getInterval() throws SQLException
  { return (Section_S) _struct.getAttribute(4); }

  public void setInterval(Section_S interval) throws SQLException
  { _struct.setAttribute(4, interval); }

}
