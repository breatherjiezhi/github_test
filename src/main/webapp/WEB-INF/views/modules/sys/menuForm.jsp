<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div class="widget-body" id="menuDivId">
	<div class="widget-main" >
	<form:form id="inputForm" modelAttribute="menu" action="${ctx}/sys/menu/doSave" method="post" class="form-horizontal">
		<div class="form-group">			
			<label class="control-label col-xs-12 col-sm-3 no-padding" for="name" data-locale="MenuName">名称:</label>
			<div class="col-xs-12 col-sm-9">
				<div class="clearfix">				
					<form:input path="name" htmlEscape="false" maxlength="50" class="form-control width-100" placeholder="请输入菜单名称" />
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-3 no-padding" for="signs" data-locale="EnglishName">英文名称</label>
			<div class="col-xs-12 col-sm-9">
				<div class="clearfix">
					<form:input path="signs" htmlEscape="false" maxlength="50" class="form-control width-100" placeholder="请输入翻译名称" />
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-3 no-padding" for="parent.name" data-locale="Superiprmenu">上级菜单</label>
			<div class="col-xs-12 col-sm-9">
				<div class="clearfix check-out input-group">
					<input readonly="" type="text" id="parent.name" name="parent.name" value="${menu.parent.name}" class="form-control"/>
					<span class="input-group-btn">
						<button type="button" id="selectParentMenu" class="btn btn-purple btn-sm">
							<span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
							<span data-locale='Choose'>选择</span>

						</button>
					</span>					
					<input type="hidden" id="parent.id" name="parent.id" value="${menu.parent.id}"/>
				</div>
			</div>			
		</div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-3 no-padding" for="href" data-locale="Link">链接</label>
			<div class="col-xs-12 col-sm-9">
				<div class="clearfix">
					<form:input path="href" htmlEscape="false" maxlength="2000" class="form-control width-100" placeholder="请输入链接地址(点击菜单跳转的页面)" />
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-3 no-padding" for="icon" data-locale="Icon">图标</label>
			<div class="col-xs-12 col-sm-9">
				<div class="clearfix">
					<form:input path="icon" htmlEscape="false" maxlength="50" class="form-control width-100" placeholder="请输入图标名称"/> 
				</div>
			</div>
		</div>
		<div class="form-group">			
			<label class="control-label col-xs-12 col-sm-3 no-padding" for="remarks" data-locale="SequenceNumber">排序</label>
			<div class="col-xs-12 col-sm-9">
				<div class="clearfix">
				<form:input path="sort" htmlEscape="false" maxlength="11" class="form-control width-100"/> 
				</div>	
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-3 no-padding" for="isShow" data-locale="Show/Hide">是否可见</label>
			<div class="col-xs-12 col-sm-9">
				<div class="clearfix">
					<label>
						<input id="isShow" name="isShow" ${menu.isShow=='1'?'checked':''} class="ace ace-switch ace-switch-4 btn-flat" type="checkbox" />
						<span class="lbl"></span>
					</label>
					<br>
					<span class="help-inline" data-locale="isShowToSysMenu">该菜单或操作是否显示到系统菜单中</span>
					<%-- <form:input path="icon" htmlEscape="false" maxlength="50" class="form-control width-100" placeholder="请输入图标名称"/>  --%>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-3 no-padding" for="isMobile" data-locale="Cellphonemenu">手机菜单</label>
			<div class="col-xs-12 col-sm-3">
				<div class="clearfix">
					<label>
						<input id="isMobile" name="isMobile" ${menu.isMobile=='1'?'checked':''} class="ace ace-switch ace-switch-4 btn-flat" type="checkbox" />
						<span class="lbl"></span>
					</label>
					
				</div>
			</div>
			
			<label class="control-label col-xs-12 col-sm-3 no-padding" for="isPad" data-locale="Padmenu">平板菜单</label>
			<div class="col-xs-12 col-sm-3">
				<div class="clearfix">
					<label>
						<input id="isPad" name="isPad" ${menu.isPad=='1'?'checked':''} class="ace ace-switch ace-switch-4 btn-flat" type="checkbox" />
						<span class="lbl"></span>
					</label>
					
				</div>
			</div>
			
		</div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-3 no-padding" for="permission" data-locale="Permissionsidentification">权限标识</label>
			<div class="col-xs-12 col-sm-9">
				<div class="clearfix">
				<form:input path="permission" htmlEscape="false" maxlength="200" class="form-control width-100"/> 
				</div>
				<span class="help-inline" data-locale="AuthorityManagementExample">控制器中定义的权限标识，如：@RequiresPermissions("权限标识")</span>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-xs-12 col-sm-3 no-padding" for="remarks" data-locale="Notes">备注</label>
			<div class="col-xs-12 col-sm-9">
				<div class="clearfix">
					<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="form-control width-100" placeholder="请输入备注信息" />
				</div>	
			</div>
		</div>
		<form:hidden path="id"/>
	</form:form>
	</div>
</div>
<div id="selectParentMenuDiv" class="hide widget-body">
	<div class="widget-main padding-8">
		<div id="treeview" class="" data-id="" data-text=""></div>
	</div>
</div>