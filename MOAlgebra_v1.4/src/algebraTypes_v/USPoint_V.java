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

public class USPoint_V implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.USPOINT_V";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final USPoint_V _USPoint_VFactory = new USPoint_V();

  public static ORADataFactory getORADataFactory()
  { return _USPoint_VFactory; }
  /* constructors */
  public USPoint_V()
  {
    this((USPoint[])null);
  }

  public USPoint_V(USPoint[] a)
  {
    _array = new MutableArray(2002, a, USPoint.getORADataFactory());
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
    USPoint_V a = new USPoint_V();
    a._array = new MutableArray(2002, (ARRAY) d, USPoint.getORADataFactory());
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
  public USPoint[] getArray() throws SQLException
  {
    return (USPoint[]) _array.getObjectArray(
      new USPoint[_array.length()]);
  }

  public USPoint[] getArray(long index, int count) throws SQLException
  {
    return (USPoint[]) _array.getObjectArray(index,
      new USPoint[_array.sliceLength(index, count)]);
  }

  public void setArray(USPoint[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(USPoint[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public USPoint getElement(long index) throws SQLException
  {
    return (USPoint) _array.getObjectElement(index);
  }

  public void setElement(USPoint a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

}
