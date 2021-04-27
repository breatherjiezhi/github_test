<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<div class="widget-body" id="IwlsProjectFormDivId">
    <div class="widget-main no-padding">
        <form:form id="inputForm" modelAttribute="pzUserScore" action="${ctx}/pzUserScore/doSave"
                   method="post" class="form-horizontal">
            <form:hidden path="id"/>
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="userId">用户名称:</label>
                <div class="col-xs-12 col-sm-8">
                    <form:input path="userId" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="restaurantId">餐厅名称:</label>
                <div class="col-xs-12 col-sm-8">
                    <form:input path="restaurantId" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="canteenIntegral">个人餐厅所属积分:</label>
                <div class="col-xs-12 col-sm-8">
                    <form:input path="canteenIntegral" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
                </div>
            </div>

        </form:form>
    </div>
</div>


