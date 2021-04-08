﻿<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file = "GenXmlData.jsp" %>
<%
String str = request.getParameter("param");

StringBuffer RecordsetQuerySQL = new StringBuffer();
RecordsetQuerySQL.append("select enw.c_enwaer_code as cEnwareCode, ");
RecordsetQuerySQL.append("enw.billfrom, ");
RecordsetQuerySQL.append("enw.remarks, ");
RecordsetQuerySQL.append("shr.materiel_code as materielCode, ");
RecordsetQuerySQL.append("m.materiel_name, ");
RecordsetQuerySQL.append("m.materiel_forname, ");
RecordsetQuerySQL.append("m.materiel_unit, ");
RecordsetQuerySQL.append("shr.achieve_num as enNum, ");
RecordsetQuerySQL.append("shr.allocation_code as allocationCode, ");
RecordsetQuerySQL.append("store.storehouse_name ");

RecordsetQuerySQL.append("from zhl_c_shift_en shr ");
RecordsetQuerySQL.append("left join zhl_c_enware enw on shr.pk_c_enware = enw.c_enware_id ");
RecordsetQuerySQL.append("left join zhl_basis_materiel m on m.materiel_code = shr.materiel_code ");
RecordsetQuerySQL.append("left join zhl_basis_storehouse store on store.id = shr.pk_storehouse ");
RecordsetQuerySQL.append("where shr.pk_c_enware = '");
RecordsetQuerySQL.append(str);
RecordsetQuerySQL.append("' ");
RecordsetQuerySQL.append("and shr.del_flag='0' ");
RecordsetQuerySQL.append("order by m.materiel_code ");
System.out.println(RecordsetQuerySQL.toString());
XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
%> 