package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class Interval_V implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.INTERVAL_V";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,2,2,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[4];
  protected static final Interval_V _Interval_VFactory = new Interval_V();

  public static ORADataFactory getORADataFactory()
  { return _Interval_VFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[4], _sqlType, _factory); }
  public Interval_V()
  { _init_struct(true); }
  public Interval_V(java.math.BigDecimal s, java.math.BigDecimal e, java.math.BigDecimal lc, java.math.BigDecimal rc) throws SQLException
  { _init_struct(true);
    setS(s);
    setE(e);
    setLc(lc);
    setRc(rc);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(Interval_V o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new Interval_V();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getS() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setS(java.math.BigDecimal s) throws SQLException
  { _struct.setAttribute(0, s); }


  public java.math.BigDecimal getE() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(1); }

  public void setE(java.math.BigDecimal e) throws SQLException
  { _struct.setAttribute(1, e); }


  public java.math.BigDecimal getLc() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(2); }

  public void setLc(java.math.BigDecimal lc) throws SQLException
  { _struct.setAttribute(2, lc); }


  public java.math.BigDecimal getRc() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(3); }

  public void setRc(java.math.BigDecimal rc) throws SQLException
  { _struct.setAttribute(3, rc); }

}
