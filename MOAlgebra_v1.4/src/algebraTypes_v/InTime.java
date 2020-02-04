package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class InTime implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.INTIME";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2002,2,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[3];
  static
  {
    _factory[0] = GPoint.getORADataFactory();
  }
  protected static final InTime _InTimeFactory = new InTime();

  public static ORADataFactory getORADataFactory()
  { return _InTimeFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[3], _sqlType, _factory); }
  public InTime()
  { _init_struct(true); }
  public InTime(GPoint gp, java.math.BigDecimal val, java.math.BigDecimal t) throws SQLException
  { _init_struct(true);
    setGp(gp);
    setVal(val);
    setT(t);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(InTime o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new InTime();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public GPoint getGp() throws SQLException
  { return (GPoint) _struct.getAttribute(0); }

  public void setGp(GPoint gp) throws SQLException
  { _struct.setAttribute(0, gp); }


  public java.math.BigDecimal getVal() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(1); }

  public void setVal(java.math.BigDecimal val) throws SQLException
  { _struct.setAttribute(1, val); }


  public java.math.BigDecimal getT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(2); }

  public void setT(java.math.BigDecimal t) throws SQLException
  { _struct.setAttribute(2, t); }

}
