﻿<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file = "GenXmlData.jsp" %>
<%
String str = request.getParameter("param");

StringBuffer RecordsetQuerySQL = new StringBuffer();
RecordsetQuerySQL.append("SELECT M.MATERIEL_CODE AS materielCode, ");
RecordsetQuerySQL.append("M.MATERIEL_NAME AS materielName, ");
RecordsetQuerySQL.append("M.MATERIEL_FORNAME AS materielForName, ");
RecordsetQuerySQL.append("M.MATERIEL_SPECS AS materielSpecs, ");
RecordsetQuerySQL.append("M.MATERIEL_BRAND AS materielBrand, ");
RecordsetQuerySQL.append("ENWD.PRODUCTION_DATE AS productionDate, ");
RecordsetQuerySQL.append("M.PRODUCT_PARAM AS productParam, ");
RecordsetQuerySQL.append("M.STOR_ENVIRONMENT AS storEnvironment, ");
RecordsetQuerySQL.append("ENWD.ACHIEVE_NUM AS achieveNum, ");
RecordsetQuerySQL.append("M.MATERIEL_UNIT AS UNIT ");

RecordsetQuerySQL.append("FROM ZHL_ENWARE_DE ENWD ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_BASIS_MATERIEL M ON M.ID = ENWD.PK_MATERIEL  ");
RecordsetQuerySQL.append("WHERE ENWD.ENWARE_DE_ID IN ");
RecordsetQuerySQL.append(str);
RecordsetQuerySQL.append(" AND ENWD.DEL_FLAG = '0' AND M.DEL_FLAG = '0' ");
System.out.println(RecordsetQuerySQL.toString());
XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
%> 