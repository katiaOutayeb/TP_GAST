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

public class FuncPar_V implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.FUNCPAR_V";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final FuncPar_V _FuncPar_VFactory = new FuncPar_V();

  public static ORADataFactory getORADataFactory()
  { return _FuncPar_VFactory; }
  /* constructors */
  public FuncPar_V()
  {
    this((FuncPar[])null);
  }

  public FuncPar_V(FuncPar[] a)
  {
    _array = new MutableArray(2002, a, FuncPar.getORADataFactory());
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
    FuncPar_V a = new FuncPar_V();
    a._array = new MutableArray(2002, (ARRAY) d, FuncPar.getORADataFactory());
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
  public FuncPar[] getArray() throws SQLException
  {
    return (FuncPar[]) _array.getObjectArray(
      new FuncPar[_array.length()]);
  }

  public FuncPar[] getArray(long index, int count) throws SQLException
  {
    return (FuncPar[]) _array.getObjectArray(index,
      new FuncPar[_array.sliceLength(index, count)]);
  }

  public void setArray(FuncPar[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(FuncPar[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public FuncPar getElement(long index) throws SQLException
  {
    return (FuncPar) _array.getObjectElement(index);
  }

  public void setElement(FuncPar a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

}
