﻿<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file = "GenXmlData.jsp" %>
<%
String str = request.getParameter("param");

StringBuffer RecordsetQuerySQL = new StringBuffer();
RecordsetQuerySQL.append("select ad.c_adjust_code, ");
RecordsetQuerySQL.append("ad.department, ");
RecordsetQuerySQL.append("ad.buyer, ");
RecordsetQuerySQL.append("ad.handler, ");
RecordsetQuerySQL.append("ad.remarks, ");
RecordsetQuerySQL.append("ad.c_adjust_status, ");
RecordsetQuerySQL.append("m.materiel_code, ");
RecordsetQuerySQL.append("m.materiel_name, ");
RecordsetQuerySQL.append("m.materiel_forname, ");
RecordsetQuerySQL.append("ade.materiel_num as materielNum, ");
RecordsetQuerySQL.append("m.materiel_specs, ");
RecordsetQuerySQL.append("m.materiel_unit, ");
RecordsetQuerySQL.append("ade.batch_code, ");
RecordsetQuerySQL.append("store1.storehouse_name as storeOut, ");
RecordsetQuerySQL.append("store2.storehouse_name as storeIn ");

RecordsetQuerySQL.append("from zhl_c_adjust_de ade ");
RecordsetQuerySQL.append("left join zhl_c_adjust ad on ade.pk_c_adjust = ad.c_adjust_id and ad.del_flag = '0' ");
RecordsetQuerySQL.append("left join zhl_basis_materiel m on m.id = ade.pk_materiel ");
RecordsetQuerySQL.append("left join zhl_basis_storehouse store1 on store1.id = ade.pk_storehouse_out ");
RecordsetQuerySQL.append("left join zhl_basis_storehouse store2 on store2.id = ade.pk_storehouse_in ");
RecordsetQuerySQL.append("where ade.pk_c_adjust = '");
RecordsetQuerySQL.append(str);
RecordsetQuerySQL.append("' ");
RecordsetQuerySQL.append("and ade.del_flag = '0' ");
RecordsetQuerySQL.append("order by m.materiel_code ");
System.out.println(RecordsetQuerySQL.toString());
XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
%> 