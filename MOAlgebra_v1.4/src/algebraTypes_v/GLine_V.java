package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class GLine_V implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.GLINE_V";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 4,2003 };
  protected static ORADataFactory[] _factory = new ORADataFactory[2];
  static
  {
    _factory[1] = Sections_V.getORADataFactory();
  }
  protected static final GLine_V _GLine_VFactory = new GLine_V();

  public static ORADataFactory getORADataFactory()
  { return _GLine_VFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[2], _sqlType, _factory); }
  public GLine_V()
  { _init_struct(true); }
  public GLine_V(Integer nid, Sections_V rints) throws SQLException
  { _init_struct(true);
    setNid(nid);
    setRints(rints);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(GLine_V o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new GLine_V();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public Integer getNid() throws SQLException
  { return (Integer) _struct.getAttribute(0); }

  public void setNid(Integer nid) throws SQLException
  { _struct.setAttribute(0, nid); }


  public Sections_V getRints() throws SQLException
  { return (Sections_V) _struct.getAttribute(1); }

  public void setRints(Sections_V rints) throws SQLException
  { _struct.setAttribute(1, rints); }

}
