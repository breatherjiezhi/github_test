<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="GenXmlData.jsp"%>
<%
String reqType = request.getParameter("reqType");
if("ewAllot".equals(reqType)){
	String str = request.getParameter("param");
	str = HTMLEncode(str);
	StringBuffer RecordsetQuerySQL = new StringBuffer();
	RecordsetQuerySQL.append("SELECT EWZV.INVCODE,");
	RecordsetQuerySQL.append("EWZV.INVNAME,");
	RecordsetQuerySQL.append("EWZV.INVSPEC,");
	RecordsetQuerySQL.append("EWZV.INVTYPE,");
	RecordsetQuerySQL.append("EWZV.MEASNAME,");
	RecordsetQuerySQL.append("EWZV.BATCHCODE,");
	RecordsetQuerySQL.append("EWZV.BATCHCODENUM,");
	RecordsetQuerySQL.append("EWZV.NINNUM,");
	RecordsetQuerySQL.append("EWZV.INVSTATUS,");
	RecordsetQuerySQL.append("EWZV.STOKEEPNAME,");
	RecordsetQuerySQL.append("EWZV.STATIONCODE,");
	RecordsetQuerySQL.append("EWZV.CSPACECODE,");
	RecordsetQuerySQL.append("EWZV.PK_EWHID,");
	RecordsetQuerySQL.append("EWZV.VBILLCODE,");
	RecordsetQuerySQL.append("EWZV.DBILLDATE,");
	RecordsetQuerySQL.append("EWZV.CBIZNAME,");
	RecordsetQuerySQL.append("EWZV.CALBODYNAME,");
	RecordsetQuerySQL.append("EWZV.CPROVIDERNAME,");
	RecordsetQuerySQL.append("EWZV.STORNAME,");
	RecordsetQuerySQL.append("EWZV.COPERATORNAME ");
	RecordsetQuerySQL.append("FROM LLM_ENWAREHOUSE_Z_VIEW EWZV ");
	RecordsetQuerySQL.append("WHERE EWZV.PK_EWHID = '");
    RecordsetQuerySQL.append(str);
    RecordsetQuerySQL.append("'");
    RecordsetQuerySQL.append(" ORDER BY EWZV.INVCODE");
    XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
}else if("enCode".equals(reqType)){
	String str = request.getParameter("param");
	str = HTMLEncode(str);
	StringBuffer RecordsetQuerySQL = new StringBuffer();
	RecordsetQuerySQL.append("SELECT distinct EWZV.VBILLCODE ");
	RecordsetQuerySQL.append("FROM LLM_ENWAREHOUSE_Z_VIEW EWZV ");
	RecordsetQuerySQL.append("WHERE EWZV.PK_EWHID = '");
    RecordsetQuerySQL.append(str);
    RecordsetQuerySQL.append("'");
    XML_GenOneRecordset(response, RecordsetQuerySQL.toString());    
}else if("arriveOrder".equals(reqType)){
	String str = request.getParameter("param");
	str = HTMLEncode(str);
	StringBuffer RecordsetQuerySQL = new StringBuffer();
	RecordsetQuerySQL.append("SELECT AOBV.INVCODE,");
	RecordsetQuerySQL.append("gzzxbm as REMARKS,");
	RecordsetQuerySQL.append("AOBV.INVNAME,");
	RecordsetQuerySQL.append("AOBV.INVSPEC,");
	RecordsetQuerySQL.append("AOBV.INVTYPE,");
	RecordsetQuerySQL.append("AOBV.MEASNAME,");
	RecordsetQuerySQL.append("AOBV.NARRVNUM,");
	RecordsetQuerySQL.append("AOBV.OVERSTOCKFLAG,");
	RecordsetQuerySQL.append("AOBV.OVERSTOCKNUM,");
	RecordsetQuerySQL.append("AOBV.CARRIVEORDERID,");
	RecordsetQuerySQL.append("AOBV.ID,");
	RecordsetQuerySQL.append("AOBV.VARRORDERCODE,");
	RecordsetQuerySQL.append("AOBV.CUSTNAME,");
	RecordsetQuerySQL.append("AOBV.CBIZTYPE,");
	RecordsetQuerySQL.append("AOBV.BUSINAME,");
	RecordsetQuerySQL.append("AOBV.CCONTRACTID,");
	RecordsetQuerySQL.append("AOBV.CCONTRACTCODE,");
	RecordsetQuerySQL.append("AOBV.VORDERCODE,");
	RecordsetQuerySQL.append("AOBV.STORNAME,");
	RecordsetQuerySQL.append("AOBV.CALBODYNAME,");
	RecordsetQuerySQL.append("AOBV.NPRICE,");
	RecordsetQuerySQL.append("AOBV.NMONEY,");
	//RecordsetQuerySQL.append("AOBV.REMARKS,");
	RecordsetQuerySQL.append("AOBV.JOBNAME,");
	RecordsetQuerySQL.append("AOBV.BISBACK,");
	RecordsetQuerySQL.append("AOBV.VBACKREASONH,");
	RecordsetQuerySQL.append("AOBV.COPERATORNAME,");
	RecordsetQuerySQL.append("AOBV.TMAKETIME ");
	RecordsetQuerySQL.append("FROM LLM_ARRIVEORDER_B_VIEW AOBV ");
	RecordsetQuerySQL.append(" left join (select i.invcode, s.storname,listagg(w.gzzxbm,'/') WITHIN GROUP(ORDER BY i.invcode,s.storname) as gzzxbm from llm_onhandnum o ");
	RecordsetQuerySQL.append("join llm_invbasdoc i on i.id=o.pk_invbasdoc and i.del_flag='0' ");
	RecordsetQuerySQL.append("join llm_stordoc s on s.id=o.pk_stordoc and s.del_flag='0'  ");
	RecordsetQuerySQL.append("join llm_wk w on w.id=o.pk_stationid and w.del_flag='0' where o.del_flag='0'  GROUP by i.invcode,s.storname) a on a.invcode=AOBV.INVCODE and a.storname=AOBV.STORNAME");                
	RecordsetQuerySQL.append(" WHERE AOBV.CARRIVEORDERID = '");
    RecordsetQuerySQL.append(str);
    RecordsetQuerySQL.append("'");
    RecordsetQuerySQL.append(" ORDER BY AOBV.INVCODE");
    XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
}else if("batchCode".equals(reqType)){
	String str = request.getParameter("param");
	str = HTMLEncode(str);
	StringBuffer RecordsetQuerySQL = new StringBuffer(); 
	RecordsetQuerySQL.append("SELECT IBD.INVCODE,");
	RecordsetQuerySQL.append("IBD.INVNAME,");
	RecordsetQuerySQL.append("EWZ.BATCHCODE ");
	RecordsetQuerySQL.append("FROM LLM_ENWAREHOUSE_Z EWZ ");
	RecordsetQuerySQL.append("JOIN LLM_INVBASDOC IBD ON IBD.ID = EWZ.CINVBASID AND IBD.DEL_FLAG = '0' ");
	RecordsetQuerySQL.append("WHERE EWZ.DEL_FLAG = '0' AND EWZ.NINNUM <> 0 AND EWZ.BATCHCODE IS NOT NULL ");
	RecordsetQuerySQL.append("AND EWZ.PK_EWHID = '");
    RecordsetQuerySQL.append(str);
    RecordsetQuerySQL.append("'");

    XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
}else if("batchCodeNum".equals(reqType)){
	String str = request.getParameter("param");
	str = HTMLEncode(str);
	StringBuffer RecordsetQuerySQL = new StringBuffer();
	RecordsetQuerySQL.append("SELECT IBD.INVCODE,");
	RecordsetQuerySQL.append("IBD.INVNAME,");
	RecordsetQuerySQL.append("EWZ.BATCHCODENUM ");
	RecordsetQuerySQL.append("FROM LLM_ENWAREHOUSE_Z EWZ ");
	RecordsetQuerySQL.append("JOIN LLM_INVBASDOC IBD ON IBD.ID = EWZ.CINVBASID AND IBD.DEL_FLAG = '0' ");
	RecordsetQuerySQL.append("WHERE EWZ.DEL_FLAG = '0' AND EWZ.NINNUM <> 0 AND EWZ.BATCHCODENUM IS NOT NULL ");
	RecordsetQuerySQL.append("AND EWZ.PK_EWHID = '");
    RecordsetQuerySQL.append(str);
    RecordsetQuerySQL.append("'");
    
    XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
}else if("goStock".equals(reqType)){
	String str = request.getParameter("param");
	str = HTMLEncode(str);
	StringBuffer RecordsetQuerySQL = new StringBuffer();
	RecordsetQuerySQL.append(" SELECT IBD.INVCODE,");
	RecordsetQuerySQL.append(" IBD.INVNAME,");
	RecordsetQuerySQL.append(" EN.VBILLCODE,");
	RecordsetQuerySQL.append(" TO_CHAR(ENS.CREATE_DATE, 'YYYY/MM/DD HH:MM:SS') AS DBILLDATE,");
	RecordsetQuerySQL.append(" IBD.INVSPEC,");
	RecordsetQuerySQL.append(" IBD.INVTYPE,");
	RecordsetQuerySQL.append(" MEA.MEASNAME,");
	RecordsetQuerySQL.append(" ENS.BATCHCODE,");
	RecordsetQuerySQL.append(" ENS.BATCHCODENUM,");
	RecordsetQuerySQL.append(" ENS.NINNUM,");
	RecordsetQuerySQL.append(" CASE");
	RecordsetQuerySQL.append(" WHEN ENS.INVSTATUS = '0' THEN");
	RecordsetQuerySQL.append(" '正常物料'");
	RecordsetQuerySQL.append(" WHEN ENS.INVSTATUS = '1' THEN");
	RecordsetQuerySQL.append(" '紧急物料'");
	RecordsetQuerySQL.append(" END INVSTATUS,");
	RecordsetQuerySQL.append(" SU.NAME AS STOKEEPNAME,");
	RecordsetQuerySQL.append(" WK.GZZXBM AS STATIONCODE,");
	RecordsetQuerySQL.append(" CG.CODE AS CSPACECODE,");
	RecordsetQuerySQL.append(" ZD.NAME AS COPERATORNAME,");
	RecordsetQuerySQL.append(" SD.STORNAME,");
	RecordsetQuerySQL.append(" CUB.CUSTNAME AS CPROVIDERNAME,");
	RecordsetQuerySQL.append(" YE.NAME AS CBIZNAME");
	RecordsetQuerySQL.append(" FROM LLM_ENWAREHOUSE_S ENS");
	RecordsetQuerySQL.append(" JOIN LLM_INVBASDOC IBD");
	RecordsetQuerySQL.append(" ON IBD.ID = ENS.CINVBASID");
	RecordsetQuerySQL.append(" AND IBD.DEL_FLAG = '0'");
	RecordsetQuerySQL.append(" JOIN LLM_MEASDOC MEA");
	RecordsetQuerySQL.append(" ON MEA.ID = IBD.PK_MEASDOC");
	RecordsetQuerySQL.append(" JOIN LLM_ENWAREHOUSE EN");
	RecordsetQuerySQL.append(" ON EN.ID = ENS.PK_EWHID");
	RecordsetQuerySQL.append(" AND EN.DEL_FLAG = '0'");
	RecordsetQuerySQL.append(" JOIN SYS_USER SU");
	RecordsetQuerySQL.append(" ON SU.ID = ENS.PK_STOKEEPID");
	RecordsetQuerySQL.append(" AND SU.DEL_FLAG = '0'");
	RecordsetQuerySQL.append(" JOIN LLM_WK WK");
	RecordsetQuerySQL.append(" ON WK.ID = ENS.PK_STATIONID");
	RecordsetQuerySQL.append(" AND WK.DEL_FLAG = '0'");
	RecordsetQuerySQL.append(" JOIN LLM_CARGDOC CG");
	RecordsetQuerySQL.append(" ON CG.ID = ENS.PK_CSPACEID");
	RecordsetQuerySQL.append(" AND CG.DEL_FLAG = '0'");
	RecordsetQuerySQL.append(" JOIN SYS_USER ZD");
	RecordsetQuerySQL.append(" ON ZD.ID = EN.COPERATORID");
	RecordsetQuerySQL.append(" JOIN SYS_USER YE ON YE.ID = EN.CBIZID ");
	RecordsetQuerySQL.append(" JOIN LLM_STORDOC SD");
	RecordsetQuerySQL.append(" ON SD.ID = EN.CWAREHOUSEID");
	RecordsetQuerySQL.append(" JOIN LLM_CUMANDOC CUM");
	RecordsetQuerySQL.append(" ON CUM.ID = EN.CPROVIDERID");
	RecordsetQuerySQL.append(" JOIN LLM_CUBASDOC CUB");
	RecordsetQuerySQL.append(" ON CUB.ID = CUM.PK_CUBASDOC");
	RecordsetQuerySQL.append(" AND ENS.DEL_FLAG = '0'");
	RecordsetQuerySQL.append(" WHERE ENS.PK_EWHID = '");
    RecordsetQuerySQL.append(str);
    RecordsetQuerySQL.append("'");
    XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
}
%>