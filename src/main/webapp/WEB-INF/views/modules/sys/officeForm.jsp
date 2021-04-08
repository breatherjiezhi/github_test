<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div class="widget-body" id="officeDivId">
	<div class="widget-main" >
	<form:form id="inputForm" modelAttribute="office" action="${ctx}/sys/office/doSave" method="post" class="form-horizontal">
		<div class="form-group">			
			<label class="control-label col-xs-12 col-sm-2 no-padding" for="name" data-locale="OrganizationName">机构名称</label>
			<div class="col-xs-12 col-sm-4">
				<div class="clearfix">				
					<form:input path="name" htmlEscape="false" maxlength="50" class="form-control width-100" placeholder="请输入机构名称" />
				</div>
			</div>			
			<label class="control-label col-xs-12 col-sm-2 no-padding" for="code" data-locale="OrganizationCode">机构编码</label>
			<div class="col-xs-12 col-sm-4">
				<div class="clearfix">				
					<form:input path="code" htmlEscape="false" maxlength="50" class="form-control width-100" placeholder="请输入机构编码" />
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-2 no-padding" for="parent.name" data-locale="Superiororganization">上级机构</label>
			<div class="col-xs-12 col-sm-4">
				<div class="clearfix check-out input-group">
					<input readonly="" type="text" id="parent.name" name="parent.name" value="${office.parent.name == null ?'根节点':office.parent.name}" class="form-control"/>
					<span class="input-group-btn">
						<button type="button" id="selectParentoffice" class="btn btn-purple btn-sm ${office.parent.id == null ?'disabled':''}">
							<span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
							<span data-locale="Choose">选择</span>
						</button>
					</span>					
					<input type="hidden" id="parent.id" name="parent.id" value="${office.parent.id == null ?'0':office.parent.id}"/>
				</div>
			</div>
			<label class="control-label col-xs-12 col-sm-2 no-padding" for="area.name" data-locale="OrganizationArea">归属区域</label>
			<div class="col-xs-12 col-sm-4">
				<div class="clearfix check-out input-group">
					<input readonly="" type="text" id="area.name" name="area.name" value="${office.area.name}" class="form-control"/>
					<span class="input-group-btn">
						<button type="button" id="selectArea" class="btn btn-purple btn-sm">
							<span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
							<span data-locale="Choose">选择</span>
						</button>
					</span>					
					<input type="hidden" id="area.id" name="area.id" value="${office.area.id}"/>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-2 no-padding" for="type" data-locale="OrganizationType">机构类型</label>
			<div class="col-xs-12 col-sm-4">
			<c:choose>
	  			<c:when test="${office.type == 1}">
					<input type="text" id="officetype" readonly="" class="form-control" name="officetype" value="${fns:getDictLabel(office.type, 'sys_office_type', '')}"/>
					<input type="hidden" id="type" class="form-control" name="type" value="${office.type}"/>
	  			</c:when>
	  			<c:otherwise>
		  			<form:select  path="type" class="chosen-select form-control width-100" data-placeholder="点击选择...">
						<form:options items="${fns:getDictList('sys_office_type')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</c:otherwise>
	  		</c:choose>
				
			</div>
			<label class="control-label col-xs-12 col-sm-2 no-padding" for="grade" data-locale="OrganizationLevel">机构级别</label>
			<div class="col-xs-12 col-sm-4">
				<form:select  path="grade" class="chosen-select form-control width-100" data-placeholder="点击选择...">
					<form:options items="${fns:getDictList('sys_office_grade')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-2 no-padding" for="useable" data-locale="AvailableOrNot">是否可用</label>
			<div class="col-xs-12 col-sm-4">
				<form:select  path="useable" class="chosen-select form-control width-100" data-placeholder="点击选择...">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
			<label class="control-label col-xs-12 col-sm-2 no-padding" for="company.id" data-locale="OrganizationCompany">所属公司</label>
			<div class="col-xs-12 col-sm-4">
				<form:select  path="company.id" class="chosen-select form-control width-100" data-placeholder="点击选择...">
					<form:options items="${companys}" itemLabel="name" itemValue="id" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-2 no-padding" for="remarks" data-locale="Notes">备注</label>
			<div class="col-xs-12 col-sm-10">
				<div class="clearfix">
					<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="form-control width-100" placeholder="请输入备注信息" />
				</div>	
			</div>
		</div>
		<form:hidden path="id"/>		
	</form:form>
	</div>
</div>
<div id="selectParentofficeDiv" class="hide widget-body">
	<div class="widget-main padding-8">
		<div id="treeview" class="" data-id="" data-text=""></div>
	</div>
</div>
<div id="selectAreaDiv" class="hide widget-body">
	<div class="widget-main padding-8">
		<div id="areatreeview" class="" data-id="" data-text=""></div>
	</div>
</div>