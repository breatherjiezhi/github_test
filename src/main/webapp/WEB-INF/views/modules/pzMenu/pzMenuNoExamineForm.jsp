<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<div class="widget-body" id="IwlsProjectFormDivId">
    <div class="widget-main no-padding">
        <form:form id="inputForm" modelAttribute="pzMenu" action="${ctx}/pzMenu/doSave"
                   method="post" class="form-horizontal">
            <form:hidden path="id"/>
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="menuName">菜单名称:</label>
                <div class="col-xs-12 col-sm-8">
                    <form:input path="menuName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>

                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="menuDescription">菜单描述:</label>
                <div class="col-xs-12 col-sm-8">
                    <form:input path="menuDescription" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
                </div>
            </div>
        </form:form>
    </div>
</div>