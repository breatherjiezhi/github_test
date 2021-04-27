<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<div class="widget-body" id="IwlsProjectFormDivId">
    <div class="widget-main no-padding">
        <form:form id="inputForm" modelAttribute="pzBoxCode" action="${ctx}/pzBoxCode/doSave"
                   method="post" class="form-horizontal">
            <form:hidden path="id"/>
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="boxCode">箱子编码:</label>
                <div class="col-xs-12 col-sm-8">
                    <form:input path="boxCode" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="boxCnName">箱子中文名称:</label>
                <div class="col-xs-12 col-sm-8">
                    <form:input path="boxCnName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="restaurantId">餐厅名称:</label>
                <div class="col-xs-12 col-sm-8">
                    <form:input path="restaurantId" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="serviceUnitId">服务单元名称:</label>
                <div class="col-xs-12 col-sm-8">
                    <form:input path="serviceUnitId" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
                </div>
            </div>
        </form:form>
    </div>
</div>


