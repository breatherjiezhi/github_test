﻿<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file = "GenXmlData.jsp" %>
<%
String str = request.getParameter("param");

StringBuffer RecordsetQuerySQL = new StringBuffer();
RecordsetQuerySQL.append("SELECT PUR.PURCHASE_CODE AS purchaseCode, ");
RecordsetQuerySQL.append("DEL.DELIVERY_CODE AS deliveryCode, ");
RecordsetQuerySQL.append("SUP.STOR_CODE AS storCode, ");
RecordsetQuerySQL.append("SUP.SUPPLIER_NAME AS supplierName, ");
RecordsetQuerySQL.append("SUP.Liaison AS Liaison, ");
RecordsetQuerySQL.append("SUP.LIAISONMODE AS LiaisonMode, ");
RecordsetQuerySQL.append("to_char(PUR.PLAN_DELIVERY_TIME, 'yyyy-mm-dd') AS pDeliveryTime, ");
RecordsetQuerySQL.append("to_char(PUR.REAL_DELIVERY_TIME, 'yyyy-mm-dd hh24:mi:ss') AS rDeliveryTime, ");
RecordsetQuerySQL.append("DELE.PK_MATERIEL as materielKey, ");
RecordsetQuerySQL.append("DELE.MATERIEL_NUM as materielNum, ");
RecordsetQuerySQL.append("DELE.CREATE_DATE as createTimeDe, ");
RecordsetQuerySQL.append("DELE.UPDATE_DATE as updateTimeDe, ");
RecordsetQuerySQL.append("m.MATERIEL_CODE as materielCode, ");
RecordsetQuerySQL.append("PRO.PROFESSION_NAME AS Profession, ");
RecordsetQuerySQL.append("m.MATERIEL_NAME AS  materielName, ");
RecordsetQuerySQL.append("m.MATERIEL_FORNAME as materielForName, ");
RecordsetQuerySQL.append("m.MATERIEL_SPECS as  materielSpecs, ");
RecordsetQuerySQL.append("m.MATERIEL_TEXTURES as  materielTextures,  ");
RecordsetQuerySQL.append("m.MATERIEL_BRAND as materielBrand, ");
RecordsetQuerySQL.append("m.MATERIEL_MODEL as materielModel, ");
RecordsetQuerySQL.append("M.PACKAGE_MODE AS packageMode, ");
RecordsetQuerySQL.append("m.customs_code AS HScode, ");
RecordsetQuerySQL.append("m.MATERIEL_UNIT as materielUnit, ");
RecordsetQuerySQL.append("m.length as length, ");
RecordsetQuerySQL.append("m.width as width, ");
RecordsetQuerySQL.append("m.height as height, ");
RecordsetQuerySQL.append("m.volume as volume, ");
RecordsetQuerySQL.append("m.volume*DELE.MATERIEL_NUM AS TOTALVOLUME,  ");
RecordsetQuerySQL.append("m.gross_weight as GW, ");
RecordsetQuerySQL.append("M.GROSS_WEIGHT*DELE.MATERIEL_NUM AS TOTALWEIGHT, ");
RecordsetQuerySQL.append("m.net_weight as NW, ");
RecordsetQuerySQL.append("M.NET_WEIGHT*DELE.MATERIEL_NUM AS TOTALNW, ");
RecordsetQuerySQL.append("m.materiel_price as price, ");
RecordsetQuerySQL.append("M.MATERIEL_PRICE*DELE.MATERIEL_NUM AS TOTALPRICE, ");
RecordsetQuerySQL.append("DEL.REMARKS AS remarks ");

RecordsetQuerySQL.append("FROM ZHL_DELIVERY_DE DELE ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_BASIS_MATERIEL m on m.ID  = DELE.PK_MATERIEL ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_DELIVERY DEL ON DEL.DELIVERY_ID = DELE.PK_DELIVERY ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_PURCHASE PUR ON PUR.PURCHASE_ID = DEL.PK_PURCHASE ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_BASIS_SUPPLIER SUP ON SUP.ID = DEL.PK_SUPPLIER ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_BASIS_PROFESSION PRO ON PRO.ID = M.PK_PROFESSION ");
RecordsetQuerySQL.append("WHERE DELE.PK_DELIVERY = '");
RecordsetQuerySQL.append(str);
RecordsetQuerySQL.append("' ");
RecordsetQuerySQL.append("AND DELE.DEL_FLAG = '0' ");
RecordsetQuerySQL.append("AND DEL.DEL_FLAG = '0' ");
RecordsetQuerySQL.append("ORDER BY M.materiel_Code");
System.out.println(RecordsetQuerySQL.toString());
XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
%> 