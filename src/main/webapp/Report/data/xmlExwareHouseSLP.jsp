﻿<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file = "GenXmlData.jsp" %>
<%
String str = request.getParameter("param");

StringBuffer RecordsetQuerySQL = new StringBuffer();
RecordsetQuerySQL.append("SELECT IBD.INVCODE AS CINVENTORYID, ");
RecordsetQuerySQL.append("IBD.invname AS invname, ");
RecordsetQuerySQL.append("b.NOUTNUM AS num, ");
RecordsetQuerySQL.append("LM.MEASNAME AS noutnum, ");
RecordsetQuerySQL.append("(select wk.GZZXBM from LLM_WK wk where wk.id = b.PK_STATIONID) AS PK_STATIONID, ");
RecordsetQuerySQL.append("OH.CODE AS PK_CSPACEID, ");
RecordsetQuerySQL.append("b.GXH, ");
RecordsetQuerySQL.append("b.ZDY18, ");
RecordsetQuerySQL.append("b.INVONLYCODE  AS tm, ");
RecordsetQuerySQL.append("(select h.ZDY9 from LLM_PICKM h where h.id = b.CSOURCEBILLHID) AS ZDY9 ");

RecordsetQuerySQL.append("FROM LLM_EXWAREHOUSE_Z b ");
RecordsetQuerySQL.append("JOIN llm_invmandoc dc on dc.id = b.cinventoryid AND dc.DEL_FLAG = '0' ");
RecordsetQuerySQL.append("JOIN LLM_INVBASDOC IBD ON IBD.ID = DC.PK_INVBASDOC AND IBD.DEL_FLAG = '0' ");
RecordsetQuerySQL.append("LEFT JOIN LLM_MEASDOC LM ON LM.ID = IBD.PK_MEASDOC AND LM.DEL_FLAG = '0' ");
RecordsetQuerySQL.append("LEFT JOIN llm_CARGDOC OH ON OH.ID = b.PK_CSPACEID AND OH.DEL_FLAG = '0' ");
RecordsetQuerySQL.append("WHERE b.ID in ");
RecordsetQuerySQL.append(str);
RecordsetQuerySQL.append(" AND b.DEL_FLAG = '0'  AND b.SHELF_TYPE = '1' ");
System.out.println(RecordsetQuerySQL.toString());
XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
%> 