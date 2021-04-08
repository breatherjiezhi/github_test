<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file = "GenXmlData.jsp" %>
<%
String str = request.getParameter("param");
StringBuffer RecordsetQuerySQL = new StringBuffer();

RecordsetQuerySQL.append("SELECT  PUR.PURCHASE_CODE AS purchaseCode, ");
RecordsetQuerySQL.append("PAC.PACKAGE_CODE AS packageCode, ");
RecordsetQuerySQL.append("SUP.STOR_CODE AS storCode, ");
RecordsetQuerySQL.append("PACD.PK_MATERIEL as materielKey, ");
RecordsetQuerySQL.append("PACD.MATERIEL_NUM as materielNum, ");
RecordsetQuerySQL.append("m.MATERIEL_CODE as materielCode, ");
RecordsetQuerySQL.append("PRO.PROFESSION_NAME AS Profession, ");
RecordsetQuerySQL.append("m.MATERIEL_NAME AS  materielName, ");
RecordsetQuerySQL.append("m.MATERIEL_FORNAME as materielForName, ");
RecordsetQuerySQL.append("m.MATERIEL_SPECS as  materielSpecs, ");
RecordsetQuerySQL.append("m.MATERIEL_TEXTURES as  materielTextures, ");
RecordsetQuerySQL.append("m.MATERIEL_BRAND as materielBrand, ");
RecordsetQuerySQL.append("m.MATERIEL_MODEL as materielModel, ");
RecordsetQuerySQL.append("M.PACKAGE_MODE AS packageMode, ");
RecordsetQuerySQL.append("m.customs_code AS HScode, ");
RecordsetQuerySQL.append("m.MATERIEL_UNIT as materielUnit, ");
RecordsetQuerySQL.append("m.length as length, ");
RecordsetQuerySQL.append("m.width as width, ");
RecordsetQuerySQL.append("m.height as height, ");
RecordsetQuerySQL.append("m.volume as volume, ");
RecordsetQuerySQL.append("m.volume*PACD.MATERIEL_NUM AS TOTALVOLUME, ");
RecordsetQuerySQL.append("m.gross_weight as GW, ");
RecordsetQuerySQL.append("m.net_weight as NW,  ");
RecordsetQuerySQL.append("M.gross_weight*PACD.MATERIEL_NUM AS TOTALWEIGHT, ");
RecordsetQuerySQL.append("m.materiel_price as price, ");
RecordsetQuerySQL.append("M.MATERIEL_PRICE*PACD.MATERIEL_NUM AS TOTALPRICE, ");
RecordsetQuerySQL.append("PACD.REMARKS AS remarks ");
RecordsetQuerySQL.append("FROM ZHL_PACKAGE_DE PACD ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_BASIS_MATERIEL m on m.ID  = PACD.PK_MATERIEL  ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_PACKAGE PAC ON PAC.PACKAGE_ID = PACD.PK_PACKAGE  ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_PURCHASE PUR ON PUR.PURCHASE_CODE = PAC.PURCHASE_CODE  ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_BASIS_SUPPLIER SUP ON SUP.ID = PUR.PK_SUPPLIER ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_BASIS_PROFESSION PRO ON PRO.ID = M.PK_PROFESSION  ");
RecordsetQuerySQL.append("WHERE PAC.PACKAGE_ID IN ");
RecordsetQuerySQL.append(str);
RecordsetQuerySQL.append("AND PACD.DEL_FLAG = '0'  ");
RecordsetQuerySQL.append("AND PAC.DEL_FLAG = '0' ");
System.out.println(RecordsetQuerySQL);
XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
%>
 