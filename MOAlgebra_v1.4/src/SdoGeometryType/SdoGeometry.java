package SdoGeometryType;

/*@lineinfo:filename=SdoGeometry*//*@lineinfo:user-code*//*@lineinfo:1^1*/import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;
import sqlj.runtime.ref.DefaultContext;
import sqlj.runtime.ConnectionContext;

public class SdoGeometry implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "MDSYS.SDO_GEOMETRY";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  /* connection management */
  protected Connection __onn = null;
  protected javax.sql.DataSource __dataSource = null;
  public void setDataSource(javax.sql.DataSource dataSource) throws SQLException
  { release(); __dataSource = dataSource; }
  public void setDataSourceLocation(String dataSourceLocation) throws SQLException {
    javax.sql.DataSource dataSource;
    try {
      Class cls = Class.forName("javax.naming.InitialContext");
      Object ctx = cls.newInstance();
      java.lang.reflect.Method meth = cls.getMethod("lookup", new Class[]{String.class});
      dataSource = (javax.sql.DataSource) meth.invoke(ctx, new Object[]{"java:comp/env/" + dataSourceLocation});
      setDataSource(dataSource);
    } catch (Exception e) {
      throw new java.sql.SQLException("Error initializing DataSource at " + dataSourceLocation + ": " + e.getMessage());
    }
  }
  public Connection getConnection() throws SQLException
  { 
    if (__onn!=null) return __onn;
     else if (__tx!=null) return __tx.getConnection(); 
     else if (__dataSource!=null) __onn= __dataSource.getConnection(); 
     return __onn; 
   } 
  public void release() throws SQLException
  { if (__tx!=null && __onn!=null) __tx.close(ConnectionContext.KEEP_CONNECTION);
    __onn = null; __tx = null;
    __dataSource = null;
  }

  public void closeConnection(){
    if (__dataSource!=null) {
      try { if (__onn!=null) { __onn.close(); } } catch (java.sql.SQLException e) {}
      try { if (__tx!=null) {__tx.close(); } } catch (java.sql.SQLException e) {}
      __onn=null;
      __tx=null;
    }
  }
  protected DefaultContext __tx = null;
  public void setConnectionContext(DefaultContext ctx) throws SQLException
  { release(); __tx = ctx; }
  public DefaultContext getConnectionContext() throws SQLException
  { if (__tx==null)
    { __tx = (getConnection()==null) ? DefaultContext.getDefaultContext() : new DefaultContext(getConnection()); }
    return __tx;
  };
  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,2,2002,2003,2003 };
  protected static ORADataFactory[] _factory = new ORADataFactory[5];
  static
  {
    _factory[2] = SdoPointType.getORADataFactory();
    _factory[3] = SdoElemInfoArray.getORADataFactory();
    _factory[4] = SdoOrdinateArray.getORADataFactory();
  }
  protected static final SdoGeometry _SdoGeometryFactory = new SdoGeometry();

  public static ORADataFactory getORADataFactory()
  { return _SdoGeometryFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[5], _sqlType, _factory); }
  public SdoGeometry()
  { _init_struct(true); __tx = DefaultContext.getDefaultContext(); }
  public SdoGeometry(DefaultContext c) /*throws SQLException*/
  { _init_struct(true); __tx = c; }
  public SdoGeometry(Connection c) /*throws SQLException*/
  { _init_struct(true); __onn = c; }
  public SdoGeometry(java.math.BigDecimal sdoGtype, java.math.BigDecimal sdoSrid, SdoPointType sdoPoint, SdoElemInfoArray sdoElemInfo, SdoOrdinateArray sdoOrdinates) throws SQLException
  {
    _init_struct(true);
    setSdoGtype(sdoGtype);
    setSdoSrid(sdoSrid);
    setSdoPoint(sdoPoint);
    setSdoElemInfo(sdoElemInfo);
    setSdoOrdinates(sdoOrdinates);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    if (__tx!=null && __onn!=c) release();
    __onn = c;
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  public void setFrom(SdoGeometry o) throws SQLException
  { setContextFrom(o); setValueFrom(o); }
  protected void setContextFrom(SdoGeometry o) throws SQLException
  { release(); __tx = o.__tx; __onn = o.__onn; }
  protected void setValueFrom(SdoGeometry o) { _struct = o._struct; }
  protected ORAData create(SdoGeometry o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) { if (o!=null) { o.release(); }; return null; }
    if (o == null) o = new SdoGeometry();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    o.__onn = ((STRUCT) d).getJavaSqlConnection();
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getSdoGtype() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setSdoGtype(java.math.BigDecimal sdoGtype) throws SQLException
  { _struct.setAttribute(0, sdoGtype); }


  public java.math.BigDecimal getSdoSrid() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(1); }

  public void setSdoSrid(java.math.BigDecimal sdoSrid) throws SQLException
  { _struct.setAttribute(1, sdoSrid); }


  public SdoPointType getSdoPoint() throws SQLException
  { return (SdoPointType) _struct.getAttribute(2); }

  public void setSdoPoint(SdoPointType sdoPoint) throws SQLException
  { _struct.setAttribute(2, sdoPoint); }


  public SdoElemInfoArray getSdoElemInfo() throws SQLException
  { return (SdoElemInfoArray) _struct.getAttribute(3); }

  public void setSdoElemInfo(SdoElemInfoArray sdoElemInfo) throws SQLException
  { _struct.setAttribute(3, sdoElemInfo); }


  public SdoOrdinateArray getSdoOrdinates() throws SQLException
  { return (SdoOrdinateArray) _struct.getAttribute(4); }

  public void setSdoOrdinates(SdoOrdinateArray sdoOrdinates) throws SQLException
  { _struct.setAttribute(4, sdoOrdinates); }


  public java.math.BigDecimal getDims ()
  throws java.sql.SQLException
  {
    SdoGeometry __jPt_temp = (SdoGeometry) this;
    java.math.BigDecimal __jPt_result;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:163^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := :__jPt_temp.GET_DIMS();
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  :=  :2 .GET_DIMS();\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0SdoGeometry",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   if (__jPt_temp==null) __sJT_st.setNull(2,2002,"MDSYS.SDO_GEOMETRY"); else __sJT_st.setORAData(2,__jPt_temp);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:167^5*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:173^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := :__jPt_temp.GET_DIMS();
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  :=  :2 .GET_DIMS();\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1SdoGeometry",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   if (__jPt_temp==null) __sJT_st.setNull(2,2002,"MDSYS.SDO_GEOMETRY"); else __sJT_st.setORAData(2,__jPt_temp);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:177^5*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal getGtype ()
  throws java.sql.SQLException
  {
    SdoGeometry __jPt_temp = (SdoGeometry) this;
    java.math.BigDecimal __jPt_result;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:192^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := :__jPt_temp.GET_GTYPE();
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  :=  :2 .GET_GTYPE();\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2SdoGeometry",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   if (__jPt_temp==null) __sJT_st.setNull(2,2002,"MDSYS.SDO_GEOMETRY"); else __sJT_st.setORAData(2,__jPt_temp);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:196^5*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:202^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := :__jPt_temp.GET_GTYPE();
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  :=  :2 .GET_GTYPE();\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3SdoGeometry",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   if (__jPt_temp==null) __sJT_st.setNull(2,2002,"MDSYS.SDO_GEOMETRY"); else __sJT_st.setORAData(2,__jPt_temp);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:206^5*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal getLrsDim ()
  throws java.sql.SQLException
  {
    SdoGeometry __jPt_temp = (SdoGeometry) this;
    java.math.BigDecimal __jPt_result;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:221^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := :__jPt_temp.GET_LRS_DIM();
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  :=  :2 .GET_LRS_DIM();\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"4SdoGeometry",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   if (__jPt_temp==null) __sJT_st.setNull(2,2002,"MDSYS.SDO_GEOMETRY"); else __sJT_st.setORAData(2,__jPt_temp);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:225^5*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:231^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := :__jPt_temp.GET_LRS_DIM();
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  :=  :2 .GET_LRS_DIM();\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"5SdoGeometry",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   if (__jPt_temp==null) __sJT_st.setNull(2,2002,"MDSYS.SDO_GEOMETRY"); else __sJT_st.setORAData(2,__jPt_temp);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:235^5*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public oracle.sql.BLOB getWkb ()
  throws java.sql.SQLException
  {
    SdoGeometry __jPt_temp = (SdoGeometry) this;
    oracle.sql.BLOB __jPt_result;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:250^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := :__jPt_temp.GET_WKB();
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  :=  :2 .GET_WKB();\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"6SdoGeometry",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BLOB);
   }
   // set IN parameters
   if (__jPt_temp==null) __sJT_st.setNull(2,2002,"MDSYS.SDO_GEOMETRY"); else __sJT_st.setORAData(2,__jPt_temp);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (oracle.sql.BLOB)__sJT_st.getBLOB(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:254^5*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:260^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := :__jPt_temp.GET_WKB();
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  :=  :2 .GET_WKB();\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"7SdoGeometry",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BLOB);
   }
   // set IN parameters
   if (__jPt_temp==null) __sJT_st.setNull(2,2002,"MDSYS.SDO_GEOMETRY"); else __sJT_st.setORAData(2,__jPt_temp);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (oracle.sql.BLOB)__sJT_st.getBLOB(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:264^5*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public oracle.sql.CLOB getWkt ()
  throws java.sql.SQLException
  {
    SdoGeometry __jPt_temp = (SdoGeometry) this;
    oracle.sql.CLOB __jPt_result;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:279^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := :__jPt_temp.GET_WKT();
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  :=  :2 .GET_WKT();\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"8SdoGeometry",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.CLOB);
   }
   // set IN parameters
   if (__jPt_temp==null) __sJT_st.setNull(2,2002,"MDSYS.SDO_GEOMETRY"); else __sJT_st.setORAData(2,__jPt_temp);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (oracle.sql.CLOB)__sJT_st.getCLOB(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:283^5*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:289^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := :__jPt_temp.GET_WKT();
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  :=  :2 .GET_WKT();\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"9SdoGeometry",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.CLOB);
   }
   // set IN parameters
   if (__jPt_temp==null) __sJT_st.setNull(2,2002,"MDSYS.SDO_GEOMETRY"); else __sJT_st.setORAData(2,__jPt_temp);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (oracle.sql.CLOB)__sJT_st.getCLOB(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:293^5*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public SdoGeometry sdoGeometry (
    oracle.sql.CLOB WKT,
    Integer SRID,
    SdoGeometry __jPt_out[])
  throws java.sql.SQLException
  {
    SdoGeometry __jPt_temp = (SdoGeometry) this;
    SdoGeometry __jPt_result;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:311^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := SDO_GEOMETRY(
//        :WKT,
//        :SRID);
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  := SDO_GEOMETRY(\n       :2 ,\n       :3 );\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"10SdoGeometry",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2002,"MDSYS.SDO_GEOMETRY");
   }
   // set IN parameters
   __sJT_st.setCLOB(2,WKT);
   if (SRID == null) __sJT_st.setNull(3,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(3,SRID.intValue());
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (SdoGeometry)__sJT_st.getORAData(1,SdoGeometry.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:317^5*/
    __jPt_out[0] = __jPt_temp;
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:324^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := SDO_GEOMETRY(
//        :WKT,
//        :SRID);
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  := SDO_GEOMETRY(\n       :2 ,\n       :3 );\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"11SdoGeometry",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2002,"MDSYS.SDO_GEOMETRY");
   }
   // set IN parameters
   __sJT_st.setCLOB(2,WKT);
   if (SRID == null) __sJT_st.setNull(3,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(3,SRID.intValue());
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (SdoGeometry)__sJT_st.getORAData(1,SdoGeometry.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:330^5*/
    __jPt_out[0] = __jPt_temp;
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public SdoGeometry sdoGeometry (
    String WKT,
    Integer SRID,
    SdoGeometry __jPt_out[])
  throws java.sql.SQLException
  {
    SdoGeometry __jPt_temp = (SdoGeometry) this;
    SdoGeometry __jPt_result;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:349^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := SDO_GEOMETRY(
//        :WKT,
//        :SRID);
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  := SDO_GEOMETRY(\n       :2 ,\n       :3 );\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"12SdoGeometry",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2002,"MDSYS.SDO_GEOMETRY");
   }
   // set IN parameters
   __sJT_st.setString(2,WKT);
   if (SRID == null) __sJT_st.setNull(3,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(3,SRID.intValue());
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (SdoGeometry)__sJT_st.getORAData(1,SdoGeometry.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:355^5*/
    __jPt_out[0] = __jPt_temp;
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:362^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := SDO_GEOMETRY(
//        :WKT,
//        :SRID);
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  := SDO_GEOMETRY(\n       :2 ,\n       :3 );\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"13SdoGeometry",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2002,"MDSYS.SDO_GEOMETRY");
   }
   // set IN parameters
   __sJT_st.setString(2,WKT);
   if (SRID == null) __sJT_st.setNull(3,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(3,SRID.intValue());
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (SdoGeometry)__sJT_st.getORAData(1,SdoGeometry.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:368^5*/
    __jPt_out[0] = __jPt_temp;
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public SdoGeometry sdoGeometry (
    oracle.sql.BLOB WKB,
    Integer SRID,
    SdoGeometry __jPt_out[])
  throws java.sql.SQLException
  {
    SdoGeometry __jPt_temp = (SdoGeometry) this;
    SdoGeometry __jPt_result;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:387^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := SDO_GEOMETRY(
//        :WKB,
//        :SRID);
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  := SDO_GEOMETRY(\n       :2 ,\n       :3 );\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"14SdoGeometry",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2002,"MDSYS.SDO_GEOMETRY");
   }
   // set IN parameters
   __sJT_st.setBLOB(2,WKB);
   if (SRID == null) __sJT_st.setNull(3,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(3,SRID.intValue());
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (SdoGeometry)__sJT_st.getORAData(1,SdoGeometry.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:393^5*/
    __jPt_out[0] = __jPt_temp;
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:400^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := SDO_GEOMETRY(
//        :WKB,
//        :SRID);
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  := SDO_GEOMETRY(\n       :2 ,\n       :3 );\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"15SdoGeometry",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2002,"MDSYS.SDO_GEOMETRY");
   }
   // set IN parameters
   __sJT_st.setBLOB(2,WKB);
   if (SRID == null) __sJT_st.setNull(3,oracle.jdbc.OracleTypes.INTEGER); else __sJT_st.setInt(3,SRID.intValue());
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (SdoGeometry)__sJT_st.getORAData(1,SdoGeometry.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:406^5*/
    __jPt_out[0] = __jPt_temp;
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public Integer stCoorddim ()
  throws java.sql.SQLException
  {
    SdoGeometry __jPt_temp = (SdoGeometry) this;
    Integer __jPt_result;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:422^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := :__jPt_temp.ST_COORDDIM();
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  :=  :2 .ST_COORDDIM();\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"16SdoGeometry",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
   }
   // set IN parameters
   if (__jPt_temp==null) __sJT_st.setNull(2,2002,"MDSYS.SDO_GEOMETRY"); else __sJT_st.setORAData(2,__jPt_temp);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = new Integer(__sJT_st.getInt(1)); if (__sJT_st.wasNull()) __jPt_result = null;
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:426^5*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:432^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := :__jPt_temp.ST_COORDDIM();
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  :=  :2 .ST_COORDDIM();\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"17SdoGeometry",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
   }
   // set IN parameters
   if (__jPt_temp==null) __sJT_st.setNull(2,2002,"MDSYS.SDO_GEOMETRY"); else __sJT_st.setORAData(2,__jPt_temp);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = new Integer(__sJT_st.getInt(1)); if (__sJT_st.wasNull()) __jPt_result = null;
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:436^5*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public Integer stIsvalid ()
  throws java.sql.SQLException
  {
    SdoGeometry __jPt_temp = (SdoGeometry) this;
    Integer __jPt_result;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:451^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := :__jPt_temp.ST_ISVALID();
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  :=  :2 .ST_ISVALID();\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"18SdoGeometry",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
   }
   // set IN parameters
   if (__jPt_temp==null) __sJT_st.setNull(2,2002,"MDSYS.SDO_GEOMETRY"); else __sJT_st.setORAData(2,__jPt_temp);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = new Integer(__sJT_st.getInt(1)); if (__sJT_st.wasNull()) __jPt_result = null;
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:455^5*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:461^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := :__jPt_temp.ST_ISVALID();
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  :=  :2 .ST_ISVALID();\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"19SdoGeometry",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.INTEGER);
   }
   // set IN parameters
   if (__jPt_temp==null) __sJT_st.setNull(2,2002,"MDSYS.SDO_GEOMETRY"); else __sJT_st.setORAData(2,__jPt_temp);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = new Integer(__sJT_st.getInt(1)); if (__sJT_st.wasNull()) __jPt_result = null;
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:465^5*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }
}/*@lineinfo:generated-code*/