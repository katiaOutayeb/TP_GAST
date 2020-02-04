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

public class Sections_V implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.SECTIONS_V";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final Sections_V _Sections_VFactory = new Sections_V();

  public static ORADataFactory getORADataFactory()
  { return _Sections_VFactory; }
  /* constructors */
  public Sections_V()
  {
    this((Section[])null);
  }

  public Sections_V(Section[] a)
  {
    _array = new MutableArray(2002, a, Section.getORADataFactory());
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
    Sections_V a = new Sections_V();
    a._array = new MutableArray(2002, (ARRAY) d, Section.getORADataFactory());
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
  public Section[] getArray() throws SQLException
  {
    return (Section[]) _array.getObjectArray(
      new Section[_array.length()]);
  }

  public Section[] getArray(long index, int count) throws SQLException
  {
    return (Section[]) _array.getObjectArray(index,
      new Section[_array.sliceLength(index, count)]);
  }

  public void setArray(Section[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(Section[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public Section getElement(long index) throws SQLException
  {
    return (Section) _array.getObjectElement(index);
  }

  public void setElement(Section a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

}
