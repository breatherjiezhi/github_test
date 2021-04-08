<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<link href="${ctxStatic}/bootstrap-treeview/css/bootstrap-treeview.css" rel="stylesheet" type="text/css"/>
<title>业务表管理</title>
<style type="text/css">
	.checkboxSize {
		height: 17px;
		width: 17px;
	}
</style>
<meta name="decorator" content="default"/>
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<div class="widget-body" id="genTableDivId">
			<c:choose>
				<c:when test="${empty genTable.name}">
					<form:form id="inputForm2" modelAttribute="genTable" action="${ctx}/gen/genTable/form" method="post"
					           class="form-horizontal">
						<form:hidden path="id"/>
						<div class="form-group">
							<label class="control-label col-xs-12 col-sm-1 no-padding">表名:</label>
							<div class="col-xs-12 col-sm-4">
								<form:select path="name" class="chosen-select input-xxlarge">
									<form:options items="${tableList}" itemLabel="nameAndComments" itemValue="name"
									              htmlEscape="false"/>
								</form:select>
							</div>
						</div>
						<c:if test="${not empty message}">
							<div class="col-xs-12 col-sm-1"></div>
							<label style="color:red;">${message}</label>
						</c:if>
						<div class="form-group">
							<div class="col-xs-12 col-sm-1">
							</div>
							<div class="col-xs-12 col-sm-1">
								<input id="btnNext" class="btn btn-primary" type="button" value="下一步"/>
							</div>
						</div>
					</form:form>
				</c:when>
				<c:otherwise>
					<form:form id="inputForm" modelAttribute="genTable" action="${ctx}/gen/genTable/save" method="post"
					           class="form-horizontal">
						<form:hidden path="id"/>
						<fieldset>
							<legend>基本信息</legend>
							<div class="form-group">
								<label class="control-label">表名:</label>
								<div class="controls">
									<form:input path="name" htmlEscape="false" maxlength="200" class="required"
									            readonly="true"/>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">说明:</label>
								<div class="controls">
									<form:input path="comments" htmlEscape="false" maxlength="200" class="required"/>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">类名:</label>
								<div class="controls">
									<form:input path="className" htmlEscape="false" maxlength="200" class="required"/>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label">父表表名:</label>
								<div class="controls">
									<form:select path="parentTable" cssClass="chosen-select">
										<form:option value="">无</form:option>
										<form:options items="${tableList}" itemLabel="nameAndComments" itemValue="name"
										              htmlEscape="false"/>
									</form:select>
									&nbsp;当前表外键：
									<form:select path="parentTableFk" cssClass="chosen-select">
										<form:option value="">无</form:option>
										<form:options items="${genTable.columnList}" itemLabel="nameAndComments"
										              itemValue="name" htmlEscape="false"/>
									</form:select>
									<span class="help-inline">如果有父表，请指定父表表名和外键</span>
								</div>
							</div>
							<div class="form-group hide">
								<label class="control-label">备注:</label>
								<div class="controls">
									<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200"
									               class="input-xxlarge"/>
								</div>
							</div>
							<legend>字段列表</legend>
							<div class="form-group">
								<table id="contentTable" class="table table-striped table-bordered table-condensed">
									<thead>
									<tr>
										<th title="数据库字段名">列名</th>
										<th title="默认读取数据库字段备注">说明</th>
										<th title="数据库中设置的字段类型及长度">物理类型</th>
										<th title="实体对象的属性字段类型">Java类型</th>
										<th title="实体对象的属性字段（对象名.属性名|属性名2|属性名3，例如：用户user.id|name|loginName，属性名2和属性名3为Join时关联查询的字段）">
											Java属性名称 <i class="icon-question-sign"></i></th>
										<th title="是否是数据库主键">主键</th>
										<th title="字段是否可为空值，不可为空字段自动进行空值验证">可空</th>
										<th title="选中后该字段被加入到insert语句里">插入</th>
										<th title="选中后该字段被加入到update语句里">编辑</th>
										<th title="选中后该字段被加入到查询列表里">列表</th>
										<th title="选中后该字段被加入到查询条件里">查询</th>
										<th title="该字段为查询字段时的查询匹配放松">查询匹配方式</th>
										<th title="字段在表单中显示的类型">显示表单类型</th>
										<th title="显示表单类型设置为“下拉框、复选框、点选框”时，需设置字典的类型">字典类型</th>
										<th>排序</th>
									</tr>
									</thead>
									<tbody>
									<c:forEach items="${genTable.columnList}" var="column" varStatus="vs">
										<tr${column.delFlag eq '1'?' class="error" title="已删除的列，保存之后消失！"':''}>
											<td nowrap>
												<input type="hidden" name="columnList[${vs.index}].id"
												       value="${column.id}"/>
												<input type="hidden" name="columnList[${vs.index}].delFlag"
												       value="${column.delFlag}"/>
												<input type="hidden" name="columnList[${vs.index}].genTable.id"
												       value="${column.genTable.id}"/>
												<input type="hidden" name="columnList[${vs.index}].name"
												       value="${column.name}"/>${column.name}
											</td>
											<td>
												<input type="text" name="columnList[${vs.index}].comments"
												       value="${column.comments}" maxlength="200" class="required"
												       style="width:100px;"/>
											</td>
											<td nowrap>
												<input type="hidden" name="columnList[${vs.index}].jdbcType"
												       value="${column.jdbcType}"/>${column.jdbcType}
											</td>
											<td>
												<select name="columnList[${vs.index}].javaType" class="chosen-select"
												        style="width:85px;*width:75px">
													<c:forEach items="${config.javaTypeList}" var="dict">
														<option value="${dict.value}" ${dict.value==column.javaType?'selected':''}
														        title="${dict.description}">${dict.label}</option>
													</c:forEach>
												</select>
											</td>
											<td>
												<input type="text" name="columnList[${vs.index}].javaField"
												       value="${column.javaField}" maxlength="200"
												       class="required input-small"/>
											</td>
											<td>
												<input type="checkbox" name="columnList[${vs.index}].isPk"
												       class="checkboxSize" ${column.isPk=='1'?'checked':''}
												       value="${column.isPk=='1'?'1':''}"/>
											</td>
											<td>
												<input type="checkbox" name="columnList[${vs.index}].isNull"
												       class="checkboxSize"  ${column.isNull eq '1' ? 'checked' : ''}
												       value="${column.isNull=='1'?'1':''}"/>
											</td>
											<td>
												<input type="checkbox" name="columnList[${vs.index}].isInsert"
												       class="checkboxSize"  ${column.isInsert eq '1' ? 'checked' : ''}
												       value="${column.isInsert=='1'?'1':''}"/>
											</td>
											<td>
												<input type="checkbox" name="columnList[${vs.index}].isEdit"
												       class="checkboxSize" ${column.isEdit eq '1' ? 'checked' : ''}
												       value="${column.isEdit=='1'?'1':''}"/>
											</td>
											<td>
												<input type="checkbox" name="columnList[${vs.index}].isList"
												       class="checkboxSize"  ${column.isList eq '1' ? 'checked' : ''}
												       value="${column.isList=='1'?'1':''}"/>
											</td>
											<td>
												<input type="checkbox" name="columnList[${vs.index}].isQuery"
												       class="checkboxSize"  ${column.isQuery eq '1' ? 'checked' : ''}
												       value="${column.isQuery=='1'?'1':''}"/>
											</td>
											<td>
												<select name="columnList[${vs.index}].queryType" class="chosen-select">
													<c:forEach items="${config.queryTypeList}" var="dict">
														<option value="${fns:escapeHtml(dict.value)}" ${fns:escapeHtml(dict.value)==column.queryType?'selected':''}
														        title="${dict.description}">${fns:escapeHtml(dict.label)}</option>
													</c:forEach>
												</select>
											</td>
											<td>
												<select name="columnList[${vs.index}].showType" class="chosen-select"
												        style="width:100px;">
													<c:forEach items="${config.showTypeList}" var="dict">
														<option value="${dict.value}" ${dict.value==column.showType?'selected':''}
														        title="${dict.description}">${dict.label}</option>
													</c:forEach>
												</select>
											</td>
											<td>
												<input type="text" name="columnList[${vs.index}].dictType"
												       value="${column.dictType}" maxlength="200" class="input-mini"/>
											</td>
											<td>
												<input type="text" name="columnList[${vs.index}].sort"
												       value="${column.sort}" maxlength="200"
												       class="required input-min digits"/>
											</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
							</div>
						</fieldset>
						<div class="form-group">
							<shiro:hasPermission name="gen:genTable:edit"><input id="btnSubmit" class="btn btn-primary"
							                                                     type="button"
							                                                     value="保 存"/>&nbsp;</shiro:hasPermission>
							<input id="btnCancel" class="btn" type="button" value="返 回"
							       onclick="location.href='${ctx}#page/gen/genTable'"/>
						</div>
					</form:form>
				</c:otherwise>
			</c:choose>
			<div id="messageBox"></div>
		</div>
	</div>
