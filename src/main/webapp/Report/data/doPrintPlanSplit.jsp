<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="GenXmlData.jsp"%>
<%
String reqType = request.getParameter("reqType");
if("taskCode".equals(reqType)){
	String str = request.getParameter("param");
	str = HTMLEncode(str);
	if(str == null || "".equals(str)){
		str = "'1'";
	}
	StringBuffer RecordsetQuerySQL = new StringBuffer();
	RecordsetQuerySQL.append("SELECT CP.TASK_CODE,");
	RecordsetQuerySQL.append("'AN ' || F.FROCK_NUM AS FROCK_NUM,");
	RecordsetQuerySQL.append("CP.PJOB || ' - ' || CP.PRODCARNUMBER AS PRODCARNUMBER,");
	RecordsetQuerySQL.append("STA.GZZXBM,");
	RecordsetQuerySQL.append("CP.OUTFEED_PLAN_TIME,");
	RecordsetQuerySQL.append("CP.DELIVERY_PLAN_TIME,");
	RecordsetQuerySQL.append("CP.REMARKS ");
	RecordsetQuerySQL.append(" FROM LLM_CARRYPLAN CP ");
	RecordsetQuerySQL.append("JOIN LLM_WK STA ON STA.ID = CP.STATION_ID AND STA.DEL_FLAG = '0' ");
	RecordsetQuerySQL.append("LEFT JOIN (SELECT ID,FROCK_NUM FROM LLM_FROCK WHERE DEL_FLAG = '0') F ON F.ID = CP.FROCK_ID ");
	RecordsetQuerySQL.append("WHERE CP.DEL_FLAG = '0' ");
	RecordsetQuerySQL.append("AND CP.ID IN (");
    RecordsetQuerySQL.append(str);
    RecordsetQuerySQL.append(") ");
    XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
}
%>