package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class Section_S implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.S_Section";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[6];
  protected static final Section_S _Section_SFactory = new Section_S();

  public static ORADataFactory getORADataFactory()
  { return _Section_SFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[6], _sqlType, _factory); }
  public Section_S()
  { _init_struct(true); }
  public Section_S(java.math.BigDecimal cle1, java.math.BigDecimal cle2) throws SQLException
  { _init_struct(true);
    setCle1(cle1);
    setCle2(cle2);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(Section_S o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new Section_S();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */

  public java.math.BigDecimal getCle1() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setCle1(java.math.BigDecimal cle1) throws SQLException
  { _struct.setAttribute(0, cle1); }


  public java.math.BigDecimal getCle2() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(1); }

  public void setCle2(java.math.BigDecimal cle2) throws SQLException
  { _struct.setAttribute(1, cle2); }

}
