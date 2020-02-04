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

public class UGInt_V implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.UGINT_V";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final UGInt_V _UGInt_VFactory = new UGInt_V();

  public static ORADataFactory getORADataFactory()
  { return _UGInt_VFactory; }
  /* constructors */
  public UGInt_V()
  {
    this((UGInt[])null);
  }

  public UGInt_V(UGInt[] a)
  {
    _array = new MutableArray(2002, a, UGInt.getORADataFactory());
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
    UGInt_V a = new UGInt_V();
    a._array = new MutableArray(2002, (ARRAY) d, UGInt.getORADataFactory());
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
  public UGInt[] getArray() throws SQLException
  {
    return (UGInt[]) _array.getObjectArray(
      new UGInt[_array.length()]);
  }

  public UGInt[] getArray(long index, int count) throws SQLException
  {
    return (UGInt[]) _array.getObjectArray(index,
      new UGInt[_array.sliceLength(index, count)]);
  }

  public void setArray(UGInt[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(UGInt[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public UGInt getElement(long index) throws SQLException
  {
    return (UGInt) _array.getObjectElement(index);
  }

  public void setElement(UGInt a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

}
