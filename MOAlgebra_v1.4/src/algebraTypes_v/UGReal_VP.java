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

public class UGReal_VP implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.UGREAL_VP";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final UGReal_VP _UGReal_VPFactory = new UGReal_VP();

  public static ORADataFactory getORADataFactory()
  { return _UGReal_VPFactory; }
  /* constructors */
  public UGReal_VP()
  {
    this((UGReal_P[])null);
  }

  public UGReal_VP(UGReal_P[] a)
  {
    _array = new MutableArray(2002, a, UGReal_P.getORADataFactory());
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
    UGReal_VP a = new UGReal_VP();
    a._array = new MutableArray(2002, (ARRAY) d, UGReal_P.getORADataFactory());
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
  public UGReal_P[] getArray() throws SQLException
  {
    return (UGReal_P[]) _array.getObjectArray(
      new UGReal_P[_array.length()]);
  }

  public UGReal_P[] getArray(long index, int count) throws SQLException
  {
    return (UGReal_P[]) _array.getObjectArray(index,
      new UGReal_P[_array.sliceLength(index, count)]);
  }

  public void setArray(UGReal_P[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(UGReal_P[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public UGReal_P getElement(long index) throws SQLException
  {
    return (UGReal_P) _array.getObjectElement(index);
  }

  public void setElement(UGReal_P a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

}
