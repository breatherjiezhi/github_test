<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<div class="widget-body" id="IwlsProjectFormDivId">
    <div class="widget-main no-padding">
        <form:form id="inputForm" modelAttribute="pzBoxCode" action="${ctx}/pzBoxCode/doSave"
                   method="post" class="form-horizontal">
            <form:hidden path="id"/>
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="boxCode">箱子编号:</label>
                <div class="col-xs-12 col-sm-8">
                    <form:input path="boxCode" htmlEscape="false" maxlength="64" class="input-xlarge required" onkeyup="this.value=this.value.replace(/\D/g,'')"/>
                </div>
            </div>
            <%--<div class="form-group">--%>
                <%--<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="boxCnName">箱子中文名称:</label>--%>
                <%--<div class="col-xs-12 col-sm-8">--%>
                    <%--<form:input path="boxCnName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>--%>
                <%--</div>--%>
            <%--</div>--%>
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="restaurantId">餐厅名称:</label>
                <div class="col-xs-12 col-sm-8">
                    <form:select path="restaurantId" class="select2 tag-input-style width-75"
                                 data-placeholder="点击选择...">
                    </form:select>
                </div>
            </div>
            <%--<div class="form-group">--%>
                <%--<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="serviceUnitId">服务单元名称:</label>--%>
                <%--<div class="col-xs-12 col-sm-8 input-group">--%>
                    <%--<input readonly="" type="text" id="serviceUnit" name="serviceUnit" value="${pzBoxCode.serviceUnit}"--%>
                           <%--class="input-xlarge required"/>--%>
                    <%--<span class="input-group-btn">--%>
							<%--<button type="button" id="selectOfficeMenu" class="btn btn-purple btn-sm">--%>
								<%--<span class="ace-icon fa fa-search icon-on-right bigger-110"></span>--%>
								<%--<span data-locale="Choose">选择</span>--%>
							<%--</button>--%>
                        <%--<input type="hidden" id="serviceUnitId" name="serviceUnitId"--%>
                               <%--value="${pzBoxCode.serviceUnitId}"/>--%>
                <%--</span>--%>
                <%--</div>--%>
            <%--</div>--%>
        </form:form>
    </div>
</div>
<div id="selectOfficeTreeDiv" class="hide widget-body">
    <div class="widget-main padding-8">
        <div id="popuptreeview" class="" data-id="" data-text=""></div>
    </div>
</div>
<script>
    showCarNum();

    function showCarNum() {
        $.ajax({
            url: "${ctx}/sys/office/findRestaurantOffice",
            type: "post",
            success: function (data) {
                var htmlT1 = "<option value=''>---请选择---</option>";
                if (data.length != 0) {
                    for (var i = 0; i < data.length; i++) {
                        htmlT1 += "<option value=" + data[i].id + ">" + data[i].name + "</option>";
                    }
                }
                $("#restaurantId").html(htmlT1);
                $("#restaurantId").trigger("chosen:updated");
                $("#restaurantId").select2("val", "${pzBoxCode.restaurantId}");
            }


        })

    }
</script>

