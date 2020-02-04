package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.jpub.runtime.MutableArray;

public class UGPoint_V implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.UGPOINT_V";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final UGPoint_V _UGPoint_VFactory = new UGPoint_V();

  public static ORADataFactory getORADataFactory()
  { return _UGPoint_VFactory; }
  /* constructors */
  public UGPoint_V()
  {
    this((UGPoint[])null);
  }

  public UGPoint_V(UGPoint[] a)
  {
    _array = new MutableArray(2002, a, UGPoint.getORADataFactory());
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _array.toDatum(c, _SQL_NAME);
  }

  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    UGPoint_V a = new UGPoint_V();
    a._array = new MutableArray(2002, (ARRAY) d, UGPoint.getORADataFactory());
    return a;
  }

  public int length() throws SQLException
  {
    return _array.length();
  }

  public int getBaseType() throws SQLException
  {
    return _array.getBaseType();
  }

  public String getBaseTypeName() throws SQLException
  {
    return _array.getBaseTypeName();
  }

  public ArrayDescriptor getDescriptor() throws SQLException
  {
    return _array.getDescriptor();
  }

  /* array accessor methods */
  public UGPoint[] getArray() throws SQLException
  {
    return (UGPoint[]) _array.getObjectArray(
      new UGPoint[_array.length()]);
  }

  public UGPoint[] getArray(long index, int count) throws SQLException
  {
    return (UGPoint[]) _array.getObjectArray(index,
      new UGPoint[_array.sliceLength(index, count)]);
  }

  public void setArray(UGPoint[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(UGPoint[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public UGPoint getElement(long index) throws SQLException
  {
    return (UGPoint) _array.getObjectElement(index);
  }

  public void setElement(UGPoint a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

}
