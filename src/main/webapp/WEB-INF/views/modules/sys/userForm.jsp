<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div class="widget-body" id="userDivId">
	<div class="widget-main" > 
		<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/doSave" method="post" class="form-horizontal">
			<div class="form-group">			
				<label class="control-label col-xs-12 col-sm-1 no-padding" for="name" data-locale="name">姓名</label>
				<div class="col-xs-12 col-sm-5">
					<div class="clearfix">				
						<form:input path="name" htmlEscape="false" maxlength="50" class="form-control width-100" placeholder="请输入姓名" />
					</div>
				</div>			
				<label class="control-label col-xs-12 col-sm-1 no-padding" for="loginName" data-locale="loginName">登录名</label>
				<div class="col-xs-12 col-sm-5">
					<div class="clearfix">				
						<form:input path="loginName" htmlEscape="false" maxlength="50" class="form-control width-100" placeholder="请输入登录名" />
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-1 no-padding" for="no" data-locale="No">工号</label>
				<div class="col-xs-12 col-sm-5">
					<div class="clearfix">				
						<form:input path="no" htmlEscape="false" maxlength="50" class="form-control width-100" placeholder="请输入工号" />
					</div>
				</div>
				
				<%--<label class="control-label col-xs-12 col-sm-1 no-padding" for="no">卡号:</label>--%>
				<%--<div class="col-xs-12 col-sm-5">--%>
					<%--<div class="clearfix">				--%>
						<%--<form:input path="cardID" htmlEscape="false" maxlength="50" class="form-control width-100" placeholder="请输入卡号" />--%>
					<%--</div>--%>
				<%--</div>--%>
				
							
			</div>
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-1 no-padding" for="office.name" data-locale="officeName">机构</label>
				<div class="col-xs-12 col-sm-5">
					<div class="clearfix check-out input-group">
						<input readonly="" type="text" id="office.name" name="office.name" value="${user.office.name}" class="form-control"/>
						<span class="input-group-btn">
							<button type="button" id="selectOfficeMenu" class="btn btn-purple btn-sm">
								<span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
								<span data-locale="Choose">选择</span>
							</button>
						</span>					
						<input type="hidden" id="office.id" name="office.id" value="${user.office.id}"/>
					</div>
				</div>
				<label class="control-label col-xs-12 col-sm-1 no-padding" for="email" data-locale="Enmail">邮箱</label>
				<div class="col-xs-12 col-sm-5">
					<div class="clearfix">
						<form:input path="email" htmlEscape="false" maxlength="100" class="form-control width-100" placeholder="请输入邮箱" />
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-1 no-padding" for="phone" hidden="hidden" data-locale="TEL">办公电话</label>
				<div class="col-xs-12 col-sm-5" hidden="hidden">
					<div class="clearfix">
						<form:input path="phone" htmlEscape="false" maxlength="50" class="form-control width-100" placeholder="请输入办公电话"/> 
					</div>
				</div>
				<label class="control-label col-xs-12 col-sm-1 no-padding" for="mobile" data-locale="mobile">手机</label>
				<div class="col-xs-12 col-sm-5">
					<div class="clearfix">
						<form:input path="mobile" htmlEscape="false" maxlength="50" class="form-control width-100" placeholder="请输入手机号码"/> 
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-1 no-padding" for="userType" data-locale="userType">用户类型</label>
				<div class="col-xs-12 col-sm-5">
					<form:select  path="userType" class="chosen-select form-control width-100" data-placeholder="点击选择...">
						<form:options items="${fns:getDictList('sys_user_type')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</div>
				
				<label class="control-label col-xs-12 col-sm-1 no-padding" for="loginFlag" data-locale="loginFlag">允许登录</label>
				<div class="col-xs-12 col-sm-5">
					<div class="clearfix">
						<label>
							<input id="loginFlag" name="loginFlag" ${user.loginFlag=='1'?'checked':''} class="ace ace-switch ace-switch-5 btn-flat" type="checkbox" />
							<span class="lbl"></span>
						</label>
						<span class="help-inline" data-locale='Allowusertologinornot'>该用户是否允许登录系统</span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-1 no-padding" for="userType" data-locale="roleNames">角色</label>
				<div class="col-xs-12 col-sm-11">
						<form:select  path="roleIdList" multiple="multiple"  class="select2 tag-input-style width-100" data-placeholder="点击选择...">
							<form:options items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" />
						</form:select>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-1 no-padding" for="remarks" data-locale="Notes">备注</label>
				<div class="col-xs-12 col-sm-11">
					<div class="clearfix">
						<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="form-control width-100" placeholder="请输入备注信息" />
					</div>	
				</div>
			</div>			
			<form:hidden path="id"/>
		</form:form>
 	</div>
</div>
<div id="selectOfficeTreeDiv" class="hide widget-body">
	<div class="widget-main padding-8">
		<div id="popuptreeview" class="" data-id="" data-text=""></div>
	</div>
</div>