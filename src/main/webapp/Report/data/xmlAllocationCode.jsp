<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="GenXmlData.jsp" %>
<%
String str = request.getParameter("param");
StringBuffer RecordsetQuerySQL = new StringBuffer();
RecordsetQuerySQL.append("SELECT A.ALLOCATION_CODE ");
RecordsetQuerySQL.append("FROM ZHL_BASIS_ALLOCATION A ");
RecordsetQuerySQL.append("WHERE A.ID IN ");
RecordsetQuerySQL.append(str);
RecordsetQuerySQL.append(" AND A.DEL_FLAG = '0'");
RecordsetQuerySQL.append("ORDER BY A.ALLOCATION_CODE");
System.out.println(RecordsetQuerySQL.toString());
XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
%>
