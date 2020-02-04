package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class GPoint implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.GPOINT";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 4,4,2,4 };
  protected static ORADataFactory[] _factory = new ORADataFactory[4];
  protected static final GPoint _GPointFactory = new GPoint();

  public static ORADataFactory getORADataFactory()
  { return _GPointFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[4], _sqlType, _factory); }
  public GPoint()
  { _init_struct(true); }
  public GPoint(Integer nid, Integer rid, java.math.BigDecimal pos, Integer side) throws SQLException
  { _init_struct(true);
    setNid(nid);
    setRid(rid);
    setPos(pos);
    setSide(side);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(GPoint o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new GPoint();
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


  public java.math.BigDecimal getPos() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(2); }

  public void setPos(java.math.BigDecimal pos) throws SQLException
  { _struct.setAttribute(2, pos); }


  public Integer getSide() throws SQLException
  { return (Integer) _struct.getAttribute(3); }

  public void setSide(Integer side) throws SQLException
  { _struct.setAttribute(3, side); }

}
