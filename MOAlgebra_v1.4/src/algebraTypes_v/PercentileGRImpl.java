package algebraTypes_v;

/*@lineinfo:filename=PercentileGRImpl*//*@lineinfo:user-code*//*@lineinfo:1^1*/import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;
import sqlj.runtime.ref.DefaultContext;
import sqlj.runtime.ConnectionContext;

public class PercentileGRImpl implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "SYSMAN.PERCENTILEGRIMPL";
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

  protected static int[] _sqlType =  { 2002,2002,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[3];
  static
  {
    _factory[0] = GReal_VP.getORADataFactory();
    _factory[1] = GReal_V.getORADataFactory();
  }
  protected static final PercentileGRImpl _PercentileGRImplFactory = new PercentileGRImpl();

  public static ORADataFactory getORADataFactory()
  { return _PercentileGRImplFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[3], _sqlType, _factory); }
  public PercentileGRImpl()
  { _init_struct(true); __tx = DefaultContext.getDefaultContext(); }
  public PercentileGRImpl(DefaultContext c) /*throws SQLException*/
  { _init_struct(true); __tx = c; }
  public PercentileGRImpl(Connection c) /*throws SQLException*/
  { _init_struct(true); __onn = c; }
  public PercentileGRImpl(GReal_VP pttemp, GReal_V pt, java.math.BigDecimal percentile) throws SQLException
  {
    _init_struct(true);
    setPttemp(pttemp);
    setPt(pt);
    setPercentile(percentile);
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
  public void setFrom(PercentileGRImpl o) throws SQLException
  { setContextFrom(o); setValueFrom(o); }
  protected void setContextFrom(PercentileGRImpl o) throws SQLException
  { release(); __tx = o.__tx; __onn = o.__onn; }
  protected void setValueFrom(PercentileGRImpl o) { _struct = o._struct; }
  protected ORAData create(PercentileGRImpl o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) { if (o!=null) { o.release(); }; return null; }
    if (o == null) o = new PercentileGRImpl();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    o.__onn = ((STRUCT) d).getJavaSqlConnection();
    return o;
  }
  /* accessor methods */
  public GReal_VP getPttemp() throws SQLException
  { return (GReal_VP) _struct.getAttribute(0); }

  public void setPttemp(GReal_VP pttemp) throws SQLException
  { _struct.setAttribute(0, pttemp); }


  public GReal_V getPt() throws SQLException
  { return (GReal_V) _struct.getAttribute(1); }

  public void setPt(GReal_V pt) throws SQLException
  { _struct.setAttribute(1, pt); }


  public java.math.BigDecimal getPercentile() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(2); }

  public void setPercentile(java.math.BigDecimal percentile) throws SQLException
  { _struct.setAttribute(2, percentile); }


  public java.math.BigDecimal odciaggregateinitialize (
    PercentileGRImpl SCTX[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:146^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(SYSMAN.PERCENTILEGRIMPL.ODCIAGGREGATEINITIALIZE(
//        :SCTX[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := SYSMAN.PERCENTILEGRIMPL.ODCIAGGREGATEINITIALIZE(\n       :2 )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0PercentileGRImpl",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,2002,"SYSMAN.PERCENTILEGRIMPL");
   }
   // set IN parameters
   if (SCTX[0]==null) __sJT_st.setNull(2,2002,"SYSMAN.PERCENTILEGRIMPL"); else __sJT_st.setORAData(2,SCTX[0]);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   SCTX[0] = (PercentileGRImpl)__sJT_st.getORAData(2,PercentileGRImpl.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:147^26*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:153^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(SYSMAN.PERCENTILEGRIMPL.ODCIAGGREGATEINITIALIZE(
//        :SCTX[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := SYSMAN.PERCENTILEGRIMPL.ODCIAGGREGATEINITIALIZE(\n       :2 )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1PercentileGRImpl",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,2002,"SYSMAN.PERCENTILEGRIMPL");
   }
   // set IN parameters
   if (SCTX[0]==null) __sJT_st.setNull(2,2002,"SYSMAN.PERCENTILEGRIMPL"); else __sJT_st.setORAData(2,SCTX[0]);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   SCTX[0] = (PercentileGRImpl)__sJT_st.getORAData(2,PercentileGRImpl.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:154^26*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal odciaggregateiterate (
    GReal_V GR,
    PercentileGRImpl __jPt_out[])
  throws java.sql.SQLException
  {
    PercentileGRImpl __jPt_temp = (PercentileGRImpl) this;
    java.math.BigDecimal __jPt_result;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:171^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := :__jPt_temp.ODCIAGGREGATEITERATE(
//        :GR);
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  :=  :2 .ODCIAGGREGATEITERATE(\n       :3 );\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2PercentileGRImpl",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,2002,"SYSMAN.PERCENTILEGRIMPL");
   }
   // set IN parameters
   if (__jPt_temp==null) __sJT_st.setNull(2,2002,"SYSMAN.PERCENTILEGRIMPL"); else __sJT_st.setORAData(2,__jPt_temp);
   if (GR==null) __sJT_st.setNull(3,2002,"SYSMAN.GREAL_V"); else __sJT_st.setORAData(3,GR);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   __jPt_temp = (PercentileGRImpl)__sJT_st.getORAData(2,PercentileGRImpl.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:176^5*/
    __jPt_out[0] = __jPt_temp;
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:183^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := :__jPt_temp.ODCIAGGREGATEITERATE(
//        :GR);
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  :=  :2 .ODCIAGGREGATEITERATE(\n       :3 );\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3PercentileGRImpl",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,2002,"SYSMAN.PERCENTILEGRIMPL");
   }
   // set IN parameters
   if (__jPt_temp==null) __sJT_st.setNull(2,2002,"SYSMAN.PERCENTILEGRIMPL"); else __sJT_st.setORAData(2,__jPt_temp);
   if (GR==null) __sJT_st.setNull(3,2002,"SYSMAN.GREAL_V"); else __sJT_st.setORAData(3,GR);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   __jPt_temp = (PercentileGRImpl)__sJT_st.getORAData(2,PercentileGRImpl.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:188^5*/
    __jPt_out[0] = __jPt_temp;
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal odciaggregatemerge (
    PercentileGRImpl CTX2,
    PercentileGRImpl __jPt_out[])
  throws java.sql.SQLException
  {
    PercentileGRImpl __jPt_temp = (PercentileGRImpl) this;
    java.math.BigDecimal __jPt_result;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:206^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := :__jPt_temp.ODCIAGGREGATEMERGE(
//        :CTX2);
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  :=  :2 .ODCIAGGREGATEMERGE(\n       :3 );\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"4PercentileGRImpl",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,2002,"SYSMAN.PERCENTILEGRIMPL");
   }
   // set IN parameters
   if (__jPt_temp==null) __sJT_st.setNull(2,2002,"SYSMAN.PERCENTILEGRIMPL"); else __sJT_st.setORAData(2,__jPt_temp);
   if (CTX2==null) __sJT_st.setNull(3,2002,"SYSMAN.PERCENTILEGRIMPL"); else __sJT_st.setORAData(3,CTX2);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   __jPt_temp = (PercentileGRImpl)__sJT_st.getORAData(2,PercentileGRImpl.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:211^5*/
    __jPt_out[0] = __jPt_temp;
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:218^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := :__jPt_temp.ODCIAGGREGATEMERGE(
//        :CTX2);
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  :=  :2 .ODCIAGGREGATEMERGE(\n       :3 );\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"5PercentileGRImpl",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,2002,"SYSMAN.PERCENTILEGRIMPL");
   }
   // set IN parameters
   if (__jPt_temp==null) __sJT_st.setNull(2,2002,"SYSMAN.PERCENTILEGRIMPL"); else __sJT_st.setORAData(2,__jPt_temp);
   if (CTX2==null) __sJT_st.setNull(3,2002,"SYSMAN.PERCENTILEGRIMPL"); else __sJT_st.setORAData(3,CTX2);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   __jPt_temp = (PercentileGRImpl)__sJT_st.getORAData(2,PercentileGRImpl.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:223^5*/
    __jPt_out[0] = __jPt_temp;
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal odciaggregateterminate (
    GReal_V RETURNVALUE[],
    java.math.BigDecimal FLAGS)
  throws java.sql.SQLException
  {
    PercentileGRImpl __jPt_temp = (PercentileGRImpl) this;
    java.math.BigDecimal __jPt_result;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:241^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := :__jPt_temp.ODCIAGGREGATETERMINATE(
//        :RETURNVALUE[0],
//        :FLAGS);
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  :=  :2 .ODCIAGGREGATETERMINATE(\n       :3 ,\n       :4 );\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"6PercentileGRImpl",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(3,2002,"SYSMAN.GREAL_V");
   }
   // set IN parameters
   if (__jPt_temp==null) __sJT_st.setNull(2,2002,"SYSMAN.PERCENTILEGRIMPL"); else __sJT_st.setORAData(2,__jPt_temp);
   __sJT_st.setBigDecimal(4,FLAGS);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   RETURNVALUE[0] = (GReal_V)__sJT_st.getORAData(3,GReal_V.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:247^5*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:253^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { BEGIN
//        :__jPt_result := :__jPt_temp.ODCIAGGREGATETERMINATE(
//        :RETURNVALUE[0],
//        :FLAGS);
//        END;
//       };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN\n       :1  :=  :2 .ODCIAGGREGATETERMINATE(\n       :3 ,\n       :4 );\n      END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"7PercentileGRImpl",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(3,2002,"SYSMAN.GREAL_V");
   }
   // set IN parameters
   if (__jPt_temp==null) __sJT_st.setNull(2,2002,"SYSMAN.PERCENTILEGRIMPL"); else __sJT_st.setORAData(2,__jPt_temp);
   __sJT_st.setBigDecimal(4,FLAGS);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   RETURNVALUE[0] = (GReal_V)__sJT_st.getORAData(3,GReal_V.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:259^5*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }
}/*@lineinfo:generated-code*/