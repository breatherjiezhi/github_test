<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="GenXmlData.jsp" %>
<%
String str = request.getParameter("param");
StringBuffer RecordsetQuerySQL = new StringBuffer();
RecordsetQuerySQL.append("SELECT PAC.PACKAGE_CODE, ");
RecordsetQuerySQL.append("PAC.ENWARE_CODE ");
RecordsetQuerySQL.append("FROM ZHL_PACKAGE PAC ");
RecordsetQuerySQL.append("WHERE PAC.PACKAGE_ID IN ");
RecordsetQuerySQL.append(str);
RecordsetQuerySQL.append(" AND PAC.DEL_FLAG='0'");
RecordsetQuerySQL.append("ORDER BY PAC.PACKAGE_CODE");
System.out.println(RecordsetQuerySQL.toString());
XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
%>
