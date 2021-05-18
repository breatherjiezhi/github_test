<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<div class="widget-body" id="IwlsProjectFormDivId">
    <div class="widget-main no-padding">
        <form:form id="inputForm" modelAttribute="pzMenu" action="${ctx}/pzMenu/doSave"
                   method="post" class="form-horizontal">
            <form:hidden path="id"/>
            <input type="hidden"  value="${httpUrl}" id="httpUrl"/>
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="menuName">菜单名称:</label>
                <div class="col-xs-12 col-sm-8">
                    <form:select path="menuName" class="chosen-select form-control width-100"
                                 data-placeholder="点击选择...">
                        <option value="">---请选择---</option>
                        <form:options items="${fns:getDictList('pz_menu_type_name')}" itemLabel="label" itemValue="value"
                                      htmlEscape="false"/>
                    </form:select>
                </div>
            </div>
            <c:forEach items="${pzMenu.pzMenuContentList}" var="pmcl" varStatus="status" >
                <div class="form-group">
                    <form:hidden path="pzMenuContentList[${status.index}].eatDate"  />
                    <form:hidden path="pzMenuContentList[${status.index}].eatWeek" />
                    <label class="control-label col-xs-12 col-sm-3 no-padding-right" >${pmcl.eatDate}(${pmcl.eatWeek}):</label>
                    <div class="col-xs-12 col-sm-8">
                        <form:input path="pzMenuContentList[${status.index}].menuDetail" htmlEscape="false" maxlength="64" class="input-xlarge required" />
                    </div>
                </div>
            </c:forEach>

            <%--<div class="form-group">--%>
            <%--<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="menuType">套餐规格:</label>--%>
            <%--<div class="col-xs-12 col-sm-6">--%>
            <%--<form:select path="menuType" class="chosen-select form-control width-100"--%>
            <%--data-placeholder="点击选择...">--%>
            <%--<option value="">---请选择---</option>--%>
            <%--<form:options items="${fns:getDictList('pz_menu_type')}" itemLabel="label" itemValue="value"--%>
            <%--htmlEscape="false"/>--%>
            <%--</form:select>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--<div class="form-group">--%>
            <%--<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="menuLimited">套餐是否限量</label>--%>
            <%--<div class="col-xs-12 col-sm-6">--%>
            <%--<form:select path="menuLimited" class="chosen-select form-control width-100"--%>
            <%--data-placeholder="点击选择...">--%>
            <%--<option value="">---请选择---</option>--%>
            <%--<form:options items="${fns:getDictList('pz_menu_limited')}" itemLabel="label" itemValue="value"--%>
            <%--htmlEscape="false"/>--%>
            <%--</form:select>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--<div class="form-group">--%>
            <%--<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="menuCount">套餐余量:</label>--%>
            <%--<div class="col-xs-12 col-sm-8">--%>
            <%--<form:input path="menuCount" htmlEscape="false" maxlength="64" class="input-xlarge required"/>--%>

            <%--</div>--%>
            <%--</div>--%>
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="menuImgUrl">菜单图片:</label>
                <div class="control-label col-xs-12 col-sm-6 no-padding-right">
                    <div class="layui-upload">
                        <input id="menuImgUrl" name="menuImgUrl" type="hidden" htmlEscape="false" maxlength="128" class="input-xlarge" value="${pzMenu.menuImgUrl}" />
                        <div class="layui-upload-list">
                            <img class="layui-upload-img" id="menuImg" >
                            <p id="imgText"></p>
                        </div>
                    </div>
                </div>
            </div>

        </form:form>
    </div>
</div>
<script type="text/javascript">
    var scripts = [null, '${ctxStatic}/layui/js/layer.js', null];
    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        jQuery(function ($) {
            var menuImgUrl = $('#menuImgUrl').val();
            var httpUrl = $('#httpUrl').val();
            if (menuImgUrl != "") {
                $('#menuImg').attr('style','width: 300px;height: 200px;');
                $('#menuImg').attr('src',httpUrl+menuImgUrl); //图片链接（base64）
            }

        });
    });
</script>