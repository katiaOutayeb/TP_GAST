package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class USInt implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.USINT";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 4,2002 };
  protected static ORADataFactory[] _factory = new ORADataFactory[2];
  static
  {
    _factory[2] = Section_S.getORADataFactory();
  }
  protected static final USInt _USIntFactory = new USInt();

  public static ORADataFactory getORADataFactory()
  { return _USIntFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[2], _sqlType, _factory); }
  public USInt()
  { _init_struct(true); }
  public USInt(Integer val, Section_S interval) throws SQLException
  { _init_struct(true);
    setVal(val);
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
  protected ORAData create(USInt o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new USInt();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public Integer getVal() throws SQLException
  { return (Integer) _struct.getAttribute(0); }

  public void setVal(Integer val) throws SQLException
  { _struct.setAttribute(0, val); }



  public Section_S getInterval() throws SQLException
  { return (Section_S) _struct.getAttribute(1); }

  public void setInterval(Section_S interval) throws SQLException
  { _struct.setAttribute(1, interval); }


}
