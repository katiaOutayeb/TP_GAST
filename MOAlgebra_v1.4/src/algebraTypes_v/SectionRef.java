package algebraTypes_v;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class SectionRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "SYSMAN.SECTION";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final SectionRef _SectionRefFactory = new SectionRef();

  public static ORADataFactory getORADataFactory()
  { return _SectionRefFactory; }
  /* constructor */
  public SectionRef()
  {
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _ref;
  }

  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    SectionRef r = new SectionRef();
    r._ref = (REF) d;
    return r;
  }

  public static SectionRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (SectionRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to SectionRef: "+exn.toString()); }
  }

  public Section getValue() throws SQLException
  {
     return (Section) Section.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(Section c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
