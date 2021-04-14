<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<div class="widget-body" id="IwlsProjectFormDivId">
    <div class="widget-main no-padding">
        <form:form id="inputForm" modelAttribute="holiday" action="${ctx}/holiday/doSave"
                   method="post" class="form-horizontal">
            <form:hidden path="id"/>
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="holidayType">假期类型:</label>
                <div class="col-xs-12 col-sm-8">
                    <form:input path="holidayType" htmlEscape="false" maxlength="64" class="input-xlarge required"/>

                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="holidayDate">假期日期:</label>
                <div class="col-xs-12 col-sm-8">
                    <form:input path="holidayDate" htmlEscape="false" maxlength="64" class="input-xlarge required"/>

                </div>
            </div>
        </form:form>
    </div>
</div>