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

public class Sections_S_V implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.S_SECTIONS_V";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final Sections_S_V _S_Sections_VFactory = new Sections_S_V();

  public static ORADataFactory getORADataFactory()
  { return _S_Sections_VFactory; }
  /* constructors */
  public Sections_S_V()
  {
    this((Section_S[])null);
  }

  public Sections_S_V(Section_S[] a)
  {
    _array = new MutableArray(2002, a, Section_S.getORADataFactory());
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
    Sections_S_V a = new Sections_S_V();
    a._array = new MutableArray(2002, (ARRAY) d, Section_S.getORADataFactory());
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
  public Section_S[] getArray() throws SQLException
  {
    return (Section_S[]) _array.getObjectArray(
      new Section_S[_array.length()]);
  }

  public Section_S[] getArray(long index, int count) throws SQLException
  {
    return (Section_S[]) _array.getObjectArray(index,
      new Section_S[_array.sliceLength(index, count)]);
  }

  public void setArray(Section_S[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(Section_S[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public Section_S getElement(long index) throws SQLException
  {
    return (Section_S) _array.getObjectElement(index);
  }

  public void setElement(Section_S a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

}
