<%
response.setStatus(403);

//获取异常类
Throwable ex = Exceptions.getThrowable(request);

// 如果是异步请求或是手机端，则直接返回信息
if (Servlets.isAjaxRequest(request)) {
	if (ex!=null && StringUtils.startsWith(ex.getMessage(), "msg:")){
		out.print(StringUtils.replace(ex.getMessage(), "msg:", ""));
	}else{
		out.print("操作权限不足.");
	}
}

//输出异常信息页面
else {
	if(request.getHeader("html")!=null &&request.getHeader("html").equals("true")){
		response.setStatus(200);
	}
%>
<%@page import="com.dhc.rad.common.web.Servlets"%>
<%@page import="com.dhc.rad.common.utils.Exceptions"%>
<%@page import="com.dhc.rad.common.utils.StringUtils"%>
<%@page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@include file="/WEB-INF/views/include/taglib.jsp"%>
	<title>403 - 操作权限不足</title>
	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class="page-header"><h1>操作权限不足.</h1></div>
			<%
				if (ex!=null && StringUtils.startsWith(ex.getMessage(), "msg:")){
					out.print("<div>"+StringUtils.replace(ex.getMessage(), "msg:", "")+" <br/> <br/></div>");
				}
			%>
			<div><a href="javascript:" onclick="history.go(-1);" class="btn">返回上一页</a></div>
			<script>try{top.$.jBox.closeTip();}catch(e){}</script>
		</div>
	</div>
	<!-- page specific plugin scripts -->
	<script type="text/javascript">
		var scripts = [null, null]
		$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
		  //inline scripts related to this page
		});
	</script>
<%
} out = pageContext.pushBody();
%>