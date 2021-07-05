<%@ page import="com.dhc.rad.common.utils.StringUtils" %>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file = "GenXmlData.jsp" %>
<%

    String serviceUnitName = request.getParameter("serviceUnitName");
    String restaurantId = request.getParameter("restaurantId");
    String eatDate = request.getParameter("eatDate");
    String roleFlag = request.getParameter("roleFlag");

    StringBuffer RecordsetQuerySQL = new StringBuffer();
    RecordsetQuerySQL.append(" SELECT EAT_DATE,EAT_WEEK,RESTAURANT_ID,RESTAURANT_NAME,SERVICE_UNIT_ID,SERVICE_UNIT_NAME,AREA_NAME,COUNT_A,COUNT_B,COUNT_C,COUNT_D,COUNT_E,COUNT_F ");
    RecordsetQuerySQL.append("  FROM pz_census WHERE  ");
    RecordsetQuerySQL.append("  eat_date=  '").append(eatDate).append("'  ");
    if(StringUtils.isNotBlank(serviceUnitName)){
        RecordsetQuerySQL.append("and service_unit_name like  '%").append(serviceUnitName).append("%' ");
    }
    if("true".equals(roleFlag)){
        RecordsetQuerySQL.append(" AND  restaurant_id = '").append(restaurantId).append("'  ");
    }
    RecordsetQuerySQL.append(" ORDER BY  area_name ASC,service_unit_name ASC,restaurant_name DESC");
    System.out.println(RecordsetQuerySQL);
    XML_GenOneRecordset(response, RecordsetQuerySQL.toString());
%>
GenOneRecordset(response, RecordsetQuerySQL.toString());