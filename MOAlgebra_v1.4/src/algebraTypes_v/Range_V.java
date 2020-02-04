package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class Range_V implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.RANGE_V";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,2003 };
  protected static ORADataFactory[] _factory = new ORADataFactory[2];
  static
  {
    _factory[1] = Intervals_V.getORADataFactory();
  }
  protected static final Range_V _Range_VFactory = new Range_V();

  public static ORADataFactory getORADataFactory()
  { return _Range_VFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[2], _sqlType, _factory); }
  public Range_V()
  { _init_struct(true); }
  public Range_V(java.math.BigDecimal type, Intervals_V intvs) throws SQLException
  { _init_struct(true);
    setType(type);
    setIntvs(intvs);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(Range_V o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new Range_V();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getType() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setType(java.math.BigDecimal type) throws SQLException
  { _struct.setAttribute(0, type); }


  public Intervals_V getIntvs() throws SQLException
  { return (Intervals_V) _struct.getAttribute(1); }

  public void setIntvs(Intervals_V intvs) throws SQLException
  { _struct.setAttribute(1, intvs); }

}
