package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class UInt implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.UINT";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 4,2,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[3];
  protected static final UInt _UIntFactory = new UInt();

  public static ORADataFactory getORADataFactory()
  { return _UIntFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[3], _sqlType, _factory); }
  public UInt()
  { _init_struct(true); }
  public UInt(Integer val, java.math.BigDecimal t1, java.math.BigDecimal t2) throws SQLException
  { _init_struct(true);
    setVal(val);
    setT1(t1);
    setT2(t2);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(UInt o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new UInt();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public Integer getVal() throws SQLException
  { return (Integer) _struct.getAttribute(0); }

  public void setVal(Integer val) throws SQLException
  { _struct.setAttribute(0, val); }


  public java.math.BigDecimal getT1() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(1); }

  public void setT1(java.math.BigDecimal t1) throws SQLException
  { _struct.setAttribute(1, t1); }


  public java.math.BigDecimal getT2() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(2); }

  public void setT2(java.math.BigDecimal t2) throws SQLException
  { _struct.setAttribute(2, t2); }

}
