﻿<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file = "GenXmlData.jsp" %>
<%
String str = request.getParameter("param");

StringBuffer RecordsetQuerySQL = new StringBuffer();
RecordsetQuerySQL.append("SELECT  PUR.PROJECT_NAME AS ProjectName, ");
RecordsetQuerySQL.append("PUR.PURCHASE_CODE AS PurchaseCode, ");
RecordsetQuerySQL.append("M.MATERIEL_CODE AS MaterielCode, ");
RecordsetQuerySQL.append("M.MATERIEL_NAME AS MaterielName, ");
RecordsetQuerySQL.append("M.MATERIEL_FORNAME AS MaterielForName, ");
RecordsetQuerySQL.append("M.MATERIEL_SPECS AS Specs, ");
RecordsetQuerySQL.append("M.MATERIEL_TEXTURES AS Textures, ");
RecordsetQuerySQL.append("PURD.MATERIEL_QULITY AS Quality, ");
RecordsetQuerySQL.append("M.MATERIEL_UNIT AS UNIT, ");
RecordsetQuerySQL.append("PURD.MATERIEL_NUM AS NUM, ");
RecordsetQuerySQL.append("PRO.PROFESSION_NAME AS PROFESSION, ");
RecordsetQuerySQL.append("PUR.MAKE_BY AS DOBY, ");
RecordsetQuerySQL.append("PUR.approved_By AS APPROVEDBY, ");
RecordsetQuerySQL.append("PUR.RECIEVED_BY AS RECIEVEDBY, ");
RecordsetQuerySQL.append("PURD.REMARKS AS REMARKS ");

RecordsetQuerySQL.append("FROM ZHL_PURCHASE_DE PURD ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_BASIS_MATERIEL M ON M.ID = PURD.PK_MATERIEL ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_PURCHASE PUR ON PUR.PURCHASE_ID = PURD.PK_PURCHASE ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_BASIS_PROFESSION PRO ON PRO.ID = M.PK_MATERIEL_TYPE ");
RecordsetQuerySQL.append("WHERE PURD.PK_PURCHASE = '");
RecordsetQuerySQL.append(str);
RecordsetQuerySQL.append("' ");
RecordsetQuerySQL.append("AND PUR.DEL_FLAG = '0' ");
RecordsetQuerySQL.append("AND PURD.DEL_FLAG = '0' ");
RecordsetQuerySQL.append("ORDER BY PURD.PURCHASE_DE_ID ");
System.out.println(RecordsetQuerySQL.toString());
XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
%> 