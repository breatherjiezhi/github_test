<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file = "GenXmlData.jsp" %>
<%
String str = request.getParameter("param");
StringBuffer RecordsetQuerySQL = new StringBuffer();

RecordsetQuerySQL.append("SELECT CENW.C_ENWAER_CODE AS cEnwareCode, ");
RecordsetQuerySQL.append("CENW.DECLEAR_CODE AS declearCode,  ");
RecordsetQuerySQL.append("U.NAME AS createBy, ");
RecordsetQuerySQL.append("CENW.CREATE_DATE AS createDate, ");
RecordsetQuerySQL.append("CENW.BILLFROM AS fromBill, ");
RecordsetQuerySQL.append("PRO.PROFESSION_NAME AS professionName, ");
RecordsetQuerySQL.append("CENWD.MATERIEL_CODE AS materielCode, ");
RecordsetQuerySQL.append("M.MATERIEL_NAME AS materielName, ");
RecordsetQuerySQL.append("M.MATERIEL_FORNAME AS materielForName, ");
RecordsetQuerySQL.append("M.MATERIEL_BRAND AS materielBrand,  ");
RecordsetQuerySQL.append("M.MATERIEL_SPECS AS materielSpecs, ");
RecordsetQuerySQL.append("M.PRODUCT_PARAM AS productParam, ");
RecordsetQuerySQL.append("M.STOR_ENVIRONMENT AS storeEn, ");
RecordsetQuerySQL.append("M.PACKAGE_MODE AS packageMode, ");
RecordsetQuerySQL.append("CENWD.ACHIEVE_NUM AS achieveNum, ");
RecordsetQuerySQL.append("M.MATERIEL_UNIT AS unit, ");
RecordsetQuerySQL.append("M.CUSTOMS_CODE AS customsCode,  ");
RecordsetQuerySQL.append("M.LENGTH AS length, ");
RecordsetQuerySQL.append("M.WIDTH AS width, ");
RecordsetQuerySQL.append("M.HEIGHT AS height, ");
RecordsetQuerySQL.append("M.VOLUME AS vol, ");
RecordsetQuerySQL.append("M.GROSS_WEIGHT AS GW, ");
RecordsetQuerySQL.append("M.NET_WEIGHT AS NW ");
RecordsetQuerySQL.append("FROM ZHL_C_ENWARE_DE CENWD  ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_C_ENWARE CENW ON CENWD.C_ENWARE_ID = CENW.C_ENWARE_ID ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_BASIS_MATERIEL M ON M.MATERIEL_CODE = CENWD.MATERIEL_CODE ");
RecordsetQuerySQL.append("LEFT JOIN SYS_USER U ON U.ID = CENW.CREATE_BY ");
RecordsetQuerySQL.append("LEFT JOIN ZHL_BASIS_PROFESSION PRO ON PRO.ID = M.PK_PROFESSION ");
RecordsetQuerySQL.append("WHERE CENWD.C_ENWARE_ID = '");
RecordsetQuerySQL.append(str);
RecordsetQuerySQL.append("' ");
RecordsetQuerySQL.append("AND CENWD.DEL_FLAG = '0' ");
RecordsetQuerySQL.append("ORDER BY M.materiel_Code");
System.out.println(RecordsetQuerySQL);
XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
%>
 