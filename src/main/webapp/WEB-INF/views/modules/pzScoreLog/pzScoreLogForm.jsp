<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<div class="widget-body" id="IwlsProjectFormDivId">
    <div class="widget-main no-padding">
        <form:form id="inputForm" modelAttribute="pzScoreLog" action="${ctx}/pzScoreLog/doSave"
                   method="post" class="form-horizontal">
            <form:hidden path="id"/>
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="userId">用户名称:</label>
                <div class="col-xs-12 col-sm-8">
                    <form:input path="userId" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="scoreType">积分类型:</label>
                <div class="col-xs-12 col-sm-6">
                    <form:select path="scoreType" class="chosen-select form-control width-100"
                                 data-placeholder="点击选择...">
                        <option value="">---请选择---</option>
                        <form:options items="${fns:getDictList('pz_score_type')}" itemLabel="label" itemValue="value"
                                      htmlEscape="false"/>
                    </form:select>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="scoreChange">变动积分:</label>
                <div class="col-xs-12 col-sm-8">
                    <form:input path="scoreChange" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="scoreDescription">积分变动描述:</label>
                <div class="col-xs-12 col-sm-8">
                    <form:input path="scoreDescription" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
                </div>
            </div>
        </form:form>
    </div>
</div>


