﻿<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file = "GenXmlData.jsp" %>
<%
String str = request.getParameter("param");

StringBuffer RecordsetQuerySQL = new StringBuffer();
RecordsetQuerySQL.append("SELECT M.MATERIEL_CODE, ");
RecordsetQuerySQL.append("M.MATERIEL_NAME, ");
RecordsetQuerySQL.append("M.MATERIEL_FORNAME, ");
RecordsetQuerySQL.append("M.MATERIEL_SPECS, ");
RecordsetQuerySQL.append("CEXWD.EX_NUM, ");
RecordsetQuerySQL.append("M.MATERIEL_UNIT, ");
RecordsetQuerySQL.append("STORE.STOREHOUSE_NAME, ");
RecordsetQuerySQL.append("STO.ALLOCATION_CODE, ");
RecordsetQuerySQL.append("JT.JOBTEAM_CODE, ");
RecordsetQuerySQL.append("CEXWD.C_EXONLY_CODE ");

RecordsetQuerySQL.append("FROM ZHL_C_EXWARE_DE CEXWD ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_BASIS_MATERIEL M ON M.ID = CEXWD.PK_MATERIEL ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_C_REQUISITION REQ ON REQ.C_REQUISITION_ID = CEXWD.PK_C_REQUISITION  ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_BASIS_STOREHOUSE STORE ON STORE.ID = CEXWD.PK_STOREHOUSE  ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_C_STOCK STO ON STO.C_STOCK_ID = CEXWD.PK_C_STOCK  ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_BASIS_JOBTEAM JT ON JT.ID = REQ.PK_JOBTEAM  ");
RecordsetQuerySQL.append("WHERE CEXWD.C_EXWARE_DE_ID IN ");
RecordsetQuerySQL.append(str);
RecordsetQuerySQL.append(" AND CEXWD.DEL_FLAG = '0' ");
System.out.println(RecordsetQuerySQL.toString());
XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
%> 