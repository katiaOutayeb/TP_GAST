package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class MGPoint_V implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.MGPOINT_V";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2003 };
  protected static ORADataFactory[] _factory = new ORADataFactory[1];
  static
  {
    _factory[0] = UGPoint_V.getORADataFactory();
  }
  protected static final MGPoint_V _MGPoint_VFactory = new MGPoint_V();

  public static ORADataFactory getORADataFactory()
  { return _MGPoint_VFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[1], _sqlType, _factory); }
  public MGPoint_V()
  { _init_struct(true); }
  public MGPoint_V(UGPoint_V units) throws SQLException
  { _init_struct(true);
    setUnits(units);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(MGPoint_V o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new MGPoint_V();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public UGPoint_V getUnits() throws SQLException
  { return (UGPoint_V) _struct.getAttribute(0); }

  public void setUnits(UGPoint_V units) throws SQLException
  { _struct.setAttribute(0, units); }

}
