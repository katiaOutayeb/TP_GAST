package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class InGPoint implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.INGPOINT";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,2002 };
  protected static ORADataFactory[] _factory = new ORADataFactory[2];
  static
  {
    _factory[1] = GPoint.getORADataFactory();
  }
  protected static final InGPoint _InGPointFactory = new InGPoint();

  public static ORADataFactory getORADataFactory()
  { return _InGPointFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[2], _sqlType, _factory); }
  public InGPoint()
  { _init_struct(true); }
  public InGPoint(java.math.BigDecimal val, GPoint gp) throws SQLException
  { _init_struct(true);
    setVal(val);
    setGp(gp);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(InGPoint o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new InGPoint();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getVal() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setVal(java.math.BigDecimal val) throws SQLException
  { _struct.setAttribute(0, val); }


  public GPoint getGp() throws SQLException
  { return (GPoint) _struct.getAttribute(1); }

  public void setGp(GPoint gp) throws SQLException
  { _struct.setAttribute(1, gp); }

}
