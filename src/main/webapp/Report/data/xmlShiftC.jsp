﻿<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file = "GenXmlData.jsp" %>
<%
String str = request.getParameter("param");

StringBuffer RecordsetQuerySQL = new StringBuffer();
RecordsetQuerySQL.append("select exw.c_exware_code as cExwareCode, ");
RecordsetQuerySQL.append("exw.frombill, ");
RecordsetQuerySQL.append("exw.remarks, ");
RecordsetQuerySQL.append("exw.make_by, ");
RecordsetQuerySQL.append("shc.materiel_code as materielCode, ");
RecordsetQuerySQL.append("m.materiel_name, ");
RecordsetQuerySQL.append("m.materiel_forname, ");
RecordsetQuerySQL.append("m.materiel_unit, ");
RecordsetQuerySQL.append("shc.exware_num as exNum, ");
RecordsetQuerySQL.append("shc.allocate_code as allocationCode, ");
RecordsetQuerySQL.append("store.storehouse_name ");

RecordsetQuerySQL.append("from zhl_c_shift_ex shc ");
RecordsetQuerySQL.append("left join zhl_c_exware exw on shc.pk_c_exware = exw.c_exware_id ");
RecordsetQuerySQL.append("left join zhl_basis_materiel m on m.materiel_code = shc.materiel_code ");
RecordsetQuerySQL.append("left join zhl_basis_storehouse store on store.id = shc.pk_storehouse ");
RecordsetQuerySQL.append("where shc.pk_c_exware = '");
RecordsetQuerySQL.append(str);
RecordsetQuerySQL.append("' ");
RecordsetQuerySQL.append("and shc.del_flag = '0' ");
RecordsetQuerySQL.append("order by m.materiel_code ");
System.out.println(RecordsetQuerySQL.toString());
XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
%> 