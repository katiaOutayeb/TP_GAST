package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class UGPoint implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.UGPOINT";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 4,4,4,2,2,2,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[7];
  protected static final UGPoint _UGPointFactory = new UGPoint();

  public static ORADataFactory getORADataFactory()
  { return _UGPointFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[7], _sqlType, _factory); }
  public UGPoint()
  { _init_struct(true); }
  public UGPoint(Integer nid, Integer rid, Integer side, java.math.BigDecimal t1, java.math.BigDecimal t2, java.math.BigDecimal pos1, java.math.BigDecimal pos2) throws SQLException
  { _init_struct(true);
    setNid(nid);
    setRid(rid);
    setSide(side);
    setT1(t1);
    setT2(t2);
    setPos1(pos1);
    setPos2(pos2);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(UGPoint o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new UGPoint();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public Integer getNid() throws SQLException
  { return (Integer) _struct.getAttribute(0); }

  public void setNid(Integer nid) throws SQLException
  { _struct.setAttribute(0, nid); }


  public Integer getRid() throws SQLException
  { return (Integer) _struct.getAttribute(1); }

  public void setRid(Integer rid) throws SQLException
  { _struct.setAttribute(1, rid); }


  public Integer getSide() throws SQLException
  { return (Integer) _struct.getAttribute(2); }

  public void setSide(Integer side) throws SQLException
  { _struct.setAttribute(2, side); }


  public java.math.BigDecimal getT1() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(3); }

  public void setT1(java.math.BigDecimal t1) throws SQLException
  { _struct.setAttribute(3, t1); }


  public java.math.BigDecimal getT2() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(4); }

  public void setT2(java.math.BigDecimal t2) throws SQLException
  { _struct.setAttribute(4, t2); }


  public java.math.BigDecimal getPos1() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(5); }

  public void setPos1(java.math.BigDecimal pos1) throws SQLException
  { _struct.setAttribute(5, pos1); }


  public java.math.BigDecimal getPos2() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(6); }

  public void setPos2(java.math.BigDecimal pos2) throws SQLException
  { _struct.setAttribute(6, pos2); }

}