</div>
<script type="text/javascript">
	var scripts = [null,
		'${ctxStatic}/assets/js/fuelux/fuelux.spinner.js',
		'${ctxStatic}/bootstrap-treeview/js/bootstrap-treeview.js', null];
	$('.page-content-area').ace_ajax('loadScripts', scripts, function () {
		jQuery(function ($) {
			var grid_selector = "#grid-table";
			var pager_selector = "#grid-pager";

            //选择框
            if (!ace.vars['touch']) {
                $('.chosen-select').chosen({allow_single_deselect: true});
                //resize the chosen on window resize
                $(window).off('resize.chosen').on('resize.chosen',function () {
                    $('.chosen-select').each(function () {
                        var $this = $(this);
                        $this.next().css({'width': $this.parent().width()});
                    })
                }).trigger('resize.chosen');
                //resize chosen on sidebar collapse/expand
                $(document).on('settings.ace.chosen', function (e, event_name, event_val) {
                    if (event_name != 'sidebar_collapsed') return;
                    $('.chosen-select').each(function () {
                        var $this = $(this);
                        $this.next().css({'width': $this.parent().width()});
                    })
                });
            }

			$('input[type=checkbox]').click(function () {
				if ($(this).prop(":checked")) {//如果被选中则加入数组中
					$(this).val("1");
				} else {//否则去除
					$(this).val("1");
				}
			});

			//为jquery.serializeArray()解决radio,checkbox未选中时没有序列化的问题
			$.fn.ghostsf_serialize = function () {
				var a = this.serializeArray();
				var $radio = $('input[type=checkbox]', this);
				var temp = {};
				$.each($radio, function () {
					if (!temp.hasOwnProperty(this.name)) {
						if ($("input[name='" + this.name + "']:checked").length == 0) {
							temp[this.name] = "";
							a.push({name: this.name, value: ""});
						}
					}
				});
				//console.log(a);
				return jQuery.param(a);
			};
			$('#btnSubmit').on('click', function () {
				$("#inputForm").bootstrapValidator('validate');
			});

			$("#inputForm").bootstrapValidator({
				message: "请录入一个有效值",
				excluded: [],
				feedbackIcons: {
					valid: "glyphicon glyphicon-ok",
					invalid: "glyphicon glyphicon-remove",
					validating: "glyphicon glyphicon-refresh"
				},
				fields: {
					name: {validators: {notEmpty: {message: "请填入表名"}}},
					className: {validators: {notEmpty: {message: "请填入类名"}}},
				}
			}).on("success.form.bv", function (e) {
				// Prevent form submission
				e.preventDefault();
				// Get the form instance
				var $form = $(e.target);
				//alert("1111")
				//// Get the BootstrapValidator instance
				//var bv = $form.data("bootstrapValidator");

				// Use Ajax to submit form data
				$.post($form.attr("action"), $form.ghostsf_serialize(), function (result) {
					if (result.messageStatus == "1") {
						$.msg_show.Init({
							'msg': result.message,
							'type': 'success'
						});

					} else if (result.messageStatus == "0") {
						$.msg_show.Init({
							'msg': result.message,
							'type': 'error'
						});
					}
				}, "json");
			});


			$("#btnNext").click(function () {
				var param = {
					'name': $("#name").val()
				};
				nextStep(param);
			});

			function nextStep(param) {
				$.get("${ctx}/gen/genTable/form", param, function (data, textStatus, object) {
					$("#genTableDivId").html("");
					$("#genTableDivId").html(object.responseText);
				})
			}

			$(document).one('ajaxloadstart.page', function (e) {
				$('.ui-jqdialog').remove();
			});
		});
	});
</script>

