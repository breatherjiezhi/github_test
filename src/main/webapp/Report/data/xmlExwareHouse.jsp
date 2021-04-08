﻿<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file = "GenXmlData.jsp" %>
<%
String str = request.getParameter("param");

StringBuffer RecordsetQuerySQL = new StringBuffer();
RecordsetQuerySQL.append("SELECT DISTINCT EXW.EXWARE_CODE AS exwareCode, ");
    RecordsetQuerySQL.append("EXW.STOR_ADDRESS AS storAddress, ");
    RecordsetQuerySQL.append("EXW.WORKING_TIME AS workingTime, ");
    RecordsetQuerySQL.append("PUR2.purchaseList, ");
    RecordsetQuerySQL.append("EXW.PLATE_NUMBER AS plateNum, ");
    RecordsetQuerySQL.append("EXW.DRIVER AS driver, ");
    RecordsetQuerySQL.append("EXW.DRIVER_TEL AS driverTel, ");
    RecordsetQuerySQL.append("EXW.CONTAINER_OWNER AS containerOwner, ");
    RecordsetQuerySQL.append("EXW.CONTAINER_TYPE AS containerType, ");
    RecordsetQuerySQL.append("EXW.CONTAINER_CODE AS containerCode, ");
    RecordsetQuerySQL.append("EXW.SEAL_CODE AS sealCode, ");
    RecordsetQuerySQL.append("EXW.UPDATE_DATE AS exwareTime, ");
    RecordsetQuerySQL.append("M.MATERIEL_CODE AS materielCode, ");
    RecordsetQuerySQL.append("M.MATERIEL_NAME AS materielName, ");
    RecordsetQuerySQL.append("NUM.materielNum, ");
    RecordsetQuerySQL.append("M.MATERIEL_SPECS AS materielSpecs, ");
    RecordsetQuerySQL.append("M.PACKAGE_MODE AS packageMode, ");
    RecordsetQuerySQL.append("M.LENGTH AS length, ");
    RecordsetQuerySQL.append("M.WIDTH AS width, ");
    RecordsetQuerySQL.append("M.HEIGHT AS height, ");
    RecordsetQuerySQL.append("M.VOLUME AS volume, ");
    RecordsetQuerySQL.append("M.GROSS_WEIGHT AS GW, ");
    RecordsetQuerySQL.append("M.VOLUME*NUM.materielNum AS TOTALVOLUME, ");
    RecordsetQuerySQL.append("M.GROSS_WEIGHT*NUM.materielNum AS TOTALWEIGHT ");

    RecordsetQuerySQL.append("FROM ZHL_PACKAGE_DE PACD ");
    RecordsetQuerySQL.append("LEFT JOIN ZHL_PACKAGE PAC ON PAC.PACKAGE_ID = PACD.PK_PACKAGE ");
    RecordsetQuerySQL.append("LEFT JOIN ZHL_PURCHASE PUR ON PUR.PURCHASE_CODE = PAC.PURCHASE_CODE ");
    RecordsetQuerySQL.append("LEFT JOIN ZHL_EXWARE_DE EXWD ON EXWD.PK_PURCHASE = PUR.PURCHASE_ID ");
    RecordsetQuerySQL.append("LEFT JOIN ZHL_EXWARE EXW ON EXW.EXWARE_ID = EXWD.PK_EXWARE ");
    RecordsetQuerySQL.append("LEFT JOIN ZHL_BASIS_MATERIEL M ON M.ID = PACD.PK_MATERIEL ");
    RecordsetQuerySQL.append("LEFT JOIN (SELECT PURD.PK_MATERIEL AS materielKey, ");
    RecordsetQuerySQL.append("SUM(PURD.MATERIEL_NUM) AS materielNum ");
    RecordsetQuerySQL.append("FROM ZHL_PURCHASE_DE PURD ");
    RecordsetQuerySQL.append("LEFT JOIN ZHL_PURCHASE PUR2 ON PURD.PK_PURCHASE = PUR2.PURCHASE_ID ");
    RecordsetQuerySQL.append("LEFT JOIN ZHL_EXWARE_DE EXWD1 ON EXWD1.PK_PURCHASE = PUR2.PURCHASE_ID ");
    RecordsetQuerySQL.append("LEFT JOIN ZHL_EXWARE EXW2 ON EXW2.EXWARE_ID = EXWD1.PK_EXWARE ");
    RecordsetQuerySQL.append("WHERE EXWD1.PK_EXWARE = '");
    RecordsetQuerySQL.append(str);
    RecordsetQuerySQL.append("' ");
    RecordsetQuerySQL.append("group by PURD.PK_MATERIEL) NUM ON NUM.materielKey = M.ID ");
    RecordsetQuerySQL.append(" LEFT JOIN (select pk_exware, ");
    RecordsetQuerySQL.append("replace(WMSYS.WM_CONCAT(pur1.purchase_code),',','；') as purchaseList ");
    RecordsetQuerySQL.append("from zhl_exware_de  ");
    RecordsetQuerySQL.append("left join zhl_purchase pur1 on pur1.purchase_id = pk_purchase  ");
    RecordsetQuerySQL.append("group by pk_exware) PUR2 ON PUR2.pk_exware = exw.exware_id ");
    RecordsetQuerySQL.append("WHERE EXW.EXWARE_ID =  '");
    RecordsetQuerySQL.append(str);
    RecordsetQuerySQL.append("' ");
    RecordsetQuerySQL.append("order by m.materiel_code ");
    System.out.println(RecordsetQuerySQL.toString());
XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
%> 