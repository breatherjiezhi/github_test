<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file = "GenXmlData.jsp" %>
<%
String str = request.getParameter("param");
StringBuffer RecordsetQuerySQL = new StringBuffer();

RecordsetQuerySQL.append("SELECT  PUR.PURCHASE_CODE AS purchaseCode, ");
RecordsetQuerySQL.append("ENW.ENWARE_CODE AS enwareCode,  ");
RecordsetQuerySQL.append("ENW.DRIVER AS DRIVER, ");
RecordsetQuerySQL.append("ENW.DRIVER_TEL AS TEL, ");
RecordsetQuerySQL.append("ENW.PLATE_NUMBER AS PLATE, ");
RecordsetQuerySQL.append("ENW.STOR_ADDRESS AS ADDRESS, ");
RecordsetQuerySQL.append("ENW.WORKING_TIME AS WKTIME, ");
RecordsetQuerySQL.append("ENW.RECIEVED_BY AS RECIEVEDBY, ");
RecordsetQuerySQL.append("to_char(ENW.UNLOAD_TIME, 'yyyy-mm-dd hh24:mi:ss') AS UNLOADTIME, ");
RecordsetQuerySQL.append("SUP.SUPPLIER_NAME AS supplierName,  ");
RecordsetQuerySQL.append("SUP.Liaison AS Liaison, ");
RecordsetQuerySQL.append("SUP.LIAISONMODE AS LiaisonMode, ");
RecordsetQuerySQL.append("to_char(PUR.PLAN_DELIVERY_TIME, 'yyyy-mm-dd') AS pDeliveryTime, ");
RecordsetQuerySQL.append("to_char(PUR.REAL_DELIVERY_TIME, 'yyyy-mm-dd hh24:mi:ss') AS rDeliveryTime, ");
RecordsetQuerySQL.append("ENWD.PK_MATERIEL as materielKey, ");
RecordsetQuerySQL.append("ENWD.ACHIEVE_NUM as achieveNum, ");
RecordsetQuerySQL.append("m.MATERIEL_CODE as materielCode,  ");
RecordsetQuerySQL.append("PRO.PROFESSION_NAME AS Profession, ");
RecordsetQuerySQL.append("m.MATERIEL_NAME AS  materielName, ");
RecordsetQuerySQL.append("m.MATERIEL_FORNAME as materielForName, ");
RecordsetQuerySQL.append("m.MATERIEL_SPECS as  materielSpecs, ");
RecordsetQuerySQL.append("M.PACKAGE_MODE AS packageMode, ");
RecordsetQuerySQL.append("m.length as length, ");
RecordsetQuerySQL.append("m.width as width, ");
RecordsetQuerySQL.append("m.height as height, ");
RecordsetQuerySQL.append("m.volume as volume, ");
RecordsetQuerySQL.append("m.volume*ENWD.ACHIEVE_NUM AS TOTALVOLUME, ");
RecordsetQuerySQL.append("m.gross_weight as GW, ");
RecordsetQuerySQL.append("M.gross_weight*ENWD.ACHIEVE_NUM AS TOTALWEIGHT, ");
RecordsetQuerySQL.append("ENWD.REMARKS AS remarks ");

RecordsetQuerySQL.append("FROM ZHL_ENWARE_DE ENWD ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_BASIS_MATERIEL m on m.ID  = ENWD.PK_MATERIEL  ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_ENWARE ENW ON ENW.ENWARE_ID = ENWD.PK_ENWARE ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_PURCHASE PUR ON PUR.PURCHASE_ID = ENW.PK_PURCHASE ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_BASIS_SUPPLIER SUP ON SUP.ID = ENW.PK_SUPPLIER  ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_BASIS_PROFESSION PRO ON PRO.ID = M.PK_PROFESSION ");
RecordsetQuerySQL.append("WHERE ENWD.PK_ENWARE = '");
RecordsetQuerySQL.append(str);
RecordsetQuerySQL.append("' ");
RecordsetQuerySQL.append("AND ENWD.DEL_FLAG = '0' ");
RecordsetQuerySQL.append("AND ENW.DEL_FLAG = '0'");
RecordsetQuerySQL.append("ORDER BY M.materiel_Code");
System.out.println(RecordsetQuerySQL);
XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
%>
 