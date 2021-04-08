﻿<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file = "GenXmlData.jsp" %>
<%
String str = request.getParameter("param");

StringBuffer RecordsetQuerySQL = new StringBuffer();
RecordsetQuerySQL.append("select distinct cexwd.c_exware_de_id as cExwareDeId, ");
RecordsetQuerySQL.append("req.c_requisition_code as requisitionCode, ");
RecordsetQuerySQL.append("jt.jobteam_code, ");
RecordsetQuerySQL.append("req.project_name, ");
RecordsetQuerySQL.append("req.reqmtype, ");
RecordsetQuerySQL.append("req.pk_kgy, ");
RecordsetQuerySQL.append("req.remarks, ");
RecordsetQuerySQL.append("req.department, ");
RecordsetQuerySQL.append("req.make_by, ");
RecordsetQuerySQL.append("cexw.c_exware_code, ");
RecordsetQuerySQL.append("m.id as materielKey, ");
RecordsetQuerySQL.append("m.materiel_code as materielCode, ");
RecordsetQuerySQL.append("m.materiel_name as materielName, ");
RecordsetQuerySQL.append("m.materiel_forname as materielForName, ");
RecordsetQuerySQL.append("m.materiel_specs as specs, ");
RecordsetQuerySQL.append("m.materiel_unit as unit, ");
RecordsetQuerySQL.append("reqd.plan_num as planNum, ");
RecordsetQuerySQL.append("reqd.ex_num as exNum, ");
RecordsetQuerySQL.append("nvl(cexwd.ex_num,'0.00') as cexNum, ");
RecordsetQuerySQL.append("nvl(cexwd.xjflag,'03') as xjflag, ");
RecordsetQuerySQL.append("stor.storehouse_name as storeHouseName ");

RecordsetQuerySQL.append("from zhl_c_requisition_de reqd ");
RecordsetQuerySQL.append("left join zhl_c_exware_de cexwd on reqd.c_requisition_de_id = cexwd.pk_c_requisition_de and cexwd.del_flag = '0' ");
RecordsetQuerySQL.append("left join zhl_c_requisition req on req.c_requisition_id = reqd.pk_c_requisition and req.del_flag = '0' ");
RecordsetQuerySQL.append("left join zhl_c_exware cexw on req.c_requisition_code = cexw.frombill and (cexw.c_exware_type = '00' or cexw.c_exware_type = '02') and cexw.del_flag = '0' ");
RecordsetQuerySQL.append("left join zhl_basis_materiel m on m.id = reqd.pk_materiel ");
RecordsetQuerySQL.append("left join zhl_basis_storehouse stor on stor.id = cexwd.pk_storehouse ");
RecordsetQuerySQL.append("left join zhl_basis_jobteam jt on jt.id = req.pk_jobteam ");
RecordsetQuerySQL.append("where reqd.pk_c_requisition = '");
RecordsetQuerySQL.append(str);
RecordsetQuerySQL.append("' ");
RecordsetQuerySQL.append("AND reqd.del_flag = '0' ");
RecordsetQuerySQL.append("ORDER BY xjflag ");
System.out.println(RecordsetQuerySQL.toString());
XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
%> 