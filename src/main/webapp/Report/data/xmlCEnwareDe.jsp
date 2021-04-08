<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file = "GenXmlData.jsp" %>
<%
String str = request.getParameter("param");
StringBuffer RecordsetQuerySQL = new StringBuffer();

RecordsetQuerySQL.append("SELECT M.MATERIEL_CODE, ");
RecordsetQuerySQL.append("M.MATERIEL_NAME, ");
RecordsetQuerySQL.append("M.MATERIEL_FORNAME, ");
RecordsetQuerySQL.append("M.MATERIEL_SPECS ");
RecordsetQuerySQL.append("FROM ZHL_C_ENWARE_DE CENWD ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_BASIS_MATERIEL M ON CENWD.MATERIEL_CODE = M.MATERIEL_CODE ");
RecordsetQuerySQL.append("WHERE CENWD.C_ENWARE_DE_ID IN ");
RecordsetQuerySQL.append(str);
RecordsetQuerySQL.append("AND CENWD.DEL_FLAG = '0' ");
RecordsetQuerySQL.append("ORDER BY M.materiel_Code");
System.out.println(RecordsetQuerySQL);
XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
%>
 