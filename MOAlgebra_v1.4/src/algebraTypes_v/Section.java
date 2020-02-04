package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class Section implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.SECTION";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 4,4,2,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[4];
  protected static final Section _SectionFactory = new Section();

  public static ORADataFactory getORADataFactory()
  { return _SectionFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[4], _sqlType, _factory); }
  public Section()
  { _init_struct(true); }
  public Section(Integer rid, Integer side, java.math.BigDecimal pos1, java.math.BigDecimal pos2) throws SQLException
  { _init_struct(true);
    setRid(rid);
    setSide(side);
    setPos1(pos1);
    setPos2(pos2);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(Section o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new Section();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public Integer getRid() throws SQLException
  { return (Integer) _struct.getAttribute(0); }

  public void setRid(Integer rid) throws SQLException
  { _struct.setAttribute(0, rid); }


  public Integer getSide() throws SQLException
  { return (Integer) _struct.getAttribute(1); }

  public void setSide(Integer side) throws SQLException
  { _struct.setAttribute(1, side); }


  public java.math.BigDecimal getPos1() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(2); }

  public void setPos1(java.math.BigDecimal pos1) throws SQLException
  { _struct.setAttribute(2, pos1); }


  public java.math.BigDecimal getPos2() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(3); }

  public void setPos2(java.math.BigDecimal pos2) throws SQLException
  { _struct.setAttribute(3, pos2); }

}
