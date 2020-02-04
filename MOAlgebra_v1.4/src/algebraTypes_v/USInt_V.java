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

public class USInt_V implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.USINT_V";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final USInt_V _USInt_VFactory = new USInt_V();

  public static ORADataFactory getORADataFactory()
  { return _USInt_VFactory; }
  /* constructors */
  public USInt_V()
  {
    this((USInt[])null);
  }

  public USInt_V(USInt[] a)
  {
    _array = new MutableArray(2002, a, USInt.getORADataFactory());
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
    USInt_V a = new USInt_V();
    a._array = new MutableArray(2002, (ARRAY) d, USInt.getORADataFactory());
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
  public USInt[] getArray() throws SQLException
  {
    return (USInt[]) _array.getObjectArray(
      new USInt[_array.length()]);
  }

  public USInt[] getArray(long index, int count) throws SQLException
  {
    return (USInt[]) _array.getObjectArray(index,
      new USInt[_array.sliceLength(index, count)]);
  }

  public void setArray(USInt[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(USInt[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public USInt getElement(long index) throws SQLException
  {
    return (USInt) _array.getObjectElement(index);
  }

  public void setElement(USInt a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

}
