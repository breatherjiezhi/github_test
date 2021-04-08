<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考勤信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/kaoqin/aomKaoqin/">考勤信息列表</a></li>
		<shiro:hasPermission name="kaoqin:aomKaoqin:edit"><li><a href="${ctx}/kaoqin/aomKaoqin/form">考勤信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="aomKaoqin" action="${ctx}/kaoqin/aomKaoqin/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>部门：</label>
				<form:input path="branch" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label>考勤员：</label>
				<form:input path="kepperby" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label>年：</label>
				<form:input path="year" htmlEscape="false" maxlength="4" class="input-medium"/>
			</li>
			<li><label>月：</label>
				<form:input path="month" htmlEscape="false" maxlength="2" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>部门</th>
				<th>考勤员</th>
				<th>年</th>
				<th>月</th>
				<shiro:hasPermission name="kaoqin:aomKaoqin:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="aomKaoqin">
			<tr>
				<td><a href="${ctx}/kaoqin/aomKaoqin/form?id=${aomKaoqin.id}">
					${aomKaoqin.branch}
				</a></td>
				<td>
					${aomKaoqin.kepperby}
				</td>
				<td>
					${aomKaoqin.year}
				</td>
				<td>
					${aomKaoqin.month}
				</td>
				<shiro:hasPermission name="kaoqin:aomKaoqin:edit"><td>
    				<a href="${ctx}/kaoqin/aomKaoqin/form?id=${aomKaoqin.id}">修改</a>
					<a href="${ctx}/kaoqin/aomKaoqin/delete?id=${aomKaoqin.id}" onclick="return confirmx('确认要删除该考勤信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>