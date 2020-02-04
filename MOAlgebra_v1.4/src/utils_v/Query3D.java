package utils_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class Query3D implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.QUERY3D";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,2,2,2,2,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[6];
  protected static final Query3D _Query3DFactory = new Query3D();

  public static ORADataFactory getORADataFactory()
  { return _Query3DFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[6], _sqlType, _factory); }
  public Query3D()
  { _init_struct(true); }
  public Query3D(java.math.BigDecimal xmincoordinate, java.math.BigDecimal ymincoordinate, java.math.BigDecimal xmaxcoordinate, java.math.BigDecimal ymaxcoordinate, java.math.BigDecimal tmin, java.math.BigDecimal tmax) throws SQLException
  { _init_struct(true);
    setXmincoordinate(xmincoordinate);
    setYmincoordinate(ymincoordinate);
    setXmaxcoordinate(xmaxcoordinate);
    setYmaxcoordinate(ymaxcoordinate);
    setTmin(tmin);
    setTmax(tmax);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(Query3D o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new Query3D();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getXmincoordinate() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setXmincoordinate(java.math.BigDecimal xmincoordinate) throws SQLException
  { _struct.setAttribute(0, xmincoordinate); }


  public java.math.BigDecimal getYmincoordinate() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(1); }

  public void setYmincoordinate(java.math.BigDecimal ymincoordinate) throws SQLException
  { _struct.setAttribute(1, ymincoordinate); }


  public java.math.BigDecimal getXmaxcoordinate() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(2); }

  public void setXmaxcoordinate(java.math.BigDecimal xmaxcoordinate) throws SQLException
  { _struct.setAttribute(2, xmaxcoordinate); }


  public java.math.BigDecimal getYmaxcoordinate() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(3); }

  public void setYmaxcoordinate(java.math.BigDecimal ymaxcoordinate) throws SQLException
  { _struct.setAttribute(3, ymaxcoordinate); }


  public java.math.BigDecimal getTmin() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(4); }

  public void setTmin(java.math.BigDecimal tmin) throws SQLException
  { _struct.setAttribute(4, tmin); }


  public java.math.BigDecimal getTmax() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(5); }

  public void setTmax(java.math.BigDecimal tmax) throws SQLException
  { _struct.setAttribute(5, tmax); }

}
