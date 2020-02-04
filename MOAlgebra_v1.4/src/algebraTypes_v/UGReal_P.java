package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class UGReal_P implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.UGREAL_P";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2003,2,4,2002,4 };
  protected static ORADataFactory[] _factory = new ORADataFactory[5];
  static
  {
    _factory[0] = FuncPar_V.getORADataFactory();
    _factory[3] = Section.getORADataFactory();
  }
  protected static final UGReal_P _UGReal_PFactory = new UGReal_P();

  public static ORADataFactory getORADataFactory()
  { return _UGReal_PFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[5], _sqlType, _factory); }
  public UGReal_P()
  { _init_struct(true); }
  public UGReal_P(FuncPar_V fp, java.math.BigDecimal r, Integer nid, Section interval, Integer sequence) throws SQLException
  { _init_struct(true);
    setFp(fp);
    setR(r);
    setNid(nid);
    setInterval(interval);
    setSequence(sequence);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(UGReal_P o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new UGReal_P();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public FuncPar_V getFp() throws SQLException
  { return (FuncPar_V) _struct.getAttribute(0); }

  public void setFp(FuncPar_V fp) throws SQLException
  { _struct.setAttribute(0, fp); }


  public java.math.BigDecimal getR() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(1); }

  public void setR(java.math.BigDecimal r) throws SQLException
  { _struct.setAttribute(1, r); }


  public Integer getNid() throws SQLException
  { return (Integer) _struct.getAttribute(2); }

  public void setNid(Integer nid) throws SQLException
  { _struct.setAttribute(2, nid); }


  public Section getInterval() throws SQLException
  { return (Section) _struct.getAttribute(3); }

  public void setInterval(Section interval) throws SQLException
  { _struct.setAttribute(3, interval); }


  public Integer getSequence() throws SQLException
  { return (Integer) _struct.getAttribute(4); }

  public void setSequence(Integer sequence) throws SQLException
  { _struct.setAttribute(4, sequence); }

}
