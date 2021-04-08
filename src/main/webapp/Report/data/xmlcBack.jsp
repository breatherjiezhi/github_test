﻿<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file = "GenXmlData.jsp" %>
<%
String str = request.getParameter("param");

StringBuffer RecordsetQuerySQL = new StringBuffer();
RecordsetQuerySQL.append("select bac.c_back_code as backCode, ");
RecordsetQuerySQL.append("jt.jobteam_code as jobTeamCode, ");
RecordsetQuerySQL.append("bac.project_name as projectName, ");
RecordsetQuerySQL.append("bac.batch_code as batchCode, ");
RecordsetQuerySQL.append("bac.back_by as backBy, ");
RecordsetQuerySQL.append("bac.department, ");
RecordsetQuerySQL.append("pro.profession_name, ");
RecordsetQuerySQL.append("bacd.materiel_code as materielCode, ");
RecordsetQuerySQL.append("m.materiel_name as materielName, ");
RecordsetQuerySQL.append("m.materiel_forname as materielForName, ");
RecordsetQuerySQL.append("m.materiel_specs as specs, ");
RecordsetQuerySQL.append("m.materiel_unit as unit, ");
RecordsetQuerySQL.append("bacd.back_num, ");
RecordsetQuerySQL.append("store.storehouse_name, ");
RecordsetQuerySQL.append("f_get_code_val('xjflag',bacd.xjflag) AS xjflag ");

RecordsetQuerySQL.append("from zhl_c_back_de bacd ");
RecordsetQuerySQL.append("left join zhl_c_back bac on bac.c_back_id = bacd.pk_c_back ");
RecordsetQuerySQL.append("left join zhl_basis_materiel m on bacd.materiel_code = m.materiel_code ");
RecordsetQuerySQL.append("left join zhl_basis_storehouse store on store.id = bacd.pk_storehouse ");
RecordsetQuerySQL.append("left join zhl_basis_jobteam jt on jt.id = bac.pk_jobteam ");
RecordsetQuerySQL.append("left join zhl_basis_profession pro on pro.id = bac.pk_profession ");
RecordsetQuerySQL.append("where bacd.pk_c_back = '");
RecordsetQuerySQL.append(str);
RecordsetQuerySQL.append("' ");
RecordsetQuerySQL.append("AND bacd.del_flag = '0' ");
RecordsetQuerySQL.append("ORDER BY xjflag ");
System.out.println(RecordsetQuerySQL.toString());
XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
%> 