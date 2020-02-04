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

public class USReal_V implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.USREAL_V";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final USReal_V _USReal_VFactory = new USReal_V();

  public static ORADataFactory getORADataFactory()
  { return _USReal_VFactory; }
  /* constructors */
  public USReal_V()
  {
    this((USReal[])null);
  }

  public USReal_V(USReal[] a)
  {
    _array = new MutableArray(2002, a, USReal.getORADataFactory());
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
    USReal_V a = new USReal_V();
    a._array = new MutableArray(2002, (ARRAY) d, USReal.getORADataFactory());
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
  public USReal[] getArray() throws SQLException
  {
    return (USReal[]) _array.getObjectArray(
      new USReal[_array.length()]);
  }

  public USReal[] getArray(long index, int count) throws SQLException
  {
    return (USReal[]) _array.getObjectArray(index,
      new USReal[_array.sliceLength(index, count)]);
  }

  public void setArray(USReal[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(USReal[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public USReal getElement(long index) throws SQLException
  {
    return (USReal) _array.getObjectElement(index);
  }

  public void setElement(USReal a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

}
