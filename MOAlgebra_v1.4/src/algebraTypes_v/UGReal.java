package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class UGReal implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.UGREAL";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,2,2,2,4,2002,4 };
  protected static ORADataFactory[] _factory = new ORADataFactory[7];
  static
  {
    _factory[5] = Section.getORADataFactory();
  }
  protected static final UGReal _UGRealFactory = new UGReal();

  public static ORADataFactory getORADataFactory()
  { return _UGRealFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[7], _sqlType, _factory); }
  public UGReal()
  { _init_struct(true); }
  public UGReal(java.math.BigDecimal a, java.math.BigDecimal b, java.math.BigDecimal c, java.math.BigDecimal r, Integer nid, Section interval, Integer sequence) throws SQLException
  { _init_struct(true);
    setA(a);
    setB(b);
    setC(c);
    setR(r);
    setNid(nid);
    setInterval(interval);
    setSequence(sequence);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(UGReal o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new UGReal();
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


  public Integer getNid() throws SQLException
  { return (Integer) _struct.getAttribute(4); }

  public void setNid(Integer nid) throws SQLException
  { _struct.setAttribute(4, nid); }


  public Section getInterval() throws SQLException
  { return (Section) _struct.getAttribute(5); }

  public void setInterval(Section interval) throws SQLException
  { _struct.setAttribute(5, interval); }


  public Integer getSequence() throws SQLException
  { return (Integer) _struct.getAttribute(6); }

  public void setSequence(Integer sequence) throws SQLException
  { _struct.setAttribute(6, sequence); }

}
