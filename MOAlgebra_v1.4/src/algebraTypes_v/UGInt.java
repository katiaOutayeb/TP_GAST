package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class UGInt implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.UGINT";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 4,4,2002,4 };
  protected static ORADataFactory[] _factory = new ORADataFactory[4];
  static
  {
    _factory[2] = Section.getORADataFactory();
  }
  protected static final UGInt _UGIntFactory = new UGInt();

  public static ORADataFactory getORADataFactory()
  { return _UGIntFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[4], _sqlType, _factory); }
  public UGInt()
  { _init_struct(true); }
  public UGInt(Integer val, Integer nid, Section interval, Integer sequence) throws SQLException
  { _init_struct(true);
    setVal(val);
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
  protected ORAData create(UGInt o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new UGInt();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public Integer getVal() throws SQLException
  { return (Integer) _struct.getAttribute(0); }

  public void setVal(Integer val) throws SQLException
  { _struct.setAttribute(0, val); }


  public Integer getNid() throws SQLException
  { return (Integer) _struct.getAttribute(1); }

  public void setNid(Integer nid) throws SQLException
  { _struct.setAttribute(1, nid); }


  public Section getInterval() throws SQLException
  { return (Section) _struct.getAttribute(2); }

  public void setInterval(Section interval) throws SQLException
  { _struct.setAttribute(2, interval); }


  public Integer getSequence() throws SQLException
  { return (Integer) _struct.getAttribute(3); }

  public void setSequence(Integer sequence) throws SQLException
  { _struct.setAttribute(3, sequence); }

}
