<%@ page import="com.dhc.rad.common.utils.StringUtils" %>
<%@ page import="com.dhc.rad.modules.sys.utils.UserUtils" %>
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
    String officeId = request.getParameter("officeId");
    String roleFlag = request.getParameter("roleFlag");
    StringBuffer RecordsetQuerySQL = new StringBuffer();
    RecordsetQuerySQL.append("select pbc.ID as BOX_ID,CONCAT(sso.`NAME`,pbc.BOX_CODE) as BOX_CN_NAME,sso.`NAME` as 'RESTAURANT_NAME',pbc.BOX_CODE ");
    RecordsetQuerySQL.append("from pz_box_code pbc  ");
    RecordsetQuerySQL.append("left join sys_office sso on pbc.RESTAURANT_ID=sso.ID WHERE ");
    RecordsetQuerySQL.append("  pbc.del_flag = '0'  ");
    if(StringUtils.isNotBlank(temp)){
        RecordsetQuerySQL.append(" AND pbc.ID IN (");
        RecordsetQuerySQL.append(temp);
        RecordsetQuerySQL.append(") ");
    }
    if("true".equals(roleFlag)){
        RecordsetQuerySQL.append(" AND  pbc.RESTAURANT_ID = '").append(officeId).append("'  ");
    }

    System.out.println(RecordsetQuerySQL);
    XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
%>
GenOneRecordset(response, RecordsetQuerySQL.toString());