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

            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" name="menuImgUrl">菜单图片:</label>
                <div class="control-label col-xs-12 col-sm-3 no-padding-right">
                    <button type="button" class="layui-btn" style="background: #7392c2;margin-left: 1%" id="upload" ><i class="layui-icon"></i>上传</button>
                </div>
            </div>
            <div class="form-group">
            <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="menuType">套餐规格:</label>
            <div class="col-xs-12 col-sm-5">
                <form:select path="menuType" class="chosen-select form-control width-100"
                             data-placeholder="点击选择...">
                    <option value="">---请选择---</option>
                    <form:options items="${fns:getDictList('pz_menu_type')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </div>
            </div>
            <div class="form-group">
            <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="menuLimited">套餐是否限量</label>
            <div class="col-xs-12 col-sm-5">
                <form:select path="menuLimited" class="chosen-select form-control width-100"
                             data-placeholder="点击选择...">
                    <option value="">---请选择---</option>
                    <form:options items="${fns:getDictList('pz_menu_limited')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </div>
            </div>
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="menuCount">套餐余量:</label>
                <div class="col-xs-12 col-sm-8">
                    <form:input path="menuCount" htmlEscape="false" maxlength="64" class="input-xlarge required"/>

                </div>
            </div>
            <div class="form-group">
            <label class="control-label col-xs-12 col-sm-1 no-padding-right" for="menuStatus">套餐状态</label>
            <div class="col-xs-12 col-sm-5">
                <form:select path="menuStatus" class="chosen-select form-control width-100"
                             data-placeholder="点击选择...">
                    <option value="">---请选择---</option>
                    <form:options items="${fns:getDictList('pz_menu_status')}" itemLabel="label" itemValue="value"
                                  htmlEscape="false"/>
                </form:select>
            </div>
            </div>
        </form:form>
    </div>
</div>