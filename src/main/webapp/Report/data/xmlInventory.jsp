﻿<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file = "GenXmlData.jsp" %>
<%
String str = request.getParameter("param");

StringBuffer RecordsetQuerySQL = new StringBuffer();
RecordsetQuerySQL.append("SELECT CH.INVENTORYCODE, ");
RecordsetQuerySQL.append("CH.CHECK_TIME, ");
RecordsetQuerySQL.append("CHDE.PACKAGE_CODE, ");
RecordsetQuerySQL.append("LOC.LOCATOR_CODE, ");
RecordsetQuerySQL.append("f_get_code_val('checkStatus',CHDE.CHECK_STATUS) AS FZ ");
RecordsetQuerySQL.append("FROM ZHL_CHECK_TASK_DE CHDE ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_CHECK_TASK CH ON (CH.DEL_FLAG = '0' AND CH.CHECK_ID = CHDE.PK_CHECK_TASK) ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_BASIS_LOCATOR LOC ON (LOC.ID = CHDE.PK_LOCATOR AND LOC.DEL_FLAG = '0') ");
RecordsetQuerySQL.append("WHERE CHDE.DEL_FLAG = '0' AND CH.CHECK_ID ='");
RecordsetQuerySQL.append(str);
RecordsetQuerySQL.append("' ");
System.out.println(RecordsetQuerySQL.toString());
XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
%> 