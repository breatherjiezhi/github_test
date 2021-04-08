<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<title>修改密码</title>

<!-- <div class="page-header">
	<h1>
		用户管理
		<small>
			<i class="ace-icon fa fa-angle-double-right"></i>
			修改密码
		</small>
	</h1>
</div> -->
						
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<form:form id="inputForm" modelAttribute="user"	action="${ctx}/sys/user/doModifyPwd" method="post" class="form-horizontal" novalidate="novalidate">
			<!-- <fieldset>	 -->
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-4 no-padding" for="oldPassword" data-locale="OldPassword">旧密码</label>
				<div class="col-xs-12 col-sm-7">
					<div class="clearfix">
						<span class="block input-icon">
							<input type="password" name="oldPassword" id="oldPassword" class="width-100 form-control" />
							<i class="ace-icon fa fa-lock blue"></i>
						</span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-4 no-padding" for="newPassword" data-locale="NewPassword">新密码</label>
				<div class="col-xs-12 col-sm-7">
					<div class="clearfix">
					<span class="block input-icon">
						<input type="password" name="newPassword" id="newPassword" class="width-100 form-control"/>
						<i class="ace-icon fa fa-lock blue"></i>
					</span>
					</div>
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-xs-12 col-sm-4 no-padding" for="confirmNewPassword" data-locale="ConfirmNewPassword">确认新密码</label>
				<div class="col-xs-12 col-sm-7">
					<div class="clearfix">
						<span class="block input-icon">
							<input type="password" name="confirmNewPassword" id="confirmNewPassword" class="width-100 form-control" />
							<i class="ace-icon fa fa-lock blue"></i>
						</span>
					</div>
				</div>
			</div>
			<form:hidden path="id" />
			<sys:message content="${message}" />
		</form:form>
	</div>
</div>


	<script type="text/javascript">
	var scripts = [null, null];
	$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		jQuery(function($){
            var qCode = sessionStorage.getItem("qCode");
            typeChange(qCode);
		});
	});
	</script>
