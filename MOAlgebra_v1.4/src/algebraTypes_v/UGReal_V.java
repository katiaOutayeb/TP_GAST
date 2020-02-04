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

public class UGReal_V implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.UGREAL_V";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final UGReal_V _UGReal_VFactory = new UGReal_V();

  public static ORADataFactory getORADataFactory()
  { return _UGReal_VFactory; }
  /* constructors */
  public UGReal_V()
  {
    this((UGReal[])null);
  }

  public UGReal_V(UGReal[] a)
  {
    _array = new MutableArray(2002, a, UGReal.getORADataFactory());
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
    UGReal_V a = new UGReal_V();
    a._array = new MutableArray(2002, (ARRAY) d, UGReal.getORADataFactory());
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
  public UGReal[] getArray() throws SQLException
  {
    return (UGReal[]) _array.getObjectArray(
      new UGReal[_array.length()]);
  }

  public UGReal[] getArray(long index, int count) throws SQLException
  {
    return (UGReal[]) _array.getObjectArray(index,
      new UGReal[_array.sliceLength(index, count)]);
  }

  public void setArray(UGReal[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(UGReal[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public UGReal getElement(long index) throws SQLException
  {
    return (UGReal) _array.getObjectElement(index);
  }

  public void setElement(UGReal a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

}
