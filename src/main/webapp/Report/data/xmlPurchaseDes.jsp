<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file = "GenXmlData.jsp" %>
<%
String str = request.getParameter("param");

StringBuffer RecordsetQuerySQL = new StringBuffer();
RecordsetQuerySQL.append("SELECT    U2.NAME AS approvedBy, ");
RecordsetQuerySQL.append("U.NAME AS loginName, ");
RecordsetQuerySQL.append("D.DESCRIPTION AS description ");

RecordsetQuerySQL.append("FROM ZHL_PURCHASE P ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_PURCHASE_DES D ON P.PURCHASE_ID = D.PK_PURCHASE ");
RecordsetQuerySQL.append("LEFT JOIN SYS_USER U ON U.ID = P.CREATE_BY ");
RecordsetQuerySQL.append("LEFT JOIN SYS_USER U2 ON U2.ID = P.CREATE_BY ");
RecordsetQuerySQL.append("WHERE P.PURCHASE_ID = '");
RecordsetQuerySQL.append(str);
RecordsetQuerySQL.append("' ");
RecordsetQuerySQL.append("AND P.DEL_FLAG = '0' ");
RecordsetQuerySQL.append("AND D.DEL_FLAG = '0' ");
RecordsetQuerySQL.append("AND U.DEL_FLAG = '0' ");
RecordsetQuerySQL.append("ORDER BY D.DESCRIPTION ");
System.out.println(RecordsetQuerySQL.toString());
XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
%>