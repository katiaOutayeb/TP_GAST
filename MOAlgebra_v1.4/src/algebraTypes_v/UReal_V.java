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

public class UReal_V implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.UREAL_V";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final UReal_V _UReal_VFactory = new UReal_V();

  public static ORADataFactory getORADataFactory()
  { return _UReal_VFactory; }
  /* constructors */
  public UReal_V()
  {
    this((UReal[])null);
  }

  public UReal_V(UReal[] a)
  {
    _array = new MutableArray(2002, a, UReal.getORADataFactory());
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
    UReal_V a = new UReal_V();
    a._array = new MutableArray(2002, (ARRAY) d, UReal.getORADataFactory());
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
  public UReal[] getArray() throws SQLException
  {
    return (UReal[]) _array.getObjectArray(
      new UReal[_array.length()]);
  }

  public UReal[] getArray(long index, int count) throws SQLException
  {
    return (UReal[]) _array.getObjectArray(index,
      new UReal[_array.sliceLength(index, count)]);
  }

  public void setArray(UReal[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(UReal[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public UReal getElement(long index) throws SQLException
  {
    return (UReal) _array.getObjectElement(index);
  }

  public void setElement(UReal a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

}
