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

public class UInt_V implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.UINT_V";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final UInt_V _UInt_VFactory = new UInt_V();

  public static ORADataFactory getORADataFactory()
  { return _UInt_VFactory; }
  /* constructors */
  public UInt_V()
  {
    this((UInt[])null);
  }

  public UInt_V(UInt[] a)
  {
    _array = new MutableArray(2002, a, UInt.getORADataFactory());
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
    UInt_V a = new UInt_V();
    a._array = new MutableArray(2002, (ARRAY) d, UInt.getORADataFactory());
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
  public UInt[] getArray() throws SQLException
  {
    return (UInt[]) _array.getObjectArray(
      new UInt[_array.length()]);
  }

  public UInt[] getArray(long index, int count) throws SQLException
  {
    return (UInt[]) _array.getObjectArray(index,
      new UInt[_array.sliceLength(index, count)]);
  }

  public void setArray(UInt[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(UInt[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public UInt getElement(long index) throws SQLException
  {
    return (UInt) _array.getObjectElement(index);
  }

  public void setElement(UInt a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

}
