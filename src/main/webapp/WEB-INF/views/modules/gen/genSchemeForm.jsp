<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<div class="widget-body" id="userDivId">
	<div class="widget-main">
		<form:form id="inputForm" modelAttribute="genScheme" action="${ctx}/gen/genScheme/save" method="post" class="form-horizontal">
			<form:hidden path="id"/><form:hidden path="flag"/>
			<%--<sys:message content="${message}"/>--%>
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-2 no-padding" for="name">方案名称:</label>
				<div class="col-xs-12 col-sm-10">
					<div class="clearfix col-xs-12 col-sm-5">
						<form:input path="name" htmlEscape="false" maxlength="200" class="required"/>
						<span class="help-inline"></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-2 no-padding" for="category">模板分类:</label>
				<div class="col-xs-12 col-sm-10">
					<div class="clearfix col-xs-12 col-sm-5">
						<form:select path="category" class="chosen-select required input-xlarge">
							<form:options items="${config.categoryList}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
					<div class="col-xs-12 col-sm-7">
						<span class="help-inline">
							生成结构：{包名}/{模块名}/{分层(dao,entity,service,web)}/{子模块名}/{java类}
						</span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-2 no-padding" for="packageName">生成包路径:</label>
				<div class="col-xs-12 col-sm-10">
					<div class="clearfix col-xs-12 col-sm-5">
						<form:input path="packageName" value="com.dhc.rad.modules" htmlEscape="false" maxlength="500" class="required input-xlarge"/>
					</div>
					<div class="col-xs-12 col-sm-7">
						<span class="help-inline">建议模块包：com.dhc.rad.modules</span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-2 no-padding" for="moduleName">生成模块名:</label>
				<div class="col-xs-12 col-sm-10">
					<div class="clearfix col-xs-12 col-sm-5">
						<form:input path="moduleName" htmlEscape="false" maxlength="500" class="required input-xlarge"/>
					</div>
					<div class="col-xs-12 col-sm-7">
						<span class="help-inline">可理解为子系统名，例如 sys</span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-2 no-padding" for="subModuleName">生成子模块名:</label>
				<div class="col-xs-12 col-sm-10">
					<div class="clearfix col-xs-12 col-sm-5">
						<form:input path="subModuleName" htmlEscape="false" maxlength="500" class="input-xlarge"/>
					</div>
					<div class="col-xs-12 col-sm-7">
						<span class="help-inline">可选，分层下的文件夹，例如 </span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-2 no-padding" for="functionName">生成功能描述:</label>
				<div class="col-xs-12 col-sm-10">
					<div class="clearfix col-xs-12 col-sm-5">
						<form:input path="functionName" htmlEscape="false" maxlength="500" class="required input-xlarge"/>
					</div>
					<div class="col-xs-12 col-sm-7">
						<span class="help-inline">将设置到类描述</span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-2 no-padding" for="functionNameSimple">生成功能名:</label>
				<div class="col-xs-12 col-sm-10">
					<div class="clearfix col-xs-12 col-sm-5">
						<form:input path="functionNameSimple" htmlEscape="false" maxlength="500" class="required input-xlarge"/>
					</div>
					<div class="col-xs-12 col-sm-7">
						<span class="help-inline">用作功能提示，如：保存“某某”成功</span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-2 no-padding" for="functionAuthor">生成功能作者:</label>
				<div class="col-xs-12 col-sm-10">
					<div class="clearfix col-xs-12 col-sm-5">
						<form:input path="functionAuthor" htmlEscape="false" maxlength="500" class="required input-xlarge"/>
					</div>
					<div class="col-xs-12 col-sm-7">
						<span class="help-inline">功能开发者</span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-2 no-padding" for="genTable.id">业务表名:</label>
				<div class="col-xs-12 col-sm-10">
					<div class="clearfix col-xs-12 col-sm-5">
						<form:select path="genTable.id" class="chosen-select required input-xlarge">
							<form:options items="${tableList}" itemLabel="nameAndComments" itemValue="id" htmlEscape="false"/>
						</form:select>
					</div>
					<div class="col-xs-12 col-sm-7">
						<span class="help-inline">生成的数据表，一对多情况下请选择主表。</span>
					</div>
				</div>
			</div>
			<div class="form-group hide">
				<label class="control-label col-xs-12 col-sm-2 no-padding" for="remarks">备注:</label>
				<div class="col-xs-12 col-sm-10">
					<div class="clearfix col-xs-12 col-sm-5">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-2 no-padding" for="replaceFile">生成选项:</label>
				<div class="col-xs-12 col-sm-10">
					<div class="clearfix col-xs-12 col-sm-5">
						<form:checkbox path="replaceFile" label="是否替换现有文件"/>
					</div>
					<div class="clearfix col-xs-12 col-sm-5">
						<form:checkbox path="codeFlag" label="是否生成代码"/>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>

