<%@ page import="com.dhc.rad.common.utils.StringUtils" %>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file = "GenXmlData.jsp" %>
<%
    String str = request.getParameter("param");
    String temp=null;
    if(StringUtils.isNotBlank(str)){
        String[] strTemp =  str.split(",");
        for (int i =0 ;i<strTemp.length;i++){
            if(StringUtils.isNotBlank(temp)){
                temp += ",'"+strTemp[i]+"'";
            }else{
                temp = "'"+strTemp[i]+"'";
            }
        }
    }

    StringBuffer RecordsetQuerySQL = new StringBuffer();
    RecordsetQuerySQL.append("select pbc.BOX_CODE,pbc.BOX_CN_NAME,so.`NAME` as 'SERVICE_UNIT',sso.`NAME` as 'RESTAURANT_NAME',sa.`NAME` as 'AREA_NAME' ");
    RecordsetQuerySQL.append("from pz_box_code pbc  ");
    RecordsetQuerySQL.append("left join sys_office so on pbc.SERVICE_UNIT_ID=so.ID ");
    RecordsetQuerySQL.append("left join sys_office sso on pbc.RESTAURANT_ID=sso.ID ");
    RecordsetQuerySQL.append("left join sys_area sa on so.AREA_ID=sa.ID WHERE ");
    if(StringUtils.isNotBlank(temp)){
        RecordsetQuerySQL.append(" pbc.ID IN (");
        RecordsetQuerySQL.append(temp);
        RecordsetQuerySQL.append(") AND");
    }
    RecordsetQuerySQL.append("  pbc.del_flag = '0'  ");
    System.out.println(RecordsetQuerySQL);
    XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
%>
GenOneRecordset(response, RecordsetQuerySQL.toString());