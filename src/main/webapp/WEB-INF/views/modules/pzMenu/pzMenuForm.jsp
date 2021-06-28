<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<div class="widget-body" id="IwlsProjectFormDivId">
    <div class="widget-main no-padding">
        <form:form id="inputForm" modelAttribute="pzMenu" action="${ctx}/pzMenu/doSave"
                   method="post" class="form-horizontal">
            <form:hidden path="id"/>
            <input type="hidden" value="${httpUrl}" id="httpUrl"/>
            <div class="form-group">
                <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="menuName">菜单名称:</label>
                <div class="col-xs-12 col-sm-8">
                    <form:select path="menuName" class="chosen-select form-control width-100"
                                 onchange="menuNameChanges(this.value)">
                        data-placeholder="点击选择...">
                        <option value="">---请选择---</option>
                        <form:options items="${fns:getDictList('pz_menu_type_name')}" itemLabel="label"
                                      itemValue="value"
                                      htmlEscape="false"/>
                    </form:select>
                </div>
            </div>
            <c:forEach items="${pzMenu.pzMenuContentList}" var="pmcl" varStatus="status">
                <div class="form-group">
                    <form:hidden path="pzMenuContentList[${status.index}].eatDate"/>
                    <form:hidden path="pzMenuContentList[${status.index}].eatWeek"/>
                    <label class="control-label col-xs-12 col-sm-3 no-padding-right">${pmcl.eatDate}(${pmcl.eatWeek}):</label>
                    <div class="col-xs-12 col-sm-8">
                        <form:hidden path="pzMenuContentList[${status.index}].id"/>
                        <form:input path="pzMenuContentList[${status.index}].menuDetail" htmlEscape="false"
                                    maxlength="64" class="input-xlarge required" placeholder="请填写套餐明细"/>
                        <form:select path="pzMenuContentList[${status.index}].menuLimited"
                                     class="chosen-select form-control width-75" onchange="menuLimitChanges(this)"
                                     data-placeholder="点击选择...">
                            <form:options items="${fns:getDictList('pz_menu_limited')}" itemLabel="label"
                                          itemValue="value"
                                          htmlEscape="false"/>
                        </form:select>
                        <form:input path="pzMenuContentList[${status.index}].menuCount" htmlEscape="false"
                                    maxlength="64" class="input-xlarge required"
                                    onkeyup="this.value=this.value.replace(/\D/g,'')" placeholder="请填写限量份数"/>
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
                        <input id="menuImgUrl" name="menuImgUrl" type="hidden" htmlEscape="false" maxlength="255"
                               class="input-xlarge required" value="${pzMenu.menuImgUrl}"/>
                        <button type="button" class="layui-btn" id="imgButton">上传图片</button>
                        <div class="layui-upload-list">
                            <img class="layui-upload-img" id="menuImg">
                            <p id="imgText"></p>
                        </div>
                    </div>
                </div>
            </div>

        </form:form>
    </div>
</div>
<script type="text/javascript">

    function menuNameChanges(d) {
        if (d === "A") {
            $("select[name*='menuLimited']").each(function () {
                $(this).val(0);
                $(this).trigger("chosen:updated");
                $(this).hide();

                $(this).next().val("");
                $(this).next().hide();
            })

        }else if(d==""){
            $("select[name*='menuLimited']").each(function () {
                    $(this).val(0);
                    $(this).trigger("chosen:updated");
                    $(this).show();
                    $(this).next().val("0");
                    $(this).next().hide();
            })
        } else {
            $("select[name*='menuLimited']").each(function () {
                if ($(this).val() == "0") {
                    $(this).val(0);
                    $(this).trigger("chosen:updated");
                    $(this).show();
                    $(this).next().val("");
                    $(this).next().hide();
                } else {
                    $(this).show();
                    $(this).next().show();

                }
            })

        }
    }

    function menuLimitChanges(d) {
        if (d.value == "0") {
            $(d).next().val("");
            $(d).next().hide();
        } else {
            $(d).next().val("0");
            $(d).next().show();
        }
    }


    var scripts = [null, '${ctxStatic}/layui/js/layer.js', null];
    $('.page-content-area').ace_ajax('loadScripts', scripts, function () {
        jQuery(function ($) {
            //修改页面禁止选择套餐名称
            var menuName = "${pzMenu.menuName}";
            if (menuName != "") {
                $("select[id='menuName']").find('option').each(function () {
                    if($(this).attr("value") != menuName){
                        $(this).remove();
                    }
                });
            }else{

            }
            menuNameChanges("${pzMenu.menuName}");
            layui.use('upload', function () {
                var $ = layui.jquery, upload = layui.upload;
                //普通图片上传
                var uploadInst = upload.render({
                    elem: '#imgButton'
                    , url: '${ctx}/pzMenuFile/uploadImg' //改成您自己的上传接口
                    , before: function (obj) {
                        obj.preview(function (index, file, result) {
                            $('#menuImg').attr('style', 'width: 300px;height: 200px;');
                            $('#menuImg').attr('src', result); //图片链接（base64）
                        });
                    }
                    , done: function (res) {
                        //如果上传失败
                        if (res.code > 0) {
                            var imgText = $('#imgText');
                            imgText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                            imgText.find('.demo-reload').on('click', function () {
                                uploadInst.upload();
                            });
                        } else {
                            //上传成功
                            var imgText = $('#imgText');
                            imgText.html('');
                            $('#menuImgUrl').val(res.pzMenuFile.filePath);
                            return layer.msg('上传成功!');
                        }

                    }
                    , error: function () {
                        //失败状态，并实现重传
                        var imgText = $('#imgText');
                        imgText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                        imgText.find('.demo-reload').on('click', function () {
                            uploadInst.upload();
                        });
                    }
                });
            });

            var menuImgUrl = $('#menuImgUrl').val();
            var httpUrl = $('#httpUrl').val();
            if (menuImgUrl != "") {
                $('#menuImg').attr('style', 'width: 300px;height: 200px;');
                $('#menuImg').attr('src', httpUrl + menuImgUrl); //图片链接（base64）
            }

        });
    });
</script>